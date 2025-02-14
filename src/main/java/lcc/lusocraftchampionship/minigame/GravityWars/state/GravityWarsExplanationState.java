package lcc.lusocraftchampionship.minigame.GravityWars.state;

import lcc.lusocraftchampionship.minigame.GravityWars.GravityWars;
import lcc.lusocraftchampionship.minigame.Minigame;
import lcc.lusocraftchampionship.minigame.MinigameStages;
import lcc.lusocraftchampionship.util.SpawnLocation;
import lcc.lusocraftchampionship.util.Timer;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class GravityWarsExplanationState extends MinigameStages {

    @Override
    public void onEnable(Minigame minigame) {

    }

    @Override
    public void onUpdate(int ticks, int stopwatch, Minigame minigame, boolean isTesting, int minigameSize, float coinMultiplier) {
        GravityWars gravityWars = (GravityWars) minigame;

        goNextState();

        //gravityWars.explanation(stopwatch);

        //if (Timer.ticksToSec(stopwatch) <= 1) {
            //sendTeamPlayerFlash();
        //}

        gravityWars.refreshAll(ticks, minigameSize, "§c§lExplicação", stopwatch, coinMultiplier);
    }

    @Override
    public void onDisable(Minigame minigame, boolean isTesting) {
        GravityWars gravityWars = (GravityWars) minigame;

        //Location downedge = gravityWars.getParkourSpawn("downedge");
        //Location upedge = gravityWars.getParkourSpawn("upedge");
        //SpawnLocation spawnLocation = new SpawnLocation(downedge, upedge, downedge.getYaw(), downedge.getPitch());

        //for (Player player : Bukkit.getOnlinePlayers()) {
            //Location location = spawnLocation.randomSpawnLocationXZ();
            //player.teleport(location);
            //player.setBedSpawnLocation(location, true);
            //player.setGameMode(GameMode.SURVIVAL);
            //giveBoots(player);
        //}
    }

    @Override
    public int stateTime(Minigame minigame) {
        return 0;
    }

}
