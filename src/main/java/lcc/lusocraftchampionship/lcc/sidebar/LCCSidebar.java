package lcc.lusocraftchampionship.lcc.sidebar;

import java.util.HashMap;

import lcc.lusocraftchampionship.LCCPlugin;
import lcc.lusocraftchampionship.lcc.player.VirtualPlayer;
import net.megavex.scoreboardlibrary.api.sidebar.Sidebar;

import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitTask;

public enum LCCSidebar {
  INSTANCE;

  private HashMap<VirtualPlayer, Sidebar> playerSidebar = new HashMap<>();
  private Sidebar sidebar;
  private ExampleComponentSidebar exampleComponentSidebar;

  private BukkitTask task;

  LCCSidebar() {
    sidebar = LCCPlugin.getPlugin().scoreboardLibrary.createSidebar();
    exampleComponentSidebar = new ExampleComponentSidebar((Plugin) LCCPlugin.getPlugin(), (net.megavex.scoreboardlibrary.api.sidebar.Sidebar) sidebar);
    
    task = LCCPlugin.getPlugin().getServer().getScheduler().runTaskTimer(LCCPlugin.getPlugin(), () -> {
      exampleComponentSidebar.tick();
    }, 0, 1);
  }

  public void addPlayer(Player player) {
    sidebar.addPlayer(player);
  } 
}
