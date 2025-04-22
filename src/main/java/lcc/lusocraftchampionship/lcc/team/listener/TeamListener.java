package lcc.lusocraftchampionship.lcc.team.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import lcc.lusocraftchampionship.lcc.LCCUtils;
import lcc.lusocraftchampionship.lcc.sidebar.LCCSidebar;
import lcc.lusocraftchampionship.lcc.team.Teams;

public class TeamListener implements Listener {
  @EventHandler
  public void onPlayerJoin(PlayerJoinEvent event) {
    LCCUtils.addPlayerToTeam(event.getPlayer());

    // TODO: Remove this when the sidebar is implemented
    LCCSidebar.INSTANCE.addPlayer(event.getPlayer());
  }
}
