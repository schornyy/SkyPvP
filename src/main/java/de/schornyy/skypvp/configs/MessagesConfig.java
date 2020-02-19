package de.schornyy.skypvp.configs;

import de.schornyy.skypvp.SkyPvPPlugin;
import de.schornyy.skypvp.configs.messages.KitMessagesConfig;
import de.schornyy.skypvp.configs.messages.NPCMessagesConfig;
import de.schornyy.skypvp.configs.messages.SignMessagesConfig;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class MessagesConfig {

    private File file;
    private FileConfiguration cfg;
    private NPCMessagesConfig npcMessagesConfig;
    private KitMessagesConfig kitMessagesConfig;
    private SignMessagesConfig signMessagesConfig;

    public String prefix;

    public MessagesConfig() {
        file = new File(SkyPvPPlugin.getInstance().getDataFolder() + "/Configs/Messages.yml");
        cfg = YamlConfiguration.loadConfiguration(file);
        load();
        npcMessagesConfig = new NPCMessagesConfig(this);
        kitMessagesConfig = new KitMessagesConfig(this);
        signMessagesConfig = new SignMessagesConfig(this);
    }

    private void load() {
        if(!getFile().exists()) {
            getCfg().set("Prefix", "&6SkyPvP &f>> ");
            try {
                getCfg().save(getFile());
            }catch (IOException e){}
        }
        prefix = getCfg().getString("Prefix").replaceAll("&", "§");
    }

    public File getFile() {
        return file;
    }

    public FileConfiguration getCfg() {
        return cfg;
    }

    public NPCMessagesConfig getNpcMessagesConfig() {
        return npcMessagesConfig;
    }

    public KitMessagesConfig getKitMessagesConfig() {
        return kitMessagesConfig;
    }

    public SignMessagesConfig getSignMessagesConfig() {
        return signMessagesConfig;
    }
}

