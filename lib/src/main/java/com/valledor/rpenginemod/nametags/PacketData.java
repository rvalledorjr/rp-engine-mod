package com.valledor.rpenginemod.nametags;

import java.beans.ConstructorProperties;

enum PacketData {
  v1_7("e", "c", "d", "a", "f", "g", "b", "NA", "NA"),
  cauldron("field_149317_e", "field_149319_c", "field_149316_d", "field_149320_a", "field_149314_f", "field_149315_g",
      "field_149318_b", "NA", "NA"),
  v1_8("g", "c", "d", "a", "h", "i", "b", "NA", "e"), v1_9("h", "c", "d", "a", "i", "j", "b", "f", "e"),
  v1_10("h", "c", "d", "a", "i", "j", "b", "f", "e"), v1_11("h", "c", "d", "a", "i", "j", "b", "f", "e"),
  v1_12("h", "c", "d", "a", "i", "j", "b", "f", "e");

  @ConstructorProperties({ "members", "prefix", "suffix", "teamName", "paramInt", "packOption", "displayName", "push",
      "visibility" })
  PacketData(String members, String prefix, String suffix, String teamName, String paramInt, String packOption,
      String displayName, String push, String visibility) {
    this.members = members;
    this.prefix = prefix;
    this.suffix = suffix;
    this.teamName = teamName;
    this.paramInt = paramInt;
    this.packOption = packOption;
    this.displayName = displayName;
    this.push = push;
    this.visibility = visibility;
  }

  private String members;

  private String prefix;

  private String suffix;

  private String teamName;

  private String paramInt;

  private String packOption;

  private String displayName;

  private String push;

  private String visibility;

  public String getMembers() {
    return this.members;
  }

  public String getPrefix() {
    return this.prefix;
  }

  public String getSuffix() {
    return this.suffix;
  }

  public String getTeamName() {
    return this.teamName;
  }

  public String getParamInt() {
    return this.paramInt;
  }

  public String getPackOption() {
    return this.packOption;
  }

  public String getDisplayName() {
    return this.displayName;
  }

  public String getPush() {
    return this.push;
  }

  public String getVisibility() {
    return this.visibility;
  }
}

/*
 * Location:
 * C:\Users\develmode\dev\msp-basic-chat\server\plugins\RPEngine-4.5.3.jar!\com\
 * Alvaeron\nametags\PacketData.class Java compiler version: 8 (52.0) JD-Core
 * Version: 1.1.3
 */