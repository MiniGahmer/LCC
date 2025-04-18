package lcc.lusocraftchampionship.lcc.team;

import lcc.lusocraftchampionship.LCCPlugin;
import lcc.lusocraftchampionship.file.DataManager;
import lcc.lusocraftchampionship.lcc.player.VirtualPlayer;

import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.*;
import java.util.stream.Collectors;

public enum Teams {
  INSTANCE;

  private LCCPlugin plugin;
  private DataManager data;
  private int maxPlayers;
  private final List<VirtualTeam> teams = new ArrayList<>();

  private Teams() {
    plugin = LCCPlugin.getPlugin(LCCPlugin.class);
  }

  public void reload() {
    data = new DataManager(plugin, "teams");
    getData();
  }

  private void getData() {
    teams.clear();

    FileConfiguration file = data.getConfig();

    maxPlayers = data.getConfig().getInt("team_max_players");

    file.getConfigurationSection("teams").getKeys(false).forEach(teamName -> {
      teams.add(new VirtualTeam(
          teamName,
          (List<String>) file.getList("teams." + teamName + ".players"),
          file.getString("teams." + teamName + ".icon"),
          file.getString("teams." + teamName + ".prefix"),
          getColor(file.getString("teams." + teamName + ".color")),
          file.getInt("teams." + teamName + ".points"),
          file.getInt("teams." + teamName + ".totem"),
          getConcrete(file.getString("teams." + teamName + ".block")),
          getWool(file.getString("teams." + teamName + ".block")),
          getChatColor(file.getString("teams." + teamName + ".chatcolor")), maxPlayers));
    });
  }

  public void saveData() {
    data.getConfig().getConfigurationSection("teams").getKeys(false).forEach(teamName -> {
      data.getConfig().set("teams." + teamName + ".points", getTeam(teamName).get().getPoints());
    });
    data.saveConfig();
  }

  private Color getColor(String colorName) {
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

  private ChatColor getChatColor(String chatColorName) {
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

  private Material getConcrete(String colorName) {
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

  private Material getWool(String colorName) {
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

  public List<VirtualPlayer> getPlayers() {
    return teams.stream()
        .flatMap(virtualTeam -> virtualTeam.getPlayers().stream())
        .collect(Collectors.toList());
  }

  public Optional<VirtualTeam> getPlayerTeam(Player player) {
    return teams.stream()
        .filter(virtualTeam -> virtualTeam.getPlayers().stream()
            .anyMatch(virtualPlayer -> virtualPlayer.player.getName().equals(player.getName())))
        .findFirst();
  }

  public Optional<String> getIconPrefix(Optional<VirtualTeam> team) {
    return team.map(virtualTeam -> virtualTeam.prefix + " " + virtualTeam.name);
  }

  public Optional<VirtualTeam> getTeam(String teamName) {
    return teams.stream()
        .filter(virtualTeam -> virtualTeam.name.equalsIgnoreCase(teamName))
        .findFirst();
  }

  public void addPlayerTeam(Player player) {
    Optional<VirtualTeam> team = teams.stream()
        .filter(virtualTeam -> virtualTeam.playersName.contains(player.getName()))
        .findFirst();

    if (team.isPresent()) {
      team.get().getPlayers().add(new VirtualPlayer(player, team.get()));
    } else {
      plugin.getLogger().warning("Player " + player.getName() + " is not in any team !");
    }
  }

  public String getPlayerNameFormat(VirtualTeam team, Player player) {
    StringBuilder sb = new StringBuilder();

    sb.append(team.prefix).append(" ").append(team.name).append(player.getName());

    return sb.toString();
  }

  private Optional<VirtualTeam> teamDispatcher(VirtualTeam virtualTeam) {
    return teams.stream()
        .filter(vT -> vT.equals(virtualTeam))
        .findFirst();
  }
}
