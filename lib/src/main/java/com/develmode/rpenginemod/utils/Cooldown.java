package com.develmode.rpenginemod.utils;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import org.bukkit.entity.Player;

public class Cooldown {
  private static Table<String, String, Long> cooldowns = HashBasedTable.<String, String, Long>create();

  public static long getCooldown(Player player, String key) {
    return calculateRemainder((Long) cooldowns.get(player.getName(), key));
  }

  public static long setCooldown(Player player, String key, long delay) {
    return calculateRemainder(
        (Long) cooldowns.put(player.getName(), key, Long.valueOf(System.currentTimeMillis() + delay)));
  }

  public static boolean tryCooldown(Player player, String key, long delay) {
    if (getCooldown(player, key) <= 0L) {
      setCooldown(player, key, delay);
      return true;
    }
    return false;
  }

  private static long calculateRemainder(Long expireTime) {
    return (expireTime != null) ? (expireTime.longValue() - System.currentTimeMillis()) : Long.MIN_VALUE;
  }
}

/*
 * Location:
 * C:\Users\develmode\dev\msp-basic-chat\server\plugins\RPEngine-4.5.3.jar!\com\
 * Alvaero\\utils\Cooldown.class Java compiler version: 8 (52.0) JD-Core
 * Version: 1.1.3
 */