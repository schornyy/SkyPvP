package de.schornyy.skypvp.configs;

import de.schornyy.skypvp.SkyPvPPlugin;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SignConfig {

    private File file;
    private FileConfiguration cfg;

    public List<String> storedLines;
    public String inventoryName;

    public SignConfig() {
        file = new File(SkyPvPPlugin.getInstance().getDataFolder() + "/SignConfig.yml");
        cfg = YamlConfiguration.loadConfiguration(file);
        storedLines = new ArrayList<>();
        load();
    }

    private void load() {
        if(!getFile().exists()) {
            getCfg().set("Inventory.Name", "&bEdit in SignConfig");

            ArrayList<String> sl = new ArrayList<>();
            sl.add("&f[&bSkyPvP&f]");
            sl.add("&fItemID: &6%ItemID%");
            sl.add("&6%InvSize%&f:&e%StackSize%");
            getCfg().set("SignLayout", sl);
            try {
                getCfg().save(getFile());
            }catch (IOException e){}
        }

        for(String s : getCfg().getStringList("SignLayout")) {
            if(s == null) break;
            storedLines.add(s.replaceAll("&", "§"));
        }
        inventoryName = getCfg().getString("Inventory.Name").replaceAll("&", "§");
    }

    public File getFile() {
        return file;
    }

    public FileConfiguration getCfg() {
        return cfg;
    }
}
