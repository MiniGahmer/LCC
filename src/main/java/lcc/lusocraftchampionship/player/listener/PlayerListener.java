package lcc.lusocraftchampionship.player.listener;

import lcc.lusocraftchampionship.Lusocraftchampionship;
import lcc.lusocraftchampionship.team.Teams;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.potion.PotionEffect;

public class PlayerListener implements Listener {
    private Lusocraftchampionship plugin;
    public PlayerListener(Lusocraftchampionship plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        String msg = event.getMessage();

        if(player.isOp()){
            event.setFormat("§r ꀎ §c" + player.getName() + " > §r" + msg);
        }else{
            if (Teams.getPlayerTeam(player) == null) {
                event.setFormat("§r" + "NO TEAM" + player.getName() + " > §r"+ msg);
            } else {
                event.setFormat("§r" + Teams.getIconPrefix(Teams.getPlayerTeam(player)) + player.getName() + " > §r"+ msg);
            }
        }
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        if (Teams.getPlayers().contains(player.getName())) {
            //TODO: A way to get this information auto
            player.teleport(new Location(Bukkit.getWorld("gravitywars"), -104, 33, -62.5));
            player.getInventory().clear();
            player.setExp(0);
            player.setHealth(20);
            player.setFoodLevel(20);
            player.setInvisible(false);
            player.setAllowFlight(false);
            player.setGameMode(GameMode.SURVIVAL);
            for(Player player_ : Bukkit.getOnlinePlayers())
                player.showPlayer(plugin, player_);

            for (PotionEffect effect : player.getActivePotionEffects())
                player.removePotionEffect(effect.getType());
        }

        if(player.isOp()) {
            player.setPlayerListName("ꀎ §c" + player.getName());
            event.setJoinMessage("§r ꀎ §c" + player.getName() + " §rentrou no server");
        }else {
            player.setPlayerListName(Teams.getIconPrefix(Teams.getPlayerTeam(player)) + player.getName());
            event.setJoinMessage("§r" + Teams.getIconPrefix(Teams.getPlayerTeam(player)) + player.getName() + " §rentrou no server");
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();

        if(player.isOp()){
            event.setQuitMessage("§r ꀎ §c" + player.getName() + " §rsaiu do server");
        }else{
            if (Teams.getPlayerTeam(player) == null) {
                event.setQuitMessage("§r" + "NO TEAM" + " " + player.getName() + " §rsaiu do server");
            } else {
                event.setQuitMessage("§r" + Teams.getIconPrefix(Teams.getPlayerTeam(player)) + " " + player.getName() + " §rsaiu do server");
            }
        }
    }
}
