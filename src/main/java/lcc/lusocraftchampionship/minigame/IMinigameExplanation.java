package lcc.lusocraftchampionship.minigame;

import java.util.HashMap;

import org.bukkit.Location;

public interface IMinigameExplanation {
  HashMap<Integer, String> addExplanationMessages(IMinigame minigame);

  HashMap<Integer, Location> addExplanationLocations(IMinigame minigame);

  void playerLocations(int stopwatch);

  void playerMessages(int stopwatch);
}
