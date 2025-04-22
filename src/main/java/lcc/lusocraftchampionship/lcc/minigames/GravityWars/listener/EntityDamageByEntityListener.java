package lcc.lusocraftchampionship.minigame.GravityWars.listener;
/*
package lcc.lusocraftchampionship.minigame.GravityWars.listener;

import lcc.lusocraftchampionship.lcc.team.Teams;
import lcc.lusocraftchampionship.minigame.GravityWars.GravityWars;

import org.bukkit.GameMode;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import java.util.List;

public class EntityDamageByEntityListener implements Listener {
    GravityWars gravityWars;

    public EntityDamageByEntityListener(GravityWars gravityWars) {
        this.gravityWars = gravityWars;
    }

    @EventHandler
    public void onEntityDamageByEntityEvent(EntityDamageByEntityEvent event) {
        if (event.getEntity().getLocation().getWorld().getName().equals(gravityWars.getWorldName())) {
            Player player = (Player) event.getEntity();

            if (event.getDamager() instanceof Firework) {
                Firework fw = (Firework) event.getDamager();
                if (fw.hasMetadata("nodamage"))
                    event.setCancelled(true);
            } else if ((event.getDamager() instanceof Player) && (event.getEntity() instanceof Player)) {
                if (((Player) event.getEntity()).getGameMode() == GameMode.SURVIVAL ||
                        ((Player) event.getEntity()).getGameMode() == GameMode.ADVENTURE) {
                    List<String> teamPlayers = Teams.INSTANCE.getTeamPlayersName(Teams.INSTANCE.getPlayerTeam((Player) event.getEntity()));
                    if (teamPlayers.contains(event.getDamager().getName())) {
                        event.setCancelled(true);
                    }
                }
            }
        }
    }
}
*/
