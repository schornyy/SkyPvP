package de.schornyy.skypvp.listener;

import de.schornyy.skypvp.SkyPvPPlugin;
import de.schornyy.skypvp.player.SkyPvpPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class PlayerDeathListener implements Listener {

    @EventHandler
    public void onDeath(PlayerDeathEvent e) {
        Player p = e.getEntity();
        SkyPvpPlayer skyPvpPlayer = SkyPvPPlugin.getInstance().getPlayerManager().getSkyPvpPlayerByPlayer(p);
        skyPvpPlayer.setCoins(skyPvpPlayer.getCoins() - SkyPvPPlugin.getInstance().getSettingsConfig().coins_remove_by_death);
        if(p.getKiller() != null) {
            SkyPvpPlayer killer = SkyPvPPlugin.getInstance().getPlayerManager().getSkyPvpPlayerByPlayer(p.getKiller());
            killer.increaseCoins(SkyPvPPlugin.getInstance().getSettingsConfig().coins_per_kill);
        }
    }
}
