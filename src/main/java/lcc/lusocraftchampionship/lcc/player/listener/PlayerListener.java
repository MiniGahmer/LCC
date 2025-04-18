package lcc.lusocraftchampionship.lcc.player.listener;

import lcc.lusocraftchampionship.lcc.team.Teams;
import lcc.lusocraftchampionship.lcc.team.VirtualTeam;

import java.util.Optional;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerListener implements Listener {

  @EventHandler
  public void onChat(AsyncPlayerChatEvent event) {
    Player player = event.getPlayer();
    String msg = event.getMessage();

    if (player.isOp()) {
      event.setFormat("§r ꀎ §c" + player.getName() + " > §r" + msg);
    } else {
      Optional<VirtualTeam> vt = Teams.INSTANCE.getPlayerTeam(player);
      if (vt.isPresent()) {
        event.setFormat("§r" + vt.get().icon + " " + vt.get().name + player.getName()
            + " > §r" + msg);
      } else {
        event.setFormat("§r" + "NO TEAM" + player.getName() + " > §r" + msg);

      }
    }
  }

  // TODO: Send the player to the lobby when they join the server
  @EventHandler
  public void onJoin(PlayerJoinEvent event) {
    Player player = event.getPlayer();

    if (player.isOp()) {
      player.setPlayerListName("ꀎ §c" + player.getName());
      event.setJoinMessage("§r ꀎ §c" + player.getName() + " §rentrou no server");
    } else {
      Optional<VirtualTeam> vt = Teams.INSTANCE.getPlayerTeam(player);
      if (vt.isPresent()) {
        player.setPlayerListName(vt.get().icon + " " + vt.get().name + player.getName());
        event.setJoinMessage("§r" + vt.get().icon + " " + vt.get().name + player.getName()
            + " §rentrou no server");
      } else {
        player.setPlayerListName("NO TEAM" + player.getName());
        event.setJoinMessage("§r" + "NO TEAM" + player.getName() + " §rentrou no server");
      }
    }
  }

  @EventHandler
  public void onQuit(PlayerQuitEvent event) {
    Player player = event.getPlayer();

    if (player.isOp()) {
      event.setQuitMessage("§r ꀎ §c" + player.getName() + " §rsaiu do server");
    } else {
      Optional<VirtualTeam> vt = Teams.INSTANCE.getPlayerTeam(player);
      if (vt.isPresent()) {
        event.setQuitMessage("§r" + Teams.INSTANCE.getIconPrefix(Teams.INSTANCE.getPlayerTeam(player)) + " "
            + player.getName() + " §rsaiu do server");
      } else {
        event.setQuitMessage("§r" + "NO TEAM" + " " + player.getName() + " §rsaiu do server");
      }
    }
  }
}
