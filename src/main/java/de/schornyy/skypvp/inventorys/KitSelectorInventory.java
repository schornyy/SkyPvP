package de.schornyy.skypvp.inventorys;

import de.schornyy.skypvp.SkyPvPPlugin;
import de.schornyy.skypvp.kit.Kit;
import de.schornyy.skypvp.kit.KitManager;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.Inventory;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.IOException;

public class KitSelectorInventory {

    private Inventory inventory;
    private String name;
    private int slots;

    private File file;
    private FileConfiguration cfg;

    public KitSelectorInventory() {
        file = new File(SkyPvPPlugin.getInstance().getDataFolder() + "/Inventorys/KitSelectorInventory.yml");
        cfg = YamlConfiguration.loadConfiguration(file);
        load();
    }

    private void load() {
        if(!getFile().exists()) {
            getCfg().set("Slots", 9);
            getCfg().set("InventoryName", "&eKits");
            getCfg().set("KitSlot.1.KitName", "Default");
            try {
                getCfg().save(getFile());
            }catch (IOException e){}
        }
        setName(getCfg().getString("InventoryName").replaceAll("&", "§"));
        setSlots(getCfg().getInt("Slots"));
        Inventory inv = Bukkit.createInventory(null, getCfg().getInt("Slots"), getCfg().getString("InventoryName").replaceAll("&", "§"));
        if(getCfg().isSet("KitSlot.")) {
            for(String kitNames : getCfg().getConfigurationSection("KitSlot.").getKeys(false)) {
                if(kitNames == null) break;
                inv.setItem(Integer.valueOf(kitNames), SkyPvPPlugin.getInstance().getKitManager().getKitByName(getCfg().getString("KitSlot." + kitNames + ".KitName")).getIcon());
            }
        }
        setInventory(inv);
    }

    public File getFile() {
        return file;
    }

    public FileConfiguration getCfg() {
        return cfg;
    }

    public String getName() {
        return name;
    }

    private void setName(String name) {
        this.name = name;
    }

    private void setSlots(int slots) {
        this.slots = slots;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public int getSlots() {
        return slots;
    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }
}
