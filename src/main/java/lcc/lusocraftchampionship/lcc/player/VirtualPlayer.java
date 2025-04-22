package lcc.lusocraftchampionship.lcc.player;

import org.bukkit.entity.Player;

import lcc.lusocraftchampionship.lcc.team.VirtualTeam;

public class VirtualPlayer implements IVirtualPlayer {
  public final Player player;
  public final VirtualTeam team;

  public VirtualPlayer(Player player, VirtualTeam team) {
    this.player = player;
    this.team = team;
  }

  @Override
  public String toString() {
    return "VirtualPlayer{" +
        "player=" + player.getName() +
        ", team=" + team.name +
        '}';
  }

  @Override
  public int hashCode() {
    int result = player != null ? player.getName().hashCode() : 0;
    result = 31 * result + (team != null ? team.name.hashCode() : 0);
    return result;
  }
}
