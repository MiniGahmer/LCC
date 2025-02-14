package lcc.lusocraftchampionship.player;

import lcc.lusocraftchampionship.Lusocraftchampionship;
import lcc.lusocraftchampionship.file.DataManager;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.HashMap;

public class PlayerPoints {

    private DataManager data;
    private Lusocraftchampionship plugin;

    public static HashMap<String, Integer> PLAYER_POINTS = new HashMap<>();
    public static HashMap<String, Integer> PLAYER_LIFETIME_POINTS = new HashMap<>();
    public PlayerPoints(Lusocraftchampionship plugin) {
        this.plugin = plugin;
        getData();
    }

    public void getData() {
        data = new DataManager(plugin, "playerpoints");
        FileConfiguration file = data.getConfig();
        file.getConfigurationSection("player.currentpoints").getKeys(false).forEach(player -> {
            PLAYER_POINTS.put(player, file.getInt("player.currentpoints." + player));
        });

        file.getConfigurationSection("player.currentpoints").getKeys(false).forEach(player -> {
            PLAYER_LIFETIME_POINTS.put(player, file.getInt("player.lifepoints." + player));
        });
    }

    public void saveData() {
        data.getConfig().getConfigurationSection("player.currentpoints").getKeys(false).forEach(player -> {
            data.getConfig().set("player.currentpoints." + player, PLAYER_POINTS.get(player));
        });
        data.saveConfig();
    }

    public static HashMap<String, Integer> getPlayerCurrentPoints() {
        return PLAYER_POINTS;
    }

    public static HashMap<String, Integer> getPlayerLifetimePoints() {return PLAYER_LIFETIME_POINTS; }

    public static int getPointsPlayer(String playerName) {
        return PLAYER_POINTS.get(playerName);
    }

    public static void setPointsPlayer(String playerName, int setPoints) {
        PLAYER_POINTS.put(playerName, setPoints);
    }

    public DataManager pointsGetData() {
        return data;
    }
}
