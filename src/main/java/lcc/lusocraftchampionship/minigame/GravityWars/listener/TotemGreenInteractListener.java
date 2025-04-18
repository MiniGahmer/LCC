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
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.*;

public class TotemGreenInteractListener implements Listener {
    private final GravityWars gravityWars;
    private static final String CHALLENGE_TITLE = ChatColor.GREEN + "Extração do Totem";
    private static final int TIME_LIMIT = 15;
    private static final int MAX_MISTAKES = 3;

    private List<Integer> borderTiles;
    private Player currentPlayer;
    private long challengeEndTime;
    private final HashMap<UUID, Integer> playerMistakes = new HashMap<>();
    private final HashMap<UUID, BukkitTask> activeCountdowns = new HashMap<>();

    public TotemGreenInteractListener(GravityWars gravityWars) {
        this.gravityWars = gravityWars;
    }

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
        if (clickedBlock.getType() == Material.GREEN_GLAZED_TERRACOTTA) {
            if (gravityWars.isTotemBlock(clickedBlockMaterial)) {
                Location totemLocation = gravityWars.getTotemLocation(clickedBlockMaterial, restrictedIndex);

                if (!Objects.equals(blockLocation, gravityWars.getGreenTotem().get(2))) {
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
                startChallenge(player);
            }
        }
    }

    public void startChallenge(Player player) {
        if (activeCountdowns.containsKey(player.getUniqueId())) return;

        currentPlayer = player;
        borderTiles = getBorderTiles();
        challengeEndTime = System.currentTimeMillis() + TIME_LIMIT * 1000;
        playerMistakes.put(player.getUniqueId(), 0);

        player.openInventory(createChallengeGUI());
        startCountdown(player);
        //player.openInventory(gui);
        //Inventory gui = createChallengeGUI();
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        stopCountdown(event.getPlayer());
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        if (event.getView().getTitle().equals(CHALLENGE_TITLE)) {
            stopCountdown((Player) event.getPlayer());
        }
    }

    private List<Integer> getBorderTiles() {
        List<Integer> tiles = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            tiles.add(i);
            tiles.add(45 + i);
        }
        for (int i = 1; i < 5; i++) {
            tiles.add(9 * i);
            tiles.add(8 + 9 * i);
        }
        return tiles;
    }

    private Inventory createChallengeGUI() {
        Inventory gui = Bukkit.createInventory(null, 54, CHALLENGE_TITLE);
        borderTiles.forEach(index -> gui.setItem(index, createTileItem(Material.GOLD_BLOCK, ChatColor.YELLOW + "Remove Este Bloco")));
        for (int i = 0; i < 54; i++) {
            if (!borderTiles.contains(i)) {
                gui.setItem(i, createTileItem(Material.BLACK_CONCRETE, ChatColor.DARK_GRAY + "Totem"));
            }
        }
        return gui;
    }

    private ItemStack createTileItem(Material material, String name) {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            meta.setDisplayName(name);
            item.setItemMeta(meta);
        }
        return item;
    }

    private void startCountdown(Player player) {
        BukkitTask task = new BukkitRunnable() {
            @Override
            public void run() {
                if (currentPlayer == null || !currentPlayer.isOnline()) {
                    stopCountdown(player);
                    return;
                }

                long timeLeft = (challengeEndTime - System.currentTimeMillis()) / 1000;
                if (timeLeft <= 0) {
                    player.sendMessage(ChatColor.RED + "Acabou o tempo! Não conseguiste extrair o Totem.");
                    player.closeInventory();
                    shockPlayer(player);
                    resetChallenge();
                    stopCountdown(player);
                } else {
                    player.sendMessage(ChatColor.YELLOW + "Tempo: " + timeLeft + "s");
                }
            }
        }.runTaskTimer(gravityWars.plugin, 0L, 20L);
        activeCountdowns.put(player.getUniqueId(), task);
    }

    private void stopCountdown(Player player) {
        BukkitTask task = activeCountdowns.remove(player.getUniqueId());
        if (task != null) {
            task.cancel();
        }
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (!event.getView().getTitle().equals(CHALLENGE_TITLE)) return;

        Player player = (Player) event.getWhoClicked();
        event.setCancelled(true);
        if (currentPlayer != player) return;

        int clickedSlot = event.getSlot();
        if (borderTiles.contains(clickedSlot)) {
            event.getInventory().setItem(clickedSlot, new ItemStack(Material.AIR));
            borderTiles.remove(Integer.valueOf(clickedSlot));
            player.sendMessage(ChatColor.GREEN + "Boa! Continua a remover.");
            if (borderTiles.isEmpty()) completeChallenge(player);
        } else {
            handleMistake(player);
        }
    }

    private void completeChallenge(Player player) {
        player.sendMessage(ChatColor.GOLD + "Extraíste o Totem! Protege-o e coloca-o na tua base!");
        givePlayerTotemPiece(player);
        resetChallenge();
        player.closeInventory();
    }

    private void givePlayerTotemPiece(Player player) {
        String team = Teams.INSTANCE.getPlayerTeam(player);
        ItemStack totemPiece = new ItemStack(Material.GREEN_GLAZED_TERRACOTTA);
        ItemMeta meta = totemPiece.getItemMeta();
        if (meta != null) {
            meta.setDisplayName(ChatColor.GOLD + "GREEN TOTEM PIECE");
            totemPiece.setItemMeta(meta);
            totemPiece.addUnsafeEnchantment(Enchantment.DURABILITY, 3);
        }
        player.setGlowing(true);
        player.getInventory().addItem(totemPiece);
        gravityWars.teamTotemHolders.put(team, player);
    }

    private void handleMistake(Player player) {
        int mistakes = playerMistakes.getOrDefault(player.getUniqueId(), 0) + 1;
        playerMistakes.put(player.getUniqueId(), mistakes);

        if (mistakes >= MAX_MISTAKES) {
            player.sendMessage(ChatColor.RED + "Muitos erros! O Totem rejeitou-te.");
            player.closeInventory();
            shockPlayer(player);
            resetChallenge();
        } else {
            player.sendMessage(ChatColor.RED + "Bloco errado! Erro: " + mistakes + "/" + MAX_MISTAKES);
            shockPlayer(player);
        }
    }

    private void shockPlayer(Player player) {
        player.damage(2);
    }

    private void resetChallenge() {
        borderTiles = null;
        currentPlayer = null;
    }

}
