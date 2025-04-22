package lcc.lusocraftchampionship.lcc.minigames.FlagsOfWar.state;

import lcc.lusocraftchampionship.LCCPlugin;
import lcc.lusocraftchampionship.lcc.player.VirtualPlayer;
import lcc.lusocraftchampionship.minigame.AStage;
import lcc.lusocraftchampionship.lcc.minigames.FlagsOfWar.FlagsOfWar;
import lcc.lusocraftchampionship.lcc.minigames.FlagsOfWar.FlagsOfWarsStages;
import lcc.lusocraftchampionship.util.Timer;

public class FlagsOfWarPreparationState extends AStage<FlagsOfWar, FlagsOfWarsStages> {

  public FlagsOfWarPreparationState(FlagsOfWar minigame) {
    super(minigame);
  }

  @Override
  public void onEnable() {
    LCCPlugin.LOGGER.info("Preparation state enabled");

    LCCPlugin.LOGGER.info(minigame.players.toString());
    for (VirtualPlayer vp : minigame.players) {

      // String playerTeam = vp.team.name;

      // Location spawn = minigame.getTeamSpawns().get(playerTeam);
      // if (spawn != null) {
      //   vp.player.teleport(spawn);
      //   vp.player.setBedSpawnLocation(spawn, true);
      // }
      giveBoots(vp);
    }
  }

  @Override
  public void onUpdate(int ticks, int stopwatch) {
    countdownTeamPlayer(stopwatch, 5);
  }

  @Override
  public void onDisable() {

  }

  @Override
  public int stageTime() {
    return Timer.secToTicks(10);
  }
}
