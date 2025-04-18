package lcc.lusocraftchampionship.minigame.GravityWars.listener;

import lcc.lusocraftchampionship.lcc.team.Teams;
import lcc.lusocraftchampionship.minigame.GravityWars.GravityWars;
import lcc.lusocraftchampionship.util.Particles;
import net.minecraft.network.protocol.game.PacketPlayInClientCommand;
import net.minecraft.server.level.EntityPlayer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.chunk.BulkSectionAccess;
import org.bukkit.*;
import org.bukkit.craftbukkit.v1_20_R1.entity.CraftPlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Objects;
import java.util.UUID;

public class PlayerRespawnListener implements Listener {
    GravityWars gravityWars;

    public PlayerRespawnListener(GravityWars gravityWars) {
        this.gravityWars = gravityWars;
    }

    @EventHandler
    public void onPlayerHitByPlayer(EntityDamageByEntityEvent event) {
        Player player = (Player) event.getEntity();
        if (!(event.getEntity() instanceof Player)) return;
        if (!player.getLocation().getWorld().getName().equals(gravityWars.getWorldName())) return;

        if (event.getCause().equals(EntityDamageEvent.DamageCause.FALL)){
            event.setCancelled(true);
        }

        // Check if player is about to "die"
        double finalHealth = player.getHealth() - event.getFinalDamage();
        if (finalHealth > 0.1) return; // Not "dead" yet

        // Cancel actual death
        event.setCancelled(true);
        simulateDeath(player);
        scheduleRespawn(player);
        ((Player) event.getEntity()).getKiller().sendTitle("", Teams.INSTANCE.getIconPrefix(Teams.INSTANCE.getPlayerTeam(event.getDamager().getName())) + event.getDamager().getName() + "§r" + " deu um §l§cL§r no " + Teams.INSTANCE.getIconPrefix(Teams.INSTANCE.getPlayerTeam(((Player) event.getEntity()).getPlayer())) + event.getEntity().getName(), 0, 60, 0);
        player.sendTitle("", Teams.INSTANCE.getIconPrefix(Teams.INSTANCE.getPlayerTeam(event.getDamager().getName())) + event.getDamager().getName() + "§r" + " deu um §l§cL§r no " + Teams.INSTANCE.getIconPrefix(Teams.INSTANCE.getPlayerTeam(((Player) event.getEntity()).getPlayer())) + event.getEntity().getName(), 0, 60, 0);
        for (Player p : Bukkit.getOnlinePlayers()) {
            p.sendMessage((Teams.INSTANCE.getIconPrefix(Teams.INSTANCE.getPlayerTeam(event.getDamager().getName())) + event.getDamager().getName() + "§r" + " deu um §l§cL§r no " + Teams.INSTANCE.getIconPrefix(Teams.INSTANCE.getPlayerTeam(((Player) event.getEntity()).getPlayer())) + event.getEntity().getName()));
        }
    }


    @EventHandler
    public void onVoidPlayer(EntityDamageEvent event) {
        if (!(event.getEntity() instanceof Player)) return;
        if (!event.getEntity().getLocation().getWorld().getName().equals(gravityWars.getWorldName())) return;
        if (event.getCause() == EntityDamageEvent.DamageCause.VOID) {

            Player player = (Player) event.getEntity();

            // Cancel actual death
            player.teleport(gravityWars.getSpawnpoint());
            event.setCancelled(true);
            simulateDeath(player);
            scheduleRespawn(player);
//        for (Player p : Bukkit.getOnlinePlayers()) {
//            p.sendMessage((Objects.requireNonNull(Teams.INSTANCE.getIconPrefix(Teams.INSTANCE.getPlayerTeam(event.getEntity().getName()) + event.getEntity().getName() + "§r" + " morreu no void"))));
//        }
        }
    }

    @EventHandler
    public void onAnyDeath(EntityDamageEvent event) {
        if (!(event.getEntity() instanceof Player)) return;
        if (event.getCause().equals(EntityDamageEvent.DamageCause.FALL)){
            event.setCancelled(true);
        }
        if (!event.getEntity().getLocation().getWorld().getName().equals(gravityWars.getWorldName())) return;

        Player player = (Player) event.getEntity(); // Safe cast
        // Your existing logic here

        // Check if player is about to "die"
        double finalHealth = player.getHealth() - event.getFinalDamage();
        if (finalHealth > 0.1) return; // Ensure the player is actually "dying"

        // Cancel actual death
        event.setCancelled(true);
        simulateDeath(player);
        scheduleRespawn(player);
//        for (Player p : Bukkit.getOnlinePlayers()) {
//            p.sendMessage((Objects.requireNonNull(Teams.INSTANCE.getIconPrefix(Teams.INSTANCE.getPlayerTeam(event.getEntity().getName()) + event.getEntity().getName() + "§r" + " morreu"))));
//        }
    }

    private void simulateDeath(Player player) {
        Player respawnPlayer = Bukkit.getPlayer(player.getUniqueId());
        String respawnTeam = Teams.INSTANCE.getPlayerTeam(respawnPlayer);
        if (respawnPlayer == null || !respawnPlayer.isOnline()) return; // Ensure player is online
        if (respawnTeam == null) return; // Safety check

        player.setGlowing(false);
        player.setGameMode(GameMode.SPECTATOR);
        player.getInventory().clear();
        player.setHealth(20.0); // Reset health so they don't die

        gravityWars.playerCooldowns.remove(player);
        // Check if player lost their totem
        if (gravityWars.teamTotemHolders.containsKey(respawnTeam) && gravityWars.teamTotemHolders.get(respawnTeam).equals(player)) {
            gravityWars.teamTotemHolders.remove(respawnTeam);
            player.sendMessage(ChatColor.RED + "Perdes-te o Totem oh otário!");
        }

        player.sendMessage("§cVais dar respawn em 5 segundos calma caralho...");
    }

    private void scheduleRespawn(Player player) {
        new BukkitRunnable() {
            @Override
            public void run() {
                Player respawnPlayer = Bukkit.getPlayer(player.getUniqueId());
                String respawnTeam = Teams.INSTANCE.getPlayerTeam(respawnPlayer);
                if (respawnPlayer == null || !respawnPlayer.isOnline()) return; // Ensure player is online
                if (respawnTeam == null) return; // Safety check

                respawnPlayer.teleport(getTeamSpawn(respawnTeam));
                respawnPlayer.setGameMode(GameMode.SURVIVAL);
                respawnPlayer.setHealth(20.0);
                respawnPlayer.setFoodLevel(20);
                respawnPlayer.getInventory().clear();

                gravityWars.givePlayerKit(respawnPlayer);
                gravityWars.givePlayerGravityDevice(respawnPlayer);
            }
        }.runTaskLater(gravityWars.plugin, 5 * 20L);  // Respawn after 5 seconds
    }

    // Utility function to get team spawn point
    private Location getTeamSpawn(String team) {
        switch (team) {
            case "Blaze":
                return gravityWars.getTeam1Spawn();
            case "Creeper":
                return gravityWars.getTeam2Spawn();
            case "Skeleton":
                return gravityWars.getTeam3Spawn();
            case "Enderman":
                return gravityWars.getTeam4Spawn();
            default:
                return gravityWars.getSpawnpoint();
        }
    }
}
