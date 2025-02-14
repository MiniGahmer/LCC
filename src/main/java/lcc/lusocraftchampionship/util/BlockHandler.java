package lcc.lusocraftchampionship.util;

import lcc.lusocraftchampionship.Lusocraftchampionship;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;

import java.util.logging.Level;

public class BlockHandler {
    public static void replaceBlocks(Material targetBlock, Material replaceBlock, Location edge, Location edge1) {
        float minX, maxX;
        float minZ, maxZ;
        float minY, maxY;
        int lengthX, lengthY , lengthZ;
        if(!edge1.getWorld().equals(edge1.getWorld())) {
            Lusocraftchampionship.LOGGER.log(Level.INFO, "The worlds of " + edge + " and " + edge1 + "aren't the same!");
            return;
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
                    if(Bukkit.getWorld(edge.getWorld().getName()).getBlockAt(new Location(edge.getWorld(), edge.getX() + x, edge.getY() + y, edge.getZ() + z)).getType() == targetBlock) {
                        Bukkit.getWorld(edge.getWorld().getName()).getBlockAt(new Location(edge.getWorld(), edge.getX() + x, edge.getY() + y, edge.getZ() + z)).setType(replaceBlock);
                    }
                }
            }
        }
    }

    public static void setBlocks(Material setBlock, Location edge, Location edge1) {
        float minX, maxX;
        float minZ, maxZ;
        float minY, maxY;
        int lengthX, lengthY , lengthZ;
        if(!edge1.getWorld().equals(edge1.getWorld())) {
            Lusocraftchampionship.LOGGER.log(Level.INFO, "The worlds of " + edge + " and " + edge1 + "aren't the same!");
            return;
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
                    Bukkit.getWorld(edge.getWorld().getName()).getBlockAt(new Location(edge.getWorld(), minX + x, minY + y, minZ + z)).setType(setBlock);
                    //Bukkit.broadcastMessage("World: " + edge.getWorld() + "X: " + (edge.getX() + x) + "Y: " + (edge.getY() + y) + "Z: " + (edge.getZ() + z));
                }
            }
        }
    }

    public static boolean verifyPlaceBlockArea(Location location, Location edge, Location edge1) {
        float minX, maxX;
        float minZ, maxZ;
        float minY, maxY;
        int lengthX, lengthY , lengthZ;
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
                    if(location.equals(new Location(edge.getWorld(), minX + x, minY + y, minZ + z))) {
                        //Bukkit.broadcastMessage("World: " + edge.getWorld() + " x: " + (minX + x) + " y: " + (minY + y) + " z: " + (minZ + z));
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
