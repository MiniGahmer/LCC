package lcc.lusocraftchampionship.minigame.WildWestBounty;

import lcc.lusocraftchampionship.Lusocraftchampionship;
import lcc.lusocraftchampionship.minigame.GravityWars.GravityWarsExplanation;
import lcc.lusocraftchampionship.minigame.GravityWars.GravityWarsStages;
import lcc.lusocraftchampionship.minigame.GravityWars.command.StartGravityWars;
import lcc.lusocraftchampionship.minigame.GravityWars.listener.*;
import lcc.lusocraftchampionship.minigame.GravityWars.state.*;
import lcc.lusocraftchampionship.minigame.Minigame;
import lcc.lusocraftchampionship.minigame.WildWestBounty.command.StartWildWestBounty;
import lcc.lusocraftchampionship.minigame.WildWestBounty.listener.HorseTraderNPC;
import lcc.lusocraftchampionship.team.Teams;
import lcc.lusocraftchampionship.util.Timer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WildWestBounty extends Minigame {

    public HashMap<String, Integer> PLAYER_KILLS = new HashMap<>();
    public HashMap<String, String> PLAYER_KILLER = new HashMap<>();
    public List<String> TOP_PLAYERS_KILLS = new ArrayList<>();

    public WildWestBounty(Lusocraftchampionship plugin) {
        super(plugin, "wildwestbounty");

        addStateHandler(GravityWarsStages.TELEPORT, new GravityWarsTeleportState());
        addStateHandler(GravityWarsStages.EXPLANATION, new GravityWarsExplanationState());
        addStateHandler(GravityWarsStages.PREPARATION, new GravityWarsPreparationState());
        addStateHandler(GravityWarsStages.INGAME, new GravityWarsInGameState());
        addStateHandler(GravityWarsStages.END, new GravityWarsEndState());

        addExplanation(new WildWestBountyExplanation(this));
    }

    @Override
    public void refreshPoints() {
        super.refreshPoints();

        TOP_PLAYERS_KILLS.clear();
        for (int x = 0; x < 20; x++) {
            String playerName = "";
            int points = 0;
            for (Map.Entry<String, Integer> entry : PLAYER_KILLS.entrySet()) {
                String _playerName = entry.getKey();
                Integer _points = entry.getValue();
                if (!TOP_PLAYERS_KILLS.contains(_playerName)) {
                    if (_points >= points) {
                        points = _points;
                        playerName = _playerName;
                    }
                }
            }
            TOP_PLAYERS_KILLS.add(playerName);
        }
    }

    @Override
    public void register() {
//        plugin.getServer().getPluginManager().registerEvents(new BlockBreakListener(this), plugin);
//        plugin.getServer().getPluginManager().registerEvents(new BlockPlaceListener(this), plugin);
//        plugin.getServer().getPluginManager().registerEvents(new PlayerRespawnListener(this), plugin);
//        plugin.getServer().getPluginManager().registerEvents(new EntityDamageByEntityListener(this), plugin);
//        plugin.getServer().getPluginManager().registerEvents(new ChatMessageListener(this), plugin);
//        plugin.getServer().getPluginManager().registerEvents(new GravityTunnelsListener(this), plugin);
//        plugin.getServer().getPluginManager().registerEvents(new GravityDeviceListener(this), plugin);
//        plugin.getServer().getPluginManager().registerEvents(new TotemRedInteractListener(this), plugin);
//        plugin.getServer().getPluginManager().registerEvents(new TotemBlueInteractListener(this), plugin);
//        plugin.getServer().getPluginManager().registerEvents(new TotemGreenInteractListener(this), plugin);
//        plugin.getServer().getPluginManager().registerEvents(new TotemObsidianInteractListener(this), plugin);
//        plugin.getServer().getPluginManager().registerEvents(new PlayerInventoryClickListener(this), plugin);
          plugin.getServer().getPluginManager().registerEvents(new HorseTraderNPC(this), plugin);


        plugin.getCommand("startwildwestbounty").setExecutor(new StartWildWestBounty(this));
    }

    @Override
    public void refreshScoreboard(int minigameSize, String gameState, int stopwatch, float coinMultiplier) {
        if (Timer.isOneSec(stopwatch)) {
            ArrayList<String> lines = new ArrayList<>();
            lines.add("§b§lJogo " + (6 - minigameSize) + "/5: §rAce Race");
            lines.add(gameState + "§c§l: §r" + Timer.formatMS(stopwatch));
            lines.add("");
            lines.add("§b§lOuro do jogo: §r(§e" + coinMultiplier + "x§r)");
            lines.add(" 1. " + Teams.getIconPrefix(TOP_TEAMS.get(0)) + TOP_TEAMS.get(0) + "§r > " + TEAM_POINTS.get(TOP_TEAMS.get(0)) + Teams.getScoreboardCoin());
            lines.add(" 2. " + Teams.getIconPrefix(TOP_TEAMS.get(1)) + TOP_TEAMS.get(1) + "§r > " + TEAM_POINTS.get(TOP_TEAMS.get(1)) + Teams.getScoreboardCoin());
            lines.add(" 3. " + Teams.getIconPrefix(TOP_TEAMS.get(2)) + TOP_TEAMS.get(2) + "§r > " + TEAM_POINTS.get(TOP_TEAMS.get(2)) + Teams.getScoreboardCoin());
            lines.add(" 4. " + Teams.getIconPrefix(TOP_TEAMS.get(3)) + TOP_TEAMS.get(3) + "§r > " + TEAM_POINTS.get(TOP_TEAMS.get(3)) + Teams.getScoreboardCoin());
            lines.add(" 5. " + Teams.getIconPrefix(TOP_TEAMS.get(4)) + TOP_TEAMS.get(4) + "§r > " + TEAM_POINTS.get(TOP_TEAMS.get(4)) + Teams.getScoreboardCoin());
            lines.add("");
            //PlayerScoreboard.changeAll(lines);
        }
    }

    public String getWorldName() {
        return data.getConfig().getString("spawnpoint.world");
    }
}
