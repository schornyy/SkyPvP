package de.schornyy.skypvp.listener;

import de.schornyy.skypvp.SkyPvPPlugin;
import de.schornyy.skypvp.player.SkyPvpPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuitListener implements Listener {

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        Player p = e.getPlayer();
        SkyPvpPlayer player = SkyPvPPlugin.getPlugin(SkyPvPPlugin.class).getPlayerManager().getSkyPvpPlayerByPlayer(p);
        player.save();
    }
}
