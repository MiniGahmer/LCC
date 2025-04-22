package lcc.lusocraftchampionship.minigame.stats;

public class KillStats implements IMinigameStat<Integer> {
  private int killCount;

  @Override
  public void incrementStat() {
    killCount++;
  }

  @Override
  public void setValue(Integer value) {
    this.killCount = value;
  }

  @Override
  public Integer getStat() {
    return killCount;
  }

  @Override
  public void resetStats() {
    killCount = 0;
  }
}
