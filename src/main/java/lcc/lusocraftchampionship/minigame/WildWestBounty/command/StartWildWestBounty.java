package lcc.lusocraftchampionship.minigame.WildWestBounty.command;

import lcc.lusocraftchampionship.minigame.GravityWars.GravityWars;
import lcc.lusocraftchampionship.minigame.WildWestBounty.WildWestBounty;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class StartWildWestBounty implements CommandExecutor {

    WildWestBounty wwb;

    public StartWildWestBounty(WildWestBounty wwb) {
        this.wwb = wwb;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player){
            Player player = (Player)sender;
            if(label.equalsIgnoreCase("startwildwestbounty")) {
                if (player.hasPermission("startwildwestbounty.use")) {
                    if(wwb.plugin.manager.isStart())
                        wwb.start(true,1,1.0f);

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
