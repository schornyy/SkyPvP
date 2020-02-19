package de.schornyy.skypvp.listener;

import de.schornyy.skypvp.SkyPvPPlugin;
import de.schornyy.skypvp.configs.messages.SignMessagesConfig;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;

public class SignChangeListener implements Listener {

    private SignMessagesConfig config = SkyPvPPlugin.getInstance().getMessagesConfig().getSignMessagesConfig();

    @EventHandler
    public void onSignChange(SignChangeEvent e) {
        Player p = e.getPlayer();

        if(p.hasPermission("SkyPvp.sign")) {
            
        } else {
            p.sendMessage(config.noPermissions);
        }

    }

}
