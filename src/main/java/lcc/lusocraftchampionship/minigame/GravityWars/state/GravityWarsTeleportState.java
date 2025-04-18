package lcc.lusocraftchampionship.minigame.GravityWars.state;

import lcc.lusocraftchampionship.minigame.GravityWars.GravityWars;
import lcc.lusocraftchampionship.lcc.team.Teams;
import lcc.lusocraftchampionship.minigame.AMinigame;
import lcc.lusocraftchampionship.minigame.AStage;
import lcc.lusocraftchampionship.util.DetectArea;
import org.bukkit.*;
import org.bukkit.entity.Player;

public class GravityWarsTeleportState extends AStage {

    @Override
    public void onEnable(AMinigame minigame) {
        GravityWars gravityWars = (GravityWars) minigame;

        gravityWars.teamTotemHolders.clear();
        for (int i = 0; i <= 3; i++) {
            gravityWars.getGreenTotem().get(i).getBlock().setType(Material.AIR);
            gravityWars.getRedTotem().get(i).getBlock().setType(Material.AIR);
            gravityWars.getObsidianTotem().get(i).getBlock().setType(Material.AIR);
            gravityWars.getBlueTotem().get(i).getBlock().setType(Material.AIR);
        }

        for (Player player : Bukkit.getOnlinePlayers()) {
                DetectArea.removeWoolInArea(player.getWorld(), gravityWars.MapareaEdge, gravityWars.MapareaEdge1);
            }
        }

    @Override
    public void onUpdate(int ticks, int stopwatch, AMinigame minigame, boolean isTesting, int minigameSize, float coinMultiplier) {
        GravityWars gravityWars = (GravityWars) minigame;

        for (Player player : Bukkit.getOnlinePlayers()) {
            if(Teams.INSTANCE.getPlayersName().contains(player.getName())) {
                player.teleport(gravityWars.getSpawnpoint());
                player.setBedSpawnLocation(gravityWars.getSpawnpoint(), true);
                player.setGameMode(GameMode.ADVENTURE);
                gravityWars.PLAYER_POINTS.put(player.getName(), 0);
                gravityWars.PLAYER_KILLS.put(player.getName(), 0);
                player.sendTitle("§b§lGravity Wars", "", 0, 100, 0);
                giveBoots(player);
            }
        }
    }

    @Override
    public void onDisable(AMinigame minigame, boolean isTesting) {

    }

    @Override
    public int stateTime(AMinigame minigame) {
        return 0;
    }

}
