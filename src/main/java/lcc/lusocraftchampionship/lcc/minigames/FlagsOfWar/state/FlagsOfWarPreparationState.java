package lcc.lusocraftchampionship.minigame.FlagsOfWar.state;

import lcc.lusocraftchampionship.LCCPlugin;
import lcc.lusocraftchampionship.lcc.player.VirtualPlayer;
import lcc.lusocraftchampionship.lcc.team.Teams;
import lcc.lusocraftchampionship.minigame.AStage;
import lcc.lusocraftchampionship.minigame.FlagsOfWar.FlagsOfWar;
import lcc.lusocraftchampionship.minigame.FlagsOfWar.FlagsOfWarsStages;
import lcc.lusocraftchampionship.minigame.GravityWars.GravityWars;
import lcc.lusocraftchampionship.minigame.GravityWars.GravityWarsStages;
import lcc.lusocraftchampionship.util.Timer;
import org.bukkit.Bukkit;
import org.bukkit.Location;

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
