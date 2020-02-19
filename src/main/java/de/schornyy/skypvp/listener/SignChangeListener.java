package de.schornyy.skypvp.listener;

import de.schornyy.skypvp.SkyPvPPlugin;
import de.schornyy.skypvp.configs.SignConfig;
import de.schornyy.skypvp.configs.messages.SignMessagesConfig;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;

public class SignChangeListener implements Listener {

    private SignMessagesConfig config = SkyPvPPlugin.getInstance().getMessagesConfig().getSignMessagesConfig();
    private SignConfig signConfig = SkyPvPPlugin.getInstance().getSignConfig();

    @EventHandler
    public void onSignChange(SignChangeEvent e) {
        Player p = e.getPlayer();

        if(p.hasPermission("SkyPvp.sign")) {
            if(e.getLine(0) != null && e.getLine(0).equalsIgnoreCase("SkyPvp")) {
                if(e.getLine(1) != null && e.getLine(2) != null) {
                    int itemID = 0, invSize = 9, stackSize = 1;
                    String[] split = e.getLine(2).split(":");
                    try {
                        itemID = Integer.valueOf(e.getLine(1));
                        invSize = Integer.valueOf(split[0]);
                        stackSize = Integer.valueOf(split[1]);
                    }catch (NumberFormatException ex){
                        p.sendMessage(config.mustEnterNumber);
                    }
                    if(SkyPvPPlugin.getInstance().getItemIDIndicator().storedItemIDs.get(itemID) != null) {
                        for(int i = 0; i < SkyPvPPlugin.getInstance().getSignConfig().storedLines.size();i++) {
                            String line = signConfig.storedLines.get(i);
                            if(line.contains("%ItemID%")) {
                                line = line.replaceAll("%ItemID%", "" + itemID);
                            }
                            if(line.contains("%InvSize%")) {
                                line = line.replaceAll("%InvSize%", "" + invSize);
                            }
                            if(line.contains("%StackSize%")) {
                                line = line.replaceAll("%StackSize%", "" + stackSize);
                            }
                            e.setLine(i, line);
                        }
                    } else {
                        p.sendMessage(config.itemIDdosntExists);
                    }
                } else {
                    p.sendMessage(config.help);
                }
            }
        } else {
            p.sendMessage(config.noPermissions);
        }

    }

}
