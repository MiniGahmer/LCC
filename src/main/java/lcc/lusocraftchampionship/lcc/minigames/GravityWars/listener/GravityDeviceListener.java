package lcc.lusocraftchampionship.lcc.minigames.GravityWars.listener;
/*
package lcc.lusocraftchampionship.lcc.minigames.GravityWars.listener;

import lcc.lusocraftchampionship.lcc.team.Teams;
import lcc.lusocraftchampionship.lcc.minigames.GravityWars.GravityWars;
import lcc.lusocraftchampionship.util.BlockHandler;
import lcc.lusocraftchampionship.util.Timer;
import net.minecraft.network.protocol.game.PacketPlayOutEntityTeleport;
import net.minecraft.world.entity.Pose;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_20_R1.entity.CraftPlayer;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.Vector;

import java.util.*;

public class GravityDeviceListener implements Listener {
    private final GravityWars gravityWars;
    private final Map<Material, String> deviceNames = new HashMap<>();
    private final Map<Player, BukkitTask> activeCooldownTasks = new HashMap<>();
    // Cooldown times in milliseconds
    private final Map<Material, Long> cooldownTimes = new HashMap<>();

    public GravityDeviceListener(GravityWars gravityWars) {
        this.gravityWars = gravityWars;
        initializeDevices();
        initializeCooldownTimes();
    }

    private void initializeDevices() {
        deviceNames.put(Material.CLOCK, "§d§lGravityFlip");
        deviceNames.put(Material.SLIME_BALL, "§6§lGravityDash");
        deviceNames.put(Material.RECOVERY_COMPASS, "§9§lGravityZones");
        deviceNames.put(Material.MUSIC_DISC_PIGSTEP, "§e§lGravityPush");
        deviceNames.put(Material.MUSIC_DISC_OTHERSIDE, "§lZeroGravity");
        deviceNames.put(Material.NETHERITE_PICKAXE, "§b§lTotemBreaker");
    }

    private void initializeCooldownTimes() {
        // Set cooldowns for each device
        cooldownTimes.put(Material.CLOCK, 30 * 1000L); // 1 minute
        cooldownTimes.put(Material.SLIME_BALL, 10 * 1000L); // 20 seconds
        cooldownTimes.put(Material.RECOVERY_COMPASS, 30 * 1000L); // 1 minute
        cooldownTimes.put(Material.MUSIC_DISC_PIGSTEP, 30 * 1000L); // 1 minute
        cooldownTimes.put(Material.MUSIC_DISC_OTHERSIDE, 30 * 1000L);
        cooldownTimes.put(Material.NETHERITE_PICKAXE, 0L);
    }

    @EventHandler
    private void onGravityDeviceClick(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (!player.getWorld().getName().equals(gravityWars.getWorldName())) return;
        if (!event.getAction().name().contains("RIGHT_CLICK")) return;

        ItemStack mainHandItem = player.getInventory().getItemInMainHand();
        ItemStack offHandItem = player.getInventory().getItemInOffHand();
        ItemStack deviceItem = mainHandItem;

        if (!deviceNames.containsKey(mainHandItem.getType()) && !deviceNames.containsKey(offHandItem.getType())) return;

        // If item in off-hand is a gravity device, use it instead
        if (deviceNames.containsKey(offHandItem.getType())) {
            deviceItem = offHandItem;
        }

        long currentTime = System.currentTimeMillis();

        if (isItemOnCooldown(player, deviceItem)) {
            player.sendMessage(ChatColor.RED + "Espera para poderes usar o item!");
            return;
        }

        switch (deviceItem.getType()) {
            case CLOCK:
                applyEffectToNearbyPlayers(player, PotionEffectType.LEVITATION, 1, 100, 200, true);
                break;
            case SLIME_BALL:
                player.setVelocity(player.getLocation().getDirection().multiply(2));
                player.spawnParticle(Particle.SONIC_BOOM, player.getLocation().add(0, 1, 0), 100);
                break;
            case RECOVERY_COMPASS:
                applyEffectToNearbyPlayers(player, PotionEffectType.SLOW, 5, 5, 200, true);
                break;
            case MUSIC_DISC_PIGSTEP:
                applyImpulseToNearbyEnemies(player, 100, 15);
                break;
            case MUSIC_DISC_OTHERSIDE:
                applyEffectToNearbyPlayers(player, PotionEffectType.LEVITATION, 5, 1, 200, true);
                break;
            case NETHERITE_PICKAXE:
                return;
            default:
                break;
        }

        // Set the cooldown for both the player and the item
        setCooldown(player, deviceItem.getType(), currentTime);
        setItemCooldown(deviceItem, currentTime);
        long cooldownDuration = cooldownTimes.get(deviceItem.getType());
        showCooldownOnXPBar(player, cooldownDuration, deviceItem.getType());

        event.setCancelled(true);
    }

    @EventHandler
    private void onGravityDeviceShift(PlayerToggleSneakEvent event) {
        Player player = event.getPlayer();
        if (!player.getWorld().getName().equals(gravityWars.getWorldName())) return;
        if (!player.isSneaking()) return;

        ItemStack mainHandItem = player.getInventory().getItemInMainHand();
        ItemStack offHandItem = player.getInventory().getItemInOffHand();
        boolean isMainHandDevice = deviceNames.containsKey(mainHandItem.getType());
        boolean isOffHandDevice = deviceNames.containsKey(offHandItem.getType());

        // Check if the device has the correct special name
        if (isMainHandDevice && mainHandItem.hasItemMeta() && mainHandItem.getItemMeta().hasDisplayName()) {
            String displayName = mainHandItem.getItemMeta().getDisplayName();
            if (deviceNames.get(mainHandItem.getType()).equals(displayName)) {
                String nextDeviceName = getNextDeviceName(displayName);
                if (nextDeviceName != null) {
                    switchDevice(player, mainHandItem, EquipmentSlot.HAND);
                    player.getInventory().setItemInMainHand(null); // Remove current item
                    givePlayerGravityDevice(player, nextDeviceName, EquipmentSlot.HAND);
                    player.sendTitle(nextDeviceName, "", 0, 40, 20);
                }
            }
        } else if (isOffHandDevice && offHandItem.hasItemMeta() && offHandItem.getItemMeta().hasDisplayName()) {
            String displayName = offHandItem.getItemMeta().getDisplayName();
            if (deviceNames.get(offHandItem.getType()).equals(displayName)) {
                String nextDeviceName = getNextDeviceName(displayName);
                if (nextDeviceName != null) {
                    switchDevice(player, offHandItem, EquipmentSlot.OFF_HAND);
                    player.getInventory().setItemInOffHand(null); // Remove current item
                    givePlayerGravityDevice(player, nextDeviceName, EquipmentSlot.OFF_HAND);
                    player.sendTitle(nextDeviceName, "", 0, 40, 20);
                }
            }
        }
    }

    private String getNextDeviceName(String currentName) {
        boolean returnNext = false;
        for (String name : deviceNames.values()) {
            if (returnNext) return name;
            if (name.equals(currentName)) returnNext = true;
        }
        return deviceNames.values().iterator().next();
    }

    private void applyEffectToNearbyPlayers(Player player, PotionEffectType effect, int duration, int amplifier, double range, boolean excludeSelf) {
        for (Player p : Bukkit.getOnlinePlayers()) {
            if (excludeSelf && p.equals(player)) continue;
            if (gravityWars.areTeammates(player, p)) continue; // Exclude teammates
            if (player.getLocation().distanceSquared(p.getLocation()) <= range) {
                p.addPotionEffect(new PotionEffect(effect, duration * 20, amplifier, false, false, false));
            }
        }
    }

    private void givePlayerGravityDevice(Player player, String deviceName, EquipmentSlot slot) {
        Material material = deviceNames.entrySet().stream()
                .filter(entry -> entry.getValue().equals(deviceName))
                .map(Map.Entry::getKey)
                .findFirst()
                .orElse(null);
        if (material == null) return;

        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            meta.setUnbreakable(true);
            meta.addEnchant(Enchantment.DURABILITY, 3, true);
            meta.setDisplayName(deviceName);
            item.setItemMeta(meta);
        }
        if (slot == EquipmentSlot.HAND) {
            player.getInventory().setItemInMainHand(item);
        } else {
            player.getInventory().setItemInOffHand(item);
        }
    }

    private void applyImpulseToNearbyEnemies(Player player, double power, double radius) {
        for (Player p : Bukkit.getOnlinePlayers()) {
            if (p.equals(player)) continue; // Não afeta o próprio jogador
            if (gravityWars.areTeammates(player, p)) continue; // Exclude teammates
            // Verifica se o jogador está dentro do alcance de "radius" (distância)
            if (player.getLocation().distanceSquared(p.getLocation()) <= radius * radius) {
                // Calcula a direção oposta do jogador alvo para empurrá-lo para longe
                Vector direction = p.getLocation().toVector().subtract(player.getLocation().toVector()).normalize();
                p.setVelocity(direction.multiply(power).add(new Vector(0, 0.5, 0))); // Aplica o impulso
                p.spawnParticle(Particle.EXPLOSION_LARGE, p.getLocation(), 1); // Efeito visual
            }
        }
    }

    private void setCooldown(Player player, Material item, long currentTime) {
        if (!gravityWars.playerCooldowns.containsKey(player)) {
            gravityWars.playerCooldowns.put(player, new HashMap<>());
        }
        Map<Material, Long> playerCooldown = gravityWars.playerCooldowns.get(player);
        playerCooldown.put(item, currentTime);
    }

    private void setItemCooldown(ItemStack item, long currentTime) {
        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            meta.getPersistentDataContainer().set(new NamespacedKey("gravitywars", "cooldown"), PersistentDataType.LONG, currentTime);
            item.setItemMeta(meta);
        }
    }

    private long getItemCooldownTimeLeft(ItemStack item, long currentTime) {
        if (!item.hasItemMeta()) return 0; // No cooldown if no metadata

        ItemMeta meta = item.getItemMeta();
        if (meta != null && meta.getPersistentDataContainer().has(new NamespacedKey("gravitywars", "cooldown"), PersistentDataType.LONG)) {
            long lastUsed = meta.getPersistentDataContainer().get(new NamespacedKey("gravitywars", "cooldown"), PersistentDataType.LONG);
            long cooldown = cooldownTimes.get(item.getType());
            return cooldown - (currentTime - lastUsed); // Time left on the cooldown
        }
        return 0;
    }

    private void showCooldownOnXPBar(Player player, long cooldownTime, Material itemType) {
        // Reset the cooldown for the player but only for the specific item
        if (activeCooldownTasks.containsKey(player)) {
            activeCooldownTasks.get(player).cancel();
        }

        BukkitTask task = new BukkitRunnable() {
            long remainingTime = cooldownTime / 1000;  // cooldownTime should be in milliseconds, converting to seconds

            @Override
            public void run() {
                if (remainingTime <= 0) {
                    player.setExp(0);
                    player.setLevel(0);
                    activeCooldownTasks.remove(player);
                    resetCooldownState(player, itemType);  // Reset cooldown for the specific item
                    cancel();
                    return;
                }
                float progress = (float) remainingTime / (cooldownTime / 1000);
                player.setExp(progress);
                player.setLevel((int) remainingTime);
                remainingTime--;
            }
        }.runTaskTimer(gravityWars.plugin, 0, 20L);  // Every 1 second (20 ticks)

        activeCooldownTasks.put(player, task);
    }

    private void switchDevice(Player player, ItemStack currentItem, EquipmentSlot slot) {
        String currentDisplayName = currentItem.getItemMeta().getDisplayName();
        String nextDeviceName = getNextDeviceName(currentDisplayName);

        if (nextDeviceName != null) {

            // Remove current item before switching and store cooldown only if switching out
            if (slot == EquipmentSlot.HAND) {
                player.getInventory().setItemInMainHand(null);
            } else {
                player.getInventory().setItemInOffHand(null);
            }

            // Store the cooldown before switching if necessary
            storeCooldown(player, currentItem);

            // Cancel any active cooldown task
            if (activeCooldownTasks.containsKey(player)) {
                activeCooldownTasks.get(player).cancel();
                activeCooldownTasks.remove(player);
            }

            // Reset cooldown visual
            player.setExp(0);
            player.setLevel(0);

            // Give the player the new device
            givePlayerGravityDevice(player, nextDeviceName, slot);
            player.sendTitle(nextDeviceName, "", 0, 40, 20);

            // After switching the item, restore the cooldown of the new item (if applicable)
            ItemStack newItem = slot == EquipmentSlot.HAND ? player.getInventory().getItemInMainHand() : player.getInventory().getItemInOffHand();
            if (newItem != null && cooldownTimes.containsKey(newItem.getType())) {
                restoreCooldown(player, newItem);  // Restore cooldown from previously stored cooldown
            }
        }
    }

    // Storing cooldowns only when switching out of the device
    private void storeCooldown(Player player, ItemStack item) {
        if (cooldownTimes.containsKey(item.getType())) {
            long currentTime = System.currentTimeMillis();
            long cooldownLeft = getItemCooldownTimeLeft(item, currentTime);

            if (cooldownLeft > 0) {
                gravityWars.playerCooldowns.putIfAbsent(player, new HashMap<>());
                // Store the remaining time, not the absolute timestamp
                gravityWars.playerCooldowns.get(player).put(item.getType(), currentTime + cooldownLeft);
            }
        }
    }

    // Restores the cooldown time when switching back to an item
    private void restoreCooldown(Player player, ItemStack item) {
        if (gravityWars.playerCooldowns.containsKey(player) && gravityWars.playerCooldowns.get(player).containsKey(item.getType())) {
            long storedCooldownEndTime = gravityWars.playerCooldowns.get(player).get(item.getType()); // Get stored cooldown end timestamp
            long currentTime = System.currentTimeMillis();
            long cooldownLeft = storedCooldownEndTime - currentTime; // Calculate remaining cooldown

            if (cooldownLeft > 0) {
                showCooldownOnXPBar(player, cooldownLeft, item.getType());
                gravityWars.playerCooldowns.get(player).put(item.getType(), storedCooldownEndTime); // Ensure cooldown is still active
            } else {
                gravityWars.playerCooldowns.get(player).remove(item.getType()); // Cleanup expired cooldowns
            }
        }
    }

    private boolean isItemOnCooldown(Player player, ItemStack item) {
        if (gravityWars.playerCooldowns.containsKey(player) && gravityWars.playerCooldowns.get(player).containsKey(item.getType())) {
            // Get the last time the item was used
            long lastUsedTime = gravityWars.playerCooldowns.get(player).get(item.getType());
            // Get the cooldown duration for the item
            long cooldownDuration = cooldownTimes.get(item.getType());
            // Get the current time
            long currentTime = System.currentTimeMillis();

            // If cooldown duration is 0, return false as item can be used immediately
            if (cooldownDuration == 0) {
                return false;
            }

            // Check if the cooldown period has not yet passed
            if (currentTime - lastUsedTime < cooldownDuration) {
                return true; // The item is still on cooldown, return true indicating it can't be used
            }
        }
        return false; // The item is not on cooldown, it can be used
    }

    private void resetCooldownState(Player player, Material itemType) {
        // Only reset the cooldown for the specified item type
        if (gravityWars.playerCooldowns.containsKey(player)) {
            Map<Material, Long> playerCooldown = gravityWars.playerCooldowns.get(player);
            playerCooldown.remove(itemType);  // Remove only the cooldown for the item used
        }
    }
}
*/
