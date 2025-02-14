package lcc.lusocraftchampionship.minigame.GravityWars;

import lcc.lusocraftchampionship.minigame.Minigame;
import lcc.lusocraftchampionship.minigame.MinigameExplanation;
import lcc.lusocraftchampionship.util.Timer;
import org.bukkit.Bukkit;
import org.bukkit.Location;

import java.util.HashMap;

public class GravityWarsExplanation extends MinigameExplanation {

    public GravityWarsExplanation(GravityWars minigame) {
        super(minigame);
    }

    @Override
    public HashMap<Integer, String> addExplanationMessages(Minigame minigame) {
        HashMap<Integer, String> messages = new HashMap<>();

        messages.put(Timer.secToTicks(40),
                "§r§a§m                                                                               " +
                        "§r\n" +
                        "§r\n" +
                        "§6§l    Ace Race§r\n" +
                        "§r\n" +
                        "§r§o      Como jogar...§r\n" +
                        "§r\n" +
                        "§r\n" +
                        "§r\n" +
                        "§r§a§m                                                                               ");

        messages.put(Timer.secToTicks(30),
                "§a§m                                                                                 §r\n" +
                        "§r\n" +
                        "§r    §lEste jogo consiste numa corrida com vários obstáculos.§r\n" +
                        "§r\n" +
                        "§r    Para completar o mapa é necessário completar §r§6§l3 voltas§r com um limite de 10 minutos.\n" +
                        "§r    Certas áreas requerem a utilização de §lelytra§r ou §ltrident§r.\n" +
                        "§r\n" +
                        "§a§m                                                                                 ");

        messages.put(Timer.secToTicks(20),
                "§a§m                                                                                 §r\n" +
                        "§r\n" +
                        "    Certos blocos têm mecanicas características especiais.\n" +
                        "§r\n" +
                        "    §aBlocos Verdes§r - §lSpeed§r\n" +
                        "§r  §cBlocos Vermelhos§r - §lBoost Frontal§r\n" +
                        "§r  §bBlocos Azuis§r - §lJump Boost§r\n" +
                        "§a§m                                                                                 ");

        messages.put(Timer.secToTicks(10),
                "§a§m                                                                                 §r\n" +
                        "§r\n" +
                        "§6§l    Pontos são dados por:§r\n" +
                        "§r\n" +
                        "§l  - Completar primeiro que os restantes players§r\n" +
                        "§r\n" +
                        "§r\n" +
                        "§b§l  btw não existe fall damage§r§b☠§r\n" +
                        "§r\n" +
                        "§a§m                                                                                 ");

        return messages;
    }

    @Override
    protected HashMap<Integer, Location> addExplanationLocations(Minigame minigame) {
        HashMap<Integer, Location> locations = new HashMap<>();

        locations.put(Timer.secToTicks(34), new Location(Bukkit.getWorld("gravitywars"), -1987, 19, -1991, 0, 0));

        locations.put(Timer.secToTicks(23), new Location(Bukkit.getWorld("gravitywars"), -2038, 17, -1890, 150, 13));

        locations.put(Timer.secToTicks(12), new Location(Bukkit.getWorld("gravitywars"), -2092, 19, -2192, -51, 25));

        locations.put(Timer.secToTicks(0), new Location(Bukkit.getWorld("gravitywars"), -2013, 45, -2168, -35, 21));


        return locations;
    }

}
