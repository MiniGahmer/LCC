package lcc.lusocraftchampionship.minigame.GravityWars.state;

import lcc.lusocraftchampionship.minigame.GravityWars.GravityWars;
import lcc.lusocraftchampionship.minigame.Minigame;
import lcc.lusocraftchampionship.minigame.MinigameStages;
import lcc.lusocraftchampionship.team.Teams;
import lcc.lusocraftchampionship.util.Timer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class GravityWarsEndState extends MinigameStages {

    @Override
    public void onEnable(Minigame minigame) {

    }

    @Override
    public void onUpdate(int ticks, int stopwatch, Minigame minigame, boolean isTesting, int minigameSize, float coinMultiplier) {
        GravityWars gravityWars = (GravityWars) minigame;

        for (Player player : Bukkit.getOnlinePlayers()) {
            playerImmune(player);
        }

        if(Timer.isTicksEqualSec(stopwatch, 40))
            sendTeamStatus(gravityWars.TOP_TEAMS, gravityWars.TEAM_POINTS, "PONTUAÇÕES DAS EQUIPAS");

        if(Timer.isTicksEqualSec(stopwatch, 30))
            sendPlayerStatus(gravityWars.TOP_PLAYERS_KILLS, gravityWars.PLAYER_KILLS, "TOP 5 PLAYERS (§r§c§lKills§r§l)");

        gravityWars.refreshAll(ticks, minigameSize, "§c§lLobby em", stopwatch, coinMultiplier);
    }

    @Override
    public void onDisable(Minigame minigame, boolean isTesting) {
        GravityWars gravityWars = (GravityWars) minigame;

        if (!isTesting) {
            for (String team : gravityWars.TEAM_POINTS.keySet()) {
                Teams.addPointTeam(team, gravityWars.TEAM_POINTS.get(team));
            }
        }
    }

    @Override
    public int stateTime(Minigame minigame) {
        return Timer.secToTicks(45);
    }

}
