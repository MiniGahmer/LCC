package lcc.lusocraftchampionship;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;

import lcc.lusocraftchampionship.lcc.command.ReloadLCC;
import lcc.lusocraftchampionship.lcc.player.listener.PlayerListener;
import lcc.lusocraftchampionship.lcc.team.Teams;
import lcc.lusocraftchampionship.lcc.team.listener.TeamListener;
import lcc.lusocraftchampionship.manager.Manager;

import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Level;
import java.util.logging.Logger;

public class LCCPlugin extends JavaPlugin {

  public static final Logger LOGGER = Logger.getLogger(LCCPlugin.class.getName());

  public ProtocolManager protocolManager;

  public Manager manager;

  @Override
  public void onEnable() {
    protocolManager = ProtocolLibrary.getProtocolManager();

    manager = new Manager(this);
    manager.register();

    // LCC
    this.getServer().getPluginManager().registerEvents(new PlayerListener(), this);
    this.getServer().getPluginManager().registerEvents(new TeamListener(), this);
    Teams.INSTANCE.reload();

    this.getCommand("reloadlcc").setExecutor(new ReloadLCC());
    // -----

    manager.sendPlayerToLobby();

    LOGGER.log(Level.INFO, "[LCC]: " + "\u001B[34m" + "=============================" + "\u001B[0m");
    LOGGER.log(Level.INFO, "[LCC]: " + "\u001B[34m" + "   LCC STARTED WITH SUCESS   " + "\u001B[0m");
    LOGGER.log(Level.INFO, "[LCC]: " + "\u001B[34m" + "   READY TO PLAY MINIGAMES   " + "\u001B[0m");
    LOGGER.log(Level.INFO, "[LCC]: " + "\u001B[34m" + "=============================" + "\u001B[0m");
  }

  public static LCCPlugin getPlugin() {
    return LCCPlugin.getPlugin(LCCPlugin.class);
  }
}
