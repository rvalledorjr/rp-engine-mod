package com.valledor.rpenginemod.commands;

import com.valledor.rpenginemod.Engine;
import com.valledor.rpenginemod.player.RoleplayPlayer;
import com.valledor.rpenginemod.utils.Lang;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

public class BirdCommand extends AbstractCommand {
  public BirdCommand(Engine plugin) {
    super(plugin, new AbstractCommand.Senders[] { AbstractCommand.Senders.PLAYER });
  }

  public boolean handleCommand(CommandSender sender, Command cmd, String Commandlabel, final String[] args) {
    final Player player = (Player) sender;
    final RoleplayPlayer rpp = Engine.manager.getPlayer(player.getUniqueId());
    if (args.length >= 2) {
      if (player.getServer().getPlayer(args[0]) != null) {
        if (player.getWorld() == this.plugin.getServer().getPlayer(args[0]).getWorld()) {
          if (player.getInventory().contains(Material.PAPER)) {
            player.getInventory().removeItem(new ItemStack[] { new ItemStack(Material.PAPER, 1) });
            final RoleplayPlayer targetPlayer = Engine.manager
                .getPlayer(player.getServer().getPlayer(args[0]).getUniqueId());
            StringBuilder sb = new StringBuilder();
            for (int i = 1; i < args.length; i++)
              sb.append(args[i]).append(" ");
            final String message = sb.toString().trim();
            double distance = targetPlayer.getPlayer().getLocation().distance(player.getLocation());
            double time = distance / this.plugin.getConfig().getDouble("speed") * 20.0D;
            player.sendMessage(Lang.BIRD_SENT.toString()
                .replace("%n", Engine.mu.getRaceColour(targetPlayer.getRace()) + targetPlayer.getName())
                .replace("%p", targetPlayer.getPlayer().getName()));
            this.plugin.getServer().getScheduler().scheduleSyncDelayedTask((Plugin) this.plugin, new Runnable() {
              public void run() {
                if (player.getServer().getPlayer(args[0]) != null) {
                  player.sendMessage(Lang.BIRD_DELIVER.toString()
                      .replace("%n", Engine.mu.getRaceColour(targetPlayer.getRace()) + targetPlayer.getName())
                      .replace("%p", targetPlayer.getPlayer().getName()));
                  targetPlayer.getPlayer()
                      .sendMessage(Lang.BIRD_LAND.toString()
                          .replace("%n", Engine.mu.getRaceColour(rpp.getRace()) + rpp.getName())
                          .replace("%p", player.getName()));
                  targetPlayer.getPlayer().sendMessage(ChatColor.WHITE + message);
                  targetPlayer.getPlayer().sendMessage(ChatColor.AQUA + "-----------------------------");
                } else {
                  player.sendMessage(Lang.BIRD_LOST.toString());
                }
              }
            }, (long) time);
          } else {
            player.sendMessage(Lang.BIRD_PAPER.toString());
          }
        } else {
          player.sendMessage(Lang.BIRD_DIFFERENT_WORLD.toString());
        }
      } else {
        player.sendMessage(Lang.BIRD_OFFLINE.toString().replace("%p", args[0]));
      }
    } else {
      player.sendMessage(Lang.BIRD_USAGE.toString());
    }
    return true;
  }
}

/*
 * Location:
 * C:\Users\develmode\dev\msp-basic-chat\server\plugins\RPEngine-4.5.3.jar!\com\
 * Alvaeron\commands\BirdCommand.class Java compiler version: 8 (52.0) JD-Core
 * Version: 1.1.3
 */