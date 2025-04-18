package lcc.lusocraftchampionship.minigame.MGTest.stages;

import lcc.lusocraftchampionship.lcc.player.VirtualPlayer;
import lcc.lusocraftchampionship.minigame.AStage;
import lcc.lusocraftchampionship.minigame.MGTest.MGStages;
import lcc.lusocraftchampionship.minigame.MGTest.MGTest;

public class MGTestBegin extends AStage<MGTest, MGStages> {
  public MGTestBegin(MGTest minigame) {
    super(minigame);
  }

  @Override
  public void onEnable() {
    for (VirtualPlayer vp : minigame.players) {
      vp.player.sendMessage("Enable Begin");
    }
  }

  @Override
  public void onUpdate(int ticks, int stopwatch) {
    for (VirtualPlayer vp : minigame.players) {
      vp.player.sendMessage("Update Begin");
    }
  }

  @Override
  public void onDisable() {
    for (VirtualPlayer vp : minigame.players) {
      vp.player.sendMessage("Disable Begin");
    }
  }

  @Override
  public int stageTime() {
    return 10;
  }
}
