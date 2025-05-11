package lcc.lusocraftchampionship.lcc;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitTask;

import lcc.lusocraftchampionship.LCCPlugin;
import lcc.lusocraftchampionship.lcc.command.CommandFlagsOfWar;
import lcc.lusocraftchampionship.lcc.command.CommandMGTest;
import lcc.lusocraftchampionship.lcc.command.ReloadLCC;
import lcc.lusocraftchampionship.lcc.player.listener.PlayerListener;
import lcc.lusocraftchampionship.lcc.sidebar.SideBarListener;
import lcc.lusocraftchampionship.lcc.sidebar.LCCSidebar;
import lcc.lusocraftchampionship.lcc.team.Teams;
import lcc.lusocraftchampionship.lcc.team.listener.TeamListener;
import lcc.lusocraftchampionship.minigame.IMinigame;

public enum LCC {
  INSTANCE;

  private LCCPlugin plugin;
  private HashMap<LCCMinigames, Class<? extends IMinigame>> classes = new HashMap<>();
  private BukkitTask lcc;
  private IMinigame minigame;

  LCC() {
    plugin = LCCPlugin.getPlugin();

    addClass(LCCMinigames.GRAVITYWARS, lcc.lusocraftchampionship.lcc.minigames.GravityWars.GravityWars.class);
    addClass(LCCMinigames.FALGSOFWAR, lcc.lusocraftchampionship.lcc.minigames.FlagsOfWar.FlagsOfWar.class);
    addClass(LCCMinigames.MGTEST, lcc.lusocraftchampionship.lcc.minigames.MGTest.MGTest.class);

    plugin.getServer().getPluginManager().registerEvents(new TeamListener(), plugin);
    plugin.getServer().getPluginManager().registerEvents(new PlayerListener(), plugin);
    plugin.getServer().getPluginManager().registerEvents(new SideBarListener(), plugin);

    Teams.INSTANCE.reload();

    Bukkit.getOnlinePlayers().forEach(player -> {
      Teams.INSTANCE.addPlayerTeam(player);
      LCCSidebar.INSTANCE.addPlayer(player);
      LCCUtils.givePlayerCostum(player);
    });

    plugin.getCommand(LCCPropreties.COMMAND_RELOAD_LCC.getValue()).setExecutor(new ReloadLCC());
    plugin.getCommand(LCCPropreties.COMMAND_START_MGTEST.getValue()).setExecutor(new CommandMGTest());
    plugin.getCommand(LCCPropreties.COMMAND_START_FLAGSOFWAR.getValue()).setExecutor(new CommandFlagsOfWar());
  }

  public void init() {
  }

  public void startMinigame(LCCMinigames minigame, String data, boolean isTesting) {
    if (this.minigame != null) {
      this.minigame.stop();
    }

    try {
      this.minigame = classes.get(minigame).getDeclaredConstructor(String.class, boolean.class).newInstance(data, isTesting);
      this.minigame.start();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void stopMinigame() {
    if (minigame != null) {
      minigame.stop();
    }
  }

  private void addClass(LCCMinigames minigame, Class<? extends IMinigame> clazz) {
    classes.put(minigame, clazz);
  }
}
