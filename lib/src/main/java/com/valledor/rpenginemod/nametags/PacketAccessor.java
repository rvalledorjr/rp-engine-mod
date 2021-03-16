package com.valledor.rpenginemod.nametags;

import com.valledor.rpenginemod.Engine;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Collection;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

class PacketAccessor {
  static Field MEMBERS;

  static Field PREFIX;

  static Field SUFFIX;

  static Field TEAM_NAME;

  static Field PARAM_INT;

  static Field PACK_OPTION;

  static Field DISPLAY_NAME;

  static Field PUSH;

  static Field VISIBILITY;

  private static Method getHandle;

  private static Method sendPacket;

  private static Field playerConnection;

  private static Class<?> packetClass;

  static {
    try {
      String version = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
      Class<?> typeCraftPlayer = Class.forName("org.bukkit.craftbukkit." + version + ".entity.CraftPlayer");
      getHandle = typeCraftPlayer.getMethod("getHandle", new Class[0]);
      packetClass = Class.forName("net.minecraft.server." + version + ".PacketPlayOutScoreboardTeam");
      Class<?> typeNMSPlayer = Class.forName("net.minecraft.server." + version + ".EntityPlayer");
      Class<?> typePlayerConnection = Class.forName("net.minecraft.server." + version + ".PlayerConnection");
      playerConnection = typeNMSPlayer.getField("playerConnection");
      sendPacket = typePlayerConnection.getMethod("sendPacket",
          new Class[] { Class.forName("net.minecraft.server." + version + ".Packet") });
      PacketData currentVersion = null;
      for (PacketData packetData : PacketData.values()) {
        if (version.contains(packetData.name()))
          currentVersion = packetData;
      }
      if (currentVersion != null) {
        PREFIX = getNMS(currentVersion.getPrefix());
        SUFFIX = getNMS(currentVersion.getSuffix());
        MEMBERS = getNMS(currentVersion.getMembers());
        TEAM_NAME = getNMS(currentVersion.getTeamName());
        PARAM_INT = getNMS(currentVersion.getParamInt());
        PACK_OPTION = getNMS(currentVersion.getPackOption());
        DISPLAY_NAME = getNMS(currentVersion.getDisplayName());
        if (isPushVersion(version))
          PUSH = getNMS(currentVersion.getPush());
        if (isVisibilityVersion(version))
          VISIBILITY = getNMS(currentVersion.getVisibility());
      }
    } catch (Exception e) {
      if (Engine.utils.sendDebug())
        e.printStackTrace();
    }
  }

  private static boolean isPushVersion(String version) {
    return (Integer.parseInt(version.split("_")[1]) >= 9);
  }

  private static boolean isVisibilityVersion(String version) {
    return (Integer.parseInt(version.split("_")[1]) >= 8);
  }

  private static Field getNMS(String path) throws Exception {
    Field field = packetClass.getDeclaredField(path);
    field.setAccessible(true);
    return field;
  }

  static Object createPacket() {
    try {
      return packetClass.newInstance();
    } catch (Exception e) {
      if (Engine.utils.sendDebug())
        e.printStackTrace();
      return null;
    }
  }

  static void sendPacket(Collection<? extends Player> players, Object packet) {
    for (Player player : players)
      sendPacket(player, packet);
  }

  static void sendPacket(Player player, Object packet) {
    try {
      Object nmsPlayer = getHandle.invoke(player, new Object[0]);
      Object connection = playerConnection.get(nmsPlayer);
      sendPacket.invoke(connection, new Object[] { packet });
    } catch (Exception e) {
      if (Engine.utils.sendDebug())
        e.printStackTrace();
    }
  }
}

/*
 * Location:
 * C:\Users\develmode\dev\msp-basic-chat\server\plugins\RPEngine-4.5.3.jar!\com\
 * Alvaeron\nametags\PacketAccessor.class Java compiler version: 8 (52.0)
 * JD-Core Version: 1.1.3
 */