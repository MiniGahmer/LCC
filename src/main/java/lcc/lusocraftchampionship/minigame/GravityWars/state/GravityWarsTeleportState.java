package lcc.lusocraftchampionship.minigame.GravityWars.state;

import lcc.lusocraftchampionship.minigame.GravityWars.GravityWars;
import lcc.lusocraftchampionship.minigame.Minigame;
import lcc.lusocraftchampionship.minigame.MinigameStages;
import lcc.lusocraftchampionship.team.Teams;
import lcc.lusocraftchampionship.util.BlockHandler;
import lcc.lusocraftchampionship.util.DetectArea;
import lcc.lusocraftchampionship.util.SpawnLocation;
import lcc.lusocraftchampionship.util.Timer;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

public class GravityWarsTeleportState extends MinigameStages {

    @Override
    public void onEnable(Minigame minigame) {
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
    public void onUpdate(int ticks, int stopwatch, Minigame minigame, boolean isTesting, int minigameSize, float coinMultiplier) {
        GravityWars gravityWars = (GravityWars) minigame;

        for (Player player : Bukkit.getOnlinePlayers()) {
            if(Teams.getPlayers().contains(player.getName())) {
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
    public void onDisable(Minigame minigame, boolean isTesting) {

    }

    @Override
    public int stateTime(Minigame minigame) {
        return 0;
    }

}
