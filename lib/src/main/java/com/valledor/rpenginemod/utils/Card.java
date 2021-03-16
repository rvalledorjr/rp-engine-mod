package com.valledor.rpenginemod.utils;

import com.valledor.rpenginemod.Engine;
import com.valledor.rpenginemod.player.RoleplayPlayer;
import java.util.Set;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Card {
  private Engine plugin;

  public Card(Engine plugin) {
    this.plugin = plugin;
  }

  public void sendCard(RoleplayPlayer rpp) {
    rpp.getPlayer().sendMessage(Lang.CARD_OWN.toString());
    rpp.getPlayer().sendMessage(Lang.CARD_CLICK_TO_EDIT_FIELDS.toString());
    sendJson(rpp.getPlayer(),
        "[\"\",{\"text\":\"" + Lang.CARD_FIELD_NAME.toString()
            + ": \",\"color\":\"green\",\"clickEvent\":{\"action\":\"suggest_command\",\"value\":\"/card name "
            + rpp.getName()
            + "\"},\"hoverEvent\":{\"action\":\"show_text\",\"value\":{\"text\":\"\",\"extra\":[{\"text\":\""
            + Lang.CARD_CLICK_TO_EDIT.toString() + "\",\"color\":\"aqua\"}]}}},{\"text\":\"" + rpp.getName()
            + "\",\"color\":\"white\",\"clickEvent\":{\"action\":\"suggest_command\",\"value\":\"/card name "
            + rpp.getName()
            + "\"},\"hoverEvent\":{\"action\":\"show_text\",\"value\":{\"text\":\"\",\"extra\":[{\"text\":\""
            + Lang.CARD_CLICK_TO_EDIT.toString() + "\",\"color\":\"aqua\"}]}}}]");
    sendJson(rpp.getPlayer(), "[\"\",{\"text\":\"" + Lang.CARD_FIELD_AGE.toString()
        + ": \",\"color\":\"green\",\"clickEvent\":{\"action\":\"suggest_command\",\"value\":\"/card age "
        + rpp.getAge() + "\"},\"hoverEvent\":{\"action\":\"show_text\",\"value\":{\"text\":\"\",\"extra\":[{\"text\":\""
        + Lang.CARD_CLICK_TO_EDIT.toString() + "\",\"color\":\"aqua\"}]}}},{\"text\":\"" + rpp.getAge()
        + "\",\"color\":\"white\",\"clickEvent\":{\"action\":\"suggest_command\",\"value\":\"/card age " + rpp.getAge()
        + "\"},\"hoverEvent\":{\"action\":\"show_text\",\"value\":{\"text\":\"\",\"extra\":[{\"text\":\""
        + Lang.CARD_CLICK_TO_EDIT.toString() + "\",\"color\":\"aqua\"}]}}}]");
    sendJson(rpp.getPlayer(), "[\"\",{\"text\":\"" + Lang.CARD_FIELD_GENDER.toString()
        + ": \",\"color\":\"green\",\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/card gender\"},\"hoverEvent\":{\"action\":\"show_text\",\"value\":{\"text\":\"\",\"extra\":[{\"text\":\""
        + Lang.CARD_CLICK_TO_EDIT.toString() + "\",\"color\":\"aqua\"}]}}},{\"text\":\""
        + Lang.valueOf("CARD_FIELD_GENDER_" + rpp.getGender().name().toUpperCase())
        + "\",\"color\":\"white\",\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/card gender\"},\"hoverEvent\":{\"action\":\"show_text\",\"value\":{\"text\":\"\",\"extra\":[{\"text\":\""
        + Lang.CARD_CLICK_TO_EDIT.toString() + "\",\"color\":\"aqua\"}]}}}]");
    String color = "white";
    try {
      ChatColor c = ChatColor.valueOf(this.plugin.getConfig().getString("Races." + rpp.getRace() + ".Color"));
      color = c.name().toLowerCase();
    } catch (Exception e) {
      color = "white";
    }
    sendJson(rpp.getPlayer(), "[\"\",{\"text\":\"" + Lang.CARD_FIELD_RACE.toString()
        + ": \",\"color\":\"green\",\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/card race\"},\"hoverEvent\":{\"action\":\"show_text\",\"value\":{\"text\":\"\",\"extra\":[{\"text\":\""
        + Lang.CARD_CLICK_TO_EDIT.toString() + "\",\"color\":\"aqua\"}]}}},{\"text\":\"" + rpp.getRace()
        + "\",\"color\":\"" + color
        + "\",\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/card race\"},\"hoverEvent\":{\"action\":\"show_text\",\"value\":{\"text\":\"\",\"extra\":[{\"text\":\""
        + Lang.CARD_CLICK_TO_EDIT.toString() + "\",\"color\":\"aqua\"}]}}}]");
    sendJson(rpp.getPlayer(), "[\"\",{\"text\":\"" + Lang.CARD_FIELD_NATION.toString()
        + ": \",\"color\":\"green\",\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/card nation\"},\"hoverEvent\":{\"action\":\"show_text\",\"value\":{\"text\":\"\",\"extra\":[{\"text\":\""
        + Lang.CARD_CLICK_TO_EDIT.toString() + "\",\"color\":\"aqua\"}]}}},{\"text\":\"" + rpp.getNation()
        + "\",\"color\":\"white\",\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/card nation\"},\"hoverEvent\":{\"action\":\"show_text\",\"value\":{\"text\":\"\",\"extra\":[{\"text\":\""
        + Lang.CARD_CLICK_TO_EDIT.toString() + "\",\"color\":\"aqua\"}]}}}]");
    sendJson(rpp.getPlayer(),
        "[\"\",{\"text\":\"" + Lang.CARD_FIELD_DESC.toString()
            + ": \",\"color\":\"green\",\"clickEvent\":{\"action\":\"suggest_command\",\"value\":\"/card desc "
            + rpp.getDesc()
            + "\"},\"hoverEvent\":{\"action\":\"show_text\",\"value\":{\"text\":\"\",\"extra\":[{\"text\":\""
            + Lang.CARD_CLICK_TO_EDIT.toString() + "\",\"color\":\"aqua\"}]}}},{\"text\":\"" + rpp.getDesc()
            + "\",\"color\":\"white\",\"clickEvent\":{\"action\":\"suggest_command\",\"value\":\"/card desc "
            + rpp.getDesc()
            + "\"},\"hoverEvent\":{\"action\":\"show_text\",\"value\":{\"text\":\"\",\"extra\":[{\"text\":\""
            + Lang.CARD_CLICK_TO_EDIT.toString() + "\",\"color\":\"aqua\"}]}}}]");
  }

  public void sendCardOther(RoleplayPlayer rpp, Player reciever) {
    reciever.sendMessage(Lang.CARD_OTHERS.toString().replace("%p", rpp.getPlayer().getName()));
    reciever.sendMessage(ChatColor.GREEN + Lang.CARD_FIELD_NAME.toString() + ": " + ChatColor.WHITE + rpp.getName());
    reciever.sendMessage(ChatColor.GREEN + Lang.CARD_FIELD_AGE.toString() + ": " + ChatColor.WHITE + rpp.getAge());
    reciever.sendMessage(ChatColor.GREEN + Lang.CARD_FIELD_GENDER.toString() + ": " + ChatColor.WHITE
        + Lang.valueOf("CARD_FIELD_GENDER_" + rpp.getGender().getName().toUpperCase()).toString());
    reciever.sendMessage(ChatColor.GREEN + Lang.CARD_FIELD_RACE.toString() + ": " + ChatColor.WHITE + rpp.getRace());
    reciever.sendMessage(ChatColor.GREEN + Lang.CARD_FIELD_NAME.toString() + ": " + ChatColor.WHITE + rpp.getNation());
    reciever.sendMessage(ChatColor.GREEN + Lang.CARD_FIELD_DESC.toString() + ": " + ChatColor.WHITE + rpp.getDesc());
  }

  public void sendGenderSelect(RoleplayPlayer rpp) {
    rpp.getPlayer().sendMessage(Lang.CARD_SELECT_GENDER.toString());
    sendJson(rpp.getPlayer(), "[\"\",{\"text\":\"" + Lang.CARD_FIELD_GENDER_MALE.toString()
        + "\",\"color\":\"blue\",\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/card gender male\"},\"hoverEvent\":{\"action\":\"show_text\",\"value\":{\"text\":\"\",\"extra\":[{\"text\":\""
        + Lang.CARD_CLICK_TO_GENDER.toString().replace("%g", Lang.CARD_FIELD_GENDER_MALE.toString())
        + "\",\"color\":\"aqua\"}]}}}]");
    sendJson(rpp.getPlayer(), "[\"\",{\"text\":\"" + Lang.CARD_FIELD_GENDER_FEMALE.toString()
        + "\",\"color\":\"light_purple\",\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/card gender female\"},\"hoverEvent\":{\"action\":\"show_text\",\"value\":{\"text\":\"\",\"extra\":[{\"text\":\""
        + Lang.CARD_CLICK_TO_GENDER.toString().replace("%g", Lang.CARD_FIELD_GENDER_FEMALE.toString())
        + "\",\"color\":\"aqua\"}]}}}]");
  }

  public void sendRaces(RoleplayPlayer rpp) {
    Set<String> races = this.plugin.getConfig().getConfigurationSection("Races").getKeys(false);
    rpp.getPlayer().sendMessage(Lang.CARD_SELECT_RACE.toString());
    for (String race : races)
      sendJson(rpp.getPlayer(),
          "[\"\",{\"text\":\"" + race + "\",\"color\":\""
              + this.plugin.getConfig().getString("Races." + race + ".Color").toLowerCase()
              + "\",\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/card race " + race
              + "\"},\"hoverEvent\":{\"action\":\"show_text\",\"value\":{\"text\":\"\",\"extra\":[{\"text\":\""
              + Lang.CARD_CLICK_TO_RACE.toString().replace("%r", race) + "\",\"color\":\"aqua\"}]}}}]");
  }

  public void sendNations(RoleplayPlayer rpp) {
    Set<String> nations = this.plugin.getConfig().getConfigurationSection("Nations").getKeys(false);
    rpp.getPlayer().sendMessage(Lang.CARD_SELECT_NATION.toString());
    for (String nation : nations)
      sendJson(rpp.getPlayer(),
          "[\"\",{\"text\":\"" + nation
              + "\",\"color\":\"white\",\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/card nation " + nation
              + "\"},\"hoverEvent\":{\"action\":\"show_text\",\"value\":{\"text\":\"\",\"extra\":[{\"text\":\""
              + Lang.CARD_CLICK_TO_NATION.toString().replace("%n", nation) + "\",\"color\":\"aqua\"}]}}}]");
  }

  public void clickToSendCard(Player p) {
    sendJson(p, "[\"\",{\"text\":\"" + Lang.CARD_CLICK_TO_SHOW.toString()
        + "\",\"color\":\"aqua\",\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/card\"},\"hoverEvent\":{\"action\":\"show_text\",\"value\":{\"text\":\"\",\"extra\":[{\"text\":\""
        + Lang.CARD_CLICK_TO_SHOW.toString() + "\",\"color\":\"aqua\"}]}}}]");
  }

  public void sendJson(Player p, String json) {
    Bukkit.getServer().dispatchCommand((CommandSender) Bukkit.getConsoleSender(),
        "tellraw " + p.getName() + " " + json);
  }
}

/*
 * Location:
 * C:\Users\develmode\dev\msp-basic-chat\server\plugins\RPEngine-4.5.3.jar!\com\
 * Alvaero\\utils\Card.class Java compiler version: 8 (52.0) JD-Core Version:
 * 1.1.3
 */