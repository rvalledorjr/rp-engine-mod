package com.valledor.rpenginemod.commands;

import com.valledor.rpenginemod.Engine;
import com.valledor.rpenginemod.player.RoleplayPlayer;
import java.util.Arrays;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public abstract class AbstractCommand implements CommandExecutor {
  protected Engine plugin;

  private Senders[] definedSenders;

  protected Player player;

  protected RoleplayPlayer rpp;

  public AbstractCommand(Engine plugin, Senders... definedSenders) {
    this.plugin = plugin;
    this.definedSenders = definedSenders;
  }

  public abstract boolean handleCommand(CommandSender paramCommandSender, Command paramCommand, String paramString,
      String[] paramArrayOfString);

  public boolean onCommand(CommandSender sender, Command cmd, String Commandlabel, String[] args) {
    boolean error = false;
    if (sender instanceof org.bukkit.command.BlockCommandSender)
      error = !Arrays.<Senders>asList(this.definedSenders).contains(Senders.COMMANDBLOCK);
    if (sender instanceof org.bukkit.command.ConsoleCommandSender)
      error = !Arrays.<Senders>asList(this.definedSenders).contains(Senders.CONSOLE);
    if (sender instanceof Player) {
      error = !Arrays.<Senders>asList(this.definedSenders).contains(Senders.PLAYER);
      this.player = (Player) sender;
      this.rpp = Engine.manager.getPlayer(this.player.getUniqueId());
    }
    if (error) {
      sender.sendMessage("This command can only be run by:" + this.definedSenders.toString());
      return false;
    }
    return handleCommand(sender, cmd, Commandlabel, args);
  }

  public enum Senders {
    CONSOLE, PLAYER, COMMANDBLOCK;
  }
}

/*
 * Location:
 * C:\Users\develmode\dev\msp-basic-chat\server\plugins\RPEngine-4.5.3.jar!\com\
 * Alvaeron\commands\AbstractCommand.class Java compiler version: 8 (52.0)
 * JD-Core Version: 1.1.3
 */