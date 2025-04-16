package lcc.lusocraftchampionship.minigame.WildWestBounty.listener;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import lcc.lusocraftchampionship.minigame.WildWestBounty.WildWestBounty;
import net.minecraft.network.protocol.game.ClientboundAddPlayerPacket;
import net.minecraft.network.protocol.game.ClientboundPlayerInfoUpdatePacket;
import net.minecraft.server.level.ServerPlayer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.craftbukkit.v1_20_R1.CraftServer;
import org.bukkit.craftbukkit.v1_20_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_20_R1.entity.CraftPlayer;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.event.vehicle.VehicleEnterEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class HorseTraderNPC implements Listener {
    WildWestBounty wwb;

    public HorseTraderNPC(WildWestBounty wwb) {
        this.wwb = wwb;
    }

    private final Map<UUID, UUID> playerHorses = new HashMap<>(); // Store Player ID → Horse ID

    @EventHandler
    public void onNPCClick(PlayerInteractEntityEvent event) {
        Player player = event.getPlayer();
            if (event.getRightClicked().getName().equals("§6§lJorge"))
                openHorseShop(player);
        }

    public void openHorseShop(Player player) {
        Inventory shop = Bukkit.createInventory(null, 9, ChatColor.DARK_GREEN + "Horse Trader");

        shop.setItem(2, createHorseItem(Material.LEATHER_HORSE_ARMOR, "Basic Horse", 0.2, 0.8, 15));
        shop.setItem(4, createHorseItem(Material.IRON_HORSE_ARMOR, "Swift Stallion", 0.4, 0.9, 30));
        shop.setItem(6, createHorseItem(Material.DIAMOND_HORSE_ARMOR, "Legendary Mustang", 1, 1.4, 50));

        player.openInventory(shop);
    }

    private ItemStack createHorseItem(Material material, String name, double speed, double jump, double health) {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            meta.setDisplayName(ChatColor.YELLOW + name);
            meta.setLore(java.util.Arrays.asList(
                    ChatColor.GRAY + "Speed: " + ChatColor.WHITE + speed,
                    ChatColor.GRAY + "Jump: " + ChatColor.WHITE + jump,
                    ChatColor.GRAY + "Health: " + ChatColor.WHITE + health,
                    ChatColor.GREEN + "Click to Buy!"
            ));
            item.setItemMeta(meta);
        }
        return item;
    }

    @EventHandler
    public void onHorseShopClick(InventoryClickEvent event) {
        if (event.getView().getTitle().equals(ChatColor.DARK_GREEN + "Horse Trader")) {
            event.setCancelled(true);

            Player player = (Player) event.getWhoClicked();
            ItemStack clickedItem = event.getCurrentItem();
            if (clickedItem == null || !clickedItem.hasItemMeta()) return;

            String itemName = ChatColor.stripColor(clickedItem.getItemMeta().getDisplayName());

            switch (itemName) {
                case "Basic Horse":
                    giveHorse(player, 0.2, 0.8, 15);
                    break;
                case "Swift Stallion":
                    giveHorse(player, 0.4, 0.9, 30);
                    break;
                case "Legendary Mustang":
                    giveHorse(player, 1, 1.4, 50);
                    break;
                default:
                    return;
            }

            player.closeInventory(); // Close GUI after selecting a horse
        }
    }

    public void giveHorse(Player player, double speed, double jump, double health) {
        Horse horse = (Horse) player.getWorld().spawnEntity(player.getLocation(), EntityType.HORSE);
        horse.setOwner(player);
        horse.setTamed(true);
        horse.setAdult();
        horse.setCustomName("§6§l" + player.getName() + "'s§r§l Horse");
        horse.setCustomNameVisible(true);
        horse.getInventory().setSaddle(new ItemStack(Material.SADDLE)); // Add saddle so it's rideable

        // **Fix the health issue**
        AttributeInstance maxHealthAttribute = horse.getAttribute(Attribute.GENERIC_MAX_HEALTH);
        if (maxHealthAttribute != null) {
            maxHealthAttribute.setBaseValue(health); // Set max health correctly
        }
        horse.setHealth(health); // Apply full health

        // **Set speed and jump strength internally**
        AttributeInstance speedAttribute = horse.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED);
        if (speedAttribute != null) {
            speedAttribute.setBaseValue(speed);
        }

        AttributeInstance jumpAttribute = horse.getAttribute(Attribute.HORSE_JUMP_STRENGTH);
        if (jumpAttribute != null) {
            jumpAttribute.setBaseValue(jump);
        }

        player.sendMessage(ChatColor.GREEN + "You bought a " + horse.getCustomName() + "!");

        // Store the horse's UUID to prevent others from riding it
        playerHorses.put(player.getUniqueId(), horse.getUniqueId());
    }

    @EventHandler
    public void onHorseMount(VehicleEnterEvent event) {
        if (event.getEntered() instanceof Player && event.getVehicle() instanceof Horse) {
            Player player = (Player) event.getEntered();
            Horse horse = (Horse) event.getVehicle();

            UUID ownerUUID = playerHorses.get(player.getUniqueId());
            if (ownerUUID == null || !ownerUUID.equals(horse.getUniqueId())) {
                player.sendMessage(ChatColor.RED + "You can only ride your own horse!");
                event.setCancelled(true);
            }
        }
    }
}
