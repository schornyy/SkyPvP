package de.schornyy.skypvp.configs.messages;

import de.schornyy.skypvp.configs.MessagesConfig;
import org.bukkit.configuration.file.FileConfiguration;

import java.io.File;
import java.io.IOException;

public class KitMessagesConfig {

    private File file;
    private FileConfiguration cfg;

    public String prefix, noPermissions, alreadyExists, created, dosntExists, deleted;

    public KitMessagesConfig(MessagesConfig messagesConfig) {
        file = messagesConfig.getFile();
        cfg = messagesConfig.getCfg();
        load();
    }

    private void load() {
        if(!getCfg().isSet("Kit.")) {
            getCfg().set("Kit.Prefix", "&6Kit &f>> ");
            getCfg().set("Kit.no_Permissions", "&cDu hast keine Rechte dazu!");
            getCfg().set("Kit.alreadyExists", "&cEin Kit mit dem namen existiert bereits!");
            getCfg().set("Kit.created", "&aDu hast ein Kit mit dem Namen &6%KitName% &aerstellt");
            getCfg().set("Kit.dosntExists", "&cEs existiert kein Kit mit dem Namen!");
            getCfg().set("Kit.deleted", "&cDu hast das Kit mit dem Namen &6%KItNamen% &cgelöscht!");
            try {
                getCfg().save(getFile());
            }catch (IOException e){}
        }
        prefix = getCfg().getString("Kit.Prefix").replaceAll("&", "§");
        created = prefix + getCfg().getString("Kit.dosntExists").replaceAll("&", "§");
        created = prefix + getCfg().getString("Kit.created").replaceAll("&", "§");
        noPermissions = prefix + getCfg().getString("Kit.no_Permissions").replaceAll("&", "§");
        alreadyExists = prefix + getCfg().getString("Kit.alreadyExists").replaceAll("&", "§");
    }

    public FileConfiguration getCfg() {
        return cfg;
    }

    public File getFile() {
        return file;
    }
}
