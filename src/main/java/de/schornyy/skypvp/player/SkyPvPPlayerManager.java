package de.schornyy.skypvp.player;

import de.schornyy.skypvp.SkyPvPPlugin;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class SkyPvPPlayerManager {

    private HashMap<Player, SkyPvpPlayer> storedSkyPvpPlayer;

    public SkyPvPPlayerManager() {
        storedSkyPvpPlayer = new HashMap<>();
        startTask();
        loadOnlinePlayers();
    }

    private void loadOnlinePlayers() {
        for(Player all : Bukkit.getOnlinePlayers()) {
            if(all == null) break;
            SkyPvpPlayer player = new SkyPvpPlayer(all);
            player.load();
        }
    }

    public void saveOnlinePlayers() {
        for(SkyPvpPlayer player : storedSkyPvpPlayer.values()) {
            if(player == null) break;
            player.save();
        }
    }

    private void startTask() {
        Bukkit.getScheduler().scheduleSyncRepeatingTask(SkyPvPPlugin.getInstance(), new Runnable() {
            @Override
            public void run() {
                for(SkyPvpPlayer player : storedSkyPvpPlayer.values()) {
                    if(player == null) continue;
                    player.increasePlayTime(3);
                }
            }
        }, 0, 20*3);
    }

    public SkyPvpPlayer getSkyPvpPlayerByPlayer(Player player){
        return storedSkyPvpPlayer.get(player);
    }

    public HashMap<Player, SkyPvpPlayer> getStoredSkyPvpPlayer() {
        return storedSkyPvpPlayer;
    }
}
