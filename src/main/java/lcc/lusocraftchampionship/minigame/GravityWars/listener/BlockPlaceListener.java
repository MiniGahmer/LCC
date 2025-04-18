//package lcc.lusocraftchampionship.minigame.GravityWars.listener;
//
//import lcc.lusocraftchampionship.lcc.team.Teams;
//import lcc.lusocraftchampionship.minigame.GravityWars.GravityWars;
//import lcc.lusocraftchampionship.util.BlockHandler;
//import net.md_5.bungee.api.ChatMessageType;
//import net.md_5.bungee.api.chat.TextComponent;
//import org.bukkit.Bukkit;
//import org.bukkit.ChatColor;
//import org.bukkit.Location;
//import org.bukkit.Material;
//import org.bukkit.block.Block;
//import org.bukkit.entity.Player;
//import org.bukkit.event.EventHandler;
//import org.bukkit.event.Listener;
//import org.bukkit.event.block.BlockPlaceEvent;
//import org.bukkit.inventory.EntityEquipment;
//import org.bukkit.inventory.ItemStack;
//
//import java.util.*;
//
//public class BlockPlaceListener implements Listener {
//    GravityWars gravityWars;
//
//    public BlockPlaceListener(GravityWars gravityWars) {
//        this.gravityWars = gravityWars;
//    }
//
//    @EventHandler
//    public void onBlockPlace(BlockPlaceEvent event) {
//        Player player = event.getPlayer();
//        Location blockLocation = event.getBlock().getLocation();
//        Material clickedBlockMaterial = event.getBlock().getType();
//
//        // Get the team of the player
//        String playerTeam = Teams.INSTANCE.getPlayerTeam(player); // Implement this method to get the player's team
//        // Load the restricted index for the team from Teams.INSTANCE.yml
//        int restrictedIndex = Teams.INSTANCE.getTotemTeam(playerTeam);
//        // Ensure the player's team exists in the restriction map
//        if (restrictedIndex == -1) {
//            return; // If the restricted index is not found, do nothing
//        }
//
//        if (player.isOp()) return;
//        if (player.getLocation().getWorld().getName().equals(gravityWars.getWorldName())) {
//            EntityEquipment equipment = player.getEquipment();
//            ItemStack main = equipment.getItemInMainHand();
//            ItemStack off = equipment.getItemInOffHand();
//            if (gravityWars.WOOLS.contains(player.getInventory().getItemInMainHand().getType())) {
//                player.getInventory().setItemInMainHand(main);
//            }
//            if (gravityWars.WOOLS.contains(player.getInventory().getItemInOffHand().getType())) {
//                player.getInventory().setItemInOffHand(off);
//            }
//
//            if (gravityWars.TOTEM_BLOCK.contains(event.getBlock().getType())) {
//                List<Location> totemLocations = new ArrayList<>();
//
//                // Collect all totem locations dynamically
//                for (int i = 0; i <= 3; i++) {
//                    totemLocations.add(gravityWars.getBlueTotem().get(i));
//                    totemLocations.add(gravityWars.getGreenTotem().get(i));
//                    totemLocations.add(gravityWars.getRedTotem().get(i));
//                    totemLocations.add(gravityWars.getObsidianTotem().get(i));
//                }
//
//                Map<Location, Material> allowedBlocks = new HashMap<>();
//
//                // Blue Totem: Blue wool for all get(1), get(2), get(3), get(4)
//                for (int i = 0; i <= 3; i++) {
//                    allowedBlocks.put(gravityWars.getBlueTotem().get(i), Material.LIGHT_BLUE_GLAZED_TERRACOTTA);
//                }
//
//                // Green Totem: Green wool for all get(1), get(2), get(3), get(4)
//                for (int i = 0; i <= 3; i++) {
//                    allowedBlocks.put(gravityWars.getGreenTotem().get(i), Material.GREEN_GLAZED_TERRACOTTA);
//                }
//
//                // Red Totem: Red wool for all get(1), get(2), get(3), get(4)
//                for (int i = 0; i <= 3; i++) {
//                    allowedBlocks.put(gravityWars.getRedTotem().get(i), Material.RED_GLAZED_TERRACOTTA);
//                }
//
//                // Obsidian Totem: Obsidian for all get(1), get(2), get(3), get(4)
//                for (int i = 0; i <= 3; i++) {
//                    allowedBlocks.put(gravityWars.getObsidianTotem().get(i), Material.CRYING_OBSIDIAN);
//                }
//
//                // Check if blockLocation matches any of the totem locations
//                boolean isMatch = false;
//                for (Location totemLocation : totemLocations) {
//                    if (blockLocation.getBlockX() == totemLocation.getBlockX() &&
//                            blockLocation.getBlockY() == totemLocation.getBlockY() &&
//                            blockLocation.getBlockZ() == totemLocation.getBlockZ()) {
//
//                        // Check if the block type matches the allowed block for this totem
//                        Material allowedBlock = allowedBlocks.get(totemLocation);
//                        if (allowedBlock != null && event.getBlock().getType() == allowedBlock) {
//                            isMatch = true;
//                            break; // Exit the loop once a match is found
//                        }
//                    }
//                }
//
//                if (isMatch) {
//                    if (gravityWars.isTotemBlock(clickedBlockMaterial)) {
//                        // Check if the broken block is at the restricted index
//                        Location totemLocation = gravityWars.getTotemLocation(clickedBlockMaterial, restrictedIndex);
//
//                        if (totemLocation != null && blockLocation.equals(totemLocation)) {
//                            String team = Teams.INSTANCE.getPlayerTeam(player);
//                            if (gravityWars.teamTotemHolders.containsKey(team) && gravityWars.teamTotemHolders.get(team).equals(player)) {
//                                gravityWars.teamTotemHolders.remove(team);
//                                player.sendMessage(ChatColor.GOLD + "Totem Adquirido!");
//                            }
//                            player.setGlowing(false);
//                            event.setCancelled(false);  // Prevent the player from breaking the block
//                        } else {
//                            event.setCancelled(true);
//                            player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent("§cYou can't place this totem in another team's base!"));
//                        }
//                    }
//                } else {
//                    event.setCancelled(true);
//                }
//            } else if (gravityWars.WOOLS.contains(event.getBlock().getType())) {
//                event.setCancelled(false);
//            } else {
//                event.setCancelled(true);
//            }
//        }
//    }
//}
