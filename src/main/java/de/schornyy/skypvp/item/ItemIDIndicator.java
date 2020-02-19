package de.schornyy.skypvp.item;

import de.schornyy.skypvp.SkyPvPPlugin;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class ItemIDIndicator {

    public HashMap<Integer, Material> storedItemIDs = new HashMap<>();

    private File file;
    private FileConfiguration cfg;

    public ItemIDIndicator() {
        file = new File(SkyPvPPlugin.getInstance().getDataFolder() + "/ItemIDs.yml");
        cfg = YamlConfiguration.loadConfiguration(file);
        load();
    }

    private void load() {
        if(!getFile().exists()) {
            int i = 0;
            for(Material material : Material.values()) {
                if(material == null)continue;
                getCfg().set("Item." + i, material.name());
                i++;
            }

            try {
                getCfg().save(getFile());
            }catch (IOException e){}
        }

        for(String id : getCfg().getConfigurationSection("Item.").getKeys(false)) {
            if(id == null) break;
            storedItemIDs.put(Integer.valueOf(id), Material.getMaterial(getCfg().getString("Item." + id)));
        }
    }

    public FileConfiguration getCfg() {
        return cfg;
    }

    public File getFile() {
        return file;
    }
}
