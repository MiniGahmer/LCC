package lcc.lusocraftchampionship.minigame;

import lcc.lusocraftchampionship.LCCPlugin;
import lcc.lusocraftchampionship.file.DataManager;
import lcc.lusocraftchampionship.lcc.LCCUtils;
import lcc.lusocraftchampionship.lcc.player.VirtualPlayer;
import lcc.lusocraftchampionship.lcc.team.Teams;
import lcc.lusocraftchampionship.lcc.team.VirtualTeam;
import lcc.lusocraftchampionship.util.Timer;
import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.*;

public abstract class AMinigame implements IMinigame {
  public LCCPlugin plugin;
  public DataManager data;

  public BukkitTask game;

  // This variables have to be cleaned when the game is finished
  public List<VirtualPlayer> players = new ArrayList<>();
  public List<VirtualPlayer> INGAME_PLAYERS = new ArrayList<>();
  public HashMap<VirtualPlayer, Integer> PLAYER_POINTS = new HashMap<>();
  public HashMap<VirtualTeam, Integer> TEAM_POINTS = new HashMap<>();
  public List<VirtualTeam> TOP_TEAMS = new ArrayList<>();
  public List<VirtualTeam> TOP_PLAYERS = new ArrayList<>();
  // ------------------------------------------------

  private boolean isTesting = false;

  private final List<Class<? extends Listener>> listeners = new ArrayList<>();
  private final List<Listener> listenersInstances = new ArrayList<>();

  private final LinkedHashMap<Enum<?>, Class<? extends IStage>> MINIGAME_STAGES = new LinkedHashMap<>();

  private IMinigameExplanation minigameExplanation;

  public AMinigame(String data, boolean isTesting) {
    this.plugin = LCCPlugin.getPlugin(LCCPlugin.class);
    this.data = new DataManager(plugin, data);
    this.isTesting = isTesting;
  }

  // public void explanation(int stopwatch) {
  //   minigameExplanation.playerLocations(stopwatch);
  //   minigameExplanation.playerMessages(stopwatch);
  // }

  // New functions
  @Override
  public void updateScore() {
  }

  @Override
  public void updateScoreboard(String gameState, int stopwatch) {
  }

  @Override
  public void update(int time, String gameState, int stopwatch) {
    if (Timer.isOneSec(time)) {
      updateScore();
      updateScoreboard(gameState, stopwatch);
    }
  }

  @Override
  public void start() {    
    setup();

    game = new BukkitRunnable() {
      int ticks = 0;
      int index = 0;
      IStage stage = createInstance(getSafeCast(MINIGAME_STAGES.values().toArray()[index]));
      int stopwatch = stage.stageTime();

      @Override
      public void run() {
        for (VirtualPlayer vp : players) {
          if (!vp.player.isOnline()) {
            stop();
            Bukkit.broadcastMessage("§cErro: O minigame foi terminado, devido à ausência de pelo menos um jogador");
            return;
          }
        }

        if (stopwatch == stage.stageTime()) {
          stage.onEnable();
        }

        stage.onUpdate(ticks, stopwatch);

        if (stage.getState() != null) {
          index = getPositionByKey(MINIGAME_STAGES, stage.getState());
          stopwatch = 0;
          ticks = 0;
          stage.onDisable();
          stage = createInstance(getSafeCast(MINIGAME_STAGES.values().toArray()[index]));
          stopwatch = stage.stageTime();
          return;
        }

        if (stopwatch == 0) {
          stage.onDisable();

          if ((index + 1) == MINIGAME_STAGES.size()) {
            stop();
          } else {
            index++;
            ticks = 0;
            stage = createInstance(getSafeCast(MINIGAME_STAGES.values().toArray()[index]));
            stopwatch = stage.stageTime();
          }
        } else {
          stopwatch--;
          ticks++;
        }
      }
    }.runTaskTimer(plugin, 0, 1);
  }

  @Override
  public void stop() {
    if (game != null) {
      game.cancel();
    }
    
    // if (!isTesting) {
    //   for (String team : TEAM_POINTS.keySet()) {
    //     Teams.INSTANCE.addPointTeam(team, TEAM_POINTS.get(team));
    //   }
    // }
    
    clear();
  }

  @Override
  public void reload() {
    data = new DataManager(plugin, data.getFileName());
  }

  @Override
  public void addState(Enum<?> a, Class<? extends IStage> b) {
    MINIGAME_STAGES.put(a, b);
  }

  @Override
  public void addClassListener(Class<? extends Listener> listener) {
    if (!listeners.contains(listener)) {
      listeners.add(listener);
    }
  }

  @Override
  public void removeClassListener(Class<? extends Listener> listener) {
    listeners.remove(listener);
  }

  @Override
  public void removeAllClassListeners() {
    listeners.clear();
  }

  // TODO: INIT the variables
  // TODO: Teleport players to the map 
  @Override
  public void setup() {
    players = Teams.INSTANCE.getPlayers();

    LCCUtils.clearPlayers(plugin, players);

    for (Class<? extends Listener> listener : listeners) {
      try {
        Listener instance = listener.getDeclaredConstructor().newInstance();
        listenersInstances.add(instance);
        Bukkit.getPluginManager().registerEvents(instance, plugin);
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }

  // TODO: CLEAR ALL THE LISTS AND HASHMAPS
  @Override
  public void clear() {
    LCCUtils.clearPlayers(plugin, players);
    LCCUtils.backToLobby(plugin, players);

    for (Listener listener : listenersInstances) {
      HandlerList.unregisterAll(listener);
    }

    listenersInstances.clear();
  }

  @Override
  public boolean isTesting() {
    return isTesting;
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

  @SuppressWarnings("unchecked")
  private Class<? extends IStage> getSafeCast(Object obj) {
    if (obj instanceof Class<?> && IStage.class.isAssignableFrom((Class<?>) obj)) {
      return (Class<? extends IStage>) obj;
    }
    throw new ClassCastException("Cannot cast object to Class<? extends IStage>");
  }

  private IStage createInstance(Class<? extends IStage> clazz) {
    try {
      return clazz.getDeclaredConstructor(this.getClass()).newInstance(this);
    } catch (Exception e) {
      throw new RuntimeException("Failed to create instance of " + clazz.getName(), e);
    }
  }
}
