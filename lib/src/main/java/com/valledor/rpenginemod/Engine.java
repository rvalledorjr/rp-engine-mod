package com.valledor.rpenginemod;

import com.valledor.rpenginemod.commands.BirdCommand;
import com.valledor.rpenginemod.commands.CardCommand;
import com.valledor.rpenginemod.commands.ChatCommands;
import com.valledor.rpenginemod.commands.CountdownCommand;
import com.valledor.rpenginemod.commands.RPEngineCommand;
import com.valledor.rpenginemod.commands.RollCommand;
import com.valledor.rpenginemod.commands.SpawnPointCommand;
import com.valledor.rpenginemod.listeners.EventListener;
import com.valledor.rpenginemod.nametags.NametagManager;
import com.valledor.rpenginemod.nametags.Utils;
import com.valledor.rpenginemod.player.PlayerManager;
import com.valledor.rpenginemod.utils.Card;
import com.valledor.rpenginemod.utils.Lang;
import com.valledor.rpenginemod.utils.MessageUtil;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.logging.Level;
import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public class Engine extends JavaPlugin {
  public static MessageUtil mu = null;

  public static MySQLManager mm = null;

  public static EventListener listener = null;

  public static PlayerManager manager = null;

  public static Card card = null;

  public static Engine rpEngine = null;

  public static NametagManager nametags = null;

  public static Utils utils = null;

  public static YamlConfiguration LANG;

  public static File LANG_FILE;

  public static Economy econ = null;

  public static Permission perms = null;

  public static Chat chat = null;

  public Boolean vault = Boolean.valueOf(false);

  public void onDisable() {
    mm.onDisable();
  }

  public void onEnable() {
    mu = new MessageUtil(this);
    mm = new MySQLManager(this);
    listener = new EventListener(this);
    manager = new PlayerManager(this);
    card = new Card(this);
    rpEngine = this;
    nametags = new NametagManager(this);
    utils = new Utils(this);
    getServer().getPluginManager().registerEvents((Listener) manager, (Plugin) this);
    getServer().getPluginManager().registerEvents((Listener) listener, (Plugin) this);
    loadCommands();
    saveDefaultConfig();
    mm.OnEnable();
    mm.initDatabase();
    for (Player pl : Bukkit.getOnlinePlayers())
      mm.createRoleplayPlayer(pl);
    checkSoftDependencies();
    loadLang();
  }

  private void loadCommands() {
    getCommand("card").setExecutor((CommandExecutor) new CardCommand(this));
    getCommand("roll").setExecutor((CommandExecutor) new RollCommand(this));
    getCommand("bird").setExecutor((CommandExecutor) new BirdCommand(this));
    getCommand("countdown").setExecutor((CommandExecutor) new CountdownCommand(this));
    getCommand("rpengine").setExecutor((CommandExecutor) new RPEngineCommand(this));
    getCommand("spawnpoint").setExecutor((CommandExecutor) new SpawnPointCommand(this));
    ChatCommands ch = new ChatCommands(this);
    getCommand("whisper").setExecutor((CommandExecutor) ch);
    getCommand("shout").setExecutor((CommandExecutor) ch);
    getCommand("RP").setExecutor((CommandExecutor) ch);
    getCommand("OOC").setExecutor((CommandExecutor) ch);
    getCommand("toggleooc").setExecutor((CommandExecutor) ch);
  }

  private boolean setupEconomy() {
    if (getServer().getPluginManager().getPlugin("Vault") == null)
      return false;
    RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
    if (rsp == null)
      return false;
    econ = (Economy) rsp.getProvider();
    return (econ != null);
  }

  private boolean setupChat() {
    RegisteredServiceProvider<Chat> rsp = getServer().getServicesManager().getRegistration(Chat.class);
    chat = (Chat) rsp.getProvider();
    return (chat != null);
  }

  private boolean setupPermissions() {
    RegisteredServiceProvider<Permission> rsp = getServer().getServicesManager().getRegistration(Permission.class);
    perms = (Permission) rsp.getProvider();
    return (perms != null);
  }

  public void loadLang() {
    File lang = new File(getDataFolder(), getConfig().getString("language") + ".yml");
    OutputStream out = null;
    InputStream defLangStream = getResource(getConfig().getString("language"));
    if (!lang.exists()) {
      lang = new File(getDataFolder(), "en_us.yml");
      defLangStream = getResource("en_us.yml");
      if (!lang.exists()) {
        try {
          getDataFolder().mkdir();
          lang.createNewFile();
          if (defLangStream != null) {
            out = new FileOutputStream(lang);
            byte[] bytes = new byte[1024];
            int read;
            while ((read = defLangStream.read(bytes)) != -1)
              out.write(bytes, 0, read);
            YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(lang);
            Lang.setFile(defConfig);
          }
        } catch (IOException e) {
          if (utils.sendDebug())
            e.printStackTrace();
          getLogger().severe("[RPEngine] Couldn't create language file.");
          getLogger().severe("[RPEngine] This is a fatal error. Now disabling");
          setEnabled(false);
        } finally {
          if (defLangStream != null)
            try {
              defLangStream.close();
            } catch (IOException e) {
              if (utils.sendDebug())
                e.printStackTrace();
            }
        }
        if (out != null)
          try {
            out.close();
          } catch (IOException e) {
            if (utils.sendDebug())
              e.printStackTrace();
          }
      }
    }
    YamlConfiguration conf = YamlConfiguration.loadConfiguration(lang);
    for (Lang item : Lang.values()) {
      if (conf.getString(item.getPath()) == null)
        conf.set(item.getPath(), item.getDefault());
    }
    Lang.setFile(conf);
    try {
      conf.save(lang);
    } catch (IOException e) {
      getLogger().log(Level.WARNING, "RPEngine: Failed to save lang.yml.");
      getLogger().log(Level.WARNING, "RPEngine: Report this stack trace to a tech.");
      if (utils.sendDebug())
        e.printStackTrace();
    }
  }

  public void debug(String message) {
    if (getConfig().getBoolean("debug"))
      getLogger().info("[DEBUG] " + message);
  }

  private void checkSoftDependencies() {
    if (!setupEconomy()) {
      getLogger().log(Level.WARNING, "Vault not found.");
      this.vault = Boolean.valueOf(false);
    } else {
      setupChat();
      setupPermissions();
      this.vault = Boolean.valueOf(true);
    }
  }

  public void disablePlugin() {
    setEnabled(false);
  }
}

/*
 * Location:
 * C:\Users\develmode\dev\msp-basic-chat\server\plugins\RPEngine-4.5.3.jar!\com\
 * Alvaeron\Engine.class Java compiler version: 8 (52.0) JD-Core Version: 1.1.3
 */