package de.schornyy.skypvp.listener;

import de.schornyy.skypvp.SkyPvPPlugin;
import de.schornyy.skypvp.commands.KitCommand;
import de.schornyy.skypvp.kit.Kit;
import de.schornyy.skypvp.kit.KitManager;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class InventoryClickListener implements Listener {

    @EventHandler
    public void onInvClick(InventoryClickEvent e) {
        Player p = (Player)e.getWhoClicked();

        if(p.getOpenInventory().getTopInventory() != null) {
            for(Kit kit : KitManager.storedKits.values()) {
                if(kit == null) break;
                if(e.getView().getTitle().equalsIgnoreCase(kit.getInventoryName().replaceAll("&", "§"))) {
                    if(!KitCommand.storedKitEdit.contains(p)) {
                        e.setCancelled(true);
                    }
                }
            }

            if(e.getView().getTitle().equalsIgnoreCase(SkyPvPPlugin.getInstance().getKitSelectorInventory().getName()+ "§f: §6" + p.getName())) {
                e.setCancelled(true);

                if(e.getCurrentItem() != null || e.getCurrentItem().getType() != Material.AIR) {
                    if(e.getCurrentItem().getItemMeta() != null) {
                        if(e.isRightClick()) {
                            Kit kit = SkyPvPPlugin.getInstance().getKitManager().getKitByDisplayname(e.getCurrentItem().getItemMeta().getDisplayName());
                            p.openInventory(kit.getInventory());
                        }
                    }
                }
            }

        }
    }
}
