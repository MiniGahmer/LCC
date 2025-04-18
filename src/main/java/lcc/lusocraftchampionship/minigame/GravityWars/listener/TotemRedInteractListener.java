package lcc.lusocraftchampionship.minigame.GravityWars.listener;

import com.comphenix.protocol.wrappers.Pair;

import lcc.lusocraftchampionship.lcc.team.Teams;
import lcc.lusocraftchampionship.minigame.GravityWars.GravityWars;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import net.minecraft.network.chat.IChatBaseComponent;
import net.minecraft.network.protocol.game.PacketPlayOutEntityMetadata;
import net.minecraft.network.protocol.game.PacketPlayOutScoreboardTeam;
import net.minecraft.network.syncher.DataWatcher;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.PlayerConnection;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_20_R1.entity.CraftPlayer;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

public class TotemRedInteractListener implements Listener {
    GravityWars gravityWars;

    public TotemRedInteractListener(GravityWars gravityWars) {
        this.gravityWars = gravityWars;
    }

    private final Map<Player, String> playerSelections = new HashMap<>();
    private final Map<Player, Set<String>> playerMatches = new HashMap<>();
    private final List<Material> wireMaterials = Arrays.asList(
            Material.REDSTONE, Material.LAPIS_LAZULI, Material.EMERALD,
            Material.AMETHYST_SHARD, Material.COPPER_INGOT, Material.GOLD_INGOT
    );
    private final List<String> wireNames = Arrays.asList("Red", "Blue", "Green", "Purple", "Gray", "Yellow");

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

        if (clickedBlock.getType() == Material.RED_GLAZED_TERRACOTTA) {
                if (gravityWars.isTotemBlock(clickedBlockMaterial)) {
                    Location totemLocation = gravityWars.getTotemLocation(clickedBlockMaterial, restrictedIndex);

                    if (!Objects.equals(blockLocation, gravityWars.getRedTotem().getFirst())) {
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
                    openCircuitGUI(player);
                }
            }
    }

    private void openCircuitGUI(Player player) {
        Inventory gui = Bukkit.createInventory(null, 54, ChatColor.RED + "Conecta os Fios");

        // Criar uma lista de pares (Material, Nome)
        List<Pair<Material, String>> wires = new ArrayList<>();
        for (int i = 0; i < wireMaterials.size(); i++) {
            wires.add(new Pair<>(wireMaterials.get(i), wireNames.get(i)));
        }

        // Embaralhar os fios
        Collections.shuffle(wires);

        // Criar uma cópia da lista e embaralhar novamente para evitar simetria
        List<Pair<Material, String>> shuffledOutputs = new ArrayList<>(wires);
        Collections.shuffle(shuffledOutputs);

        for (int i = 0; i < wires.size(); i++) {
            Pair<Material, String> leftWire = wires.get(i);
            Pair<Material, String> rightWire = shuffledOutputs.get(i); // Ordem diferente

            gui.setItem(i * 9, createWireItem(leftWire.getFirst(), ChatColor.RED + leftWire.getSecond() + " Wire"));
            gui.setItem(i * 9 + 8, createWireItem(rightWire.getFirst(), ChatColor.BLUE + rightWire.getSecond() + " Output"));
        }

        player.openInventory(gui);
    }

    private ItemStack createWireItem(Material material, String name) {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            meta.setDisplayName(name);
            item.setItemMeta(meta);
        }
        return item;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (!event.getView().getTitle().equals(ChatColor.RED + "Conecta os Fios")) return;

        event.setCancelled(true);
        Player player = (Player) event.getWhoClicked();
        ItemStack clickedItem = event.getCurrentItem();
        if (clickedItem == null || !clickedItem.hasItemMeta()) return;

        String itemName = clickedItem.getItemMeta().getDisplayName();
        if (!playerSelections.containsKey(player)) {
            playerSelections.put(player, itemName);
        } else {
            String firstSelection = playerSelections.remove(player);
            if (extractColor(firstSelection).equals(extractColor(itemName))) {
                player.sendMessage(ChatColor.GREEN + "Correto!");
                playerMatches.computeIfAbsent(player, k -> new HashSet<>()).add(extractColor(firstSelection));
                if (playerMatches.get(player).size() == 6) {
                    player.sendMessage(ChatColor.GOLD + "Roubas-te o Totem! Protege-o e coloca-o na tua base!");
                    player.closeInventory();
                    givePlayerTotemPiece(player);
                    playerMatches.remove(player);
                }
            } else {
                player.sendMessage(ChatColor.RED + "Incorreto. Tenta outra vez!");
                playerMatches.remove(player);
                player.closeInventory();
                player.damage(2);
            }
        }
    }

    private String extractColor(String itemName) {
        return wireNames.stream().filter(itemName::contains).findFirst().orElse("");
    }

    private void givePlayerTotemPiece(Player player) {
        String team = Teams.INSTANCE.getPlayerTeam(player);
        ItemStack totemPiece = new ItemStack(Material.RED_GLAZED_TERRACOTTA);
        ItemMeta meta = totemPiece.getItemMeta();
        if (meta != null) {
            meta.setDisplayName(ChatColor.GOLD + "RED TOTEM PIECE");
            totemPiece.setItemMeta(meta);
            totemPiece.addUnsafeEnchantment(Enchantment.DURABILITY, 3);
        }
        player.getInventory().addItem(totemPiece);
        gravityWars.teamTotemHolders.put(team, player);
        player.setGlowing(true);
    }


}
