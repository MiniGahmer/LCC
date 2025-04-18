package lcc.lusocraftchampionship.minigame.FlagsOfWar;

import lcc.lusocraftchampionship.minigame.IMinigame;
import lcc.lusocraftchampionship.minigame.MinigameExplanation;
import org.bukkit.Location;

import java.util.HashMap;

public class FlagsOfWarExplanation extends MinigameExplanation {
    public FlagsOfWarExplanation(IMinigame minigame) {
        super(minigame);
    }

    @Override
    public HashMap<Integer, String> addExplanationMessages(IMinigame minigame) {
        return null;
    }

    @Override
    public HashMap<Integer, Location> addExplanationLocations(IMinigame minigame) {
        return null;
    }
}
