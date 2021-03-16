package com.valledor.rpenginemod.nametags;

import com.valledor.rpenginemod.Engine;
import java.beans.ConstructorProperties;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

public class NametagManager {
  private final HashMap<String, FakeTeam> TEAMS;

  private final HashMap<String, FakeTeam> CACHED_FAKE_TEAMS;

  private Engine plugin;

  @ConstructorProperties({ "plugin" })
  public NametagManager(Engine plugin) {
    this.TEAMS = new HashMap<>();
    this.CACHED_FAKE_TEAMS = new HashMap<>();
    this.plugin = plugin;
  }

  private FakeTeam getFakeTeam(String prefix, String suffix) {
    for (FakeTeam fakeTeam : this.TEAMS.values()) {
      if (fakeTeam.isSimilar(prefix, suffix))
        return fakeTeam;
    }
    return null;
  }

  private void addPlayerToTeam(String player, String prefix, String suffix, int sortPriority, boolean playerTag) {
    FakeTeam previous = getFakeTeam(player);
    if (previous != null && previous.isSimilar(prefix, suffix)) {
      this.plugin.debug(player + " already belongs to a similar team (" + previous.getName() + ")");
      return;
    }
    reset(player);
    FakeTeam joining = getFakeTeam(prefix, suffix);
    if (joining != null) {
      joining.addMember(player);
      this.plugin.debug("Using existing team for " + player);
    } else {
      joining = new FakeTeam(prefix, suffix, sortPriority, playerTag);
      joining.addMember(player);
      this.TEAMS.put(joining.getName(), joining);
      addTeamPackets(joining);
      this.plugin.debug("Created FakeTeam " + joining.getName() + ". Size: " + this.TEAMS.size());
    }
    Player adding = Bukkit.getPlayerExact(player);
    if (adding != null) {
      addPlayerToTeamPackets(joining, adding.getName());
      cache(adding.getName(), joining);
    } else {
      OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(player);
      addPlayerToTeamPackets(joining, offlinePlayer.getName());
      cache(offlinePlayer.getName(), joining);
    }
    this.plugin.debug(player + " has been added to team " + joining.getName());
  }

  public FakeTeam reset(String player) {
    return reset(player, decache(player));
  }

  private FakeTeam reset(String player, FakeTeam fakeTeam) {
    if (fakeTeam != null && fakeTeam.getMembers().remove(player)) {
      boolean delete;
      Player removing = Bukkit.getPlayerExact(player);
      if (removing != null) {
        delete = removePlayerFromTeamPackets(fakeTeam, new String[] { removing.getName() });
      } else {
        OfflinePlayer toRemoveOffline = Bukkit.getOfflinePlayer(player);
        delete = removePlayerFromTeamPackets(fakeTeam, new String[] { toRemoveOffline.getName() });
      }
      this.plugin.debug(player + " was removed from " + fakeTeam.getName());
      if (delete) {
        removeTeamPackets(fakeTeam);
        this.TEAMS.remove(fakeTeam.getName());
        this.plugin.debug("FakeTeam " + fakeTeam.getName() + " has been deleted. Size: " + this.TEAMS.size());
      }
    }
    return fakeTeam;
  }

  private FakeTeam decache(String player) {
    return this.CACHED_FAKE_TEAMS.remove(player);
  }

  public FakeTeam getFakeTeam(String player) {
    return this.CACHED_FAKE_TEAMS.get(player);
  }

  private void cache(String player, FakeTeam fakeTeam) {
    this.CACHED_FAKE_TEAMS.put(player, fakeTeam);
  }

  public void setNametag(String player, String prefix, String suffix) {
    setNametag(player, prefix, suffix, -1);
  }

  void setNametag(String player, String prefix, String suffix, int sortPriority) {
    setNametag(player, prefix, suffix, sortPriority, false);
  }

  void setNametag(String player, String prefix, String suffix, int sortPriority, boolean playerTag) {
    addPlayerToTeam(player, (prefix != null) ? prefix : "", (suffix != null) ? suffix : "", sortPriority, playerTag);
  }

  public void sendTeams(Player player) {
    for (FakeTeam fakeTeam : this.TEAMS.values())
      (new PacketWrapper(fakeTeam.getName(), fakeTeam.getPrefix(), fakeTeam.getSuffix(), 0, fakeTeam.getMembers()))
          .send(player);
  }

  void reset() {
    for (FakeTeam fakeTeam : this.TEAMS.values()) {
      removePlayerFromTeamPackets(fakeTeam, fakeTeam.getMembers());
      removeTeamPackets(fakeTeam);
    }
    this.CACHED_FAKE_TEAMS.clear();
    this.TEAMS.clear();
  }

  private void removeTeamPackets(FakeTeam fakeTeam) {
    (new PacketWrapper(fakeTeam.getName(), fakeTeam.getPrefix(), fakeTeam.getSuffix(), 1, new ArrayList())).send();
  }

  private boolean removePlayerFromTeamPackets(FakeTeam fakeTeam, String... players) {
    return removePlayerFromTeamPackets(fakeTeam, Arrays.asList(players));
  }

  private boolean removePlayerFromTeamPackets(FakeTeam fakeTeam, List<String> players) {
    (new PacketWrapper(fakeTeam.getName(), 4, players)).send();
    fakeTeam.getMembers().removeAll(players);
    return fakeTeam.getMembers().isEmpty();
  }

  private void addTeamPackets(FakeTeam fakeTeam) {
    (new PacketWrapper(fakeTeam.getName(), fakeTeam.getPrefix(), fakeTeam.getSuffix(), 0, fakeTeam.getMembers()))
        .send();
  }

  private void addPlayerToTeamPackets(FakeTeam fakeTeam, String player) {
    (new PacketWrapper(fakeTeam.getName(), 3, Collections.singletonList(player))).send();
  }
}

/*
 * Location:
 * C:\Users\develmode\dev\msp-basic-chat\server\plugins\RPEngine-4.5.3.jar!\com\
 * Alvaeron\nametags\NametagManager.class Java compiler version: 8 (52.0)
 * JD-Core Version: 1.1.3
 */