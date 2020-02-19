package de.schornyy.skypvp.listener;

import de.schornyy.skypvp.SkyPvPPlugin;
import de.schornyy.skypvp.configs.SignConfig;
import de.schornyy.skypvp.item.ItemIDIndicator;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class PlayerInteractListener implements Listener {

    private SignConfig signConfig = SkyPvPPlugin.getInstance().getSignConfig();
    private ItemIDIndicator itemIDIndicator = SkyPvPPlugin.getInstance().getItemIDIndicator();

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        Player p = e.getPlayer();

        if(e.getAction() == Action.RIGHT_CLICK_BLOCK && e.getClickedBlock().getType().name().contains("SIGN")) {
            Sign sign = (Sign) e.getClickedBlock().getState();

            if(sign.getLine(0) != null && sign.getLine(0).equalsIgnoreCase(signConfig.storedLines.get(0))) {
                String[] itemIDline = sign.getLine(1).split(": ");
                int itemID = Integer.valueOf(ChatColor.stripColor(itemIDline[1]));

                String[] invstackLine = sign.getLine(2).split(":");
                int invSize = Integer.valueOf(ChatColor.stripColor(invstackLine[0]));
                int stackSize = Integer.valueOf(ChatColor.stripColor(invstackLine[1]));
                ItemStack item = new ItemStack(itemIDIndicator.storedItemIDs.get(itemID), stackSize);

                Inventory inventory = Bukkit.createInventory(null, invSize, signConfig.inventoryName);

                for(int i = 0; i < invSize;i++) {
                    inventory.setItem(i, item);
                }

                p.openInventory(inventory);
            }
        }

    }
}
