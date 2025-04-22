package lcc.lusocraftchampionship.minigame.stats;

public interface IMinigameStat<T> {
  void incrementStat();

  void setValue(T value);

  T getStat();

  void resetStats();
}