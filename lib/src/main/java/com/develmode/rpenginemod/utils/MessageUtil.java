package com.develmode.rpenginemod.utils;

import com.develmode.rpenginemod.Engine;
import java.util.Set;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class MessageUtil {
  private Engine plugin;

  public MessageUtil(Engine plugin) {
    this.plugin = plugin;
  }

  public void sendRangedMessage(Player player, String text, String key) {
    for (Player p : Bukkit.getOnlinePlayers()) {
      if (player.getWorld() == p.getWorld()
          && p.getLocation().distance(player.getLocation()) <= this.plugin.getConfig().getInt(key))
        p.sendMessage(text);
    }
  }

  public void sendLocalizedMessage(Player player, String key) {
  }

  public boolean containsCaseInsensitive(String s, Set<String> l) {
    for (String string : l) {
      if (string.equalsIgnoreCase(s))
        return true;
    }
    return false;
  }

  public String getValueFromSet(String s, Set<String> l) {
    for (String string : l) {
      if (string.equalsIgnoreCase(s))
        return string;
    }
    return null;
  }

  public ChatColor getRaceColour(String race) {
    if (!this.plugin.getConfig().contains("Races." + race + ".Color"))
      return ChatColor.GRAY;
    String color = this.plugin.getConfig().getString("Races." + race + ".Color");
    try {
      return ChatColor.valueOf(color);
    } catch (IllegalArgumentException ex) {
      return ChatColor.WHITE;
    }
  }

  public String replaceIfOdd(String stringToChange, String searchingWord, String replacingWord) {
    String separator = "#######";
    String splittingString = stringToChange.replaceAll(searchingWord, "#######" + searchingWord);
    String[] splitArray = splittingString.split("#######");
    String result = "";
    for (int i = 0; i < splitArray.length; i++) {
      if (i % 2 == 1)
        splitArray[i] = splitArray[i].replace(searchingWord, replacingWord);
      result = result + splitArray[i];
    }
    return result;
  }

  public String replaceIfEven(String stringToChange, String searchingWord, String replacingWord) {
    String separator = "#######";
    String splittingString = stringToChange.replaceAll(searchingWord, "#######" + searchingWord);
    String[] splitArray = splittingString.split("#######");
    String result = "";
    for (int i = 0; i < splitArray.length; i++) {
      if (i % 2 == 0)
        splitArray[i] = splitArray[i].replace(searchingWord, replacingWord);
      result = result + splitArray[i];
    }
    return result;
  }
}

/*
 * Location:
 * C:\Users\develmode\dev\msp-basic-chat\server\plugins\RPEngine-4.5.3.jar!\com\
 * Alvaero\\utils\MessageUtil.class Java compiler version: 8 (52.0) JD-Core
 * Version: 1.1.3
 */