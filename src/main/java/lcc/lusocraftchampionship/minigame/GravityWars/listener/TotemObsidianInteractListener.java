/*
package lcc.lusocraftchampionship.minigame.GravityWars.listener;

import lcc.lusocraftchampionship.lcc.team.Teams;
import lcc.lusocraftchampionship.minigame.GravityWars.GravityWars;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class TotemObsidianInteractListener implements Listener {
    GravityWars gravityWars;

    public TotemObsidianInteractListener(GravityWars gravityWars) {
        this.gravityWars = gravityWars;
    }

    @EventHandler
    public void onTotemInteract(BlockBreakEvent event) {
        Player player = event.getPlayer();
        Block block = event.getBlock();
        String team = Teams.INSTANCE.getPlayerTeam(player);
        Location blockLocation = event.getBlock().getLocation();
        Material brokenBlock = event.getBlock().getType();

        // Get the team of the player
        String playerTeam = Teams.INSTANCE.getPlayerTeam(player); // Implement this method to get the player's team
        // Load the restricted index for the team from Teams.INSTANCE.yml
        int restrictedIndex = Teams.INSTANCE.getTotemTeam(playerTeam);
        // Ensure the player's team exists in the restriction map
        if (restrictedIndex == -1) {
            return; // If the restricted index is not found, do nothing
        }

        if (!player.getWorld().getName().equals(gravityWars.getWorldName())) return;
        if (player.getInventory().getItemInMainHand().getType().equals(Material.NETHERITE_PICKAXE) && player.getInventory().getItemInMainHand().getItemMeta().getDisplayName().equals("§b§lTotemBreaker")) {
            if (block.getType().equals(Material.CRYING_OBSIDIAN)) {
                if (gravityWars.isTotemBlock(brokenBlock)) {
                    // Check if the broken block is at the restricted index
                    Location totemLocation = gravityWars.getTotemLocation(brokenBlock, restrictedIndex);

                    if (!Objects.equals(blockLocation, gravityWars.getObsidianTotem().get(3))) {
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
                    event.setCancelled(true);
                } else {
                    event.setCancelled(true);
                    givePlayerTotemPiece(player);
                }
            }
        }
    }

    private void givePlayerTotemPiece(Player player) {
        String team = Teams.INSTANCE.getPlayerTeam(player);
        ItemStack totemPiece = new ItemStack(Material.CRYING_OBSIDIAN);
        ItemMeta meta = totemPiece.getItemMeta();
        if (meta != null) {
            meta.setDisplayName(ChatColor.GOLD + "OBSIDIAN TOTEM PIECE");
            totemPiece.setItemMeta(meta);
            totemPiece.addUnsafeEnchantment(Enchantment.DURABILITY, 3);
        }
        player.getInventory().addItem(totemPiece);
        gravityWars.teamTotemHolders.put(team, player);
        player.setGlowing(true);
        player.sendMessage(ChatColor.GOLD + "Roubas-te o Totem! Protege-o e coloca-o na tua base!!");
    }
}
*/
