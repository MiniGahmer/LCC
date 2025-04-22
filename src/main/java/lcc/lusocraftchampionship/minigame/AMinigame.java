package lcc.lusocraftchampionship.minigame;

import lcc.lusocraftchampionship.LCCPlugin;
import lcc.lusocraftchampionship.file.DataManager;
import lcc.lusocraftchampionship.lcc.LCCUtils;
import lcc.lusocraftchampionship.lcc.player.VirtualPlayer;
import lcc.lusocraftchampionship.lcc.team.Teams;
import lcc.lusocraftchampionship.lcc.team.VirtualTeam;
import lcc.lusocraftchampionship.minigame.stats.IMinigameStat;
import lcc.lusocraftchampionship.util.Timer;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.*;
import java.util.logging.Level;

public abstract class AMinigame implements IMinigame {
  public LCCPlugin plugin;
  public DataManager data;

  public BukkitTask game;

  // This variables have to be cleaned when the game is finished
  public List<MinigamePlayer> players = new ArrayList<>();
  public List<VirtualTeam> topTeams = new ArrayList<>();
  // ------------------------------------------------

  private boolean isTesting = false;

  private final Set<Class<? extends IMinigameStat<?>>> statClasses = new HashSet<>();

  private final List<Class<? extends Listener>> listeners = new ArrayList<>();
  private final List<Listener> listenersInstances = new ArrayList<>();

  private final LinkedHashMap<Enum<?>, Class<? extends IStage>> MINIGAME_STAGES = new LinkedHashMap<>();

  private IMinigameExplanation minigameExplanation;

  public AMinigame(String data, boolean isTesting) {
    this.plugin = LCCPlugin.getPlugin(LCCPlugin.class);
    this.data = new DataManager(data);
    this.isTesting = isTesting;
  }

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
  public void reload() {
    data = new DataManager(data.getFileName());
  }

  //////// Runnable //////////

  // TODO: Verify if all the players are online before starting
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
    // for (String team : TEAM_POINTS.keySet()) {
    // Teams.INSTANCE.addPointTeam(team, TEAM_POINTS.get(team));
    // }
    // }

    clear();
  }

  @Override
  public void setup() {
    List<VirtualPlayer> vps = Teams.INSTANCE.getPlayers();

    for (VirtualPlayer vp : vps) {
      MinigamePlayer player = new MinigamePlayer(vp.player, vp.team);

      for (Class<? extends IMinigameStat<?>> statClass : statClasses) {
        try {
          IMinigameStat<?> statInstance;
          
          try {
            statInstance = statClass.getDeclaredConstructor(this.getClass()).newInstance(this);
          } catch (NoSuchMethodException e) {
            statInstance = statClass.getDeclaredConstructor().newInstance();
          }

          player.addStat(statInstance);
        } catch (Exception e) {
          e.printStackTrace();
        }
      }

      players.add(player);

    }

    LCCUtils.clearPlayers(vps);

    World world = Bukkit.getWorld(getWorldName());

    if (world != null) {
      LCCUtils.teleportPlayers(vps, new Location(Bukkit.getWorld(getWorldName()), 0, 255, 0));
    } else {
      LCCPlugin.LOGGER.log(Level.SEVERE, "World not found: " + getWorldName());
    }

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

  @Override
  public void clear() {
    List<VirtualPlayer> vps = Teams.INSTANCE.getPlayers();

    LCCUtils.clearPlayers(vps);
    LCCUtils.backToLobby(vps);

    for (Listener listener : listenersInstances) {
      HandlerList.unregisterAll(listener);
    }

    listenersInstances.clear();

    players.clear();
    topTeams.clear();
  }

  @Override
  public boolean isTesting() {
    return isTesting;
  }

  //////// States //////////

  @Override
  public void addState(Enum<?> a, Class<? extends IStage> b) {
    MINIGAME_STAGES.put(a, b);
  }

  //////// Listeners //////////

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

  //////// Stats //////////

  @Override
  public void addStatClass(Class<? extends IMinigameStat<?>> statClass) {
    if (!statClasses.contains(statClass)) {
      statClasses.add(statClass);
    }
  }

  @Override
  public void removeStatClass(Class<? extends IMinigameStat<?>> statClass) {
    statClasses.remove(statClass);
  }

  @Override
  public void removeAllStatClasses() {
    statClasses.clear();
  }

  //////// Private Functions //////////

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
