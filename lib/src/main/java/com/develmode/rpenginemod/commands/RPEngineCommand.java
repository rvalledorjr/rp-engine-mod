package com.develmode.rpenginemod.commands;

import com.develmode.rpenginemod.Engine;
import com.develmode.rpenginemod.utils.Lang;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class RPEngineCommand extends AbstractCommand {
  public RPEngineCommand(Engine plugin) {
    super(plugin, AbstractCommand.Senders.values());
  }

  public boolean handleCommand(CommandSender sender, Command cmd, String Commandlabel, String[] args) {
    if (cmd.getName().equalsIgnoreCase("rpengine")) {
      if (!(sender instanceof Player) || sender.hasPermission("rpengine.admin")) {
        if (args.length == 1) {
          if (args[0].equalsIgnoreCase("reload")) {
            this.plugin.reloadConfig();
            this.plugin.loadLang();
            sender.sendMessage(Lang.TITLE.toString() + Lang.RELOAD);
          } else if (args[0].equalsIgnoreCase("debug")) {
            Engine.manager.players.clear();
            for (Player pl : Bukkit.getOnlinePlayers())
              Engine.mm.createRoleplayPlayer(pl);
            sender.sendMessage("Players recached");
          }
        } else {
          sender.sendMessage(
              Lang.TITLE.toString() + Lang.VERSION.toString().replace("%v", this.plugin.getDescription().getVersion()));
        }
      } else {
        sender.sendMessage(Lang.NO_PERMS.toString());
      }
      return true;
    }
    return false;
  }
}

/*
 * Location:
 * C:\Users\develmode\dev\msp-basic-chat\server\plugins\RPEngine-4.5.3.jar!\com\
 * Alvaeron\commands\RPEngineCommand.class Java compiler version: 8 (52.0)
 * JD-Core Version: 1.1.3
 */