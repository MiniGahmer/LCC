package lcc.lusocraftchampionship.minigame.GravityWars.listener;
/*
package lcc.lusocraftchampionship.minigame.GravityWars.listener;

import lcc.lusocraftchampionship.lcc.team.Teams;
import lcc.lusocraftchampionship.minigame.GravityWars.GravityWars;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;

public class TotemBlueInteractListener implements Listener {
    GravityWars gravityWars;

    public TotemBlueInteractListener(GravityWars gravityWars) {
        this.gravityWars = gravityWars;
    }

    private final Map<Player, Integer> playerAttempts = new HashMap<>();
    private final Map<Player, Integer> playerLockStatus = new HashMap<>();
    private final Map<Player, Timer> playerTimers = new HashMap<>();
    private final Random random = new Random();
    private static final int MAX_TUMBLERS = 8;
    private static final int MIN_TUMBLERS = 4;

    @EventHandler
    private void onPlayerInteract(PlayerInteractEvent event) {
        if (event.getClickedBlock() == null) return;

        Player player = event.getPlayer();
        String team = Teams.INSTANCE.getPlayerTeam(player);
        Material clickedBlockMaterial = event.getClickedBlock().getType();
        Location blockLocation = event.getClickedBlock().getLocation();
        Block clickedBlock = event.getClickedBlock();

        // Get the team of the player
        String playerTeam = Teams.INSTANCE.getPlayerTeam(player); // Implement this method to get the player's team
        // Load the restricted index for the team from Teams.INSTANCE.yml
        int restrictedIndex = Teams.INSTANCE.getTotemTeam(playerTeam);
        // Ensure the player's team exists in the restriction map
        if (restrictedIndex == -1) {
            return; // If the restricted index is not found, do nothing
        }

        if (!player.getWorld().getName().equals(gravityWars.getWorldName())) return;
        if (!event.getAction().name().contains("RIGHT_CLICK")) return;
        if (clickedBlock.getType() == Material.LIGHT_BLUE_GLAZED_TERRACOTTA) {
            if (gravityWars.isTotemBlock(clickedBlockMaterial)) {
                Location totemLocation = gravityWars.getTotemLocation(clickedBlockMaterial, restrictedIndex);

                if (!Objects.equals(blockLocation, gravityWars.getBlueTotem().get(1))) {
                    player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent("§cEste Totem não é desta equipa!"));
                    event.setCancelled(true);
                    return;
                }

                Block teamTotemBlock = event.getPlayer().getWorld().getBlockAt(totemLocation);
                if (teamTotemBlock.getType() != Material.AIR) {
                    player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent("§cEste Totem ou é teu, ou já o tens!"));
                    event.setCancelled(true);
                    return;
                }

                if (Objects.equals(blockLocation, totemLocation)) {
                    player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent("§cNão podes interagir com o teu próprio Totem!"));
                    event.setCancelled(true);
                    return;
                }
            }

            if (gravityWars.teamTotemHolders.containsKey(team)) {
                player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent("§cA tua equipa tem atualmente um Totem! Protege-o!"));
            } else {
                openLockPickingGUI(player);
            }
        }
    }

    private void openLockPickingGUI(Player player) {
        int numTumblers = random.nextInt(MAX_TUMBLERS - MIN_TUMBLERS + 1) + MIN_TUMBLERS;  // Random tumbler count between MIN and MAX
        Inventory gui = Bukkit.createInventory(null, 54, ChatColor.BLUE + "Lock Picking Challenge");

        // Create a list of random slots for the tumblers
        int[] randomSlots = generateRandomSlots(numTumblers);
        for (int i = 0; i < numTumblers; i++) {
            gui.setItem(randomSlots[i], createTumblerItem(i));
        }

        // Add lock item in the last slot (slot 53)
        ItemStack lockItem = new ItemStack(Material.IRON_BLOCK);
        ItemMeta meta = lockItem.getItemMeta();
        if (meta != null) {
            meta.setDisplayName(ChatColor.YELLOW + "Fechadura");
            lockItem.setItemMeta(meta);
        }
        gui.setItem(53, lockItem);

        player.openInventory(gui);
        // Start the timer for the lock-picking challenge
        startLockPickingTimer(player, numTumblers);
    }

    private ItemStack createTumblerItem(int index) {
        ItemStack tumbler = new ItemStack(Material.IRON_BARS);
        ItemMeta meta = tumbler.getItemMeta();
        if (meta != null) {
            meta.setDisplayName(ChatColor.GREEN + "Tumbler " + (index + 1));
            tumbler.setItemMeta(meta);
        }
        return tumbler;
    }

    private int[] generateRandomSlots(int numTumblers) {
        int[] slots = new int[numTumblers];
        boolean[] occupied = new boolean[54];  // Tracks which slots are taken

        for (int i = 0; i < numTumblers; i++) {
            int slot;
            do {
                slot = random.nextInt(44); // Only use slots 0-43 for tumblers (leave 44-53 for lock)
            } while (occupied[slot]); // Ensure no overlap
            slots[i] = slot;
            occupied[slot] = true; // Mark slot as occupied
        }

        return slots;
    }

    private void startLockPickingTimer(Player player, int numTumblers) {
        playerAttempts.put(player, 0);
        playerLockStatus.put(player, numTumblers);

        // Timer to "shock" player if too long
        Timer shockTimer = new Timer();
        playerTimers.put(player, shockTimer);
        shockTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (playerLockStatus.containsKey(player) && playerLockStatus.get(player) > 0) {
                    player.sendMessage(ChatColor.RED + "Demoras-te muito tempo! O Totem teve um curto circuito.");
                    playerLockStatus.remove(player);
                    playerAttempts.remove(player);
                    // Close the inventory on the main thread
                    Bukkit.getScheduler().runTask(gravityWars.plugin, () -> {
                        player.closeInventory();
                        shockPlayer(player);
                        playerLockStatus.remove(player);
                        playerAttempts.remove(player);
                    });
                }
            }
        }, 10000L);  // 10 seconds to pick the lock (adjust time as needed)
    }

    private void shockPlayer(Player player) {
        // Apply shock effects (could be damage, blindness, etc.)
        player.damage(2);  // For example, deal 2 damage
        player.sendMessage(ChatColor.RED + "Cuidado!");
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (!event.getView().getTitle().equals(ChatColor.BLUE + "Lock Picking Challenge")) return;
        event.setCancelled(true);

            Player player = (Player) event.getWhoClicked();
            ItemStack clickedItem = event.getCurrentItem();
            if (clickedItem == null || !clickedItem.hasItemMeta()) return;

            // Check if the player is clicking a tumbler
        String itemName = clickedItem.getItemMeta().getDisplayName();
            if (itemName.contains("Tumbler")) {
                int tumblerIndex = Integer.parseInt(itemName.replaceAll("[^0-9]", "")) - 1;

                // Check if the tumbler is the correct one to click (e.g., in the right order or aligned)
                if (playerAttempts.get(player) == tumblerIndex) {
                    // Correct tumbler clicked
                    player.sendMessage(ChatColor.GREEN + "Alinhamento correto!");
                    playerAttempts.put(player, playerAttempts.get(player) + 1);

                    if (playerAttempts.get(player) == playerLockStatus.get(player)) {
                        player.sendMessage(ChatColor.GOLD + "Roubas-te o Totem! Protege-o e coloca-o na tua base!");
                        playerLockStatus.remove(player);
                        player.closeInventory();  // Close the GUI when the challenge is completed
                        givePlayerTotemPiece(player);
                    }
                } else {
                    // Incorrect tumbler clicked
                    player.sendMessage(ChatColor.RED + "Incorreto! Tenta outra vez!");
                    shockPlayer(player);
                    player.closeInventory();        // Optional shock when wrong click
                }
        }
    }


    private void givePlayerTotemPiece(Player player) {
        ItemStack totemPiece = new ItemStack(Material.LIGHT_BLUE_GLAZED_TERRACOTTA);
        ItemMeta meta = totemPiece.getItemMeta();
        String team = Teams.INSTANCE.getPlayerTeam(player);
        if (meta != null) {
            meta.setDisplayName(ChatColor.GOLD + "BLUE TOTEM PIECE");
            totemPiece.setItemMeta(meta);
            totemPiece.addUnsafeEnchantment(Enchantment.DURABILITY, 3);
        }
        player.setGlowing(true);
        gravityWars.teamTotemHolders.put(team, player);
        player.getInventory().addItem(totemPiece);
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        Player player = (Player) event.getPlayer();

        if (event.getView().getTitle().equals(ChatColor.BLUE + "Lock Picking Challenge")) {
            // Cancel the timer if the player closes the inventory
            Timer timer = playerTimers.get(player);
            if (timer != null) {
                timer.cancel();
                playerTimers.remove(player);
            }
            // Make sure to stop the shock effect if the player leaves before time is up
            if (playerLockStatus.containsKey(player)) {
                playerLockStatus.remove(player);
                playerAttempts.remove(player);
            }
        }
    }
}
*/
