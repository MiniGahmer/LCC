package lcc.lusocraftchampionship.lcc;

public enum LCCPropreties {
  COMMAND_RELOAD_LCC("reloadlcc"),
  COMMAND_START_FLAGSOFWAR("startflagsofwar"),
  COMMAND_START_PARKOURWARRIORS("startparkourwarriors"),
  COMMAND_START_GRAVITYWARS("startgravitywars"),
  COMMAND_START_MGTEST("startmgtest");

  private final String value;

  LCCPropreties(String value) {
    this.value = value;
  }

  public final String getValue() {
    return value;
  }
}
