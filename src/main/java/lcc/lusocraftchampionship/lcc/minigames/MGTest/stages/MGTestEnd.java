package lcc.lusocraftchampionship.lcc.minigames.MGTest.stages;

import lcc.lusocraftchampionship.lcc.player.VirtualPlayer;
import lcc.lusocraftchampionship.minigame.AStage;
import lcc.lusocraftchampionship.minigame.MinigamePlayer;
import lcc.lusocraftchampionship.minigame.stats.KillStats;
import lcc.lusocraftchampionship.lcc.minigames.MGTest.MGStages;
import lcc.lusocraftchampionship.lcc.minigames.MGTest.MGTest;
import org.bukkit.Bukkit;

public class MGTestEnd extends AStage<MGTest, MGStages> {
  public MGTestEnd(MGTest minigame) {
    super(minigame);
  }

  @Override
  public void onEnable() {
    Bukkit.broadcastMessage("Enable End");

    for (VirtualPlayer vp : minigame.players) {
      vp.player.sendMessage("Enable End");
    }
  }

  @Override
  public void onUpdate(int ticks, int stopwatch) {
    Bukkit.broadcastMessage("Update End");
    // jumpToState(MGStages.BEGIN);

    for (MinigamePlayer mp : minigame.players) {
      mp.setStatValue(KillStats.class, 10);
      Bukkit.broadcastMessage("Kills " + mp.getStat(KillStats.class));
    }
  }

  @Override
  public void onDisable() {
    Bukkit.broadcastMessage("Disable End");

    for (VirtualPlayer vp : minigame.players) {
      vp.player.sendMessage("Disable End");
    }
  }

}
