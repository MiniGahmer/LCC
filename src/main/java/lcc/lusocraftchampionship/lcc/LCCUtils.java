package lcc.lusocraftchampionship.lcc;

import java.util.List;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;

import lcc.lusocraftchampionship.lcc.player.VirtualPlayer;

public class LCCUtils {

  public static void clearPlayers(Plugin plugin, List<VirtualPlayer> players)  {
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

  public static void backToLobby(Plugin plugin, List<VirtualPlayer> players) {
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
}
