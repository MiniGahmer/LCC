package lcc.lusocraftchampionship.team;

import lcc.lusocraftchampionship.Lusocraftchampionship;
import lcc.lusocraftchampionship.file.DataManager;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.*;
import java.util.stream.Collectors;

public class Teams {

    private static Lusocraftchampionship plugin;
    private static DataManager data;
    private static int maxPlayers;
    private static final List<VirtualTeam> TEAMS = new ArrayList<>();

    public static void init(Lusocraftchampionship plugin) {
        Teams.plugin = plugin;
        data = new DataManager(plugin, "teams");
        getData();
    }

    private static void getData() {
        TEAMS.clear();

        FileConfiguration file = data.getConfig();

        maxPlayers = data.getConfig().getInt("team_max_players");

        file.getConfigurationSection("teams").getKeys(false).forEach(teamName -> {
            TEAMS.add(new VirtualTeam(
                    teamName,
                    (List<String>) file.getList("teams." + teamName + ".players"),
                    file.getString("teams." + teamName + ".icon"),
                    file.getString("teams." + teamName + ".prefix"),
                    getColor(file.getString("teams." + teamName + ".color")),
                    file.getInt("teams." + teamName + ".points"),
                    file.getInt("teams." + teamName + ".totem"),
                    getConcrete(file.getString("teams." + teamName + ".block")),
                    getWool(file.getString("teams." + teamName + ".block")),
                    getChatColor(file.getString("teams." + teamName + ".chatcolor"))
            ));
        });
    }

    public static void reload() {
        data = new DataManager(plugin, "teams");
        getData();
    }

    public static void saveData() {
        data.getConfig().getConfigurationSection("teams").getKeys(false).forEach(teamName -> {
            data.getConfig().set("teams." + teamName + ".points", getPointsTeam(teamName));
        });
        data.saveConfig();
    }

    private static Color getColor(String colorName) {
        switch (colorName) {
            case "WHITE":
                return Color.WHITE;
            case "SILVER":
                return Color.SILVER;
            case "GRAY":
                return Color.GRAY;
            case "BLACK":
                return Color.BLACK;
            case "RED":
                return Color.RED;
            case "MAROON":
                return Color.MAROON;
            case "YELLOW":
                return Color.YELLOW;
            case "OLIVE":
                return Color.OLIVE;
            case "LIME":
                return Color.LIME;
            case "GREEN":
                return Color.GREEN;
            case "AQUA":
                return Color.AQUA;
            case "TEAL":
                return Color.TEAL;
            case "BLUE":
                return Color.BLUE;
            case "NAVY":
                return Color.NAVY;
            case "FUCHSIA":
                return Color.FUCHSIA;
            case "PURPLE":
                return Color.PURPLE;
            case "ORANGE":
                return Color.ORANGE;
        }
        return null;
    }

    private static ChatColor getChatColor(String chatColorName) {
        switch (chatColorName) {
            case "BLACK":
                return ChatColor.BLACK;
            case "DARK_BLUE":
                return ChatColor.DARK_BLUE;
            case "DARK_GREEN":
                return ChatColor.DARK_GREEN;
            case "DARK_AQUA":
                return ChatColor.DARK_AQUA;
            case "DARK_RED":
                return ChatColor.DARK_RED;
            case "DARK_PURPLE":
                return ChatColor.GOLD;
            case "GOLD":
                return ChatColor.BLACK;
            case "GRAY":
                return ChatColor.GRAY;
            case "DARK_GRAY":
                return ChatColor.DARK_GRAY;
            case "BLUE":
                return ChatColor.BLUE;
            case "GREEN":
                return ChatColor.GREEN;
            case "AQUA":
                return ChatColor.AQUA;
            case "RED":
                return ChatColor.RED;
            case "LIGHT_PURPLE":
                return ChatColor.LIGHT_PURPLE;
            case "YELLOW":
                return ChatColor.YELLOW;
            case "WHITE":
                return ChatColor.WHITE;
        }
        return null;
    }

    private static Material getConcrete(String colorName) {
        switch (colorName) {
            case "WHITE":
                return Material.WHITE_CONCRETE;
            case "ORANGE":
                return Material.ORANGE_CONCRETE;
            case "MAGENTA":
                return Material.MAGENTA_CONCRETE;
            case "LIGHT_BLUE":
                return Material.LIGHT_BLUE_CONCRETE;
            case "YELLOW":
                return Material.YELLOW_CONCRETE;
            case "GREEN":
                return Material.GREEN_CONCRETE;
            case "PINK":
                return Material.PINK_CONCRETE;
            case "GRAY":
                return Material.GRAY_CONCRETE;
            case "LIGHT_GRAY":
                return Material.LIGHT_GRAY_CONCRETE;
            case "CYAN":
                return Material.CYAN_CONCRETE;
            case "PURPLE":
                return Material.PURPLE_CONCRETE;
            case "BLUE":
                return Material.BLUE_CONCRETE;
            case "BROWN":
                return Material.BROWN_CONCRETE;
            case "LIME":
                return Material.LIME_CONCRETE;
            case "RED":
                return Material.RED_CONCRETE;
            case "BLACK":
                return Material.BLACK_CONCRETE;
        }
        return null;
    }

