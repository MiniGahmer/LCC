package lcc.lusocraftchampionship.lcc.sidebar;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class SideBarListener implements Listener {
  @EventHandler
  public void onPlayerJoin(PlayerJoinEvent event) {
    LCCSidebar.INSTANCE.addPlayer(event.getPlayer());
  }
}
