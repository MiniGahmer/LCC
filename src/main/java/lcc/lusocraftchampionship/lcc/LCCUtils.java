package lcc.lusocraftchampionship.lcc;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;

import lcc.lusocraftchampionship.LCCPlugin;
import lcc.lusocraftchampionship.lcc.player.VirtualPlayer;
import lcc.lusocraftchampionship.lcc.team.Teams;
import lcc.lusocraftchampionship.lcc.team.VirtualTeam;

public class LCCUtils {
  public static void clearPlayers(List<VirtualPlayer> players)  {
    LCCPlugin plugin = LCCPlugin.getPlugin(LCCPlugin.class);

    for (VirtualPlayer vp : players) {
      Player player = vp.player;
      player.setGameMode(GameMode.ADVENTURE);
      player.getInventory().clear();
      player.setLevel(0);
      player.setExp(0);
      player.setHealth(20);
      player.setFoodLevel(20);
      player.setInvisible(false);
      player.setAllowFlight(false);

      for (Player player_ : Bukkit.getOnlinePlayers())
        player.showPlayer(plugin, player_);

      for (PotionEffect effect : player.getActivePotionEffects())
        player.removePotionEffect(effect.getType());
    } 
  }

  public static void backToLobby(List<VirtualPlayer> players) {
    LCCPlugin plugin = LCCPlugin.getPlugin(LCCPlugin.class);

    for (VirtualPlayer vp : players) {
      Player player = vp.player;
      player.setGameMode(GameMode.ADVENTURE);
      player.getInventory().clear();
      player.setLevel(0);
      player.setExp(0);
      player.setHealth(20);
      player.setFoodLevel(20);
      player.setInvisible(false);
      player.setAllowFlight(false);

      for (Player player_ : Bukkit.getOnlinePlayers())
        player.showPlayer(plugin, player_);
    }
  }

  public static void teleportPlayers(List<VirtualPlayer> players, Location location) {
    for (VirtualPlayer vp : players) {
      vp.player.teleport(location);
    }
  }

  public static void addPlayerToTeam(Player player) {
    Teams.INSTANCE.addPlayerTeam(player);
  }

  public static void givePlayerCostum(Player player) {
    if (player.isOp()) {
      player.setPlayerListName("ꀎ §c" + player.getName());
    } else {
      Optional<VirtualTeam> vt = Teams.INSTANCE.getPlayerTeam(player);
      if (vt.isPresent()) {
        player.setPlayerListName(Teams.INSTANCE.getPlayerNameFormat(vt.get(), player));
      } else {
        player.setPlayerListName("NO TEAM " + player.getName());
      }
    }
  }
}
