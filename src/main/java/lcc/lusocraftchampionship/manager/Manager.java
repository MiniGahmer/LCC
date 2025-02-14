package lcc.lusocraftchampionship.manager;

import lcc.lusocraftchampionship.Lusocraftchampionship;
import lcc.lusocraftchampionship.manager.command.StopGame;
import lcc.lusocraftchampionship.minigame.GravityWars.GravityWars;
import lcc.lusocraftchampionship.minigame.WildWestBounty.WildWestBounty;
import lcc.lusocraftchampionship.team.Teams;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.scheduler.BukkitTask;

public class Manager {
    public Lusocraftchampionship plugin;

    public GravityWars gravityWars;
    public WildWestBounty wwb;

    public Manager(Lusocraftchampionship plugin) {
        this.plugin = plugin;
    }

    public void reload() {
        gravityWars.reload("gravitywars");
        wwb.reload("wildwestbounty");
    }

    public void register() {

        gravityWars = new GravityWars(plugin);
        wwb = new WildWestBounty(plugin);

        gravityWars.register();
        wwb.register();

        plugin.getCommand("stoplcc").setExecutor(new StopGame(this));
    }

    public boolean isStart() {
        BukkitTask[] games = { gravityWars.game, wwb.game};

        for (BukkitTask gameObj : games) {
            if (gameObj != null) {
                if (!gameObj.isCancelled()) {
                    Bukkit.broadcastMessage("§cErro: Pelo menos um dos minigames ou lcc está a funcionar");
                    return false;
                }
            }
        }

        return true;
    }

    public void sendPlayerToLobby() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (Teams.getPlayers().contains(player.getName())) {
                player.getInventory().clear();
                player.setExp(0);
                player.setHealth(20);
                player.setFoodLevel(20);
                player.setInvisible(false);
                player.setAllowFlight(false);
                player.setGameMode(GameMode.SURVIVAL);
                //TODO: A way to get this information auto
                //player.teleport(new Location(Bukkit.getWorld("Spawn6"), 116, 71, 198));
                for(Player player_ : Bukkit.getOnlinePlayers())
                    player.showPlayer(plugin, player_);

                for (PotionEffect effect : player.getActivePotionEffects())
                    player.removePotionEffect(effect.getType());
            }
        }
    }
}
