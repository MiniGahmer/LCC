package lcc.lusocraftchampionship.minigame;

import org.bukkit.event.Listener;

public interface IMinigame {
  IMinigameExplanation getMinigameExplanation(IMinigameExplanation minigameExplanation);

  void updateScore();

  void updateScoreboard(String gameState, int stopwatch);

  void update(int time, String gameState, int stopwatch);

  void reload();

  void start();

  void stop();

  String getWorldName();

  void addState(Enum<?> state, Class<? extends IStage> stage);

  void addClassListener(Class<? extends Listener> listener);

  void removeClassListener(Class<? extends Listener> listener);

  void removeAllClassListeners();

  void setup();

  void clear();

  boolean isTesting();
}
