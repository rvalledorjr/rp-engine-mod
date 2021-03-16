package com.develmode.rpenginemod.listeners;

import com.develmode.rpenginemod.player.RoleplayPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class CardEditEvent extends Event {
  private CardField cardField = null;

  private String stringValue = null;

  private int intValue = 0;

  private RoleplayPlayer.Gender gender = null;

  private Player p = null;

  private RoleplayPlayer rpp = null;

  private boolean cancelled = false;

  public CardEditEvent(CardField cardField, String value, Player p, RoleplayPlayer rpp) {
    this.cardField = cardField;
    this.stringValue = value;
    this.p = p;
    this.rpp = rpp;
  }

  public CardEditEvent(CardField cardField, int value, Player p, RoleplayPlayer rpp) {
    this.cardField = cardField;
    this.intValue = value;
    this.p = p;
    this.rpp = rpp;
  }

  public CardEditEvent(CardField cardField, RoleplayPlayer.Gender value, Player p, RoleplayPlayer rpp) {
    this.cardField = cardField;
    this.gender = value;
    this.p = p;
    this.rpp = rpp;
  }

  public enum CardField {
    NAME, AGE, GENDER, RACE, NATION, DESC;
  }

  public String getStringValue() {
    return this.stringValue;
  }

  public int getIntValue() {
    return this.intValue;
  }

  public RoleplayPlayer.Gender getGender() {
    return this.gender;
  }

  public CardField getCardField() {
    return this.cardField;
  }

  public Player getPlayer() {
    return this.p;
  }

  public RoleplayPlayer getRoleplayPlayer() {
    return this.rpp;
  }

  public void setStringValue(String value) {
    this.stringValue = value;
  }

  public void setIntValue(int value) {
    this.intValue = value;
  }

  public void setGender(RoleplayPlayer.Gender value) {
    this.gender = value;
  }

  public boolean isCancelled() {
    return this.cancelled;
  }

  public void setCancelled(boolean cancel) {
    this.cancelled = cancel;
  }

  private static final HandlerList handlers = new HandlerList();

  public HandlerList getHandlers() {
    return handlers;
  }

  public static HandlerList getHandlerList() {
    return handlers;
  }
}

/*
 * Location:
 * C:\Users\develmode\dev\msp-basic-chat\server\plugins\RPEngine-4.5.3.jar!\com\
 * Alvaeron\listeners\CardEditEvent.class Java compiler version: 8 (52.0)
 * JD-Core Version: 1.1.3
 */