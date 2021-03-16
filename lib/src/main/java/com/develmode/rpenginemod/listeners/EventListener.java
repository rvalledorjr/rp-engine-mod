package com.develmode.rpenginemod.listeners;

import com.develmode.rpenginemod.Engine;
import com.develmode.rpenginemod.player.RoleplayPlayer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerJoinEvent;

public class EventListener implements Listener {
  private Engine plugin;

  public boolean chatEnabled = true;

  public EventListener(Engine plugin) {
    this.plugin = plugin;
    this.chatEnabled = plugin.getConfig().getBoolean("chatEnabled", true);
  }

  @EventHandler
  public void onOOCChat(AsyncPlayerChatEvent event) {
    if (event.isCancelled())
      return;
    if (!this.chatEnabled)
      return;
    Player player = event.getPlayer();
    String message = event.getMessage();
    String format = event.getFormat();
    RoleplayPlayer rpp = Engine.manager.getPlayer(player.getUniqueId());
    if (rpp.getChannel() == RoleplayPlayer.Channel.OOC) {
      if (this.plugin.vault.booleanValue()) {
        if (Engine.chat.getPlayerPrefix(player) != null) {
          if (!Engine.chat.getPlayerPrefix(player).equals("")) {
            format = ChatColor.translateAlternateColorCodes('&', "[OOC] " + Engine.chat.getPlayerPrefix(player) + " "
                + player.getDisplayName() + ChatColor.WHITE + Engine.chat.getPlayerSuffix(player) + ": %2$s");
          } else {
            format = "[OOC] " + player.getDisplayName() + ChatColor.WHITE + ": %2$s";
          }
        } else {
          format = "[OOC] " + player.getDisplayName() + ChatColor.WHITE + ": %2$s";
        }
      } else {
        format = "[OOC] " + player.getDisplayName() + ChatColor.WHITE + ": %2$s";
      }
      if (player.hasPermission("rpengine.chat.color"))
        message = ChatColor.translateAlternateColorCodes('&', message);
      for (Player p : Bukkit.getOnlinePlayers()) {
        if (!Engine.manager.getPlayer(p.getUniqueId()).isOOC())
          event.getRecipients().remove(p);
      }
      event.setMessage(message);
      event.setFormat(format);
    }
  }

  @EventHandler(priority = EventPriority.HIGHEST)
  public void onRPChat(AsyncPlayerChatEvent event) {
    if (event.isCancelled())
      return;
    if (!this.chatEnabled)
      return;
    Player player = event.getPlayer();
    String message = event.getMessage();
    String format = event.getFormat();
    RoleplayPlayer rpp = Engine.manager.getPlayer(player.getUniqueId());
    String race = rpp.getRace();
    if (rpp.getChannel() == RoleplayPlayer.Channel.RP) {
      if (message.startsWith("*")) {
        message = Engine.mu.replaceIfEven(message, "\"", "\"" + ChatColor.YELLOW);
        message = Engine.mu.replaceIfOdd(message, "\"", ChatColor.WHITE + "\"");
        message = message.substring(1);
        format = ChatColor.YELLOW + rpp.getName() + " %2$s";
        if (this.plugin.getConfig().getBoolean("logRP"))
          player.getServer().getConsoleSender().sendMessage(ChatColor.GRAY + "[RP] *" + rpp.getName() + " " + message);
      } else if (message.length() > 2 && message.substring(0, 2).equalsIgnoreCase("((")) {
        format = ChatColor.GRAY + rpp.getName() + ": %2$s";
        message = ChatColor.GRAY + message;
        if (this.plugin.getConfig().getBoolean("logRP"))
          player.getServer().getConsoleSender()
              .sendMessage(ChatColor.GRAY + "[RP] " + ChatColor.GRAY + rpp.getName() + ": " + message);
      } else {
        format = Engine.mu.getRaceColour(race) + rpp.getName() + ChatColor.WHITE + ": %2$s";
        message = ChatColor.WHITE + message;
        if (this.plugin.getConfig().getBoolean("logRP"))
          player.getServer().getConsoleSender().sendMessage(
              ChatColor.GRAY + "[RP] " + ChatColor.GRAY + rpp.getName() + ChatColor.WHITE + ": " + message);
      }
      for (Player p : Bukkit.getOnlinePlayers()) {
        if (player.getWorld() != p.getWorld()
            || p.getLocation().distance(player.getLocation()) >= this.plugin.getConfig().getInt("rpRange"))
          event.getRecipients().remove(p);
      }
      event.setMessage(message);
      event.setFormat(format);
    }
  }

  @EventHandler
  public void shiftRightClick(PlayerInteractEntityEvent event) {
    if (event.getRightClicked() instanceof Player && !event.getRightClicked().hasMetadata("NPC")) {
      Player player = (Player) event.getRightClicked();
      Player reciever = event.getPlayer();
      RoleplayPlayer rpp = Engine.manager.getPlayer(player.getUniqueId());
      if (reciever.isSneaking())
        Engine.card.sendCardOther(rpp, reciever);
    }
  }

  @EventHandler
  public void onJoin(PlayerJoinEvent event) {
    long maxHealth = 20L;
    if (this.plugin.getConfig().contains("playerHealth"))
      maxHealth = this.plugin.getConfig().getLong("playerHealth");
    event.getPlayer().setMaxHealth(maxHealth);
  }
}

/*
 * Location:
 * C:\Users\develmode\dev\msp-basic-chat\server\plugins\RPEngine-4.5.3.jar!\com\
 * Alvaeron\listeners\EventListener.class Java compiler version: 8 (52.0)
 * JD-Core Version: 1.1.3
 */