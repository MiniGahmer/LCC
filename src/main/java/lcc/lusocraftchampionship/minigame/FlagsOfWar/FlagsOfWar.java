package lcc.lusocraftchampionship.minigame.FlagsOfWar;

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

public class FlagsOfWar extends AMinigame {

    public FlagsOfWar(boolean isTesting) {
        super("flagsofwar", isTesting);

        addState(FlagsOfWarsStages.EXPLANATION, FlagsOfWarExplanationState.class);
        addState(FlagsOfWarsStages.PREPARATION, FlagsOfWarPreparationState.class);
        addState(FlagsOfWarsStages.INGAME, FlagsOfWarInGameState.class);
        addState(FlagsOfWarsStages.END, FlagsOfWarEndState.class);

//        addClassListener(BlockBreakListener.class);
//        addClassListener(BlockBreakListener.class);
//        addClassListener(BlockPlaceListener.class);
//        addClassListener(PlayerRespawnListener.class);
//        addClassListener(EntityDamageByEntityListener.class);
//        addClassListener(GravityTunnelsListener.class);
//        addClassListener(GravityDeviceListener.class);
//        addClassListener(TotemRedInteractListener.class);
//        addClassListener(TotemBlueInteractListener.class);
//        addClassListener(TotemGreenInteractListener.class);
//        addClassListener(TotemObsidianInteractListener.class);
//        addClassListener(PlayerInventoryClickListener.class);

        // setMinigameExplanation(new GravityWarsExplanation(this));
        // registerCommand("startgravitywars", new StartGravityWars(this));
    }

    @Override
    public IMinigameExplanation getMinigameExplanation(IMinigameExplanation minigameExplanation) {
        return new FlagsOfWarExplanation(this);
    }

    @Override
    public String getWorldName() {
        return data.getConfig().getString("spawnpoint.world");
    }
}
