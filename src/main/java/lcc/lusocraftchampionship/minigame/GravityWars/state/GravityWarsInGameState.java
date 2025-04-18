package lcc.lusocraftchampionship.minigame.GravityWars.state;

import lcc.lusocraftchampionship.lcc.player.VirtualPlayer;
import lcc.lusocraftchampionship.lcc.team.Teams;
import lcc.lusocraftchampionship.minigame.AMinigame;
import lcc.lusocraftchampionship.minigame.AStage;
import lcc.lusocraftchampionship.minigame.GravityWars.GravityWars;
import lcc.lusocraftchampionship.minigame.GravityWars.GravityWarsStages;
import lcc.lusocraftchampionship.util.Timer;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.Arrays;
import java.util.List;

public class GravityWarsInGameState extends AStage<GravityWars, GravityWarsStages> {

  protected GravityWarsInGameState(GravityWars minigame) {
    super(minigame);
    // TODO Auto-generated constructor stub
  }

  /*@Override
  public void onEnable() {
    for (VirtualPlayer vp : minigame.players) {
      if (minigame.getTeam1().contains(Teams.INSTANCE.getPlayerTeam(player))) {
        player.teleport(minigame.getTeam1Spawn());
        player.setBedSpawnLocation(minigame.getTeam1Spawn(), true);
        minigame.givePlayerKit(player);
      }

      if (minigame.getTeam2().contains(Teams.INSTANCE.getPlayerTeam(player))) {
        player.teleport(minigame.getTeam2Spawn());
        player.setBedSpawnLocation(minigame.getTeam2Spawn(), true);
        minigame.givePlayerKit(player);
      }

      if (minigame.getTeam3().contains(Teams.INSTANCE.getPlayerTeam(player))) {
        player.teleport(minigame.getTeam3Spawn());
        player.setBedSpawnLocation(minigame.getTeam3Spawn(), true);
        minigame.givePlayerKit(player);
      }

      if (minigame.getTeam4().contains(Teams.INSTANCE.getPlayerTeam(player))) {
        player.teleport(minigame.getTeam4Spawn());
        player.setBedSpawnLocation(minigame.getTeam4Spawn(), true);
        minigame.givePlayerKit(player);
      }

      minigame.givePlayerGravityDevice(player);
      player.setInvulnerable(false);
    }
  }

  @Override
  public void onUpdate(int ticks, int stopwatch) {
    List<List<Location>> allTotems = Arrays.asList(minigame.getRedTotem(), minigame.getBlueTotem(),
        minigame.getGreenTotem(), minigame.getObsidianTotem());

    for (VirtualPlayer vp : minigame.players) {
      // Get the team of the player
      // String playerTeam = Teams.INSTANCE.getPlayerTeam(player); // Implement this method to get the player's team
      // Load the restricted index for the team from Teams.INSTANCE.yml
      int restrictedIndex = vp.team.totem;
      // Ensure the player's team exists in the restriction map
      if (restrictedIndex == -1) {
        return; // If the restricted index is not found, do nothing
      }

      boolean hasAir = false;

      for (List<Location> totemList : allTotems) {
        if (totemList.get(restrictedIndex).getBlock().getType() == Material.AIR) {
          hasAir = true;
          break; // No need to check further if an AIR block is found
        }
      }

      if (!hasAir) {
        jumpToState(GravityWarsStages.END);
        minigame.spawnBlackHole();
        return;
      }

      vp.player.setFoodLevel(20);
    }
  }

  @Override
  public void onDisable() {
    for (VirtualPlayer vp : minigame.players) {
      vp.player.sendTitle("ꀃ", " ", 0, 80, 20);
      vp.player.setVelocity(new Vector(0, 2, 0));
      clearPlayer(vp.player, minigame.plugin);
    }

    jumpToState(GravityWarsStages.END);
    soundEndgame();
  }

  @Override
  public int stageTime() {
    return Timer.minToTicks(60);
  }*/

}
