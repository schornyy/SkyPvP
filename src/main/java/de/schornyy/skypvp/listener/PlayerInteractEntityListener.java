package de.schornyy.skypvp.listener;

import de.schornyy.skypvp.SkyPvPPlugin;
import de.schornyy.skypvp.player.SkyPvpPlayer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class PlayerInteractEntityListener implements Listener {

    @EventHandler
    public void onInteractEntity(PlayerInteractEntityEvent e){
        Player p = e.getPlayer();
        SkyPvpPlayer skyPvpPlayer = SkyPvPPlugin.getInstance().getPlayerManager().getSkyPvpPlayerByPlayer(p);

        if(e.getRightClicked().getCustomName() != null) {
            if(SkyPvPPlugin.getInstance().getNpcManager().getNPCbyName(e.getRightClicked().getCustomName())!= null) {
                e.setCancelled(true);
                Inventory inv = Bukkit.createInventory(null, SkyPvPPlugin
                        .getInstance()
                        .getKitSelectorInventory()
                        .getSlots(), SkyPvPPlugin
                        .getInstance()
                        .getKitSelectorInventory()
                        .getName() + "§f: §6" + p.getName());
                for(int i = 0;i < inv.getSize();i++) {
                    if(SkyPvPPlugin.getInstance().getKitSelectorInventory().getInventory().getItem(i) != null) {
                        inv.setItem(i, SkyPvPPlugin.getInstance()
                                .getKitManager()
                                .getKitByDisplayname(SkyPvPPlugin.getInstance()
                                        .getKitSelectorInventory()
                                        .getInventory()
                                        .getItem(i)
                                        .getItemMeta()
                                        .getDisplayName())
                                .getIcontoPlayer(skyPvpPlayer));
                    }
                }
                p.openInventory(inv);
            }
        }

    }
}
