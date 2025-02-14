package lcc.lusocraftchampionship.minigame.GravityWars.state;

import lcc.lusocraftchampionship.minigame.GravityWars.GravityWars;
import lcc.lusocraftchampionship.minigame.GravityWars.GravityWarsStages;
import lcc.lusocraftchampionship.minigame.Minigame;
import lcc.lusocraftchampionship.minigame.MinigameStages;
import lcc.lusocraftchampionship.team.Teams;
import lcc.lusocraftchampionship.util.DetectArea;
import lcc.lusocraftchampionship.util.Timer;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class GravityWarsInGameState extends MinigameStages {

    @Override
    public void onEnable(Minigame minigame) {
        GravityWars gravityWars = (GravityWars) minigame;

        for (Player player : Bukkit.getOnlinePlayers()) {
            if (Teams.getPlayers().contains(player.getName())) {
                if(gravityWars.getTeam1().contains(Teams.getPlayerTeam(player))) {
                    player.teleport(gravityWars.getTeam1Spawn());
                    player.setBedSpawnLocation(gravityWars.getTeam1Spawn(), true);
                    gravityWars.givePlayerKit(player);
                }

                if(gravityWars.getTeam2().contains(Teams.getPlayerTeam(player))) {
                    player.teleport(gravityWars.getTeam2Spawn());
                    player.setBedSpawnLocation(gravityWars.getTeam2Spawn(), true);
                    gravityWars.givePlayerKit(player);
                }

                if(gravityWars.getTeam3().contains(Teams.getPlayerTeam(player))) {
                    player.teleport(gravityWars.getTeam3Spawn());
                    player.setBedSpawnLocation(gravityWars.getTeam3Spawn(), true);
                    gravityWars.givePlayerKit(player);
                }

                if(gravityWars.getTeam4().contains(Teams.getPlayerTeam(player))) {
                    player.teleport(gravityWars.getTeam4Spawn());
                    player.setBedSpawnLocation(gravityWars.getTeam4Spawn(), true);
                    gravityWars.givePlayerKit(player);
                }
                gravityWars.givePlayerGravityDevice(player);
                player.setInvulnerable(false);
            }
        }

    }

    @Override
    public void onUpdate(int ticks, int stopwatch, Minigame minigame, boolean isTesting, int minigameSize, float coinMultiplier) {
        GravityWars gravityWars = (GravityWars) minigame;

        for (Player player : Bukkit.getOnlinePlayers()) {
            player.setFoodLevel(20);
        }

        List<List<Location>> allTotems = Arrays.asList(gravityWars.getRedTotem(), gravityWars.getBlueTotem(), gravityWars.getGreenTotem(), gravityWars.getObsidianTotem());

        for (Player player : Bukkit.getOnlinePlayers()) {
            // Get the team of the player
            String playerTeam = Teams.getPlayerTeam(player); // Implement this method to get the player's team
            // Load the restricted index for the team from teams.yml
            int restrictedIndex = Teams.getTotemTeam(playerTeam);
            // Ensure the player's team exists in the restriction map
            if (restrictedIndex == -1) {
                return; // If the restricted index is not found, do nothing
            }

            boolean hasAir = false;

            for (List<Location> totemList : allTotems) {
                if (totemList.get(restrictedIndex).getBlock().getType() == Material.AIR) {
                    hasAir = true;
                    break; // No need to check further if an AIR block is found
                }
            }

            if (!hasAir) {
                    state = GravityWarsStages.END;
                    gravityWars.spawnBlackHole();
                    return;
            }
        }

        if (Timer.isZero(stopwatch)) {
            for (Player player : Bukkit.getOnlinePlayers()) {
                player.sendTitle("ꀃ", " ", 0, 80, 20);
                player.setVelocity(new Vector(0, 2, 0));
                clearPlayer(player, gravityWars.plugin);
            }

            state = GravityWarsStages.END;
            soundEndgame();
        }

        gravityWars.refreshAll(ticks, minigameSize, "§c§lTempo Restante", stopwatch, coinMultiplier);
    }

    @Override
    public void onDisable(Minigame minigame, boolean isTesting) {

    }

    @Override
    public int stateTime(Minigame minigame) {
        return Timer.minToTicks(60);
    }

}
