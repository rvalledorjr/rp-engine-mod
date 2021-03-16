package com.valledor.rpenginemod.player;

import com.valledor.rpenginemod.Engine;
import java.util.ArrayList;
import java.util.UUID;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerManager implements Listener {
  public Engine plugin;

  public ArrayList<RoleplayPlayer> players = new ArrayList<>();

  public PlayerManager(Engine plugin) {
    this.plugin = plugin;
  }

  public RoleplayPlayer getPlayer(UUID uuid) {
    for (RoleplayPlayer p : this.players) {
      if (p.uuid.equals(uuid))
        return p;
    }
    return null;
  }

  @EventHandler
  public void onQuit(PlayerQuitEvent event) {
    clearPlayer(event.getPlayer().getUniqueId());
    Engine.nametags.reset(event.getPlayer().getName());
  }

  public void clearPlayer(UUID u) {
    if (getPlayer(u) != null)
      this.players.remove(getPlayer(u));
  }

  public void addPlayer(RoleplayPlayer rpp) {
    clearPlayer(rpp.uuid);
    this.players.add(rpp);
  }

  @EventHandler
  public void onJoin(PlayerJoinEvent event) {
    Player player = event.getPlayer();
    Engine.nametags.sendTeams(player);
    Engine.nametags.reset(player.getName());
    Engine.mm.createRoleplayPlayer(player);
  }
}

/*
 * Location:
 * C:\Users\develmode\dev\msp-basic-chat\server\plugins\RPEngine-4.5.3.jar!\com\
 * Alvaeron\player\PlayerManager.class Java compiler version: 8 (52.0) JD-Core
 * Version: 1.1.3
 */