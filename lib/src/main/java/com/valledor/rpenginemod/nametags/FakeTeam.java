package com.valledor.rpenginemod.nametags;

import java.util.ArrayList;
import java.util.UUID;

public class FakeTeam {
  public void setName(String name) {
    this.name = name;
  }

  public void setPrefix(String prefix) {
    this.prefix = prefix;
  }

  public void setSuffix(String suffix) {
    this.suffix = suffix;
  }

  public boolean equals(Object o) {
    if (o == this)
      return true;
    if (!(o instanceof FakeTeam))
      return false;
    FakeTeam other = (FakeTeam) o;
    if (!other.canEqual(this))
      return false;

    if ((this.members == null) ? (other.members != null) : !this.members.equals(other.members))
      return false;

    this.name = getName();
    other.name = other.getName();
    if ((this.name == null) ? (other.name != null) : !this.name.equals(other.name))
      return false;

    this.prefix = getPrefix();
    other.prefix = other.getPrefix();
    if ((this.prefix == null) ? (other.prefix != null) : !this.prefix.equals(other.prefix))
      return false;

    this.suffix = getSuffix();
    other.suffix = other.getSuffix();
    return !((this.suffix == null) ? (other.suffix != null) : !this.suffix.equals(other.suffix));
  }

  protected boolean canEqual(Object other) {
    return other instanceof FakeTeam;
  }

  public int hashCode() {
    int PRIME = 59;
    int result = 1;
    ArrayList<String> members = getMembers();
    result = result * 59 + ((members == null) ? 43 : members.hashCode());
    Object $name = getName();
    result = result * 59 + (($name == null) ? 43 : $name.hashCode());
    Object $prefix = getPrefix();
    result = result * 59 + (($prefix == null) ? 43 : $prefix.hashCode());
    Object $suffix = getSuffix();
    return result * 59 + (($suffix == null) ? 43 : $suffix.hashCode());
  }

  public String toString() {
    return "FakeTeam(members=" + getMembers() + ", name=" + getName() + ", prefix=" + getPrefix() + ", suffix="
        + getSuffix() + ")";
  }

  private static final String UNIQUE_ID = UUID.randomUUID().toString().replaceAll("[^a-zA-Z]", "").toUpperCase()
      .substring(0, 5);

  private static int ID = 0;

  private final ArrayList<String> members = new ArrayList<>();

  private String name;

  public ArrayList<String> getMembers() {
    return this.members;
  }

  public String getName() {
    return this.name;
  }

  private String prefix = "";

  public String getPrefix() {
    return this.prefix;
  }

  private String suffix = "";

  public String getSuffix() {
    return this.suffix;
  }

  public FakeTeam(String prefix, String suffix, int sortPriority, boolean playerTag) {
    this.name = UNIQUE_ID + "_" + getNameFromInput(sortPriority) + ++ID + (playerTag ? "+P" : "");
    this.name = (this.name.length() > 16) ? this.name.substring(0, 16) : this.name;
    this.prefix = prefix;
    this.suffix = suffix;
  }

  public void addMember(String player) {
    if (!this.members.contains(player))
      this.members.add(player);
  }

  public boolean isSimilar(String prefix, String suffix) {
    return (this.prefix.equals(prefix) && this.suffix.equals(suffix));
  }

  private String getNameFromInput(int input) {
    if (input < 0)
      return "Z";
    char letter = (char) (input / 5 + 65);
    int repeat = input % 5 + 1;
    StringBuilder builder = new StringBuilder();
    for (int i = 0; i < repeat; i++)
      builder.append(letter);
    return builder.toString();
  }
}

/*
 * Location:
 * C:\Users\develmode\dev\msp-basic-chat\server\plugins\RPEngine-4.5.3.jar!\com\
 * Alvaeron\nametags\FakeTeam.class Java compiler version: 8 (52.0) JD-Core
 * Version: 1.1.3
 */