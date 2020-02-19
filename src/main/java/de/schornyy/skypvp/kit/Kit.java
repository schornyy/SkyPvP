package de.schornyy.skypvp.kit;

import de.schornyy.skypvp.SkyPvPPlugin;
import de.schornyy.skypvp.player.SkyPvpPlayer;
import de.schornyy.skypvp.utils.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import javax.swing.plaf.SplitPaneUI;
import java.awt.event.ItemEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Kit {

    private int costs, inventorySlots;
    private Inventory inventory;
    private boolean buyable;
    private String permissions, kitName, inventoryName;
    private ItemStack icon;

    private File file;
    private FileConfiguration cfg;

    public Kit(String kitName) {
        setKitName(kitName.replaceAll("&", "§"));
        file = new File(SkyPvPPlugin.getPlugin(SkyPvPPlugin.class).getDataFolder() + "/Kits/" + kitName + ".yml");
        cfg = YamlConfiguration.loadConfiguration(file);
    }

    public void create() {
        setInventoryName("&bEdit in Config");
        setInventorySlots(9);
        getCfg().set("Inventory.Name", "&bEdit in Config");
        getCfg().set("Inventory.Slots", 9);
        getCfg().set("KitName", getKitName().replaceAll("§", "&"));
        setCosts(0);
        getCfg().set("Costs", 0);
        setBuyable(false);
        getCfg().set("Buyable", false);
        setIcon(new ItemBuilder(Material.STICK).setName("§bDeveloped by Schornyy").Build());
        getCfg().set("Icon", getIcon());
        setPermissions("SkyPvp.kit." + getKitName());
        Inventory inv = Bukkit.createInventory(null, getInventorySlots(), getInventoryName().replaceAll("&", "§"));
        setInventory(inv);
        KitManager.storedKits.put(getKitName(), this);
        try {
            getCfg().save(getFile());
        }catch (IOException e){}
    }

    public void load() {
        setKitName(getCfg().getString("KitName"));
        setCosts(getCfg().getInt("Costs"));
        String invName = getCfg().getString("Inventory.Name");
        setInventoryName(invName);
        int slots = getCfg().getInt("Inventory.Slots");
        Inventory inv = Bukkit.createInventory(null, slots, invName.replaceAll("&", "§"));
        if(getCfg().isSet("Inventory.Item.")) {
            for(int i = 0; i< inv.getSize();i++) {
                if(getCfg().getItemStack("Inventory.Item." + i) != null) {
                    inv.setItem(i, getCfg().getItemStack("Inventory.Item." + i));
                }
            }
        }
        setInventory(inv);
        setIcon(getCfg().getItemStack("Icon"));
        setBuyable(getCfg().getBoolean("Buyable"));
        setPermissions("SkyPvp.Kits." + getKitName());
        KitManager.storedKits.put(getKitName(), this);
    }

    public void save() {
        for(int i = 0; i<getInventory().getSize();i++) {
            if(getInventory().getItem(i) != null) {
                getCfg().set("Inventory.Item." + i, getInventory().getItem(i));
            }
        }

        try {
            getCfg().save(getFile());
        }catch (IOException e){}
    }

    public void delete() {
        KitManager.storedKits.remove(getKitName());
        getFile().delete();
    }

    public void setInventoryName(String inventoryName) {
        this.inventoryName = inventoryName;
    }

    public void setInventorySlots(int inventorySlots) {
        this.inventorySlots = inventorySlots;
    }

    public int getInventorySlots() {
        return inventorySlots;
    }

    public String getInventoryName() {
        return inventoryName;
    }

    public boolean isBuyable() {
        return buyable;
    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }

    private void setKitName(String kitName) {
        this.kitName = kitName;
    }

    private void setPermissions(String permissions) {
        this.permissions = permissions;
    }

    public FileConfiguration getCfg() {
        return cfg;
    }

    public File getFile() {
        return file;
    }

    public String getPermissions() {
        return permissions;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public int getCosts() {
        return costs;
    }


    public ItemStack getIcon() {
        return icon;
    }

    public ItemStack getIcontoPlayer(SkyPvpPlayer player) {
        ItemStack item = getIcon();
        ItemMeta itemMeta = item.getItemMeta();

        List<String> lore = new ArrayList<>();
        for(String l : SkyPvPPlugin.getInstance().getKitConfig().kitIconLore) {
            if(l == null) continue;
            String lo = l.replaceAll("&", "§");
            if(lo.contains("%kitName%")) {
                lo = lo.replaceAll("%kitName%", getKitName());
            }
            if(lo.contains("%kitCosts%")) {
                if(isBuyable()) {
                    lo = lo.replaceAll("%kitCosts%", ""+ getCosts());
                }
            }
            lore.add(lo);
        }
        if(player.getStoredPlayerKits().contains(getKitName()) || player.getPlayer().hasPermission(getPermissions())) {
            lore.add(SkyPvPPlugin.getInstance().getKitConfig().playerHasKit);
        } else {
            lore.add(SkyPvPPlugin.getInstance().getKitConfig().playerdosntHaveKIt);
        }
        itemMeta.setLore(lore);
        item.setItemMeta(itemMeta);
        return item;
    }

    public String getKitName() {
        return kitName;
    }

    public void setBuyable(boolean buyable) {
        this.buyable = buyable;
    }

    public void setCosts(int costs) {
        this.costs = costs;
    }

    public void setIcon(ItemStack icon) {
        this.icon = icon;
    }

}
