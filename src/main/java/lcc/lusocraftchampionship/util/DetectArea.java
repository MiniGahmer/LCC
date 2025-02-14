package lcc.lusocraftchampionship.util;

import lcc.lusocraftchampionship.Lusocraftchampionship;
import lcc.lusocraftchampionship.team.Teams;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.util.logging.Level;

public class DetectArea {
    public static boolean detectPlayerArea(Player player, Location locationDownedge, Location locationUpedge) {
        if ((((int) player.getLocation().getX() >= locationDownedge.getX()) && ((int) player.getLocation().getX() <= locationUpedge.getX()))
                || (((int) player.getLocation().getX() <= locationDownedge.getX()) && ((int) player.getLocation().getX() >= locationUpedge.getX()))) {
            if ((((int) player.getLocation().getZ() >= locationDownedge.getZ()) && ((int) player.getLocation().getZ() <= locationUpedge.getZ()))
                    || (((int) player.getLocation().getZ() <= locationDownedge.getZ()) && ((int) player.getLocation().getZ() >= locationUpedge.getZ()))) {
                if ((((int) player.getLocation().getY() >= locationDownedge.getY()) && ((int) player.getLocation().getY() <= locationUpedge.getY()))
                        || (((int) player.getLocation().getY() <= locationDownedge.getY()) && ((int) player.getLocation().getY() >= locationUpedge.getY()))) {
                    return true;
                }
            }
        }
        return false;
    }

    public static void removeWoolInArea(World world, Location locationDownedge, Location locationUpedge) {
        int minX = Math.min(locationDownedge.getBlockX(), locationUpedge.getBlockX());
        int maxX = Math.max(locationDownedge.getBlockX(), locationUpedge.getBlockX());
        int minY = Math.min(locationDownedge.getBlockY(), locationUpedge.getBlockY());
        int maxY = Math.max(locationDownedge.getBlockY(), locationUpedge.getBlockY());
        int minZ = Math.min(locationDownedge.getBlockZ(), locationUpedge.getBlockZ());
        int maxZ = Math.max(locationDownedge.getBlockZ(), locationUpedge.getBlockZ());

        for (int x = minX; x <= maxX; x++) {
            for (int y = minY; y <= maxY; y++) {
                for (int z = minZ; z <= maxZ; z++) {
                    Block block = world.getBlockAt(x, y, z);
                    if (block.getType().toString().endsWith("_WOOL")) { // Detects all wool colors
                        block.setType(Material.AIR); // Removes the wool
                    }
                }
            }
        }
    }

    public static boolean detectBlockArea(Material targetBlock, Location edge, Location edge1) {
        float minX, maxX;
        float minZ, maxZ;
        float minY, maxY;
        int lengthX = 0, lengthY = 0, lengthZ = 0;
        if(!edge1.getWorld().equals(edge1.getWorld())) {
            Lusocraftchampionship.LOGGER.log(Level.INFO, "The worlds of " + edge + " and " + edge1 + "aren't the same!");
            return false;
        }
        if(edge.getX() <= edge1.getX()) {
            minX = (float) edge.getX();
            maxX = (float) edge1.getX();
        } else {
            minX = (float) edge1.getX();
            maxX = (float) edge.getX();
        }
        lengthX = (int) (Math.abs(maxX) - Math.abs(minX));

        if(edge.getY() <= edge1.getY()) {
            minY = (float) edge.getY();
            maxY = (float) edge1.getY();
        } else {
            minY = (float) edge1.getY();
            maxY = (float) edge.getY();
        }
        lengthY = (int) (Math.abs(maxY) - Math.abs(minY));

        if(edge.getZ() <= edge1.getZ()) {
            minZ = (float) edge.getZ();
            maxZ = (float) edge1.getZ();
        } else {
            minZ = (float) edge1.getZ();
            maxZ = (float) edge.getZ();
        }
        lengthZ = (int) (Math.abs(maxZ) - Math.abs(minZ));

        lengthX = Math.abs(lengthX);
        lengthY = Math.abs(lengthY);
        lengthZ = Math.abs(lengthZ);

        for(int y = 0; y <=lengthY; y++) {
            for(int x = 0; x <= lengthX; x++) {
                for(int z = 0; z <= lengthZ; z++) {
                    if(Bukkit.getWorld(edge.getWorld().getName()).getBlockAt(new Location(edge.getWorld(), minX + x, minY + y, minZ+ z)).getType() != targetBlock) {
                        return false;
                        //Bukkit.broadcastMessage(String.valueOf("World: " + edge.getWorld() + "X: " + (edge.getX() + x) + "Y: " + (edge.getY() + y) + "Z: " + (edge.getZ() + z)));
                    }
                }
            }
        }
        return true;
    }
}
