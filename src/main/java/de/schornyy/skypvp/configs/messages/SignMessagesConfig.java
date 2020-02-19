package de.schornyy.skypvp.configs.messages;

import de.schornyy.skypvp.configs.MessagesConfig;
import org.bukkit.configuration.file.FileConfiguration;

import java.io.File;
import java.io.IOException;

public class SignMessagesConfig {

    private File file;
    private FileConfiguration cfg;

    public String prefix, noPermissions;

    public SignMessagesConfig(MessagesConfig messagesConfig) {
        file = messagesConfig.getFile();
        cfg = messagesConfig.getCfg();
        load();
    }

    private void load() {
        if(!getCfg().isSet("Sign.")) {
            getCfg().set("Sign.Prefix", "&bSign &f>> ");
            getCfg().set("Sign.no_Permissions", "&cDu hast keine Rechte dazu!");
            try {
                getCfg().save(getFile());
            }catch (IOException e){}
        }

        prefix = getCfg().getString("Sign.Prefix").replaceAll("&", "§");
        noPermissions = prefix + getCfg().getString("Sign.no_Permissions").replaceAll("&", "§");
    }

    public File getFile() {
        return file;
    }

    public FileConfiguration getCfg() {
        return cfg;
    }
}
