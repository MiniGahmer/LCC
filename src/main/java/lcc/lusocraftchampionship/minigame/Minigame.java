package lcc.lusocraftchampionship.minigame;

import lcc.lusocraftchampionship.Lusocraftchampionship;
import lcc.lusocraftchampionship.file.DataManager;
import lcc.lusocraftchampionship.team.Teams;
import lcc.lusocraftchampionship.util.Timer;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.*;

public class Minigame {
    public Lusocraftchampionship plugin;
    public DataManager data;

    public BukkitTask game;

    public List<String> INGAME_PLAYERS = new ArrayList<>();
    public HashMap<String, Integer> PLAYER_POINTS = new HashMap<>();
    public HashMap<String, Integer> TEAM_POINTS = new HashMap<>();
    public List<String> TOP_TEAMS = new ArrayList<>();
    public List<String> TOP_PLAYERS = new ArrayList<>();

    private MinigameExplanation minigameExplanation;

    private LinkedHashMap<Enum<?>, MinigameStages> MINIGAME_STATES = new LinkedHashMap<>();

    public Minigame(Lusocraftchampionship plugin, String data) {
        this.plugin = plugin;
        this.data = new DataManager(plugin, data);
    }

    public void refreshPoints() {
        TEAM_POINTS.forEach((s, integer) -> { TEAM_POINTS.put(s, 0); });

        for(Player player : Bukkit.getOnlinePlayers()) {
            if (Teams.getPlayers().contains(player.getName())) {
                TEAM_POINTS.put(Teams.getPlayerTeam(player), PLAYER_POINTS.get(player.getName()) + TEAM_POINTS.get(Teams.getPlayerTeam(player)));
            }
        }

        TOP_TEAMS.clear();
        for(int x = 0; x < 5; x++) {
            String teamName = "";
            int points = 0;
            for (Map.Entry<String, Integer> entry : TEAM_POINTS.entrySet()) {
                String _teamName = entry.getKey();
                Integer _points = entry.getValue();
                if(!TOP_TEAMS.contains(_teamName)) {
                    if (_points >= points) {
                        points = _points;
                        teamName = _teamName;
                    }
                }
            }
            TOP_TEAMS.add(teamName);
        }

        TOP_PLAYERS.clear();
        for(int x = 0; x < 20; x++) {
            String playerName = "";
            int points = 0;
            for (Map.Entry<String, Integer> entry : PLAYER_POINTS.entrySet()) {
                String _playerName = entry.getKey();
                Integer _points = entry.getValue();
                if (!TOP_PLAYERS.contains(_playerName)) {
                    if (_points >= points) {
                        points = _points;
                        playerName = _playerName;
                    }
                }
            }
            TOP_PLAYERS.add(playerName);
        }
    }

    public void refreshScoreboard(int minigameSize, String gameState, int stopwatch, float coinMultiplier) { }

    public void refreshAll(int time, int minigameSize, String gameState, int stopwatch, float coinMultiplier) {
        if (Timer.isOneSec(time)) {
            refreshPoints();
            refreshScoreboard(minigameSize, gameState, stopwatch, coinMultiplier);
        }
    }

    public void register() { }

    public void start(boolean isTesting, int minigameSize, float coinMultiplier) {
        for (String team : Teams.getTeamsName())
            TEAM_POINTS.put(team, 0);

        for (String player : Teams.getPlayers())
            PLAYER_POINTS.put(player, 0);

        clearAllPlayer(plugin);

        game = new BukkitRunnable() {
            int size = 0;
            MinigameStages minigameState = (MinigameStages) MINIGAME_STATES.values().toArray()[size];
            int ticks = 0;
            int stopwatch = minigameState.stateTime(Minigame.this);

            @Override
            public void run() {
                for(String playerName : Teams.getPlayers()) {
                    if(Bukkit.getPlayer(playerName) == null) {
                        game.cancel();
                        clearAllPlayer(plugin);
                        playerBackToLobby(plugin);
                        //TODO: CLEAN SCOREBOARD
                        Bukkit.broadcastMessage("§cErro: O minigame foi terminado, devido à ausência de pelo menos um jogador");
                        return;
                    }
                }

                if (stopwatch == minigameState.stateTime(Minigame.this))
                    minigameState.onEnable(Minigame.this);

                minigameState.onUpdate(ticks, stopwatch, Minigame.this, isTesting, minigameSize, coinMultiplier);

                if (minigameState.state != null) {
                    size = getPositionByKey(MINIGAME_STATES, minigameState.state);
                    minigameState.state = null;
                    ticks = 0;
                    minigameState = (MinigameStages) MINIGAME_STATES.values().toArray()[size];
                    stopwatch = minigameState.stateTime(Minigame.this);
                    return;
                }

                if (minigameState.nextState) {
                    stopwatch = 0;
                    minigameState.nextState = false;
                }

                if (stopwatch == 0) {
                    minigameState.onDisable(Minigame.this, isTesting);

                    if ((size + 1) == MINIGAME_STATES.size()) {
                        game.cancel();
                        clearAllPlayer(plugin);
                        playerBackToLobby(plugin);
                    } else {
                        size++;
                        ticks = 0;
                        minigameState = (MinigameStages) MINIGAME_STATES.values().toArray()[size];
                        stopwatch = minigameState.stateTime(Minigame.this);
                    }
                } else {
                    stopwatch--;
                    ticks++;
                }
            }
        }.runTaskTimer(plugin, 0, 1);
    }

    private <K, V> int getPositionByKey(LinkedHashMap<K, V> map, K targetKey) {
        int position = 0;

        for (K key : map.keySet()) {
            if (key.equals(targetKey)) {
                return position;
            }
            position++;
        }

        return -1;
    }

    protected void addStateHandler(Enum<?> a, MinigameStages b) {
        MINIGAME_STATES.put(a, b);
    }

    protected void addExplanation(MinigameExplanation a) {
        this.minigameExplanation = a;
    }

    public void reload(String fileName) {
        this.data = new DataManager(plugin, fileName);
    }

    public void clearAllPlayer(Plugin plugin) {
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (Teams.getPlayers().contains(player.getName())) {
                player.getInventory().clear();
                player.setExp(0);
                player.setHealth(20);
                player.setFoodLevel(20);
                player.setInvisible(false);
                player.setAllowFlight(false);
                for(Player player_ : Bukkit.getOnlinePlayers())
                    player.showPlayer(plugin, player_);

                for (PotionEffect effect : player.getActivePotionEffects())
                    player.removePotionEffect(effect.getType());
            }
        }
    }

    public void playerBackToLobby(Plugin plugin) {
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

    public void backup() {
        TEAM_POINTS.keySet().forEach(key -> {
            data.getConfig().set("backup.points.team." + key, TEAM_POINTS.get(key));
        });

        PLAYER_POINTS.keySet().forEach(key -> {
            data.getConfig().set("backup.points.player." + key, PLAYER_POINTS.get(key));
        });
        data.saveConfig();
    }

    public void explanation(int stopwatch) {
        minigameExplanation.playerLocations(stopwatch);
        minigameExplanation.playerMessages(stopwatch);
    }

    public void addPlayerPoints(Player player, int points, float coinMultiplier) {
        PLAYER_POINTS.put(player.getName(), (int) (PLAYER_POINTS.get(player.getName()) + (points * coinMultiplier)));
    }

    public void addPoints(boolean isTesting) {
        if (!isTesting) {
            for (String team : TEAM_POINTS.keySet())
                Teams.addPointTeam(team, TEAM_POINTS.get(team));
        }
    }
}
