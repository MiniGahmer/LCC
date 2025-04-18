package lcc.lusocraftchampionship.lcc.command;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import lcc.lusocraftchampionship.LCCPlugin;
import lcc.lusocraftchampionship.lcc.team.Teams;

public class ReloadLCC implements CommandExecutor {
  @Override
  public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
    if (sender instanceof Player) {
      Player player = (Player) sender;
      if (label.equalsIgnoreCase("reloadlcc")) {
        if (player.hasPermission("reloadlcc.use")) {
          player.sendMessage(ChatColor.GREEN + "Reload started");

//          LCCPlugin.getPlugin().manager.reload();
          Teams.INSTANCE.reload();

          for (Player p : Bukkit.getOnlinePlayers()) {
            if (p.isOp()) {
              p.setPlayerListName("ꀎ §c" + p.getName());
            } else {
              p.setPlayerListName(Teams.INSTANCE.getIconPrefix(Teams.INSTANCE.getPlayerTeam(p)) + p.getName());
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
