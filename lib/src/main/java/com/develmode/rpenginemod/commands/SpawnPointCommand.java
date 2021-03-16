package com.develmode.rpenginemod.commands;

import com.develmode.rpenginemod.Engine;
import com.develmode.rpenginemod.utils.Lang;
import java.util.Set;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SpawnPointCommand extends AbstractCommand {
  public SpawnPointCommand(Engine plugin) {
    super(plugin, new AbstractCommand.Senders[] { AbstractCommand.Senders.PLAYER });
  }

  public boolean handleCommand(CommandSender sender, Command cmd, String Commandlabel, String[] args) {
    if (cmd.getName().equalsIgnoreCase("spawnpoint")) {
      Player player = (Player) sender;
      String nation = this.rpp.getNation();
      Set<String> nations = this.plugin.getConfig().getConfigurationSection("Nations").getKeys(false);
      StringBuilder sb = new StringBuilder();
      for (String s : nations) {
        sb.append(s);
        sb.append("/");
      }
      String nationString = sb.toString().trim();
      String nationList = nationString.substring(0, nationString.length() - 1);
      if (args.length == 1) {
        if (args[0].equalsIgnoreCase("set")) {
          if (player.hasPermission("rpengine.spawnpoint.set.all") || player.isOp()) {
            player.sendMessage(Lang.SPAWNPOINT_SET_USAGE.toString().replace("%n", nationList).replace("%c",
                Commandlabel.toLowerCase()));
          } else if (player.hasPermission("rpengine.spawnpoint.set.own")) {
            player.sendMessage(Lang.SPAWNPOINT_SET_USAGE.toString().replace("%n", this.rpp.getNation()).replace("%c",
                Commandlabel.toLowerCase()));
          } else {
            player.sendMessage(Lang.NO_PERMS.toString());
          }
          return false;
        }
        if (Engine.mu.containsCaseInsensitive(args[0], nations)) {
          nation = Engine.mu.getValueFromSet(args[0], nations);
          if (nation.equalsIgnoreCase(this.rpp.getNation())) {
            if (!player.hasPermission("rpengine.spawnpoint.others") && !player.isOp()
                && !player.hasPermission("rpengine.spawnpoint.own")) {
              player.sendMessage(Lang.NO_PERMS.toString());
              return false;
            }
          } else if (!player.hasPermission("rpengine.spawnpoint.others") && !player.isOp()) {
            player.sendMessage(Lang.NO_PERMS.toString());
            return false;
          }
        } else {
          if (player.hasPermission("rpengine.spawnpoint.others") || player.isOp()) {
            player.sendMessage(Lang.SPAWNPOINT_SET_USAGE.toString().replace("%n", nationList).replace("%c",
                Commandlabel.toLowerCase()));
          } else if (player.hasPermission("rpengine.spawnpoint.own")) {
            player.sendMessage(Lang.SPAWNPOINT_SET_USAGE.toString().replace("%n", this.rpp.getNation()).replace("%c",
                Commandlabel.toLowerCase()));
          } else {
            player.sendMessage(Lang.NO_PERMS.toString());
          }
          return false;
        }
      } else if (args.length == 2) {
        if (args[0].equalsIgnoreCase("set")) {
          if (Engine.mu.containsCaseInsensitive(args[1], nations)) {
            nation = Engine.mu.getValueFromSet(args[1], nations);
            if (nation.equalsIgnoreCase(this.rpp.getNation())) {
              if (!player.hasPermission("rpengine.spawnpoint.set.all")
                  && !player.hasPermission("rpengine.spawnpoint.set.own") && !player.isOp()) {
                player.sendMessage(Lang.NO_PERMS.toString());
                return false;
              }
            } else if (!player.hasPermission("rpengine.spawnpoint.set.all") && !player.isOp()) {
              player.sendMessage(Lang.NO_PERMS.toString());
              return false;
            }
            this.plugin.getConfig().set("Nations." + nation + ".spawnX", Double.valueOf(player.getLocation().getX()));
            this.plugin.getConfig().set("Nations." + nation + ".spawnY", Double.valueOf(player.getLocation().getY()));
            this.plugin.getConfig().set("Nations." + nation + ".spawnZ", Double.valueOf(player.getLocation().getZ()));
            this.plugin.getConfig().set("Nations." + nation + ".spawnYaw",
                Float.valueOf(player.getLocation().getYaw()));
            this.plugin.getConfig().set("Nations." + nation + ".spawnPitch",
                Float.valueOf(player.getLocation().getPitch()));
            this.plugin.getConfig().set("Nations." + nation + ".spawnWorld", player.getWorld().getName());
            this.plugin.saveConfig();
            player.sendMessage(Lang.SPAWNPOINT_SET.toString().replace("%n", nation));
            return true;
          }
          if (player.hasPermission("rpengine.spawnpoint.set.all") || player.isOp()) {
            player.sendMessage(Lang.SPAWNPOINT_SET_USAGE.toString().replace("%n", nationList).replace("%c",
                Commandlabel.toLowerCase()));
          } else if (player.hasPermission("rpengine.spawnpoint.set.own")) {
            player.sendMessage(Lang.SPAWNPOINT_SET_USAGE.toString().replace("%n", this.rpp.getNation()).replace("%c",
                Commandlabel.toLowerCase()));
          } else {
            player.sendMessage(Lang.NO_PERMS.toString());
          }
          return false;
        }
        if (player.hasPermission("rpengine.spawnpoint.set.own") || player.hasPermission("rpengine.spawnpoint.set.all")
            || player.isOp()) {
          player.sendMessage(
              Lang.SPAWNPOINT_SET_USAGE.toString().replace("%n", nationList).replace("%c", Commandlabel.toLowerCase()));
        } else {
          player.sendMessage(
              Lang.SPAWNPOINT_USAGE.toString().replace("%n", nationList).replace("%c", Commandlabel.toLowerCase()));
        }
        return false;
      }
      if (nation.equalsIgnoreCase("NONE")) {
        player.sendMessage(Lang.SPAWNPOINT_NO_NATION.toString());
        player.teleport(player.getWorld().getSpawnLocation());
        return false;
      }
      Location location = player.getLocation();
      if (this.plugin.getConfig().contains("Nations." + nation + ".spawnX")) {
        location.setX(this.plugin.getConfig().getDouble("Nations." + nation + ".spawnX"));
      } else {
        player.sendMessage(ChatColor.RED + "Error: " + ChatColor.WHITE
            + "Something went wrong with retrieving data from the config file");
        return false;
      }
      if (this.plugin.getConfig().contains("Nations." + nation + ".spawnY")) {
        location.setY(this.plugin.getConfig().getDouble("Nations." + nation + ".spawnY"));
      } else {
        player.sendMessage(Lang.SPAWNPOINT_CONFIG_ERROR.toString());
        return false;
      }
      if (this.plugin.getConfig().contains("Nations." + nation + ".spawnZ")) {
        location.setZ(this.plugin.getConfig().getDouble("Nations." + nation + ".spawnZ"));
      } else {
        player.sendMessage(Lang.SPAWNPOINT_CONFIG_ERROR.toString());
        return false;
      }
      if (this.plugin.getConfig().contains("Nations." + nation + ".spawnYaw")) {
        location.setYaw((float) this.plugin.getConfig().getDouble("Nations." + nation + ".spawnYaw"));
      } else {
        player.sendMessage(Lang.SPAWNPOINT_CONFIG_ERROR.toString());
        return false;
      }
      if (this.plugin.getConfig().contains("Nations." + nation + ".spawnPitch")) {
        location.setPitch((float) this.plugin.getConfig().getDouble("Nations." + nation + ".spawnPitch"));
      } else {
        player.sendMessage(Lang.SPAWNPOINT_CONFIG_ERROR.toString());
        return false;
      }
      if (this.plugin.getConfig().contains("Nations." + nation + ".spawnWorld")) {
        if (Bukkit.getWorld(this.plugin.getConfig().getString("Nations." + nation + ".spawnWorld")) != null) {
          location.setPitch((float) this.plugin.getConfig().getDouble("Nations." + nation + ".spawnPitch"));
        } else {
          player.sendMessage(Lang.SPAWNPOINT_CONFIG_ERROR.toString());
          return false;
        }
      } else {
        player.sendMessage(Lang.SPAWNPOINT_CONFIG_ERROR.toString());
        return false;
      }
      player.teleport(location);
      player.sendMessage(Lang.SPAWNPOINT_TELEPORT.toString().replace("%n", nation));
      return true;
    }
    return false;
  }
}

/*
 * Location:
 * C:\Users\develmode\dev\msp-basic-chat\server\plugins\RPEngine-4.5.3.jar!\com\
 * Alvaeron\commands\SpawnPointCommand.class Java compiler version: 8 (52.0)
 * JD-Core Version: 1.1.3
 */