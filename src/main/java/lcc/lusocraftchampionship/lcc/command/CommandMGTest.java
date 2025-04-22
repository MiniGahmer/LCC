package lcc.lusocraftchampionship.lcc.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import lcc.lusocraftchampionship.lcc.LCC;
import lcc.lusocraftchampionship.lcc.LCCMinigames;
import lcc.lusocraftchampionship.lcc.LCCPropreties;

public class CommandMGTest implements CommandExecutor {
  @Override
  public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
    if (sender.hasPermission("lcc." + LCCPropreties.COMMAND_START_MGTEST.getValue())) {
      if (args.length == 0) {
        LCC.INSTANCE.startMinigame(LCCMinigames.MGTEST, "mgtest", true);
        return true;
      } else {
        sender.sendMessage("§cInvalid arguments! Use /mgtest for help.");
        return false;
      }
    } else {
      sender.sendMessage("§cYou don't have permission to use this command!");
      return false;
    }
  }
  
}
