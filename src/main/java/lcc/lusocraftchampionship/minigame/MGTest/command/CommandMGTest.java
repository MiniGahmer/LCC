package lcc.lusocraftchampionship.minigame.MGTest.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import lcc.lusocraftchampionship.minigame.MGTest.MGTest;

public class CommandMGTest implements CommandExecutor {
  @Override
  public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
    if (sender.hasPermission("lcc.mgtest")) {
      if (args.length == 0) {
        MGTest mgTest = new MGTest("mgtest", true);
        mgTest.start();
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
