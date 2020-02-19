package de.schornyy.skypvp.configs.messages;

import de.schornyy.skypvp.configs.MessagesConfig;
import org.bukkit.configuration.file.FileConfiguration;

import java.io.File;
import java.io.IOException;

public class SignMessagesConfig {

    private File file;
    private FileConfiguration cfg;

    public String prefix, noPermissions, mustEnterNumber, help, itemIDdosntExists;

    public SignMessagesConfig(MessagesConfig messagesConfig) {
        file = messagesConfig.getFile();
        cfg = messagesConfig.getCfg();
        load();
    }

    private void load() {
        if(!getCfg().isSet("Sign.")) {
            getCfg().set("Sign.Prefix", "&bSign &f>> ");
            getCfg().set("Sign.no_Permissions", "&cDu hast keine Rechte dazu!");
            getCfg().set("Sign.must_Enter_Number", "&cDu musst eine Zahl angeben!");
            getCfg().set("Sign.help", "&cDu musst eine ItemID in line 2 angeben in Line 3 <InventarSlots>:<ItemsProStack>");
            getCfg().set("Sign.itemID_dosnt_exists", "&cDie ItemID existiert nicht!");
            try {
                getCfg().save(getFile());
            }catch (IOException e){}
        }

        prefix = getCfg().getString("Sign.Prefix").replaceAll("&", "§");
        noPermissions = prefix + getCfg().getString("Sign.no_Permissions").replaceAll("&", "§");
        mustEnterNumber = prefix + getCfg().getString("Sign.must_Enter_Number").replaceAll("&", "§");
        help = prefix + getCfg().getString("Sign.help").replaceAll("&", "§");
        itemIDdosntExists = help = prefix + getCfg().getString("Sign.itemID_dosnt_exists").replaceAll("&", "§");
    }

    public File getFile() {
        return file;
    }

    public FileConfiguration getCfg() {
        return cfg;
    }
}
