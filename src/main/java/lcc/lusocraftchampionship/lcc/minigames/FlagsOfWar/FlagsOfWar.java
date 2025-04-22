package lcc.lusocraftchampionship.minigame.FlagsOfWar;

import lcc.lusocraftchampionship.LCCPlugin;
import lcc.lusocraftchampionship.minigame.AMinigame;
import lcc.lusocraftchampionship.minigame.FlagsOfWar.state.FlagsOfWarEndState;
import lcc.lusocraftchampionship.minigame.FlagsOfWar.state.FlagsOfWarExplanationState;
import lcc.lusocraftchampionship.minigame.FlagsOfWar.state.FlagsOfWarInGameState;
import lcc.lusocraftchampionship.minigame.FlagsOfWar.state.FlagsOfWarPreparationState;
import lcc.lusocraftchampionship.minigame.GravityWars.GravityWarsExplanation;
import lcc.lusocraftchampionship.minigame.GravityWars.GravityWarsStages;
import lcc.lusocraftchampionship.minigame.GravityWars.listener.*;
import lcc.lusocraftchampionship.minigame.GravityWars.state.GravityWarsEndState;
import lcc.lusocraftchampionship.minigame.GravityWars.state.GravityWarsExplanationState;
import lcc.lusocraftchampionship.minigame.GravityWars.state.GravityWarsInGameState;
import lcc.lusocraftchampionship.minigame.GravityWars.state.GravityWarsPreparationState;
import lcc.lusocraftchampionship.minigame.IMinigameExplanation;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.scoreboard.Team;

import java.util.Map;
import java.util.Set;

public class FlagsOfWar extends AMinigame {

  public FlagsOfWar(String data, boolean isTesting) {
    super(data, isTesting);

    addState(FlagsOfWarsStages.EXPLANATION, FlagsOfWarExplanationState.class);
    addState(FlagsOfWarsStages.PREPARATION, FlagsOfWarPreparationState.class);
    addState(FlagsOfWarsStages.INGAME, FlagsOfWarInGameState.class);
    addState(FlagsOfWarsStages.END, FlagsOfWarEndState.class);

    // addClassListener(BlockBreakListener.class);
    // addClassListener(BlockBreakListener.class);
    // addClassListener(BlockPlaceListener.class);
    // addClassListener(PlayerRespawnListener.class);
    // addClassListener(EntityDamageByEntityListener.class);
    // addClassListener(GravityTunnelsListener.class);
    // addClassListener(GravityDeviceListener.class);
    // addClassListener(TotemRedInteractListener.class);
    // addClassListener(TotemBlueInteractListener.class);
    // addClassListener(TotemGreenInteractListener.class);
    // addClassListener(TotemObsidianInteractListener.class);
    // addClassListener(PlayerInventoryClickListener.class);

    // setMinigameExplanation(new GravityWarsExplanation(this));
    // registerCommand("startgravitywars", new StartGravityWars(this));
  }

  @Override
  public IMinigameExplanation getMinigameExplanation(IMinigameExplanation minigameExplanation) {
    return new FlagsOfWarExplanation(this);
  }

  @Override
  public String getWorldName() {
    return "world";
  }

  public Map<String, Location> getTeamSpawns() {
    return Map.of(
        getTeam(1), getTeamSpawn(1),
        getTeam(2), getTeamSpawn(2),
        getTeam(3), getTeamSpawn(3),
        getTeam(4), getTeamSpawn(4));
  }

  public String getTeam(int number) {
    LCCPlugin.LOGGER.info(data.getConfig().getString("team" + number));
    return data.getConfig().getString("team" + number);
  }

  public Location getTeamSpawn(int number) {
    String path = "teamspawn." + number;
    World world = Bukkit.getWorld(data.getConfig().getString(path + ".world"));
    double x = data.getConfig().getDouble(path + ".x");
    double y = data.getConfig().getDouble(path + ".y");
    double z = data.getConfig().getDouble(path + ".z");

    return new Location(world, x, y, z);
  }
}
