package de.schornyy.skypvp.listener;

import de.schornyy.skypvp.SkyPvPPlugin;
import de.schornyy.skypvp.npc.NPC;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public class EntityDamageListener implements Listener {

    @EventHandler
    public void onEDMG(EntityDamageEvent e) {

        if(e.getEntity().getCustomName() != null) {
            for(NPC npc : SkyPvPPlugin.getInstance().getNpcManager().getStoredNPCs().values()) {
                if(npc == null) continue;
                if(e.getEntity().getCustomName().equalsIgnoreCase(npc.getName())) {
                    e.setCancelled(true);
                }
            }
        }

    }

}
