package lcc.lusocraftchampionship.minigame.GravityWars.listener;

import lcc.lusocraftchampionship.minigame.GravityWars.GravityWars;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.player.PlayerDropItemEvent;

public class PlayerInventoryClickListener implements Listener {

    GravityWars gravityWars;

    public PlayerInventoryClickListener(GravityWars gravityWars) {
        this.gravityWars = gravityWars;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getWhoClicked() instanceof org.bukkit.entity.Player) {
            org.bukkit.entity.Player player = (org.bukkit.entity.Player) event.getWhoClicked();

            // Check if the clicked item is being removed (from inventory slots)
            if (event.getAction().toString().contains("MOVE_TO")) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onPlayerDropItem(PlayerDropItemEvent event) {
        // Prevent player from dropping items
        event.setCancelled(true);
    }

    @EventHandler
    public void onInventoryDrag(InventoryDragEvent event) {
        if (event.getWhoClicked() instanceof org.bukkit.entity.Player) {
            org.bukkit.entity.Player player = (org.bukkit.entity.Player) event.getWhoClicked();
            // Cancel any dragging action in the inventory
            event.setCancelled(true);
        }
    }
}
