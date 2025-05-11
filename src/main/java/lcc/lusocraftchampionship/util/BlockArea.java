package lcc.lusocraftchampionship.util;

import org.bukkit.Location;

public class BlockArea {
  private final Location min;
  private final Location max;

  public BlockArea(Location min, Location max) {
    this.min = min;
    this.max = max;
  }

  public boolean isPlacedBlockArea(Location blockLocation) {
    if (!min.getWorld().equals(max.getWorld())) {
      return false;
    }

    double minX = Math.min(min.getX(), max.getX());
    double maxX = Math.max(min.getX(), max.getX());
    double minY = Math.min(min.getY(), max.getY());
    double maxY = Math.max(min.getY(), max.getY());
    double minZ = Math.min(min.getZ(), max.getZ());
    double maxZ = Math.max(min.getZ(), max.getZ());

    return blockLocation.getWorld().equals(min.getWorld()) &&
        blockLocation.getX() >= minX && blockLocation.getX() <= maxX &&
        blockLocation.getY() >= minY && blockLocation.getY() <= maxY &&
        blockLocation.getZ() >= minZ && blockLocation.getZ() <= maxZ;
  }
}
