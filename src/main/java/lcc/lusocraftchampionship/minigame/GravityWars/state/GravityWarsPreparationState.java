package lcc.lusocraftchampionship.minigame.GravityWars.state;

import lcc.lusocraftchampionship.minigame.GravityWars.GravityWars;
import lcc.lusocraftchampionship.minigame.Minigame;
import lcc.lusocraftchampionship.minigame.MinigameStages;
import lcc.lusocraftchampionship.team.Teams;
import lcc.lusocraftchampionship.util.BlockHandler;
import lcc.lusocraftchampionship.util.Timer;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.Statistic;
import org.bukkit.entity.Player;

public class GravityWarsPreparationState extends MinigameStages {

    @Override
    public void onEnable(Minigame minigame) {
        GravityWars gravityWars = (GravityWars) minigame;

        // Loop through each block and set its material
        for (int i = 0; i < gravityWars.StartingBlocks.length; i++) {
            gravityWars.StartingBlocks[i].setType(gravityWars.materials[i]);
        }
        gravityWars.PLAYER_KILLER.clear();
        gravityWars.PLAYER_KILLS.clear();
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (Teams.getPlayers().contains(player.getName())) {
                    player.setGameMode(GameMode.SURVIVAL);
                    giveBoots(player);
                    player.setInvulnerable(true);
                    player.setExp(0);
                    player.setLevel(0);
            }
        }
    }

    @Override
    public void onUpdate(int ticks, int stopwatch, Minigame minigame, boolean isTesting, int minigameSize, float coinMultiplier) {
        GravityWars gravityWars = (GravityWars) minigame;

        for(Player player : Bukkit.getOnlinePlayers()) {
            if (player.getGameMode().equals(GameMode.ADVENTURE)) {
                playerImmune(player);
            }
        }

        if (Timer.isZero(stopwatch)) {
            for (Player player : Bukkit.getOnlinePlayers()) {
                if (Teams.getPlayers().contains(player.getName())) {
                    if(gravityWars.getTeam1().contains(Teams.getPlayerTeam(player))) {
                        player.teleport(gravityWars.getTeam1Spawn());
                        player.setBedSpawnLocation(gravityWars.getTeam1Spawn(), true);
                    }

                    if(gravityWars.getTeam2().contains(Teams.getPlayerTeam(player))) {
                        player.teleport(gravityWars.getTeam2Spawn());
                        player.setBedSpawnLocation(gravityWars.getTeam2Spawn(), true);
                    }

                    if(gravityWars.getTeam3().contains(Teams.getPlayerTeam(player))) {
                        player.teleport(gravityWars.getTeam3Spawn());
                        player.setBedSpawnLocation(gravityWars.getTeam3Spawn(), true);
                    }

                    if(gravityWars.getTeam4().contains(Teams.getPlayerTeam(player))) {
                        player.teleport(gravityWars.getTeam4Spawn());
                        player.setBedSpawnLocation(gravityWars.getTeam4Spawn(), true);
                    }
                }
            }
        }

        countdownTeamPlayer(stopwatch, 5);

        gravityWars.refreshAll(ticks, minigameSize, "§c§lComeça em", stopwatch, coinMultiplier);
    }

    @Override
    public void onDisable(Minigame minigame, boolean isTesting) {

    }

    @Override
    public int stateTime(Minigame minigame) {
        return Timer.secToTicks(10);
    }

}
