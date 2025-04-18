package lcc.lusocraftchampionship.lcc.player;

import org.bukkit.entity.Player;

import lcc.lusocraftchampionship.lcc.team.VirtualTeam;

public class VirtualPlayer {
  // private String name;
  public final Player player;
  public final VirtualTeam team;
  private int score;
  private int kills;
  private int deaths;

  public VirtualPlayer(Player player, VirtualTeam team) {
    this.player = player;
    this.team = team;
  }

  public void addScore(int score) {
    if (score > 0) {
      this.score += score;
    }
  }

  public void addKill(int kills) {
    if (kills > 0) {
      this.kills += kills;
    }
  }

  public void addDeath(int deaths) {
    if (deaths > 0) {
      this.deaths += deaths;
    }
  }

  public int getScore() {
    return score;
  }

  public int getKills() {
    return kills;
  }

  public int getDeaths() {
    return deaths;
  }
}
