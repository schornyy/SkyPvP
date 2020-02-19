package de.schornyy.skypvp.kit;

import de.schornyy.skypvp.SkyPvPPlugin;
import org.bukkit.Bukkit;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.util.HashMap;

public class KitManager {

    public static HashMap<String, Kit> storedKits = new HashMap<>();

    public KitManager() {
        loadDefaultKit();
        loadAllKits();
    }

    private void loadAllKits() {
        File file = new File(SkyPvPPlugin.getInstance().getDataFolder() + "/Kits/");

        if(file.listFiles() != null) {
            for(File files : file.listFiles()) {
                if(files == null) continue;
                String kitName = files.getName().replaceAll(".yml", "");
                Kit kit = new Kit(kitName);
                kit.load();
                Bukkit.getConsoleSender().sendMessage("#" + kit.getKitName());
            }
        }
    }

    private void loadDefaultKit() {
        File file = new File(SkyPvPPlugin.getInstance().getDataFolder() + "/Kits/");
        if(file.listFiles() == null) {
            Kit kit = new Kit("Default");
            kit.create();
        }
    }

    public Kit getKitByItem(ItemStack item) {
        for(Kit kit : storedKits.values()) {
            if(kit == null)continue;
            if(kit.getIcon() == item) {
                return kit;
            }
        }
        return null;
    }

    public Kit getKitByName(String kitName) {
        for(Kit kit : storedKits.values()) {
            if(kit == null)break;
            if(kit.getKitName().equalsIgnoreCase(kitName)) {
                return kit;
            }
        }
        return null;
    }

    public Kit getKitByDisplayname(String displayname){
        for(Kit kit : storedKits.values()) {
            if(kit == null) continue;
            if(kit.getIcon().getItemMeta().getDisplayName().equalsIgnoreCase(displayname)) {
                return kit;
            }
        }
        return null;
    }

    public void saveAllKits() {
        for(Kit kit : storedKits.values()) {
            if(kit == null) continue;
            kit.save();
        }
    }

    public HashMap<String, Kit> getStoredKits() {
        return storedKits;
    }
}
