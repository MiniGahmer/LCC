package lcc.lusocraftchampionship.lcc.team;

import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;

import lcc.lusocraftchampionship.lcc.player.VirtualPlayer;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class VirtualTeam {
  public final String name;
  public final String icon;
  public final String prefix;
  public final Color color;
  public final Material concrete;
  public final Material wool;
  public final ChatColor chatColor;
  public final int totem;
  public final List<String> playersName;
  // private final int maxPlayers;
  private final Set<VirtualPlayer> players = new HashSet<>(); // This set is filled when the player enters the server
  private int points;

  public VirtualTeam(String name, List<String> players, String icon, String prefix, Color color, int points,
      int totem, Material concrete, Material wool, ChatColor chatColor, int maxPlayers) {
    this.name = name;

    if (players.size() <= maxPlayers)
      this.playersName = players;
    else
      throw new IllegalArgumentException(
          "Too long value ! Max " + maxPlayers + " players, value was " + players.size() + " !");

    this.icon = icon;
    this.prefix = prefix;
    this.color = color;
    this.points = points;
    this.concrete = concrete;
    this.wool = wool;
    this.chatColor = chatColor;
    this.totem = totem;
    // this.maxPlayers = maxPlayers;
  }

  public void addPlayer(VirtualPlayer player) {
    players.add(player);
  }

  public void removePlayer(VirtualPlayer player) {
    players.remove(player);
  }

  public Set<VirtualPlayer> getPlayers() {
    return players;
  }

  public int getPoints() {
    return points;
  }

  public void setPoints(int points) {
    if (points > 0) {
      this.points = points;
    }
  }

  public void addPoints(int points) {
    if (points > 0) {
      this.points += points;
    }
  }

  @Override
  public int hashCode() {
    return name.hashCode();
  }

  @Override
  public boolean equals(Object obj) {
    if (obj instanceof VirtualTeam) {
      VirtualTeam team = (VirtualTeam) obj;
      return name.equals(team.name);
    }
    return false;
  }
}
