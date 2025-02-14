package lcc.lusocraftchampionship.manager.command;

import lcc.lusocraftchampionship.manager.Manager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

public class StopGame implements CommandExecutor {
    Manager manager;

    public StopGame(Manager manager) {
        this.manager = manager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player) {
            Player player = (Player) sender;

            if(label.equalsIgnoreCase("stoplcc")) {
                if (player.hasPermission("stoplcc.use")) {
                    BukkitTask[] games = {manager.gravityWars.game, manager.wwb.game };

                    for (BukkitTask gameObj : games) {
                        if (gameObj != null) {
                            if (!gameObj.isCancelled()) {
                                gameObj.cancel();
                                manager.sendPlayerToLobby();
                            }
                        }
                    }

                    Bukkit.broadcastMessage(ChatColor.GREEN + "LCC ou minigames terminados");
                    return true;
                }
                player.sendMessage(ChatColor.DARK_RED + "You don't have permission to use this!");
                return true;
            }
        }
        return false;
    }
}
