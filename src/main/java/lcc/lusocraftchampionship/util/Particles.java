package lcc.lusocraftchampionship.util;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;

public class Particles {
    public static void spawnParticlesCoins(String worldName, Location location) {
        Bukkit.getWorld(worldName).spawnParticle(Particle.END_ROD, location, 40, 0.5, 0.5, 0.5, 0.1);
    }

    public static void spawnFireworks(String worldName, Location location) {
        Bukkit.getWorld(worldName).spawnParticle(Particle.FIREWORKS_SPARK, location, 40, 0.5, 0.5, 0.5, 0.1);
    }
}
