package lcc.lusocraftchampionship.minigame.GravityWars;

import lcc.lusocraftchampionship.Lusocraftchampionship;
import lcc.lusocraftchampionship.minigame.GravityWars.command.StartGravityWars;
import lcc.lusocraftchampionship.minigame.GravityWars.listener.*;
import lcc.lusocraftchampionship.minigame.GravityWars.state.*;
import lcc.lusocraftchampionship.minigame.Minigame;
import lcc.lusocraftchampionship.team.Teams;
import lcc.lusocraftchampionship.util.Particles;
import lcc.lusocraftchampionship.util.Timer;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.*;

public class GravityWars extends Minigame {

    ArmorStand blackHoleEntity;
    public Location blackHoleLocation;
    public Location RedareaEdge;
    public Location RedareaEdge1;
    public Location MapareaEdge;
    public Location MapareaEdge1;
    public HashMap<String, Integer> PLAYER_KILLS = new HashMap<>();
    public HashMap<String, String> PLAYER_KILLER = new HashMap<>();
    public List<String> TOP_PLAYERS_KILLS = new ArrayList<>();
    public final Map<String, Player> teamTotemHolders = new HashMap<>();
    public final Map<Player, Map<Material, Long>> playerCooldowns = new HashMap<>();

    public Set<Material> WOOLS = new HashSet<>(
            Arrays.asList(
                    Material.WHITE_WOOL,
                    Material.ORANGE_WOOL,
                    Material.MAGENTA_WOOL,
                    Material.LIGHT_BLUE_WOOL,
                    Material.YELLOW_WOOL,
                    Material.GREEN_WOOL,
                    Material.PINK_WOOL,
                    Material.GRAY_WOOL,
                    Material.LIGHT_GRAY_WOOL,
                    Material.CYAN_WOOL,
                    Material.PURPLE_WOOL,
                    Material.BLUE_WOOL,
                    Material.BROWN_WOOL,
                    Material.LIME_WOOL,
                    Material.RED_WOOL,
                    Material.BLACK_WOOL
            )
    );

    public Set<Material> TOTEM_BLOCK = new HashSet<>(
            Arrays.asList(
                    Material.CRYING_OBSIDIAN,
                    Material.GREEN_GLAZED_TERRACOTTA,
                    Material.LIGHT_BLUE_GLAZED_TERRACOTTA,
                    Material.RED_GLAZED_TERRACOTTA

            )
    );

