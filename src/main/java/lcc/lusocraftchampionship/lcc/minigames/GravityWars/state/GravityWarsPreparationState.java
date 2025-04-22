package lcc.lusocraftchampionship.lcc.minigames.GravityWars.state;

import lcc.lusocraftchampionship.lcc.minigames.GravityWars.GravityWars;
import lcc.lusocraftchampionship.lcc.minigames.GravityWars.GravityWarsStages;
import lcc.lusocraftchampionship.minigame.AStage;

public class GravityWarsPreparationState extends AStage<GravityWars, GravityWarsStages> {

    protected GravityWarsPreparationState(GravityWars minigame) {
        super(minigame);
        // TODO Auto-generated constructor stub
    }
}
/*
  @Override
  public void onEnable() {
    // Loop through each block and set its material
    for (int i = 0; i < minigame.startingBlocks.length; i++) {
      minigame.startingBlocks[i].setType(minigame.materials[i]);
    }

    minigame.PLAYER_KILLER.clear();
    minigame.PLAYER_KILLS.clear();

    for (VirtualPlayer vp : minigame.players) {
      giveBoots(vp);
      vp.player.setInvulnerable(true);
    }
  }

  @Override
  public void onUpdate(int ticks, int stopwatch) {
    // TODO Usar listener aqui
    for (Player player : Bukkit.getOnlinePlayers()) {
      if (player.getGameMode().equals(GameMode.ADVENTURE)) {
        playerImmune(player);
      }
    }

    countdownTeamPlayer(stopwatch, 5);
  }

  @Override
  public void onDisable() {
    for (VirtualPlayer vp : minigame.players) {
      if (minigame.getTeam1().contains(Teams.INSTANCE.getPlayerTeam(player))) {
        player.teleport(minigame.getTeam1Spawn());
        player.setBedSpawnLocation(minigame.getTeam1Spawn(), true);
      }

      if (minigame.getTeam2().contains(Teams.INSTANCE.getPlayerTeam(player))) {
        player.teleport(minigame.getTeam2Spawn());
        player.setBedSpawnLocation(minigame.getTeam2Spawn(), true);
      }

      if (minigame.getTeam3().contains(Teams.INSTANCE.getPlayerTeam(player))) {
        player.teleport(minigame.getTeam3Spawn());
        player.setBedSpawnLocation(minigame.getTeam3Spawn(), true);
      }

      if (minigame.getTeam4().contains(Teams.INSTANCE.getPlayerTeam(player))) {
        player.teleport(minigame.getTeam4Spawn());
        player.setBedSpawnLocation(minigame.getTeam4Spawn(), true);
      }
    }
  }

  @Override
  public int stageTime() {
    return Timer.secToTicks(10);
  }
}
*/
