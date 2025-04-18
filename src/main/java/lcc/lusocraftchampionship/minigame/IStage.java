package lcc.lusocraftchampionship.minigame;

public interface MinigameInterface {
    void onEnable(Minigame minigame);

    void onUpdate(int ticks, int stopwatch, Minigame minigame, boolean isTesting, int minigameSize, float coinMultiplier);

    void onDisable(Minigame minigame, boolean isTesting);

    int stateTime(Minigame minigame);
}
