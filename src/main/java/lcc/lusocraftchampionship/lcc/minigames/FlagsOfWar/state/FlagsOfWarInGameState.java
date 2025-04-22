package lcc.lusocraftchampionship.lcc.minigames.FlagsOfWar.state;

import lcc.lusocraftchampionship.minigame.AStage;
import lcc.lusocraftchampionship.lcc.minigames.FlagsOfWar.FlagsOfWar;
import lcc.lusocraftchampionship.lcc.minigames.FlagsOfWar.FlagsOfWarsStages;
import lcc.lusocraftchampionship.util.Timer;

public class FlagsOfWarInGameState extends AStage<FlagsOfWar, FlagsOfWarsStages> {
  
  public FlagsOfWarInGameState(FlagsOfWar minigame) {
    super(minigame);
  }

  @Override
  public void onEnable() {

  }

  @Override
  public void onUpdate(int ticks, int stopwatch) {

  }

  @Override
  public void onDisable() {

  }

  @Override
  public int stageTime() {
    return Timer.secToTicks(10);
  }
}
