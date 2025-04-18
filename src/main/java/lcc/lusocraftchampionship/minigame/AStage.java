package lcc.lusocraftchampionship.minigame;

import lcc.lusocraftchampionship.lcc.player.VirtualPlayer;
import lcc.lusocraftchampionship.lcc.team.Teams;
import lcc.lusocraftchampionship.util.Particles;
import lcc.lusocraftchampionship.util.Timer;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;

public abstract class AStage<T extends AMinigame, S extends Enum<S>> implements IStage {
  protected T minigame;
  protected S stage;

  protected AStage(T minigame) {
    this.minigame = minigame;
  }

  @SuppressWarnings("unchecked")
  @Override
  public void jumpToState(Enum<?> state) {
    stage = (S) state;
  }

  @Override
  public void onEnable() {
  }

  @Override
  public void onUpdate(int ticks, int stopwatch) {
  }

  @Override
  public void onDisable() {
  }

  @Override
  public int stageTime() {
    return 0;
  }

  @Override
  public Enum<?> getState() {
    return stage;
  }

  // ================
  // Minigame Functions
  // ================

  public void pointsEffect(Player player) {
    Particles.spawnParticlesCoins(player.getWorld().getName(), player.getLocation().add(0, 1, 0));
    player.playSound(player.getLocation(), "minecraft:alc.coins", SoundCategory.MASTER, 1f, 1f);
  }

  public void fireworksEffect(Player player) {
    Particles.spawnFireworks(player.getWorld().getName(), player.getLocation().add(0, 1, 0));
    player.playSound(player.getLocation(), Sound.ENTITY_FIREWORK_ROCKET_BLAST, SoundCategory.MASTER, 1f, 1f);
  }

  public void countdownTeamPlayer(int stopwatch, int range) {
    if (Timer.isZero(stopwatch))
      return;
    if (Timer.ticksToSec(stopwatch) > range)
      return;
    if ((stopwatch % 20) == 0) {
      for (VirtualPlayer vp : Teams.INSTANCE.getPlayers()) {
        vp.player.sendTitle("§3Começa em", "§c>§e" + Timer.ticksToSec(stopwatch) + "§c<", 0, 20, 0);
        vp.player.playSound(vp.player.getLocation(), "minecraft:alc.countdown", SoundCategory.MASTER, 1f, 1f);
      }
    }
  }

  public void countdownPlayer(int stopwatch, int range, Player player) {
    if (Timer.isZero(stopwatch))
      return;
    if (Timer.ticksToSec(stopwatch) > range)
      return;
    if ((stopwatch % 20) == 0) {
      player.sendTitle("§3Começa em", "§c>§e" + Timer.ticksToSec(stopwatch) + "§c<", 0, 20, 0);
      player.playSound(player.getLocation(), "minecraft:alc.countdown", SoundCategory.MASTER, 1f, 1f);
    }
  }

  // public void soundStartGameParkour() {
  //   for (VirtualPlayer vp : Teams.INSTANCE.getPlayers()) {
  //     vp.player.playSound(vp.player.getLocation(), "minecraft:alc.startgame", SoundCategory.MASTER, 1f, 1f);
  //     vp.player.playSound(vp.player.getLocation(), "minecraft:alc.parkour", SoundCategory.MASTER, 0.5f, 1f);

  //   }
  // }

  // public void soundStartGameBattleBox() {
  //   for (Player player : Bukkit.getOnlinePlayers()) {
  //     if (Teams.INSTANCE.getPlayersName().contains(player.getName())) {
  //       player.playSound(player.getLocation(), "minecraft:alc.battlebox", SoundCategory.MASTER, 0.3f, 1f);
  //     }
  //   }
  // }

  // public void soundStartGameDuels() {
  //   for (Player player : Bukkit.getOnlinePlayers()) {
  //     if (Teams.INSTANCE.getPlayersName().contains(player.getName())) {
  //       player.playSound(player.getLocation(), "minecraft:alc.duels", SoundCategory.MASTER, 0.2f, 1f);
  //     }
  //   }
  // }

  // public void soundStopGameDuels() {
  //   for (Player player : Bukkit.getOnlinePlayers()) {
  //     if (Teams.INSTANCE.getPlayersName().contains(player.getName())) {
  //       player.stopSound("minecraft:alc.duels", SoundCategory.MASTER);
  //     }
  //   }
  // }

  // public void soundStartGameTGTTOS() {
  //   for (Player player : Bukkit.getOnlinePlayers()) {
  //     if (Teams.INSTANCE.getPlayersName().contains(player.getName())) {
  //       player.playSound(player.getLocation(), "minecraft:alc.tgttos", SoundCategory.MASTER, 0.2f, 1f);
  //     }
  //   }
  // }

  // public void soundStartGameAceRace() {
  //   for (Player player : Bukkit.getOnlinePlayers()) {
  //     if (Teams.INSTANCE.getPlayersName().contains(player.getName())) {
  //       player.playSound(player.getLocation(), "minecraft:alc.acerace", SoundCategory.MASTER, 0.2f, 1f);
  //     }
  //   }
  // }

  // public void soundEndgame() {
  //   for (Player player : Bukkit.getOnlinePlayers()) {
  //     if (Teams.INSTANCE.getPlayersName().contains(player.getName())) {
  //       player.playSound(player.getLocation(), "minecraft:alc.endgame", SoundCategory.MASTER, 1f, 1f);
  //     }
  //   }
  // }

