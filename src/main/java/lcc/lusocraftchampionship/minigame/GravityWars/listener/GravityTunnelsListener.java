package lcc.lusocraftchampionship.minigame.GravityWars.listener;

import lcc.lusocraftchampionship.minigame.GravityWars.GravityWars;
import lcc.lusocraftchampionship.util.BlockHandler;
import lcc.lusocraftchampionship.util.DetectArea;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Horse;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.event.player.PlayerVelocityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class GravityTunnelsListener implements Listener {
    GravityWars gravityWars;

    public GravityTunnelsListener(GravityWars gravityWars) {
        this.gravityWars = gravityWars;
    }

    double playerJumpVelocity = 0.42F;
    int multiply = 3;

    @EventHandler
    public void onGreenTunnel(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        if (!player.getWorld().getName().equals(gravityWars.getWorldName())) return;

        // Check if the player is on top of the specific block
        if (player.getLocation().subtract(0, 1, 0).getBlock().getType() == Material.GREEN_STAINED_GLASS) {
            // Check if player is jumping (if Y velocity is increasing)
            if (player.getVelocity().getY() > 0) {
                Vector velocity = player.getVelocity();
                velocity.setY(velocity.getY() + 0.1); // Increase jump height
                player.setVelocity(velocity);
            }
        } else if (player.getLocation().subtract(0, 1, 0).getBlock().getType() == Material.YELLOW_STAINED_GLASS) {
            if (player.getVelocity().getY() > 0) {
                player.setVelocity(player.getLocation().getDirection().multiply(5).setY(2));
            }
        }
    }

    @EventHandler
    public void onRedTunnel(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        if (!player.getWorld().getName().equals(gravityWars.getWorldName())) return;

        if (DetectArea.detectPlayerArea(player, gravityWars.getAreaEdge(), gravityWars.getAreaEdge1())) {
            // Start a repeating task that applies random velocity every 2 seconds
            new BukkitRunnable() {
                @Override
                public void run() {
                    if (player.isDead() || !DetectArea.detectPlayerArea(player, gravityWars.getAreaEdge(), gravityWars.getAreaEdge1())) {
                        // Stop the task if the player leaves the area or is dead
                        cancel();
                        return;
                    }

                    // Generate random values to move the player in one of four directions
                    int direction = (int) (Math.random() * 4); // Random number from 0 to 3

                    Vector velocity = player.getVelocity();

                    switch (direction) {
                        case 0: // Left
                            velocity.setX(velocity.getX() - 1);
                            break;
                        case 1: // Right
                            velocity.setX(velocity.getX() + 1);
                            break;
                        case 2: // Up
                            velocity.setY(velocity.getY() + 1);
                            break;
                        case 3: // Down
                            velocity.setY(velocity.getY() - 1);
                            break;
                    }

                    // Apply the new velocity to the player
                    player.setVelocity(velocity);
                }
            }.runTaskTimer(gravityWars.plugin, 0, 40); // Runs every 40 ticks (2 seconds)
        }
    }
}