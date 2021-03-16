package com.develmode.rpenginemod.player;

import com.develmode.rpenginemod.Engine;
import java.beans.ConstructorProperties;
import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class RoleplayPlayer {
  UUID uuid;

  String playerName;

  @ConstructorProperties({ "uuid", "playerName", "name", "race", "nation", "gender", "age", "desc", "channel", "OOC",
      "online", "plugin" })
  public RoleplayPlayer(UUID uuid, String playerName, String name, String race, String nation, Gender gender, int age,
      String desc, Channel channel, boolean OOC, boolean online, Engine plugin) {
    this.uuid = uuid;
    this.playerName = playerName;
    this.name = name;
    this.race = race;
    this.nation = nation;
    this.gender = gender;
    this.age = age;
    this.desc = desc;
    this.channel = channel;
    this.OOC = OOC;
    this.online = online;
    this.plugin = plugin;
  }

  public UUID getUuid() {
    return this.uuid;
  }

  public String getPlayerName() {
    return this.playerName;
  }

  String name = "NONE";

  public String getName() {
    return this.name;
  }

  String race = "NONE";

  public String getRace() {
    return this.race;
  }

  String nation = "NONE";

  public String getNation() {
    return this.nation;
  }

  Gender gender = Gender.NONE;

  public Gender getGender() {
    return this.gender;
  }

  int age = 0;

  public int getAge() {
    return this.age;
  }

  String desc = "NONE";

  public String getDesc() {
    return this.desc;
  }

  Channel channel = Channel.RP;

  public Channel getChannel() {
    return this.channel;
  }

  boolean OOC = false;

  public boolean isOOC() {
    return this.OOC;
  }

  boolean online = true;

  private Engine plugin;

  public boolean isOnline() {
    return this.online;
  }

  public Engine getPlugin() {
    return this.plugin;
  }

  public RoleplayPlayer(Player pl) {
    this.uuid = pl.getUniqueId();
    this.playerName = pl.getName();
  }

  public Player getPlayer() {
    if (Bukkit.getPlayer(this.uuid) != null)
      return Bukkit.getPlayer(this.uuid);
    return (Player) Bukkit.getOfflinePlayer(this.uuid);
  }

  public void setGender(Gender gender) {
    this.gender = gender;
    Engine.mm.setStringField(this.uuid, "gender", gender.name());
  }

  public void setAge(int age) {
    this.age = age;
    getPlayer().sendMessage(ChatColor.GREEN + "Age updated to" + ChatColor.WHITE + ": " + age);
    Engine.mm.setStringField(this.uuid, "age", age + "");
  }

  public void setDesc(String description) {
    this.desc = description;
    Engine.mm.setStringField(this.uuid, "desc", this.desc);
  }

  public void setName(String name) {
    this.name = name;
    Engine.mm.setStringField(this.uuid, "name", name);
    setTag();
  }

  public void setRace(String race) {
    this.race = race;
    Engine.mm.setStringField(this.uuid, "race", race);
    setTag();
  }

  public void setNation(String nation) {
    this.nation = nation;
    Engine.mm.setStringField(this.uuid, "nation", nation);
  }

  public void switchOOC() {
    if (this.OOC) {
      getPlayer().sendMessage(ChatColor.RED + "OOC chat is off now");
      Engine.mm.setStringField(this.uuid, "ooc", "0");
      this.OOC = false;
    } else {
      getPlayer().sendMessage(ChatColor.GREEN + "OOC chat is on now");
      Engine.mm.setStringField(this.uuid, "ooc", "1");
      this.OOC = true;
    }
  }

  public void setChannel(Channel channel) {
    if (this.channel == channel) {
      getPlayer()
          .sendMessage(ChatColor.YELLOW + "You are already chatting in" + ChatColor.WHITE + ": " + channel.name());
    } else {
      getPlayer().sendMessage(ChatColor.YELLOW + "You are now chatting in" + ChatColor.WHITE + ": " + channel.name());
    }
    this.channel = channel;
  }

  public void setOOC(boolean ooc) {
    this.OOC = ooc;
  }

  public void setTag() {
    Engine.nametags.setNametag(getPlayer().getName(), Engine.mu.getRaceColour(this.race)
        + this.name.substring(0, Math.min(9, this.name.length())) + ChatColor.GRAY + " [", "] ");
  }

  public enum Channel {
    OOC, RP;
  }

  public enum Gender {
    MALE, FEMALE, NONE;

    public String getName() {
      return (this == NONE) ? "NONE"
          : (Character.toString(name().charAt(0)).toUpperCase() + name().toLowerCase().substring(1));
    }
  }
}

/*
 * Location:
 * C:\Users\develmode\dev\msp-basic-chat\server\plugins\RPEngine-4.5.3.jar!\com\
 * Alvaeron\player\RoleplayPlayer.class Java compiler version: 8 (52.0) JD-Core
 * Version: 1.1.3
 */