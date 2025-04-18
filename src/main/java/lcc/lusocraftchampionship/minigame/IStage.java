package lcc.lusocraftchampionship.minigame;

public interface IStage {
  void onEnable();

  void onUpdate(int ticks, int stopwatch);

  void onDisable();

  int stageTime();

  void jumpToState(Enum<?> state);

  Enum<?> getState();
}
