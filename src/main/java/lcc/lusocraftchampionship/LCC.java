package lcc.lusocraftchampionship;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import lcc.lusocraftchampionship.manager.Manager;
//import lcc.lusocraftchampionship.player.PlayerScoreboard;
import lcc.lusocraftchampionship.player.listener.PlayerListener;
import lcc.lusocraftchampionship.team.Teams;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Lusocraftchampionship extends JavaPlugin implements CommandExecutor {

    public static final Logger LOGGER = Logger.getLogger(Lusocraftchampionship.class.getName());

    public ProtocolManager protocolManager;

    public Manager manager;

    private PlayerListener playerListener;

    @Override
    public void onEnable() {
        protocolManager = ProtocolLibrary.getProtocolManager();

        playerListener = new PlayerListener(this);

        manager = new Manager(this);
        manager.register();

        Teams.init(this);

        //Player
        this.getServer().getPluginManager().registerEvents(playerListener, this);

        this.getCommand("reloadlcc").setExecutor(this);

        manager.sendPlayerToLobby();

        LOGGER.log(Level.INFO, "[LCC]: " + "\u001B[34m" + "=============================" + "\u001B[0m");
        LOGGER.log(Level.INFO, "[LCC]: " + "\u001B[34m" + "   LCC STARTED WITH SUCESS   " + "\u001B[0m");
        LOGGER.log(Level.INFO, "[LCC]: " + "\u001B[34m" + "   READY TO PLAY MINIGAMES   " + "\u001B[0m");
        LOGGER.log(Level.INFO, "[LCC]: " + "\u001B[34m" + "=============================" + "\u001B[0m");

    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player) {
            Player player = (Player) sender;
            if(label.equalsIgnoreCase("reloadlcc")) {
                if(player.hasPermission("reloadlcc.use")) {
                    player.sendMessage(ChatColor.GREEN + "Reload started");

                    this.manager.reload();
                    Teams.reload();

                    for (Player p : Bukkit.getOnlinePlayers()) {
                        if(p.isOp()) {
                            p.setPlayerListName("ꀎ §c" + p.getName());
                        }else {
                            p.setPlayerListName(Teams.getIconPrefix(Teams.getPlayerTeam(p)) + p.getName());
                        }
                    }

                    player.sendMessage(ChatColor.GREEN + "Reload done");
                    return true;
                }
                player.sendMessage(ChatColor.DARK_RED + "You don't have permission to use this!");
                return true;
            }
        }
        return false;
    }
}
