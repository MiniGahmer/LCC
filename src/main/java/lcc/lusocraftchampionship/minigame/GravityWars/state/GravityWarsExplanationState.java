package lcc.lusocraftchampionship.minigame.GravityWars.state;

import lcc.lusocraftchampionship.minigame.GravityWars.GravityWars;
import lcc.lusocraftchampionship.minigame.AMinigame;
import lcc.lusocraftchampionship.minigame.AStage;

public class GravityWarsExplanationState extends AStage {

    @Override
    public void onEnable(AMinigame minigame) {

    }

    @Override
    public void onUpdate(int ticks, int stopwatch, AMinigame minigame, boolean isTesting, int minigameSize, float coinMultiplier) {
        GravityWars gravityWars = (GravityWars) minigame;

        goNextState();

        //gravityWars.explanation(stopwatch);

        //if (Timer.ticksToSec(stopwatch) <= 1) {
            //sendTeamPlayerFlash();
        //}

        gravityWars.refreshAll(ticks, minigameSize, "§c§lExplicação", stopwatch, coinMultiplier);
    }

    @Override
    public void onDisable(AMinigame minigame, boolean isTesting) {
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
    public int stateTime(AMinigame minigame) {
        return 0;
    }

}