    private static Material getWool(String colorName) {
        switch (colorName) {
            case "WHITE":
                return Material.WHITE_WOOL;
            case "ORANGE":
                return Material.ORANGE_WOOL;
            case "MAGENTA":
                return Material.MAGENTA_WOOL;
            case "LIGHT_BLUE":
                return Material.LIGHT_BLUE_WOOL;
            case "YELLOW":
                return Material.YELLOW_WOOL;
            case "GREEN":
                return Material.GREEN_WOOL;
            case "PINK":
                return Material.PINK_WOOL;
            case "GRAY":
                return Material.GRAY_WOOL;
            case "LIGHT_GRAY":
                return Material.LIGHT_GRAY_WOOL;
            case "CYAN":
                return Material.CYAN_WOOL;
            case "PURPLE":
                return Material.PURPLE_WOOL;
            case "BLUE":
                return Material.BLUE_WOOL;
            case "BROWN":
                return Material.BROWN_WOOL;
            case "LIME":
                return Material.LIME_WOOL;
            case "RED":
                return Material.RED_WOOL;
            case "BLACK":
                return Material.BLACK_WOOL;
        }
        return null;
    }

    public static List<String> getTeamsName() {
        return TEAMS.stream()
                .map(VirtualTeam::getName)
                .collect(Collectors.toList());
    }

    public static String getScoreboardCoin() {
        return "\uF821\uEff6";
    }

    public static List<String> getTeamPlayers(String name) {
        return teamDispatcher(name).map(VirtualTeam::getPlayers).orElse(null);
    }

    public static List<String> getPlayers() {
        return TEAMS.stream()
                .flatMap(team -> team.getPlayers().stream())
                .collect(Collectors.toList());
    }

    public static String getIcon(String name) {
        return teamDispatcher(name).map(VirtualTeam::getIcon).orElse(null);
    }

    public static String getPrefix(String name) {
        return teamDispatcher(name).map(VirtualTeam::getPrefix).orElse(null);
    }

    public static String getIconPrefix(String name) {
        Optional<VirtualTeam> virtualTeam = teamDispatcher(name);
        if (!virtualTeam.isPresent())
            return null;
        return virtualTeam.map(VirtualTeam::getIcon).orElse(null) + virtualTeam.map(VirtualTeam::getPrefix).orElse(null);
    }

    public static Color getTeamColor(String name) {
        return teamDispatcher(name).map(VirtualTeam::getColor).orElse(null);
    }

    public static ChatColor getTeamChatColor(String name) {
        return teamDispatcher(name).map(VirtualTeam::getChatColor).orElse(null);
    }

    public static Material getTeamConcrete(String name) {
        return teamDispatcher(name).map(VirtualTeam::getConcrete).orElse(null);
    }

    public static Material getTeamWool(String name) {
        return teamDispatcher(name).map(VirtualTeam::getWool).orElse(null);
    }

    public static String getPlayerTeam(Player player) {
        Optional<String> teamNameOptional = TEAMS.stream()
                .filter(virtualTeam -> virtualTeam.getPlayers().contains(player.getName()))
                .map(VirtualTeam::getName)
                .findFirst();

        return teamNameOptional.orElse(null);
    }

    public static String getPlayerTeam(String playerName) {
        Optional<String> teamNameOptional = TEAMS.stream()
                .filter(virtualTeam -> virtualTeam.getPlayers().contains(playerName))
                .map(VirtualTeam::getName)
                .findFirst();

        return teamNameOptional.orElse(null);
    }

    public static int getPointsTeam(String name) {
        return teamDispatcher(name).map(VirtualTeam::getPoints).isPresent() ? teamDispatcher(name).map(VirtualTeam::getPoints).get() : 0;
    }

    public static int getTotemTeam(String name) {
        return teamDispatcher(name).map(VirtualTeam::getPoints).isPresent() ? teamDispatcher(name).map(VirtualTeam::getTotem).get() : 0;
    }

    public static void addPointTeam(String name, int points) {
        TEAMS.stream()
                .filter(team -> Objects.equals(team.getName(), name))
                .forEach(team -> team.setPoints(team.getPoints() + points));
    }

    public static List<String> getTopTeams(int limit) {
        List<String> topTeams = new ArrayList<>();

        Teams.TEAMS.stream()
                .sorted(Comparator.comparingInt(VirtualTeam::getPoints).reversed())
                .map(VirtualTeam::getName)
                .distinct()
                .limit(limit)
                .forEach(topTeams::add);

        return topTeams;
    }

    private static Optional<VirtualTeam> teamDispatcher(String name) {
        return TEAMS.stream()
                .filter(virtualTeam -> Objects.equals(virtualTeam.getName(), name))
                .findFirst();
    }

    private static class VirtualTeam {
        private final String name;
        private final List<String> players;
        private final String icon;
        private final String prefix;
        private final Color color;
        private int points;
        private int totem;
        private final Material concrete;
        private final Material wool;
        private final ChatColor chatColor;


        public VirtualTeam(String name, List<String> players, String icon, String prefix, Color color, int points, int totem, Material concrete, Material wool, ChatColor chatColor) {
            this.name = name;

            if (players.size() <= maxPlayers)
                this.players = players;
            else
                throw new IllegalArgumentException("Too long value ! Max " + maxPlayers + " players, value was " + players.size() + " !");

            this.icon = icon;
            this.prefix = prefix;
            this.color = color;
            this.points = points;
            this.concrete = concrete;
            this.wool = wool;
            this.chatColor = chatColor;
            this.totem = totem;
        }

        public String getName() {
            return name;
        }

        public List<String> getPlayers() {
            return players;
        }

        public String getIcon() {
            return icon;
        }

        public String getPrefix() {
            return prefix;
        }

        public Color getColor() {
            return color;
        }

        public void setPoints(int points) {
            this.points = points;
        }

        public int getPoints() {
            return points;
        }

        public int getTotem() {
            return totem;
        }

        public Material getConcrete() {
            return concrete;
        }

        public Material getWool() {
            return wool;
        }

        public ChatColor getChatColor() {
            return chatColor;
        }
    }
}
