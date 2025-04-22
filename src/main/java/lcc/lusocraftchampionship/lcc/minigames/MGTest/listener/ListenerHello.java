package lcc.lusocraftchampionship.minigame.MGTest.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ListenerHello implements Listener {
  @EventHandler
  public void onChat(AsyncPlayerChatEvent event) {
    System.out.println("Hello " + event.getPlayer().getName() + "!");
  }
}
