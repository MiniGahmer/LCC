package lcc.lusocraftchampionship.minigame.GravityWars.state;

import lcc.lusocraftchampionship.minigame.GravityWars.GravityWars;
import lcc.lusocraftchampionship.lcc.team.Teams;
import lcc.lusocraftchampionship.minigame.AMinigame;
import lcc.lusocraftchampionship.minigame.AStage;
import lcc.lusocraftchampionship.minigame.GravityWars.GravityWarsStages;
import lcc.lusocraftchampionship.util.Timer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class GravityWarsEndState extends AStage<GravityWars, GravityWarsStages> {

    public GravityWarsEndState(GravityWars minigame) {
        super(minigame);
    }

    @Override
    public void onUpdate(int ticks, int stopwatch, boolean isTesting, int minigameSize, float coinMultiplier) {
        for (Player player : Bukkit.getOnlinePlayers()) {
            playerImmune(player);
        }

        if(Timer.isTicksEqualSec(stopwatch, 40))
            sendTeamStatus(minigame.TOP_TEAMS, minigame.TEAM_POINTS, "PONTUAÇÕES DAS EQUIPAS");

        if(Timer.isTicksEqualSec(stopwatch, 30))
            sendPlayerStatus(minigame.TOP_PLAYERS_KILLS, minigame.PLAYER_KILLS, "TOP 5 PLAYERS (§r§c§lKills§r§l)");

        minigame.refreshAll(ticks, minigameSize, "§c§lLobby em", stopwatch, coinMultiplier);
    }

    @Override
    public void onDisable(boolean isTesting) {
        GravityWars gravityWars = (GravityWars) minigame;

        if (!isTesting) {
            for (String team : gravityWars.TEAM_POINTS.keySet()) {
                Teams.INSTANCE.addPointTeam(team, gravityWars.TEAM_POINTS.get(team));
            }
        }
    }

    @Override
    public int stageTime() {
        return Timer.secToTicks(45);
    }

}
