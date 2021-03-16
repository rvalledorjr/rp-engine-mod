package com.develmode.rpenginemod.nametags;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.bukkit.entity.Player;

public class PacketWrapper {
  public String error;

  private Object packet = PacketAccessor.createPacket();

  public PacketWrapper(String name, int param, List<String> members) {
    if (param != 3 && param != 4)
      throw new IllegalArgumentException("Method must be join or leave for player constructor");
    setupDefaults(name, param);
    setupMembers(members);
  }

  public PacketWrapper(String name, String prefix, String suffix, int param, Collection<?> players) {
    setupDefaults(name, param);
    if (param == 0 || param == 2)
      try {
        PacketAccessor.DISPLAY_NAME.set(this.packet, name);
        PacketAccessor.PREFIX.set(this.packet, prefix);
        PacketAccessor.SUFFIX.set(this.packet, suffix);
        PacketAccessor.PACK_OPTION.set(this.packet, Integer.valueOf(1));
        if (PacketAccessor.VISIBILITY != null)
          PacketAccessor.VISIBILITY.set(this.packet, "always");
        if (param == 0)
          ((Collection) PacketAccessor.MEMBERS.get(this.packet)).addAll(players);
      } catch (Exception e) {
        this.error = e.getMessage();
      }
  }

  private void setupMembers(Collection<?> players) {
    try {
      players = (players == null || players.isEmpty()) ? new ArrayList() : players;
      ((Collection) PacketAccessor.MEMBERS.get(this.packet)).addAll(players);
    } catch (Exception e) {
      this.error = e.getMessage();
    }
  }

  private void setupDefaults(String name, int param) {
    try {
      PacketAccessor.TEAM_NAME.set(this.packet, name);
      PacketAccessor.PARAM_INT.set(this.packet, Integer.valueOf(param));
    } catch (Exception e) {
      this.error = e.getMessage();
    }
  }

  public void send() {
    PacketAccessor.sendPacket(Utils.getOnline(), this.packet);
  }

  public void send(Player player) {
    PacketAccessor.sendPacket(player, this.packet);
  }
}

/*
 * Location:
 * C:\Users\develmode\dev\msp-basic-chat\server\plugins\RPEngine-4.5.3.jar!\com\
 * Alvaeron\nametags\PacketWrapper.class Java compiler version: 8 (52.0) JD-Core
 * Version: 1.1.3
 */