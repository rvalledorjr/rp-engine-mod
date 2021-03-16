package com.develmode.rpenginemod.utils;

import com.develmode.rpenginemod.Engine;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class Countdown {
  private final Engine plugin;

  private int countdownTimer;

  public Countdown(Engine i) {
    this.plugin = i;
  }

  public void startCountdown(final Player p, final boolean all, final int time) {
    this.countdownTimer = Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask((Plugin) this.plugin,
        new Runnable() {
          int i = time;

          public void run() {
            if (all)
              Engine.mu.sendRangedMessage(p, ChatColor.GOLD + Integer.toString(this.i), "rpRange");
            this.i--;
            if (this.i <= 0) {
              Countdown.this.cancel();
              Engine.mu.sendRangedMessage(p, "Go!", "rpRange");
            }
          }
        }, 0L, 20L);
  }

  public void cancel() {
    Bukkit.getScheduler().cancelTask(this.countdownTimer);
  }
}

/*
 * Location:
 * C:\Users\develmode\dev\msp-basic-chat\server\plugins\RPEngine-4.5.3.jar!\com\
 * Alvaero\\utils\Countdown.class Java compiler version: 8 (52.0) JD-Core
 * Version: 1.1.3
 */