package com.develmode.rpenginemod.commands;

import com.develmode.rpenginemod.Engine;
import com.develmode.rpenginemod.player.RoleplayPlayer;
import com.develmode.rpenginemod.utils.Lang;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class ChatCommands extends AbstractCommand {
  public ChatCommands(Engine plugin) {
    super(plugin, new AbstractCommand.Senders[] { AbstractCommand.Senders.PLAYER });
  }

  public boolean handleCommand(CommandSender sender, Command cmd, String Commandlabel, String[] args) {
    if (Engine.listener.chatEnabled) {
      if (cmd.getName().equalsIgnoreCase("rp")) {
        this.rpp.setChannel(RoleplayPlayer.Channel.RP);
        this.rpp.setTag();
        return true;
      }
      if (cmd.getName().equalsIgnoreCase("ooc")) {
        if (!this.rpp.isOOC())
          this.rpp.switchOOC();
        this.rpp.setChannel(RoleplayPlayer.Channel.OOC);
        this.rpp.setTag();
        return true;
      }
      if (cmd.getName().equalsIgnoreCase("toggleooc")) {
        this.rpp.setChannel(RoleplayPlayer.Channel.RP);
        this.rpp.setTag();
        this.rpp.switchOOC();
        if (!this.rpp.isOOC() && this.rpp.getChannel() == RoleplayPlayer.Channel.OOC)
          this.rpp.setChannel(RoleplayPlayer.Channel.RP);
        return true;
      }
      if (cmd.getName().equalsIgnoreCase("whisper"))
        if (args.length >= 1) {
          Engine.mu.sendRangedMessage(this.player, this.formattedMessage(super.rpp.getName(), args), "whisperRange");
        } else {
          this.player.sendMessage(Lang.CHAT_WHISPER_USAGE.toString().replace("%c", Commandlabel.toLowerCase()));
        }
      if (cmd.getName().equalsIgnoreCase("shout")) {
        if (args.length >= 1) {
          Engine.mu.sendRangedMessage(this.player, this.formattedMessage(super.rpp.getName(), args), "shoutRange");
        } else {
          this.player.sendMessage(Lang.CHAT_SHOUT_USAGE.toString().replace("%c", Commandlabel.toLowerCase()));
        }
        return true;
      }
    } else {
      this.rpp.getPlayer().sendMessage(Lang.CHAT_DISABLED.toString());
    }
    return false;
  }

  private String formattedMessage(String playerName, String[] messageArgs) {
    var splitMsgs = String.join(" ", messageArgs).split("\\*", 2);
    var message = splitMsgs.length > 0 ? splitMsgs[0].trim() : "";
    var emotes = splitMsgs.length >= 2 ? String.format("*%s*", splitMsgs[1].trim().replaceAll("^\\*+|\\*+$", "")) : "";
    var formattedMessage = Lang.CHAT_WHISPER_FORMAT.toString().replace("%e", emotes).replace("%m", message)
        .replace("%n", this.rpp.getName());
    return formattedMessage;
  }
}

/*
 * Location:
 * C:\Users\develmode\dev\msp-basic-chat\server\plugins\RPEngine-4.5.3.jar!\com\
 * Alvaeron\commands\ChatCommands.class Java compiler version: 8 (52.0) JD-Core
 * Version: 1.1.3
 */