  // public void soundStartgame() {
  //   for (Player player : Bukkit.getOnlinePlayers()) {
  //     if (Teams.INSTANCE.getPlayersName().contains(player.getName())) {
  //       player.playSound(player.getLocation(), "minecraft:alc.startgame", SoundCategory.MASTER, 1f, 1f);
  //     }
  //   }
  // }

  public void giveBoots(VirtualPlayer vp) {
    ItemStack item = new ItemStack(Material.LEATHER_BOOTS);
    ItemMeta meta = item.hasItemMeta() ? item.getItemMeta() : Bukkit.getItemFactory().getItemMeta(item.getType());
    LeatherArmorMeta leatherArmorMeta = (LeatherArmorMeta) meta;
    leatherArmorMeta.setColor(vp.team.color);
    item.setItemMeta(leatherArmorMeta);

    vp.player.getInventory().setBoots(item);
  }

  // public void sendTeamStatus(List<String> top, HashMap<String, Integer> stats, String header) {
  //   for (Player player : Bukkit.getOnlinePlayers()) {
  //     player.sendMessage("§a§m                                                                                 ");
  //     player.sendMessage("                      §r§l" + header + "§r                                   ");
  //     player.sendMessage("§a§m                                                                                 ");
  //     player.sendMessage(
  //         " 1. " + Teams.INSTANCE.getIconPrefix(top.get(0)) + top.get(0) + " §r> " + stats.get(top.get(0)));
  //     player.sendMessage(
  //         " 2. " + Teams.INSTANCE.getIconPrefix(top.get(1)) + top.get(1) + " §r> " + stats.get(top.get(1)));
  //     player.sendMessage(
  //         " 3. " + Teams.INSTANCE.getIconPrefix(top.get(2)) + top.get(2) + " §r> " + stats.get(top.get(2)));
  //     player.sendMessage(
  //         " 4. " + Teams.INSTANCE.getIconPrefix(top.get(3)) + top.get(3) + " §r> " + stats.get(top.get(3)));
  //     player.sendMessage(
  //         " 5. " + Teams.INSTANCE.getIconPrefix(top.get(4)) + top.get(4) + " §r> " + stats.get(top.get(4)));
  //     player.sendMessage("§a§m                                                                                 ");
  //   }
  // }

  // public void sendPlayerStatus(List<String> top, HashMap<String, Integer> stats, String header) {
  //   for (Player player : Bukkit.getOnlinePlayers()) {
  //     player.sendMessage("§a§m                                                                                 ");
  //     player.sendMessage("                      §r§l" + header + "§r                                   ");
  //     player.sendMessage("§a§m                                                                                 ");
  //     player.sendMessage(" 1. " + Teams.INSTANCE.getIconPrefix(Teams.INSTANCE.getPlayerTeam(top.get(0))) + top.get(0)
  //         + " §r> " + stats.get(top.get(0)));
  //     player.sendMessage(" 2. " + Teams.INSTANCE.getIconPrefix(Teams.INSTANCE.getPlayerTeam(top.get(1))) + top.get(1)
  //         + " §r> " + stats.get(top.get(1)));
  //     player.sendMessage(" 3. " + Teams.INSTANCE.getIconPrefix(Teams.INSTANCE.getPlayerTeam(top.get(2))) + top.get(2)
  //         + " §r> " + stats.get(top.get(2)));
  //     player.sendMessage(" 4. " + Teams.INSTANCE.getIconPrefix(Teams.INSTANCE.getPlayerTeam(top.get(3))) + top.get(3)
  //         + " §r> " + stats.get(top.get(3)));
  //     player.sendMessage(" 5. " + Teams.INSTANCE.getIconPrefix(Teams.INSTANCE.getPlayerTeam(top.get(4))) + top.get(4)
  //         + " §r> " + stats.get(top.get(4)));
  //     player.sendMessage("§a§m                                                                                 ");
  //   }
  // }

  // public void sendPlayerFlash(Player player) {
  //   player.sendTitle("\uEff7", "", 0, 40, 20);
  // }

  // public void sendTeamPlayerFlash() {
  //   for (Player player : Bukkit.getOnlinePlayers()) {
  //     if (Teams.INSTANCE.getPlayersName().contains(player.getName())) {
  //       player.sendTitle("\uEff7", "", 0, 40, 20);
  //     }
  //   }
  // }

  // public void clearAllPlayer(Plugin plugin) {
  //   for (Player player : Bukkit.getOnlinePlayers()) {
  //     if (Teams.INSTANCE.getPlayersName().contains(player.getName())) {
  //       player.getInventory().clear();
  //       player.setExp(0);
  //       player.setHealth(20);
  //       player.setFoodLevel(20);
  //       player.setInvisible(false);
  //       for (Player player_ : Bukkit.getOnlinePlayers())
  //         player.showPlayer(plugin, player_);

  //       for (PotionEffect effect : player.getActivePotionEffects())
  //         player.removePotionEffect(effect.getType());
  //     }
  //   }
  // }

  public void clearPlayer(Player player, Plugin plugin) {
    player.getInventory().clear();
    player.setExp(0);
    player.setHealth(20);
    player.setFoodLevel(20);
    player.setInvisible(false);
    
    for (Player player_ : Bukkit.getOnlinePlayers())
      player.showPlayer(plugin, player_);

    for (PotionEffect effect : player.getActivePotionEffects())
      player.removePotionEffect(effect.getType());
  }

  public void playerImmune(Player player) {
    player.setHealth(20);
    player.setFoodLevel(20);
  }
}
