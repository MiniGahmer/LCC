package lcc.lusocraftchampionship.lcc.command;

import java.util.Optional;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import lcc.lusocraftchampionship.lcc.LCCPropreties;
import lcc.lusocraftchampionship.lcc.team.Teams;
import lcc.lusocraftchampionship.lcc.team.VirtualTeam;

public class ReloadLCC implements CommandExecutor {
  @Override
  public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
    if (sender instanceof Player) {
      Player player = (Player) sender;
      if (label.equalsIgnoreCase(LCCPropreties.COMMAND_RELOAD_LCC.getValue())) {
        if (player.hasPermission(LCCPropreties.COMMAND_RELOAD_LCC.getValue() + ".use")) {
          player.sendMessage(ChatColor.GREEN + "Reload started");

          Teams.INSTANCE.reload();

          for (Player p : Bukkit.getOnlinePlayers()) {
            if (p.isOp()) {
              p.setPlayerListName("ꀎ §c" + p.getName());
            } else {
              Optional<VirtualTeam> vt = Teams.INSTANCE.getPlayerTeam(player);

              if (vt.isPresent()) {
                p.setPlayerListName(Teams.INSTANCE.getPlayerNameFormat(vt.get(), player));
              } else {
                p.setPlayerListName("NO TEAM" + player.getName());
              }
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
