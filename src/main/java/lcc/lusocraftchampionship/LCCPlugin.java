package lcc.lusocraftchampionship;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;

import lcc.lusocraftchampionship.lcc.LCC;
import lcc.lusocraftchampionship.lcc.command.CommandMGTest;
import lcc.lusocraftchampionship.lcc.command.ReloadLCC;
import lcc.lusocraftchampionship.lcc.player.listener.PlayerListener;
import lcc.lusocraftchampionship.lcc.team.Teams;
import lcc.lusocraftchampionship.lcc.team.listener.TeamListener;
import net.megavex.scoreboardlibrary.api.ScoreboardLibrary;
import net.megavex.scoreboardlibrary.api.exception.NoPacketAdapterAvailableException;
import net.megavex.scoreboardlibrary.api.noop.NoopScoreboardLibrary;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Level;
import java.util.logging.Logger;

public class LCCPlugin extends JavaPlugin {

  public static final Logger LOGGER = Logger.getLogger(LCCPlugin.class.getName());
  public ProtocolManager protocolManager;
  public ScoreboardLibrary scoreboardLibrary;

  @Override
  public void onEnable() {
    // ProtocolLib
    protocolManager = ProtocolLibrary.getProtocolManager();

    try {
      scoreboardLibrary = ScoreboardLibrary.loadScoreboardLibrary(this);
    } catch (NoPacketAdapterAvailableException e) {
      scoreboardLibrary = new NoopScoreboardLibrary();
      LOGGER.warning("No scoreboard packet adapter available!");
    }

    // LCC
    LCC.INSTANCE.init();
    // -----

    // manager.sendPlayerToLobby();

    LOGGER.log(Level.INFO, "[LCC]: " + "\u001B[34m" + "=============================" + "\u001B[0m");
    LOGGER.log(Level.INFO, "[LCC]: " + "\u001B[34m" + "   LCC STARTED WITH SUCESS   " + "\u001B[0m");
    LOGGER.log(Level.INFO, "[LCC]: " + "\u001B[34m" + "   READY TO PLAY MINIGAMES   " + "\u001B[0m");
    LOGGER.log(Level.INFO, "[LCC]: " + "\u001B[34m" + "=============================" + "\u001B[0m");
  }

  @Override
  public void onDisable() {
    scoreboardLibrary.close();
  }

  public static LCCPlugin getPlugin() {
    return LCCPlugin.getPlugin(LCCPlugin.class);
  }
}
