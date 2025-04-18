package lcc.lusocraftchampionship.minigame.MGTest.stages;

import lcc.lusocraftchampionship.lcc.player.VirtualPlayer;
import lcc.lusocraftchampionship.minigame.AStage;
import lcc.lusocraftchampionship.minigame.MGTest.MGStages;
import lcc.lusocraftchampionship.minigame.MGTest.MGTest;

public class MGTestEnd extends AStage<MGTest, MGStages> {
  public MGTestEnd(MGTest minigame) {
    super(minigame);
  }

  @Override
  public void onEnable() {
    for (VirtualPlayer vp : minigame.players) {
      vp.player.sendMessage("Enable End");
    }
  }

  @Override
  public void onUpdate(int ticks, int stopwatch) {
    for (VirtualPlayer vp : minigame.players) {
      vp.player.sendMessage("Update End");
    }

    
  }

  @Override
  public void onDisable() {
    for (VirtualPlayer vp : minigame.players) {
      vp.player.sendMessage("Disable End");
    }
  }

}
