package lcc.lusocraftchampionship.minigame.FlagsOfWar.state;

import lcc.lusocraftchampionship.minigame.AStage;
import lcc.lusocraftchampionship.minigame.FlagsOfWar.FlagsOfWar;
import lcc.lusocraftchampionship.minigame.FlagsOfWar.FlagsOfWarsStages;
import lcc.lusocraftchampionship.util.Timer;
import org.bukkit.Bukkit;

public class FlagsOfWarExplanationState extends AStage<FlagsOfWar, FlagsOfWarsStages> {

    public FlagsOfWarExplanationState(FlagsOfWar minigame) {
        super(minigame);
    }

    @Override
    public void onEnable() {
        Bukkit.broadcastMessage("EXPLANATION");
        jumpToState(FlagsOfWarsStages.PREPARATION);
    }

    @Override
    public void onUpdate(int ticks, int stopwatch) {

    }

    @Override
    public void onDisable() {

    }

    @Override
    public int stageTime() {
        return Timer.secToTicks(30);
    }
}
