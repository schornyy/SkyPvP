package de.schornyy.skypvp.commands;

import de.schornyy.skypvp.SkyPvPPlugin;
import de.schornyy.skypvp.configs.messages.KitMessagesConfig;
import de.schornyy.skypvp.kit.Kit;
import de.schornyy.skypvp.kit.KitManager;
import de.schornyy.skypvp.player.SkyPvpPlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class KitCommand implements CommandExecutor, TabCompleter {

    /*
    kit edit <Name>
    kit create <Name>
    kit delete <Name>
     */

    public static ArrayList<Player> storedKitEdit = new ArrayList<>();
    private KitMessagesConfig config = SkyPvPPlugin.getInstance().getMessagesConfig().getKitMessagesConfig();
    private KitManager kitManager = SkyPvPPlugin.getInstance().getKitManager();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = (Player)sender;
        SkyPvpPlayer skyPvpPlayer = SkyPvPPlugin.getInstance().getPlayerManager().getSkyPvpPlayerByPlayer(player);

        if(player.hasPermission("kit.edit")) {
            switch (args.length) {
                case 2:
                    if(args[0].equalsIgnoreCase("create")) {
                        if(kitManager.getKitByName(args[1]) != null) {
                            player.sendMessage(config.alreadyExists);
                            return true;
                        }
                        Kit kit = new Kit(args[1]);
                        kit.create();
                        player.sendMessage(config.created.replaceAll("%KitName%", args[1]));
                    } else if(args[0].equalsIgnoreCase("delete")) {
                        if(kitManager.getKitByName(args[1]) == null) {
                            player.sendMessage(config.dosntExists);
                            return true;
                        }
                        Kit kit = kitManager.getKitByName(args[1]);
                        kit.delete();
                        player.sendMessage(config.deleted.replaceAll("%KitName%", args[1]));
                    } else if(args[0].equalsIgnoreCase("edit")) {
                        if(kitManager.getKitByName(args[1]) == null) {
                            player.sendMessage(config.dosntExists);
                            return true;
                        }
                        storedKitEdit.add(player);
                        player.openInventory(kitManager.getKitByName(args[1]).getInventory());
                    }
                    break;
            }
        } else {
            player.sendMessage(config.noPermissions);
        }

        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> tabs = new ArrayList<>();

        if(sender.hasPermission("kit.edit")) {
            switch (args.length) {
                case 1:
                    tabs.add("create");
                    tabs.add("edit");
                    tabs.add("delete");
                    break;
                case 2:
                    if(args[0].equalsIgnoreCase("create")) {
                        tabs.add("<Name>");
                    } else  if(args[0].equalsIgnoreCase("edit")) {
                        kitManager.getStoredKits().values().forEach(kits -> {
                            tabs.add(kits.getKitName());
                        });
                    } else if(args[0].equalsIgnoreCase("delete")) {
                        kitManager.getStoredKits().values().forEach(kits -> {
                            tabs.add(kits.getKitName());
                        });
                    }
                    break;
            }
        }

        return tabs;
    }
}
