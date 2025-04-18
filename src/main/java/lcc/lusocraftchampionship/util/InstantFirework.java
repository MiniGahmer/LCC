package lcc.lusocraftchampionship.util;

import lcc.lusocraftchampionship.LCCPlugin;
import org.bukkit.Bukkit;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.entity.Firework;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.metadata.FixedMetadataValue;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class InstantFirework {

    private LCCPlugin plugin;

    /*
     * InstantFirework class made by TehHypnoz.
     *
     * Credits to:
     *
     * - fromgate, for explaining that setting the ticksFlown field to the expectedLifespan field will create instant fireworks.
     * - Skionz, for the getNMSClass() method.
     *
     * Example usage:
     * FireworkEffect fireworkEffect = FireworkEffect.builder().flicker(false).trail(true).with(Type.BALL).withColor(Color.ORANGE).withFade(Color.RED).build();
     * Location location = p.getLocation();
     * new InstantFirework(fireworkEffect, location);
     */


    public InstantFirework(FireworkEffect fe, Location loc, LCCPlugin plugin) {
        this.plugin = plugin;
        Firework f = (Firework) loc.getWorld().spawn(loc, Firework.class);
        FireworkMeta fm = f.getFireworkMeta();
        fm.addEffect(fe);
        f.setMetadata("nodamage", new FixedMetadataValue(this.plugin, true));
        f.setFireworkMeta(fm);
        try {
            Class<?> entityFireworkClass = getClass("net.minecraft.server.", "EntityFireworks");
            Class<?> craftFireworkClass = getClass("org.bukkit.craftbukkit.", "entity.CraftFirework");
            Object firework = craftFireworkClass.cast(f);
            Method handle = firework.getClass().getMethod("getHandle");
            Object entityFirework = handle.invoke(firework);
            Field expectedLifespan = entityFireworkClass.getDeclaredField("expectedLifespan");
            Field ticksFlown = entityFireworkClass.getDeclaredField("ticksFlown");
            ticksFlown.setAccessible(true);
            ticksFlown.setInt(entityFirework, expectedLifespan.getInt(entityFirework) - 1);
            ticksFlown.setAccessible(false);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static Class<?> getClass(String prefix, String nmsClassString) throws ClassNotFoundException {
        String version = Bukkit.getServer().getClass().getPackage().getName().replace(".", ",").split(",")[3] + ".";
        String name = prefix + version + nmsClassString;
        Class<?> nmsClass = Class.forName(name);
        return nmsClass;
    }

}
