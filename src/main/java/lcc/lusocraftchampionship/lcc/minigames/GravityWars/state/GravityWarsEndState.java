package lcc.lusocraftchampionship.minigame.GravityWars.state;

import lcc.lusocraftchampionship.lcc.player.VirtualPlayer;
import lcc.lusocraftchampionship.lcc.team.Teams;
import lcc.lusocraftchampionship.minigame.AMinigame;
import lcc.lusocraftchampionship.minigame.AStage;
import lcc.lusocraftchampionship.minigame.IMinigame;
import lcc.lusocraftchampionship.minigame.GravityWars.GravityWars;
import lcc.lusocraftchampionship.minigame.GravityWars.GravityWarsStages;
import lcc.lusocraftchampionship.util.Timer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class GravityWarsEndState extends AStage<GravityWars, GravityWarsStages> {
  public GravityWarsEndState(GravityWars minigame) {
    super(minigame);
  }

  /*@Override
  public void onUpdate(int ticks, int stopwatch) {
    for (VirtualPlayer player : minigame.players) {
      playerImmune(player);
    }

    if (Timer.isTicksEqualSec(stopwatch, 40))
      sendTeamStatus(minigame.TOP_TEAMS, minigame.TEAM_POINTS, "PONTUAÇÕES DAS EQUIPAS");

    if (Timer.isTicksEqualSec(stopwatch, 30))
      sendPlayerStatus(minigame.TOP_PLAYERS_KILLS, minigame.PLAYER_KILLS, "TOP 5 PLAYERS (§r§c§lKills§r§l)");
  }

  @Override
  public void onDisable() {
    if (!minigame.isTesting) {
      for (String team : minigame.TEAM_POINTS.keySet()) {
        Teams.INSTANCE.addPointTeam(team, minigame.TEAM_POINTS.get(team));
      }
    }
  }

  @Override
  public int stageTime() {
    return Timer.secToTicks(45);
  }*/

}
