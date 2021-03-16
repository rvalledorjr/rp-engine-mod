package com.valledor.rpenginemod.commands;

import com.valledor.rpenginemod.Engine;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class RPCommand extends AbstractCommand {
  public RPCommand(Engine plugin) {
    super(plugin, new AbstractCommand.Senders[] { AbstractCommand.Senders.PLAYER });
  }

  public boolean handleCommand(CommandSender sender, Command cmd, String Commandlabel, String[] args) {
    if (cmd.getName().equalsIgnoreCase("rp"))
      ;
    return false;
  }
}

/*
 * Location:
 * C:\Users\develmode\dev\msp-basic-chat\server\plugins\RPEngine-4.5.3.jar!\com\
 * Alvaeron\commands\RPCommand.class Java compiler version: 8 (52.0) JD-Core
 * Version: 1.1.3
 */