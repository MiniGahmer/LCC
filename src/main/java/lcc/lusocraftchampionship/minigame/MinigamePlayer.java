package lcc.lusocraftchampionship.minigame;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.bukkit.entity.Player;

import lcc.lusocraftchampionship.lcc.player.VirtualPlayer;
import lcc.lusocraftchampionship.lcc.team.VirtualTeam;
import lcc.lusocraftchampionship.minigame.stats.IMinigameStat;

public class MinigamePlayer extends VirtualPlayer {
  private final Set<IMinigameStat<?>> stats = new HashSet<>();

  public MinigamePlayer(Player player, VirtualTeam team) {
    super(player, team);
  }

  public void addStat(IMinigameStat<?> stat) {
    stats.add(stat);
  }

  public void removeStat(IMinigameStat<?> stat) {
    stats.remove(stat);
  }

  public void resetStats() {
    stats.forEach(IMinigameStat::resetStats);
  }

  public void incrementStat(Class<? extends IMinigameStat<?>> statClass) {
    for (IMinigameStat<?> stat : stats) {
      if (stat.getClass().equals(statClass)) {
        stat.incrementStat();
        return;
      }
    }
  }

  @SuppressWarnings("unchecked")
  public <T> void setStatValue(Class<? extends IMinigameStat<?>> statClass, T value) {
    for (IMinigameStat<?> stat : stats) {
      if (stat.getClass().equals(statClass)) {
        ((IMinigameStat<T>) stat).setValue(value);
        return;
      }
    }
  }

  @SuppressWarnings("unchecked")
  public <T> Optional<T> getStat(Class<? extends IMinigameStat<?>> statClass) {
    for (IMinigameStat<?> stat : stats) {
      if (stat.getClass().equals(statClass)) {
        return Optional.of((T) stat.getStat());
      }
    }

    return Optional.empty();
  }
}
