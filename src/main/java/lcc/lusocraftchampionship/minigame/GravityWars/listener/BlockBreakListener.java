package lcc.lusocraftchampionship.minigame.GravityWars.listener;

import lcc.lusocraftchampionship.lcc.team.Teams;
import lcc.lusocraftchampionship.minigame.GravityWars.GravityWars;
import lcc.lusocraftchampionship.util.BlockHandler;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import java.util.HashMap;
import java.util.Map;

public class BlockBreakListener implements Listener {

    GravityWars gravityWars;

    public BlockBreakListener(GravityWars gravityWars) {
        this.gravityWars = gravityWars;
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        Location blockLocation = event.getBlock().getLocation();
        Material brokenBlock = event.getBlock().getType();
        if (event.getPlayer().isOp()) return;
        // Get the team of the player
        String playerTeam = Teams.INSTANCE.getPlayerTeam(player); // Implement this method to get the player's team

        // Load the restricted index for the team from Teams.INSTANCE.yml
        int restrictedIndex = Teams.INSTANCE.getTotemTeam(playerTeam);


        // Ensure the player's team exists in the restriction map
        if (restrictedIndex == -1) {
            return; // If the restricted index is not found, do nothing
        }

        if (player.getLocation().getWorld().getName().equals(gravityWars.getWorldName())) {
            // Check the broken block's material and compare with the team index
            if (gravityWars.isTotemBlock(brokenBlock)) {
                // Check if the broken block is at the restricted index
                Location totemLocation = gravityWars.getTotemLocation(brokenBlock, restrictedIndex);

                if (totemLocation != null && blockLocation.equals(totemLocation)) {
                    event.setCancelled(true);  // Prevent the player from breaking the block
                    player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent("§cYou cannot interact with your own team's totem!"));
                    return;
                }
            }
            if (gravityWars.WOOLS.contains(event.getBlock().getType())) {
                event.setDropItems(false);
                event.setCancelled(false);
            } else {
                event.setCancelled(true);
            }
        }
    }

}
