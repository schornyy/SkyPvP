package de.schornyy.skypvp.configs.messages;

import de.schornyy.skypvp.configs.MessagesConfig;
import org.bukkit.configuration.file.FileConfiguration;

import java.io.File;
import java.io.IOException;

public class NPCMessagesConfig {

    private File file;
    private FileConfiguration cfg;

    public String prefix, player_dont_have_Permissions, npc_created
            , npc_exists, npc_deleted, npc_dosnt_exists
            , npc_changeName, npc_changeEntityType, npc_entityType_dont_exists;

    public NPCMessagesConfig(MessagesConfig messagesConfig) {
        file = messagesConfig.getFile();
        cfg = messagesConfig.getCfg();
        load();
    }

    private void load() {
        if(!getFile().exists() || getCfg().isSet("NPC.") == false) {
            getCfg().set("NPC.Prefix", "&6NPC &f>>");
            getCfg().set("NPC.player_dont_have_Permissions", "&cDu hast keine Rechte dafür!");
            getCfg().set("NPC.created", "&aDu hast ein NPC erstellt!");
            getCfg().set("NPC.deleted", "&cDu hast ein NPC gelöscht!");
            getCfg().set("NPC.exists", "&cEin NPC mit dem Namen existiert bereits");
            getCfg().set("NPC.dont_exists", "&cDer NPC existiert nicht!");
            getCfg().set("NPC.changeName", "&aDu hast den Namen geändert!");
            getCfg().set("NPC.changeEntityType", "&aDu hast das EntityType geändert!");
            getCfg().set("NPC.entityType_dont_exists", "&cDas EntityType existiert nicht!");
            try {
                getCfg().save(getFile());
            }catch (IOException e){}
        }
        prefix = getCfg().getString("NPC.Prefix").replaceAll("&", "§");
        npc_entityType_dont_exists = prefix + getCfg().getString("NPC.entityType_dont_exists").replaceAll("&", "§");
        npc_changeEntityType = prefix + getCfg().getString("NPC.changeEntityType").replaceAll("&", "§");
        npc_changeName = prefix + getCfg().getString("NPC.changeName").replaceAll("&", "§");
        npc_dosnt_exists = prefix + getCfg().getString("NPC.dont_exists").replaceAll("&", "§");
        npc_created = prefix + getCfg().getString("NPC.created").replaceAll("&", "§");
        npc_exists = prefix + getCfg().getString("NPC.exists").replaceAll("&", "§");
        npc_deleted = prefix + getCfg().getString("NPC.deleted").replaceAll("&", "§");
        player_dont_have_Permissions = prefix + getCfg().getString("NPC.player_dont_have_Permissions").replaceAll("&", "§");
    }

    public File getFile() {
        return file;
    }

    public FileConfiguration getCfg() {
        return cfg;
    }
}
