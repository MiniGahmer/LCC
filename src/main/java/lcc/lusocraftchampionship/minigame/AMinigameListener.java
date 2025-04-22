package lcc.lusocraftchampionship.minigame;

import org.bukkit.event.Listener;

public abstract class AMinigameListener<T extends IMinigame> implements Listener {
  private T minigame;

  public AMinigameListener(T minigame) {
    this.minigame = minigame;
  }
}