    public GravityWars(Lusocraftchampionship plugin) {
        super(plugin, "gravitywars");

        addStateHandler(GravityWarsStages.TELEPORT, new GravityWarsTeleportState());
        addStateHandler(GravityWarsStages.EXPLANATION, new GravityWarsExplanationState());
        addStateHandler(GravityWarsStages.PREPARATION, new GravityWarsPreparationState());
        addStateHandler(GravityWarsStages.INGAME, new GravityWarsInGameState());
        addStateHandler(GravityWarsStages.END, new GravityWarsEndState());

        addExplanation(new GravityWarsExplanation(this));
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
        RedareaEdge = getAreaEdge();
        RedareaEdge1 = getAreaEdge1();
        MapareaEdge = getMapEdge();
        MapareaEdge1 = getMapEdge1();

        plugin.getServer().getPluginManager().registerEvents(new BlockBreakListener(this), plugin);
        plugin.getServer().getPluginManager().registerEvents(new BlockPlaceListener(this), plugin);
        plugin.getServer().getPluginManager().registerEvents(new PlayerRespawnListener(this), plugin);
        plugin.getServer().getPluginManager().registerEvents(new EntityDamageByEntityListener(this), plugin);
//        plugin.getServer().getPluginManager().registerEvents(new ChatMessageListener(this), plugin);
        plugin.getServer().getPluginManager().registerEvents(new GravityTunnelsListener(this), plugin);
        plugin.getServer().getPluginManager().registerEvents(new GravityDeviceListener(this), plugin);
        plugin.getServer().getPluginManager().registerEvents(new TotemRedInteractListener(this), plugin);
        plugin.getServer().getPluginManager().registerEvents(new TotemBlueInteractListener(this), plugin);
        plugin.getServer().getPluginManager().registerEvents(new TotemGreenInteractListener(this), plugin);
        plugin.getServer().getPluginManager().registerEvents(new TotemObsidianInteractListener(this), plugin);
        plugin.getServer().getPluginManager().registerEvents(new PlayerInventoryClickListener(this), plugin);

        plugin.getCommand("startgravitywars").setExecutor(new StartGravityWars(this));
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

    public Location getSpawnpoint() {
        return new Location(Bukkit.getWorld(data.getConfig().getString("spawnpoint.world")),
                data.getConfig().getDouble("spawnpoint.x"),
                data.getConfig().getDouble("spawnpoint.y"),
                data.getConfig().getDouble("spawnpoint.z"));
    }

    public String getTeam1() {
        return data.getConfig().getString("team1");
    }

    public String getTeam2() {
        return data.getConfig().getString("team2");
    }

    public String getTeam3() {
        return data.getConfig().getString("team3");
    }

    public String getTeam4() {
        return data.getConfig().getString("team4");
    }

    public Location getTeam1Spawn() {
        return new Location(Bukkit.getWorld(data.getConfig().getString("teamspawn.1.world")),
                data.getConfig().getDouble("teamspawn.1.x"),
                data.getConfig().getDouble("teamspawn.1.y"),
                data.getConfig().getDouble("teamspawn.1.z"));
    }

    public Location getTeam2Spawn() {
        return new Location(Bukkit.getWorld(data.getConfig().getString("teamspawn.2.world")),
                data.getConfig().getDouble("teamspawn.2.x"),
                data.getConfig().getDouble("teamspawn.2.y"),
                data.getConfig().getDouble("teamspawn.2.z"));
    }

    public Location getTeam3Spawn() {
        return new Location(Bukkit.getWorld(data.getConfig().getString("teamspawn.3.world")),
                data.getConfig().getDouble("teamspawn.3.x"),
                data.getConfig().getDouble("teamspawn.3.y"),
                data.getConfig().getDouble("teamspawn.3.z"));
    }

    public Location getTeam4Spawn() {
        return new Location(Bukkit.getWorld(data.getConfig().getString("teamspawn.4.world")),
                data.getConfig().getDouble("teamspawn.4.x"),
                data.getConfig().getDouble("teamspawn.4.y"),
                data.getConfig().getDouble("teamspawn.4.z"));
    }

    public void givePlayerGravityDevice(Player player) {
        ItemStack itemStack = new ItemStack(Material.CLOCK);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setUnbreakable(true);
        itemMeta.addEnchant(Enchantment.DURABILITY, 3, true);
        itemMeta.setDisplayName("§d§lGravityFlip");
        itemStack.setItemMeta(itemMeta);
        player.getInventory().setItemInOffHand(itemStack);
    }

    public boolean areTeammates(Player player1, Player player2) {
        return Teams.getPlayerTeam(player1).equals(Teams.getPlayerTeam(player2)); // Adjust this based on your team system
    }

    public List<Location> getRedTotem() {
        List<Location> locations = new ArrayList<>();
        FileConfiguration file = data.getConfig();
        file.getConfigurationSection("red.1").getKeys(false).forEach(kits -> {
            locations.add(new Location(Bukkit.getWorld(file.getString("red.1." + kits + ".world")),
                    file.getDouble("red.1." + kits + ".x"),
                    file.getDouble("red.1." + kits + ".y"),
                    file.getDouble("red.1." + kits + ".z")));
        });
        return locations;
    }

    public List<Location> getBlueTotem() {
        List<Location> locations = new ArrayList<>();
        FileConfiguration file = data.getConfig();
        file.getConfigurationSection("blue.1").getKeys(false).forEach(kits -> {
            locations.add(new Location(Bukkit.getWorld(file.getString("blue.1." + kits + ".world")),
                    file.getDouble("blue.1." + kits + ".x"),
                    file.getDouble("blue.1." + kits + ".y"),
                    file.getDouble("blue.1." + kits + ".z")));
        });
        return locations;
    }

    public List<Location> getGreenTotem() {
        List<Location> locations = new ArrayList<>();
        FileConfiguration file = data.getConfig();
        file.getConfigurationSection("green.1").getKeys(false).forEach(kits -> {
            locations.add(new Location(Bukkit.getWorld(file.getString("green.1." + kits + ".world")),
                    file.getDouble("green.1." + kits + ".x"),
                    file.getDouble("green.1." + kits + ".y"),
                    file.getDouble("green.1." + kits + ".z")));
        });
        return locations;
    }

    public List<Location> getObsidianTotem() {
        List<Location> locations = new ArrayList<>();
        FileConfiguration file = data.getConfig();
        file.getConfigurationSection("obsidian.1").getKeys(false).forEach(kits -> {
            locations.add(new Location(Bukkit.getWorld(file.getString("obsidian.1." + kits + ".world")),
                    file.getDouble("obsidian.1." + kits + ".x"),
                    file.getDouble("obsidian.1." + kits + ".y"),
                    file.getDouble("obsidian.1." + kits + ".z")));
        });
        return locations;
    }

    public Location getTotemLocation(Material totemMaterial, int index) {
        Location location = null;
        switch (totemMaterial) {
            case LIGHT_BLUE_GLAZED_TERRACOTTA:
                if (index >= 0 && index <= 3) {
                    location = getBlueTotem().get(index);
                }
                break;
            case GREEN_GLAZED_TERRACOTTA:
                if (index >= 0 && index <= 3) {
                    location = getGreenTotem().get(index);
                }
                break;
            case RED_GLAZED_TERRACOTTA:
                if (index >= 0 && index <= 3) {
                    location = getRedTotem().get(index);
                }
                break;
            case CRYING_OBSIDIAN:
                if (index >= 0 && index <= 3) {
                    location = getObsidianTotem().get(index);
                }
                break;
            default:
                return null;
        }

        return location;
    }

    public boolean isTotemBlock(Material block) {
        // This method checks if the block is one of the totem blocks
        return block == Material.LIGHT_BLUE_GLAZED_TERRACOTTA ||
                block == Material.GREEN_GLAZED_TERRACOTTA ||
                block == Material.RED_GLAZED_TERRACOTTA ||
                block == Material.CRYING_OBSIDIAN;
    }

    public Location getAreaEdge() {
        return new Location(Bukkit.getWorld(data.getConfig().getString("area.edge.world")),
                data.getConfig().getDouble("area.edge.x"),
                data.getConfig().getDouble("area.edge.y"),
                data.getConfig().getDouble("area.edge.z"));
    }

    public Location getAreaEdge1() {
        return new Location(Bukkit.getWorld(data.getConfig().getString("area.edge1.world")),
                data.getConfig().getDouble("area.edge1.x"),
                data.getConfig().getDouble("area.edge1.y"),
                data.getConfig().getDouble("area.edge1.z"));
    }

    public Location getMapEdge() {
        return new Location(Bukkit.getWorld(data.getConfig().getString("map.edge.world")),
                data.getConfig().getDouble("map.edge.x"),
                data.getConfig().getDouble("map.edge.y"),
                data.getConfig().getDouble("map.edge.z"));
    }

    public Location getMapEdge1() {
        return new Location(Bukkit.getWorld(data.getConfig().getString("map.edge1.world")),
                data.getConfig().getDouble("map.edge1.x"),
                data.getConfig().getDouble("map.edge1.y"),
                data.getConfig().getDouble("map.edge1.z"));
    }

    public void givePlayerKit(Player player) {
        ItemStack itemStack = new ItemStack(Material.STONE_SWORD);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setUnbreakable(true);
        itemStack.setItemMeta(itemMeta);
        player.getInventory().addItem(itemStack);

        itemStack = new ItemStack(Teams.getTeamWool(Teams.getPlayerTeam(player)), 64);
        itemMeta.setUnbreakable(true);
        player.getInventory().addItem(itemStack);

        itemStack = new ItemStack(Material.SHEARS);
        itemMeta.setUnbreakable(true);
        player.getInventory().addItem(itemStack);

        itemStack = new ItemStack(Material.LEATHER_LEGGINGS);
        ItemMeta meta = itemStack.hasItemMeta() ? itemStack.getItemMeta() : Bukkit.getItemFactory().getItemMeta(itemStack.getType());
        LeatherArmorMeta leatherArmorMeta = (LeatherArmorMeta) meta;
        leatherArmorMeta.setColor(Teams.getTeamColor(Teams.getPlayerTeam(player)));
        itemStack.setItemMeta(leatherArmorMeta);

        player.getInventory().setLeggings(itemStack);

        itemStack = new ItemStack(Material.LEATHER_CHESTPLATE);
        ItemMeta meta1 = itemStack.hasItemMeta() ? itemStack.getItemMeta() : Bukkit.getItemFactory().getItemMeta(itemStack.getType());
        LeatherArmorMeta leatherArmorMeta1 = (LeatherArmorMeta) meta1;
        leatherArmorMeta1.setColor(Teams.getTeamColor(Teams.getPlayerTeam(player)));
        itemStack.setItemMeta(leatherArmorMeta1);

        player.getInventory().setChestplate(itemStack);

        itemStack = new ItemStack(Material.LEATHER_BOOTS);
        ItemMeta meta2 = itemStack.hasItemMeta() ? itemStack.getItemMeta() : Bukkit.getItemFactory().getItemMeta(itemStack.getType());
        LeatherArmorMeta leatherArmorMeta2 = (LeatherArmorMeta) meta2;
        leatherArmorMeta2.setColor(Teams.getTeamColor(Teams.getPlayerTeam(player)));
        itemStack.setItemMeta(leatherArmorMeta2);

        player.getInventory().setBoots(itemStack);
    }

    public Block[] StartingBlocks = {
            getRedTotem().getFirst().getBlock(),
            getBlueTotem().get(1).getBlock(),
            getGreenTotem().get(2).getBlock(),
            getObsidianTotem().get(3).getBlock()
    };

    public Material[] materials = {
            Material.RED_GLAZED_TERRACOTTA,
            Material.LIGHT_BLUE_GLAZED_TERRACOTTA,
            Material.GREEN_GLAZED_TERRACOTTA,
            Material.CRYING_OBSIDIAN
    };

    // Spawns an invisible armor stand to represent the black hole
    public void spawnBlackHole() {
        World world = Bukkit.getWorld(getWorldName());

        if (world != null) {
            this.blackHoleLocation = new Location(world, -103, 52, -63);
        } else {
            this.blackHoleLocation = null;
        }

        // Spawn a 3x3 grid of floating Falling Blocks
        for (int x = -1; x <= 1; x++) {
            for (int y = -1; y <= 1; y++) {
                for (int z = -1; z <= 1; z++) {
                    Location blockLocation = blackHoleLocation.clone().add(x, y, z);
                    FallingBlock fallingBlock = world.spawnFallingBlock(blockLocation, Material.BLACK_CONCRETE.createBlockData());
                    fallingBlock.setGravity(false);
                    fallingBlock.setDropItem(false);
                }
            }
        }

        // Spawn an invisible armor stand as the black hole center
        blackHoleEntity = world.spawn(blackHoleLocation, ArmorStand.class, stand -> {
            stand.setInvisible(true);
            stand.setGravity(false);
            stand.setInvulnerable(true);
            stand.setMarker(true); // Makes it a tiny hitbox
        });

        // Start pulling players
        startPullEffect();
    }

    // Pulls all non-winning players towards the black hole every tick
    private void startPullEffect() {
        new BukkitRunnable() {
            int duration = Timer.secToTicks(10); // Lasts 10 seconds (20 ticks per second)

            @Override
            public void run() {
                if (duration <= 0 || blackHoleEntity == null || blackHoleEntity.isDead()) {
                    this.cancel();
                    return;
                }

                for (Player player : Bukkit.getOnlinePlayers()) {

                    Location playerLocation = player.getLocation();
                    Vector pullForce = blackHoleLocation.toVector().subtract(playerLocation.toVector()).normalize().multiply(0.2);

                    // Get the block in front of the player
                    Block frontBlock = getFrontBlock(player);

                    // Check if there's an obstacle in front of the player
                    if (!isPassable(frontBlock)) {
                        player.teleport(teleportCloserToBlackHole(playerLocation)); // If stuck, teleport closer
                    } else {
                        player.setVelocity(player.getVelocity().add(pullForce)); // Pull normally
                    }

                    // Optional: Add visual effects
                    player.getWorld().spawnParticle(Particle.SMOKE_LARGE, blackHoleLocation, 10);
                    player.getWorld().spawnParticle(Particle.PORTAL, player.getLocation(), 5);
                }

                duration--;
            }
        }.runTaskTimer(plugin, 0, 1); // Runs every tick
    }

    // **Returns the block in front of the player**
    private Block getFrontBlock(Player player) {
        Location eyeLocation = player.getEyeLocation();
        Vector direction = eyeLocation.getDirection().normalize();
        Location frontBlockLocation = eyeLocation.add(direction.multiply(1)); // Get 1 block in front
        return frontBlockLocation.getBlock();
    }

    // **Checks if a block is passable (not a solid wall)**
    private boolean isPassable(Block block) {
        return block.getType().isAir() || block.getType() == Material.WATER || block.getType() == Material.LAVA;
    }

    // **Teleports player 3 blocks closer to the black hole until they are no longer stuck**
    private Location teleportCloserToBlackHole(Location current) {
        Vector directionToBlackHole = blackHoleLocation.toVector().subtract(current.toVector()).normalize();

        for (int i = 0; i < 5; i++) { // Try up to 5 times if necessary
            Location newLocation = current.clone().add(directionToBlackHole.multiply(3 * (i + 1))); // Move 3 blocks closer

            if (isPassable(newLocation.getBlock())) {
                return newLocation; // If the spot is safe, return it
            }
        }

        return blackHoleLocation.clone().add(0, 3, 0); // As a last resort, teleport above the black hole
    }
}
