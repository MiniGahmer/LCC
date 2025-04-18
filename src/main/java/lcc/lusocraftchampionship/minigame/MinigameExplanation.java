package lcc.lusocraftchampionship.minigame;

import org.bukkit.GameMode;
import org.bukkit.Location;

import lcc.lusocraftchampionship.lcc.player.VirtualPlayer;
import lcc.lusocraftchampionship.lcc.team.Teams;

import java.util.HashMap;
import java.util.Map;

public abstract class MinigameExplanation implements IMinigameExplanation {
  private HashMap<Integer, String> messages = new HashMap<>();
  private HashMap<Integer, Location> locations = new HashMap<>();

  protected MinigameExplanation(IMinigame minigame) {
    locations = addExplanationLocations(minigame);
    messages = addExplanationMessages(minigame);
  }

  @Override
  public void playerLocations(int stopwatch) {
    Location location = null;
    int locTicks = -1;

    for (Map.Entry<Integer, Location> entry : locations.entrySet()) {

      if (entry.getKey() <= stopwatch) {
        if (entry.getKey() >= locTicks) {
          locTicks = entry.getKey();
          location = entry.getValue();
        }
      }
    }

    if (location != null) {
      for (VirtualPlayer vp : Teams.INSTANCE.getPlayers()) {
        vp.player.teleport(location);
        vp.player.setGameMode(GameMode.SPECTATOR);
      }
    }
  }

  @Override
  public void playerMessages(int stopwatch) {
    String message = null;

    for (Map.Entry<Integer, String> entry : messages.entrySet()) {
      if (entry.getKey() == stopwatch) {
        message = entry.getValue();
      }
    }

    if (message != null) {
      for (VirtualPlayer vp : Teams.INSTANCE.getPlayers()) {
        vp.player.sendMessage(message);
      }
    }
  }
}
