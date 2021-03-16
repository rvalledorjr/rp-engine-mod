package com.develmode.rpenginemod.api;

import com.develmode.rpenginemod.Engine;
import com.develmode.rpenginemod.player.RoleplayPlayer;
import java.util.Set;
import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class RPEngineAPI {
  public static RoleplayPlayer getRoleplayPlayer(Player p) {
    return Engine.manager.getPlayer(p.getUniqueId());
  }

  public static RoleplayPlayer getRoleplayPlayer(UUID uuid) {
    return Engine.manager.getPlayer(uuid);
  }

  public static RoleplayPlayer getRoleplayPlayer(String playerName) {
    if (Bukkit.getPlayer(playerName) != null)
      return Engine.manager.getPlayer(Bukkit.getPlayer(playerName).getUniqueId());
    if (Bukkit.getOfflinePlayer(playerName) != null) {
      Player p = (Player) Bukkit.getOfflinePlayer(playerName);
      if (Engine.manager.getPlayer(p.getUniqueId()) != null)
        return Engine.manager.getPlayer(p.getUniqueId());
      Engine.mm.createRoleplayPlayer(p);
      return Engine.manager.getPlayer(p.getUniqueId());
    }
    return null;
  }

  public static String getRpName(String playerName) {
    return getRoleplayPlayer(playerName).getName();
  }

  public static int getRpAge(String playerName) {
    return getRoleplayPlayer(playerName).getAge();
  }

  public static RoleplayPlayer.Gender getRpGender(String playerName) {
    return getRoleplayPlayer(playerName).getGender();
  }

  public static String getRpRace(String playerName) {
    return getRoleplayPlayer(playerName).getRace();
  }

  public static String getRpNation(String playerName) {
    return getRoleplayPlayer(playerName).getNation();
  }

  public static String getRpDesc(String playerName) {
    return getRoleplayPlayer(playerName).getDesc();
  }

  public static RoleplayPlayer.Channel getChannel(String playerName) {
    return getRoleplayPlayer(playerName).getChannel();
  }

  public static boolean getOOC(String playerName) {
    return getRoleplayPlayer(playerName).isOOC();
  }

  public static void setRpName(String playerName, String name) {
    getRoleplayPlayer(playerName).setName(name);
  }

  public static void setRpAge(String playerName, int age) {
    getRoleplayPlayer(playerName).setAge(age);
  }

  public static void setRpGender(String playerName, RoleplayPlayer.Gender gender) {
    getRoleplayPlayer(playerName).setGender(gender);
  }

  public static void setRpRace(String playerName, String race) {
    getRoleplayPlayer(playerName).setRace(race);
  }

  public static void setRpNation(String playerName, String nation) {
    getRoleplayPlayer(playerName).setNation(nation);
  }

  public static void setRpDesc(String playerName, String desc) {
    getRoleplayPlayer(playerName).setDesc(desc);
  }

  public static void setChannel(String playerName, RoleplayPlayer.Channel channel) {
    getRoleplayPlayer(playerName).setChannel(channel);
  }

  public static void setOOC(String playerName, boolean ooc) {
    getRoleplayPlayer(playerName).setOOC(ooc);
  }

  public static Set<String> getRaces() {
    return Engine.rpEngine.getConfig().getConfigurationSection("Races").getKeys(false);
  }

  public static Set<String> getNations() {
    return Engine.rpEngine.getConfig().getConfigurationSection("Nations").getKeys(false);
  }

  public static ChatColor getRaceColor(String race) {
    return Engine.mu.getRaceColour(race);
  }
}

/*
 * Location:
 * C:\Users\develmode\dev\msp-basic-chat\server\plugins\RPEngine-4.5.3.jar!\com\
 * Alvaeron\api\RPEngineAPI.class Java compiler version: 8 (52.0) JD-Core
 * Version: 1.1.3
 */