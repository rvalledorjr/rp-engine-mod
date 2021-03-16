package com.valledor.rpenginemod.commands;

import com.valledor.rpenginemod.Engine;
import com.valledor.rpenginemod.utils.Countdown;
import com.valledor.rpenginemod.utils.Lang;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CountdownCommand extends AbstractCommand {
  public CountdownCommand(Engine plugin) {
    super(plugin, new AbstractCommand.Senders[] { AbstractCommand.Senders.PLAYER });
  }

  public boolean handleCommand(CommandSender sender, Command cmd, String Commandlabel, String[] args) {
    if (args.length == 0)
      sender.sendMessage(Lang.COUNTDOWN_USAGE.toString());
    if (args.length == 1)
      try {
        int n = Integer.parseInt(args[0]);
        Player player = (Player) sender;
        if (n < 0) {
          player.sendMessage(Lang.COUNTDOWN_NEGATIVE.toString());
        } else if (n <= this.plugin.getConfig().getInt("maxCountdown")) {
          Engine.mu.sendRangedMessage(player, Lang.COUNTDOWN_START.toString().replace("%r", this.rpp.getName())
              .replace("%p", this.rpp.getPlayer().getName()).replace("%n", Integer.toString(n)), "rpRange");
          Countdown c = new Countdown(this.plugin);
          c.startCountdown(player, true, Integer.parseInt(args[0]));
        } else {
          player.sendMessage(Lang.COUNTDOWN_MAX.toString().replace("%m",
              Integer.toString(this.plugin.getConfig().getInt("maxCountdown"))));
        }
      } catch (NumberFormatException e) {
        this.player.sendMessage(Lang.COUNTDOWN_USAGE.toString());
      }
    return true;
  }
}

/*
 * Location:
 * C:\Users\develmode\dev\msp-basic-chat\server\plugins\RPEngine-4.5.3.jar!\com\
 * Alvaeron\commands\CountdownCommand.class Java compiler version: 8 (52.0)
 * JD-Core Version: 1.1.3
 */