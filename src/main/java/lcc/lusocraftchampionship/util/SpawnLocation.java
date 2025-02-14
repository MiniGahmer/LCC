package lcc.lusocraftchampionship.util;

import org.bukkit.Location;

public class SpawnLocation {
    private Location a;
    private Location b;
    private String worldName;
    private float yaw, pitch;
    private double xMin, xMax, yMin, yMax, zMin, zMax;

    public SpawnLocation(Location a, Location b, float yaw, float pitch) {
        if (a == null || b == null)
            throw new IllegalArgumentException("Locations must not be null");
        if (!a.getWorld().getName().equals(b.getWorld().getName()))
            throw new IllegalArgumentException("Locations must be the same world");

        this.a = new Location(a.getWorld(), a.getX(), a.getY(), a.getZ(), a.getYaw(), a.getPitch());
        this.b = new Location(b.getWorld(), b.getX(), b.getY(), b.getZ(), b.getYaw(), b.getPitch());
        this.yaw = yaw;
        this.pitch = pitch;

        worldName = a.getWorld().toString();

        xMin = Math.min(a.getX(), b.getX());
        xMax = Math.max(a.getX(), b.getX());

        yMin = Math.min(a.getY(), b.getY());
        yMax = Math.max(a.getY(), b.getY());

        zMin = Math.min(a.getZ(), b.getZ());
        zMax = Math.max(a.getZ(), b.getZ());
    }

    public Location randomSpawnLocationXZ() {
        double randomX = Math.floor(Math.random() * (xMax - xMin + 1) + xMin);
        double randomZ = Math.floor(Math.random() * (zMax - zMin + 1) + zMin);

        return new Location(a.getWorld(), randomX, a.getY(), randomZ, a.getYaw(), a.getPitch());
    }


}
