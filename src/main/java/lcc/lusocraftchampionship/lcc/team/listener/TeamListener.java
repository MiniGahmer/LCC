package lcc.lusocraftchampionship.lcc.team.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import lcc.lusocraftchampionship.lcc.team.Teams;

public class TeamListener implements Listener {
  @EventHandler
  public void onPlayerJoin(PlayerJoinEvent event) {
    Teams.INSTANCE.addPlayerTeam(event.getPlayer());
  }
}
