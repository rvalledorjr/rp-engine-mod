package com.develmode.rpenginemod.commands;

import com.develmode.rpenginemod.Engine;
import com.develmode.rpenginemod.utils.Lang;
import java.util.Random;
import org.apache.commons.lang.StringUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class RollCommand extends AbstractCommand {
  public RollCommand(Engine plugin) {
    super(plugin, AbstractCommand.Senders.values());
  }

  public boolean handleCommand(CommandSender sender, Command cmd, String Commandlabel, String[] args) {
    Random diceRoller = new Random();
    int number = 20;
    if (args.length > 1) {
      sender.sendMessage(Lang.ROLL_USAGE.toString());
      return true;
    }
    if (args.length == 1)
      if (StringUtils.isNumeric(args[0])) {
        if (Integer.parseInt(args[0]) <= this.plugin.getConfig().getInt("maxRoll")) {
          number = Integer.parseInt(args[0]);
        } else {
          sender.sendMessage(
              Lang.ROLL_MAX.toString().replace("%n", Integer.toString(this.plugin.getConfig().getInt("maxRoll"))));
          return true;
        }
      } else {
        sender.sendMessage(Lang.ROLL_USAGE.toString());
        return true;
      }
    int roll = diceRoller.nextInt(number) + 1;
    if (sender instanceof org.bukkit.entity.Player) {
      Engine.mu.sendRangedMessage(this.player,
          Lang.ROLL_RESULT.toString().replace("%p", this.rpp.getPlayer().getName()).replace("%r", this.rpp.getName())
              .replace("%n", Integer.toString(roll)).replace("%m", Integer.toString(number)),
          "rpRange");
    } else {
      this.plugin.getServer()
          .broadcastMessage(Lang.ROLL_CONSOLE.toString().replace("%p", this.rpp.getPlayer().getName())
              .replace("%r", this.rpp.getName()).replace("%n", Integer.toString(roll))
              .replace("%m", Integer.toString(number)));
    }
    return true;
  }
}

/*
 * Location:
 * C:\Users\develmode\dev\msp-basic-chat\server\plugins\RPEngine-4.5.3.jar!\com\
 * Alvaeron\commands\RollCommand.class Java compiler version: 8 (52.0) JD-Core
 * Version: 1.1.3
 */