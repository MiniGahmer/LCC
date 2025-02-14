package lcc.lusocraftchampionship.minigame;

import lcc.lusocraftchampionship.team.Teams;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public abstract class MinigameExplanation {
    private HashMap<Integer, String> MESSAGES = new HashMap<>();
    private HashMap<Integer, Location> LOCATIONS = new HashMap<>();

    public MinigameExplanation(Minigame minigame) {
        LOCATIONS = addExplanationLocations(minigame);
        MESSAGES = addExplanationMessages(minigame);
    }

    protected abstract HashMap<Integer, String> addExplanationMessages(Minigame minigame);

    protected abstract HashMap<Integer, Location> addExplanationLocations(Minigame minigame);

    public void playerLocations(int stopwatch) {
        Location location = null;
        int locTicks = -1;

        for (Map.Entry<Integer, Location> entry : LOCATIONS.entrySet()) {

            if (entry.getKey() <= stopwatch) {
                if (entry.getKey() >= locTicks) {
                    locTicks = entry.getKey();
                    location = entry.getValue();
                }
            }
        }


        if (location != null) {
            for (Player player : Bukkit.getOnlinePlayers()) {
                if (Teams.getPlayers().contains(player.getName())) {
                    player.setGameMode(GameMode.SPECTATOR);
                    player.teleport(location);
                }
            }
        }
    }

    public void playerMessages(int stopwatch) {
        String message = null;


        for (Map.Entry<Integer, String> entry : MESSAGES.entrySet()) {
            if (entry.getKey() == stopwatch) {
                message = entry.getValue();
            }
        }

        if (message != null) {
            for (Player player : Bukkit.getOnlinePlayers()) {
                if (Teams.getPlayers().contains(player.getName())) {
                    player.sendMessage(message);
                }
            }
        }
    }
}
