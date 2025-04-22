package lcc.lusocraftchampionship.lcc.minigames.FlagsOfWar.state;

import lcc.lusocraftchampionship.minigame.AStage;
import lcc.lusocraftchampionship.lcc.minigames.FlagsOfWar.FlagsOfWar;
import lcc.lusocraftchampionship.lcc.minigames.FlagsOfWar.FlagsOfWarsStages;
import lcc.lusocraftchampionship.util.Timer;

public class FlagsOfWarExplanationState extends AStage<FlagsOfWar, FlagsOfWarsStages> {
  
  public FlagsOfWarExplanationState(FlagsOfWar minigame) {
    super(minigame);
  }

  @Override
  public void onEnable() {
    jumpToState(FlagsOfWarsStages.PREPARATION);
  }

  @Override
  public void onUpdate(int ticks, int stopwatch) {

  }

  @Override
  public void onDisable() {

  }

  @Override
  public int stageTime() {
    return Timer.secToTicks(30);
  }
}
