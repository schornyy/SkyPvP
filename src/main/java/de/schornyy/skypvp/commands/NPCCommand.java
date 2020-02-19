package de.schornyy.skypvp.commands;

import de.schornyy.skypvp.SkyPvPPlugin;
import de.schornyy.skypvp.configs.messages.NPCMessagesConfig;
import de.schornyy.skypvp.npc.NPC;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class NPCCommand implements CommandExecutor, TabCompleter {

    /*
        0       1           2
    0   1       2
    npc create <Name>
    npc delete <Name>
    npc <Name> changeName <Name>
    npc <Name> changeLocation
    npc <Name> changeEntityType <Type>
     */

    private NPCMessagesConfig config = SkyPvPPlugin.getInstance().getMessagesConfig().getNpcMessagesConfig();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player p = (Player)sender;

        if(p.hasPermission("SkyPvp.npc.edit")) {
            switch (args.length) {
                case 2:
                    if(args[0].equalsIgnoreCase("create")) {
                        String name = args[1].replaceAll("&", "§");
                        if(SkyPvPPlugin.getInstance().getNpcManager().getNPCbyName(name) != null) {
                            p.sendMessage(SkyPvPPlugin.getInstance().getMessagesConfig().getNpcMessagesConfig().npc_exists);
                            return true;
                        }
                        NPC npc = new NPC(UUID.randomUUID().toString());
                        npc.create(p.getLocation(), args[1].replaceAll("_", " "));
                        p.sendMessage(config.npc_created);
                    } else if (args[0].equalsIgnoreCase("delete")) {
                        int id = Integer.valueOf(args[1]);
                        if(SkyPvPPlugin.getInstance().getNpcManager().getNPCgetByID(id) == null) {
                            p.sendMessage(config.npc_dosnt_exists);
                            return true;
                        }
                        NPC npc = SkyPvPPlugin.getInstance().getNpcManager().getNPCgetByID(id);
                        npc.delete();
                        p.sendMessage(config.npc_deleted);
                    } else if(args[1].equalsIgnoreCase("changeLocation")) {
                        NPC npc = SkyPvPPlugin.getInstance().getNpcManager().getNPCgetByID(Integer.valueOf(args[0]));
                        npc.changeLocation(p.getLocation());
                    }
                    break;
                case 3:
                    if(args[1].equalsIgnoreCase("changeName")) {
                        NPC npc = SkyPvPPlugin.getInstance().getNpcManager().getNPCgetByID(Integer.valueOf(args[0]));
                        npc.changeName(args[2].replaceAll("_", " "));
                        p.sendMessage(config.npc_changeName);
                    } else if(args[1].equalsIgnoreCase("changeEntityType")) {
                        NPC npc = SkyPvPPlugin.getInstance().getNpcManager().getNPCgetByID(Integer.valueOf(args[0]));
                        if(EntityType.fromName(args[2]) == null) {
                            p.sendMessage(config.npc_entityType_dont_exists);
                            return true;
                        }
                        npc.changeEntityType(args[2]);
                        p.sendMessage(config.npc_changeEntityType);
                    }
            }
        }
        return false;
    }

        /*
        0       1           2
    0   1       2
    npc create <Name>
    npc delete <Name>
    npc <Name> changeName <Name>
    npc <Name> changeLocation
    npc <Name> changeEntityType <Type>
     */

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> tabs = new ArrayList<>();

        if(sender.hasPermission("Skypvp.npc.edit")) {
            switch (args.length) {
                case 1:
                    tabs.add("Create");
                    SkyPvPPlugin.getInstance().getNpcManager().getStoredNPCs().values().forEach(ids -> {
                        tabs.add(ChatColor.stripColor(String.valueOf(ids.getId())));
                    });
                    break;
                case 2:
                    tabs.add("changeName");
                    tabs.add("changeLocation");
                    tabs.add("changeEntityType");
                    break;
                case 3:
                    if(args[1].equalsIgnoreCase("changeEntityType")) {
                        for(EntityType entityType : EntityType.values()) {
                            tabs.add(entityType.name());
                        }
                    }
                    break;
            }
        }

        return tabs;
    }
}
