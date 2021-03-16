package com.develmode.rpenginemod.commands;

import com.develmode.rpenginemod.Engine;
import com.develmode.rpenginemod.listeners.CardEditEvent;
import com.develmode.rpenginemod.player.RoleplayPlayer;
import com.develmode.rpenginemod.utils.Cooldown;
import com.develmode.rpenginemod.utils.Lang;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

public class CardCommand extends AbstractCommand {
  public CardCommand(Engine plugin) {
    super(plugin, new AbstractCommand.Senders[] { AbstractCommand.Senders.PLAYER });
  }

  public boolean handleCommand(CommandSender sender, Command cmd, String Commandlabel, String[] args) {
    if (args.length >= 1) {
      if (args[0].equalsIgnoreCase("name")) {
        if (args.length >= 2) {
          if (cardTime(this.player, "name")) {
            StringBuilder sb2 = new StringBuilder();
            for (int j = 1; j < args.length; j++)
              sb2.append(args[j]).append(" ");
            String name = sb2.toString().trim().replace("\"", "");
            CardEditEvent event = new CardEditEvent(CardEditEvent.CardField.NAME, name, this.player, this.rpp);
            Bukkit.getServer().getPluginManager().callEvent((Event) event);
            if (!event.isCancelled()) {
              this.rpp.setName(event.getStringValue());
              this.rpp.setTag();
              this.player.sendMessage(
                  Lang.CARD_UPDATED_TO.toString().replace("%f", Lang.CARD_FIELD_NAME.toString()).replace("%v", name));
              Engine.card.clickToSendCard(this.player);
            }
          }
        } else {
          this.player.sendMessage(Lang.CARD_NAME_USAGE.toString());
        }
      } else if (args[0].equalsIgnoreCase("age")) {
        if (args.length == 2) {
          try {
            int age = Integer.parseInt(args[1]);
            String race = this.rpp.getRace();
            if (this.plugin.getConfig().contains("Races." + race + ".MaxAge")) {
              if (age <= this.plugin.getConfig().getInt("Races." + race + ".MaxAge")) {
                if (cardTime(this.player, "age")) {
                  CardEditEvent event = new CardEditEvent(CardEditEvent.CardField.AGE, age, this.player, this.rpp);
                  Bukkit.getServer().getPluginManager().callEvent((Event) event);
                  if (!event.isCancelled()) {
                    this.rpp.setAge(event.getIntValue());
                    this.player.sendMessage(Lang.CARD_UPDATED_TO.toString()
                        .replace("%f", Lang.CARD_FIELD_AGE.toString()).replace("%v", age + ""));
                    Engine.card.clickToSendCard(this.player);
                  }
                }
              } else if (this.plugin.getConfig().contains("Races." + race + ".Plural")) {
                this.player.sendMessage(Lang.CARD_AGE_RACE_MAX.toString()
                    .replace("%r", this.plugin.getConfig().getString("Races." + race + ".Plural"))
                    .replace("%a", Integer.toString(this.plugin.getConfig().getInt("Races." + race + ".MaxAge"))));
              } else {
                this.player.sendMessage(Lang.CARD_AGE_MAX.toString().replace("%a",
                    Integer.toString(this.plugin.getConfig().getInt("Races." + race + ".MaxAge"))));
              }
            } else if (this.plugin.getConfig().contains("MaxAge")) {
              if (age <= this.plugin.getConfig().getInt("MaxAge")) {
                if (cardTime(this.player, "age")) {
                  CardEditEvent event = new CardEditEvent(CardEditEvent.CardField.AGE, age, this.player, this.rpp);
                  Bukkit.getServer().getPluginManager().callEvent((Event) event);
                  if (!event.isCancelled()) {
                    this.rpp.setAge(event.getIntValue());
                    this.player.sendMessage(Lang.CARD_UPDATED_TO.toString()
                        .replace("%f", Lang.CARD_FIELD_AGE.toString()).replace("%v", age + ""));
                    Engine.card.clickToSendCard(this.player);
                  }
                }
              } else {
                this.player.sendMessage(Lang.CARD_AGE_MAX.toString().replace("%a",
                    Integer.toString(this.plugin.getConfig().getInt("MaxAge"))));
              }
            } else if (cardTime(this.player, "age")) {
              CardEditEvent event = new CardEditEvent(CardEditEvent.CardField.AGE, age, this.player, this.rpp);
              Bukkit.getServer().getPluginManager().callEvent((Event) event);
              if (!event.isCancelled()) {
                this.rpp.setAge(event.getIntValue());
                this.player.sendMessage(Lang.CARD_UPDATED_TO.toString().replace("%f", Lang.CARD_FIELD_AGE.toString())
                    .replace("%v", age + ""));
                Engine.card.clickToSendCard(this.player);
              }
            }
          } catch (NumberFormatException e) {
            this.player.sendMessage(Lang.MUST_BE_NUMBER.toString());
          }
        } else {
          this.player.sendMessage(Lang.CARD_AGE_USAGE.toString());
        }
      } else if (args[0].equalsIgnoreCase("gender")) {
        if (args.length == 2) {
          if (args[1].equalsIgnoreCase("male")) {
            if (cardTime(this.player, "gender")) {
              CardEditEvent event = new CardEditEvent(CardEditEvent.CardField.GENDER, RoleplayPlayer.Gender.MALE,
                  this.player, this.rpp);
              Bukkit.getServer().getPluginManager().callEvent((Event) event);
              if (!event.isCancelled()) {
                this.rpp.setGender(event.getGender());
                this.player.sendMessage(Lang.CARD_UPDATED_TO.toString().replace("%f", Lang.CARD_FIELD_GENDER.toString())
                    .replace("%v", Lang.CARD_FIELD_GENDER_MALE.toString()));
                Engine.card.clickToSendCard(this.player);
              }
            }
          } else if (args[1].equalsIgnoreCase("female")) {
            if (cardTime(this.player, "gender")) {
              CardEditEvent event = new CardEditEvent(CardEditEvent.CardField.GENDER, RoleplayPlayer.Gender.FEMALE,
                  this.player, this.rpp);
              Bukkit.getServer().getPluginManager().callEvent((Event) event);
              if (!event.isCancelled()) {
                this.rpp.setGender(event.getGender());
                this.player.sendMessage(Lang.CARD_UPDATED_TO.toString().replace("%f", Lang.CARD_FIELD_GENDER.toString())
                    .replace("%v", Lang.CARD_FIELD_GENDER_FEMALE.toString()));
                Engine.card.clickToSendCard(this.player);
              }
            }
          } else {
            this.player.sendMessage(Lang.CARD_GENDER_USAGE.toString());
          }
        } else {
          Engine.card.sendGenderSelect(this.rpp);
        }
      } else if (args[0].equalsIgnoreCase("race")) {
        Set<String> races = this.plugin.getConfig().getConfigurationSection("Races").getKeys(false);
        if (args.length == 2) {
          if (Engine.mu.containsCaseInsensitive(args[1], races)) {
            if (cardTime(this.player, "race")) {
              CardEditEvent event = new CardEditEvent(CardEditEvent.CardField.RACE,
                  Engine.mu.getValueFromSet(args[1], races), this.player, this.rpp);
              Bukkit.getServer().getPluginManager().callEvent((Event) event);
              if (!event.isCancelled()) {
                this.rpp.setRace(event.getStringValue());
                this.player.sendMessage(Lang.CARD_UPDATED_TO.toString().replace("%f", Lang.CARD_FIELD_RACE.toString())
                    .replace("%v", args[1]));
                Engine.card.clickToSendCard(this.player);
                this.rpp.setTag();
              }
            }
          } else {
            StringBuilder sb = new StringBuilder();
            for (String s : races) {
              sb.append(s);
              sb.append("/");
            }
            String raceString = sb.toString().trim();
            this.player.sendMessage(
                Lang.CARD_RACE_USAGE.toString().replace("%r", raceString.substring(0, raceString.length() - 1)));
          }
        } else {
          Engine.card.sendRaces(this.rpp);
        }
      } else if (args[0].equalsIgnoreCase("nation")) {
        Set<String> nations = this.plugin.getConfig().getConfigurationSection("Nations").getKeys(false);
        if (args.length == 2) {
          if (Engine.mu.containsCaseInsensitive(args[1], nations)) {
            if (cardTime(this.player, "nation")) {
              CardEditEvent event = new CardEditEvent(CardEditEvent.CardField.NATION,
                  Engine.mu.getValueFromSet(args[1], nations), this.player, this.rpp);
              Bukkit.getServer().getPluginManager().callEvent((Event) event);
              if (!event.isCancelled()) {
                this.rpp.setNation(event.getStringValue());
                this.player.sendMessage(Lang.CARD_UPDATED_TO.toString().replace("%f", Lang.CARD_FIELD_NATION.toString())
                    .replace("%v", args[1]));
                Engine.card.clickToSendCard(this.player);
              }
            }
          } else {
            StringBuilder sb = new StringBuilder();
            for (String s : nations) {
              sb.append(s);
              sb.append("/");
            }
            String nationString = sb.toString().trim();
            this.player.sendMessage(
                Lang.CARD_NATION_USAGE.toString().replace("%n", nationString.substring(0, nationString.length() - 1)));
          }
        } else {
          Engine.card.sendNations(this.rpp);
        }
      } else if (args[0].equalsIgnoreCase("desc") || args[0].equalsIgnoreCase("description")) {
        if (args.length >= 2) {
          if (cardTime(this.player, "description")) {
            StringBuilder sb2 = new StringBuilder();
            for (int j = 1; j < args.length; j++)
              sb2.append(args[j]).append(" ");
            String desc = sb2.toString().trim().replace("\"", "");
            CardEditEvent event = new CardEditEvent(CardEditEvent.CardField.DESC, desc, this.player, this.rpp);
            Bukkit.getServer().getPluginManager().callEvent((Event) event);
            if (!event.isCancelled()) {
              this.rpp.setDesc(event.getStringValue());
              this.player.sendMessage(
                  Lang.CARD_UPDATED_TO.toString().replace("%f", Lang.CARD_FIELD_DESC.toString()).replace("%v", desc));
              Engine.card.clickToSendCard(this.player);
            }
          }
        } else {
          this.player.sendMessage(Lang.CARD_DESC_USAGE.toString());
        }
      } else {
        Player csender = this.player.getServer().getPlayer(args[0]);
        if (csender != null) {
          Engine.card.sendCardOther(Engine.manager.getPlayer(csender.getUniqueId()), this.player);
        } else {
          this.player.sendMessage(Lang.CARD_OFFLINE.toString().replace("%p", args[0]));
        }
      }
    } else {
      Engine.card.sendCard(this.rpp);
    }
    return true;
  }

  private boolean cardTime(Player player, String type) {
    if (!Cooldown.tryCooldown(player, type, (this.plugin.getConfig().getInt("cardCooldown") * 1000))) {
      long timeLeft = TimeUnit.MILLISECONDS.toMinutes(Cooldown.getCooldown(player, type));
      player.sendMessage(
          ChatColor.RED + "You must wait " + timeLeft + " minutes before changing your " + type + " again.");
      return false;
    }
    return true;
  }
}

/*
 * Location:
 * C:\Users\develmode\dev\msp-basic-chat\server\plugins\RPEngine-4.5.3.jar!\com\
 * Alvaeron\commands\CardCommand.class Java compiler version: 8 (52.0) JD-Core
 * Version: 1.1.3
 */