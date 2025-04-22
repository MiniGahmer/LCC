package lcc.lusocraftchampionship.minigame.FlagsOfWar.state;

import lcc.lusocraftchampionship.minigame.AStage;
import lcc.lusocraftchampionship.minigame.FlagsOfWar.FlagsOfWar;
import lcc.lusocraftchampionship.minigame.FlagsOfWar.FlagsOfWarsStages;
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
