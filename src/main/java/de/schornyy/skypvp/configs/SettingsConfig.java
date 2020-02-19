package de.schornyy.skypvp.configs;

import de.schornyy.skypvp.SkyPvPPlugin;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.Inventory;

import java.io.File;
import java.io.IOException;

public class SettingsConfig {

    private File file;
    private FileConfiguration cfg;

    public boolean showKitVorschau;
    private Inventory kitWahl;
    public int coins_per_kill,coins_remove_by_death;

    public SettingsConfig() {
        file = new File(SkyPvPPlugin.getInstance().getDataFolder() + "/Configs/Settings.yml");
        cfg = YamlConfiguration.loadConfiguration(file);
        load();
    }

    private void load() {
        if(!getFile().exists()) {
            getCfg().set("CoinsPerKill", 10);
            getCfg().set("CoinsRemoveByDeath", 0);
            getCfg().set("showKitVorschau", true);
            getCfg().set("Inventorys.KitWahl.Name", "&aKit wahl");
            getCfg().set("Inventorys.KitWahl.Slots", 18);
            try {
                getCfg().save(getFile());
            }catch (IOException e){}
        }
        coins_per_kill = getCfg().getInt("CoinsPerKill");
        coins_remove_by_death = getCfg().getInt("CoinsRemoveByDeath");
        showKitVorschau = getCfg().getBoolean("showKitVorschau");
        kitWahl = Bukkit.createInventory(null, getCfg().getInt("Inventorys.KitWahl.Slots"), getCfg().getString("Inventorys.KitWahl.Name").replaceAll("&", "§"));
    }

    public File getFile() {
        return file;
    }

    public FileConfiguration getCfg() {
        return cfg;
    }
}
