package lcc.lusocraftchampionship.minigame.minigame.state;

import lcc.lusocraftchampionship.minigame.minigame.minigame;
import lcc.lusocraftchampionship.lcc.team.Teams;
import lcc.lusocraftchampionship.minigame.AMinigame;
import lcc.lusocraftchampionship.minigame.AStage;
import lcc.lusocraftchampionship.util.DetectArea;
import org.bukkit.*;
import org.bukkit.entity.Player;

public class GravityWarsTeleportState extends AStage {

    @Override
    public void onEnable(AMinigame minigame) {
        minigame minigame = (minigame) minigame;

        minigame.teamTotemHolders.clear();
        for (int i = 0; i <= 3; i++) {
            minigame.getGreenTotem().get(i).getBlock().setType(Material.AIR);
            minigame.getRedTotem().get(i).getBlock().setType(Material.AIR);
            minigame.getObsidianTotem().get(i).getBlock().setType(Material.AIR);
            minigame.getBlueTotem().get(i).getBlock().setType(Material.AIR);
        }

        for (Player player : Bukkit.getOnlinePlayers()) {
                DetectArea.removeWoolInArea(player.getWorld(), minigame.MapareaEdge, minigame.MapareaEdge1);
            }
        }

    @Override
    public void onUpdate(int ticks, int stopwatch, AMinigame minigame, boolean isTesting, int minigameSize, float coinMultiplier) {
        minigame minigame = (minigame) minigame;

        for (Player player : Bukkit.getOnlinePlayers()) {
            if(Teams.INSTANCE.getPlayersName().contains(player.getName())) {
                player.teleport(minigame.getSpawnpoint());
                player.setBedSpawnLocation(minigame.getSpawnpoint(), true);
                player.setGameMode(GameMode.ADVENTURE);
                minigame.PLAYER_POINTS.put(player.getName(), 0);
                minigame.PLAYER_KILLS.put(player.getName(), 0);
                player.sendTitle("§b§lGravity Wars", "", 0, 100, 0);
                giveBoots(player);
            }
        }
    }

    @Override
    public void onDisable(AMinigame minigame, boolean isTesting) {

    }

    @Override
    public int stageTime(AMinigame minigame) {
        return 0;
    }

}
