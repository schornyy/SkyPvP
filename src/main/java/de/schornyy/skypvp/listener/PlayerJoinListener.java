package de.schornyy.skypvp.listener;

import de.schornyy.skypvp.player.SkyPvpPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent e){
        Player p = e.getPlayer();
        SkyPvpPlayer player = new SkyPvpPlayer(p);
        player.load();

    }
}
