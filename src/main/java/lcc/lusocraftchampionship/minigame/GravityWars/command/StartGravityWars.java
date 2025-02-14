package lcc.lusocraftchampionship.minigame.GravityWars.command;

import lcc.lusocraftchampionship.minigame.GravityWars.GravityWars;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class StartGravityWars implements CommandExecutor {
    GravityWars gravityWars;

    public StartGravityWars(GravityWars gravityWars) {
        this.gravityWars = gravityWars;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player){
            Player player = (Player)sender;
            if(label.equalsIgnoreCase("startgravitywars")) {
                if (player.hasPermission("startgravitywars.use")) {
                    if(gravityWars.plugin.manager.isStart())
                        gravityWars.start(true,1,1.0f);

                    return true;
                }
                player.sendMessage(ChatColor.DARK_RED + "You don't have permission to use this!");
                return true;
            }
            return true;
        }
        return false;
    }

}
