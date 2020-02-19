package de.schornyy.skypvp.configs;

import de.schornyy.skypvp.SkyPvPPlugin;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class KitConfig {

    private File file;
    private FileConfiguration cfg;

    public List<String> kitIconLore;
    public String playerHasKit, playerdosntHaveKIt;

    public KitConfig() {
        file = new File(SkyPvPPlugin.getInstance().getDataFolder() + "/Configs/KitConfig.yml");
        cfg = YamlConfiguration.loadConfiguration(file);
        load();
    }

    private void load() {
        if(!getFile().exists()) {
            getCfg().set("playerhasKit", "&aDu besitzt das Kit");
            getCfg().set("playerdosntHaveKIt", "&cDu besitzt das Kit nicht!");

            List<String> phkl = new ArrayList<>();
            phkl.add("&fName: &6%kitName%");
            phkl.add("&fKostet: &6%kitCosts%");
            phkl.add("&fRechtsklicken für Vorschau");
            getCfg().set("kitIconLore", phkl);

            try {
                getCfg().save(getFile());
            }catch (IOException e){}
        }
        kitIconLore = getCfg().getStringList("kitIconLore");
        playerHasKit = getCfg().getString("playerhasKit").replaceAll("&", "§");
        playerdosntHaveKIt = getCfg().getString("playerdosntHaveKIt").replaceAll("&", "§");
    }

    public File getFile() {
        return file;
    }

    public FileConfiguration getCfg() {
        return cfg;
    }


}
