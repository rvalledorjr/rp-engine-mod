package com.develmode.rpenginemod.nametags;

import com.develmode.rpenginemod.Engine;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class Utils {
  private Engine plugin;

  public Utils(Engine plugin) {
    this.plugin = plugin;
  }

  public static String format(String[] text, int to, int from) {
    return StringUtils.join((Object[]) text, ' ', to, from).replace("'", "");
  }

  public static String deformat(String input) {
    return input.replace("§", "&");
  }

  public static String format(String input) {
    return format(input, false);
  }

  public static String format(String input, boolean limitChars) {
    String colored = ChatColor.translateAlternateColorCodes('&', input);
    return (limitChars && colored.length() > 16) ? colored.substring(0, 16) : colored;
  }

  public static List<Player> getOnline() {
    List<Player> list = new ArrayList<>();
    for (World world : Bukkit.getWorlds())
      list.addAll(world.getPlayers());
    return Collections.unmodifiableList(list);
  }

  public static YamlConfiguration getConfig(File file) {
    try {
      if (!file.exists())
        file.createNewFile();
    } catch (IOException e) {
      if (Engine.utils.sendDebug())
        e.printStackTrace();
    }
    return YamlConfiguration.loadConfiguration(file);
  }

  public boolean sendDebug() {
    if (this.plugin.getConfig().contains("debug"))
      return this.plugin.getConfig().getBoolean("debug");
    return false;
  }

  public static YamlConfiguration getConfig(File file, String resource, Plugin plugin) {
    try {
      if (!file.exists()) {
        file.createNewFile();
        InputStream inputStream = plugin.getResource(resource);
        OutputStream outputStream = new FileOutputStream(file);
        byte[] buffer = new byte[1024];
        int bytesRead;
        while ((bytesRead = inputStream.read(buffer)) != -1)
          outputStream.write(buffer, 0, bytesRead);
        inputStream.close();
        outputStream.flush();
        outputStream.close();
      }
    } catch (IOException e) {
      if (Engine.utils.sendDebug())
        e.printStackTrace();
    }
    return YamlConfiguration.loadConfiguration(file);
  }
}

/*
 * Location:
 * C:\Users\develmode\dev\msp-basic-chat\server\plugins\RPEngine-4.5.3.jar!\com\
 * Alvaeron\nametags\Utils.class Java compiler version: 8 (52.0) JD-Core
 * Version: 1.1.3
 */