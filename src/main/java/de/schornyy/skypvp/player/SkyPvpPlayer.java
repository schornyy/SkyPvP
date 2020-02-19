package de.schornyy.skypvp.player;

import de.schornyy.skypvp.SkyPvPPlugin;
import de.schornyy.skypvp.kit.Kit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SkyPvpPlayer {

    private int kills, tode, coins, playedTime;
    private Player player;
    private List<Kit> storedPlayerKits;

    private File file;
    private FileConfiguration cfg;

    public SkyPvpPlayer(Player player) {
        this.player = player;
        storedPlayerKits = new ArrayList<>();
        file = new File(SkyPvPPlugin.getInstance().getDataFolder() + "/Player/" + player.getName() + ".yml");
        cfg = YamlConfiguration.loadConfiguration(file);
    }

    public void load() {
        if(!getFile().exists()) {
            getCfg().set("Coins", 0);
            getCfg().set("Kills", 0);
            getCfg().set("Tode", 0);
            getCfg().set("PlayedTime", 0);

            try {
                getCfg().save(getFile());
            }catch (IOException e){}
        }
        setCoins(getCfg().getInt("Coins"));
        setKills(getCfg().getInt("Kills"));
        setTode(getCfg().getInt("Tode"));
        setPlayedTime(getCfg().getInt("PlayedTime"));
        SkyPvPPlugin.getInstance().getPlayerManager().getStoredSkyPvpPlayer().put(getPlayer(), this);
    }

    public void save() {
        getCfg().set("Kills", getKills());
        getCfg().set("Tode", getTode());
        getCfg().set("Coins", getCoins());
        getCfg().set("PlayedTime", getPlayedTime());
        ArrayList<String> kitNames = new ArrayList<>();
        if(getStoredPlayerKits() != null || getStoredPlayerKits().size() != 0) {
            getStoredPlayerKits().forEach(kits -> {
                kitNames.add(kits.getKitName());
            });
        }
        getCfg().set("Kits", kitNames);

        try {
            getCfg().save(getFile());
        }catch (IOException e){}
    }

    public void setStoredPlayerKits(List<Kit> storedPlayerKits) {
        this.storedPlayerKits = storedPlayerKits;
    }

    public void increaseKills() {
        kills++;
    }

    public void increaseTode() {
        tode++;
    }

    public void increaseCoins(int value) {
        setCoins(getCoins() + value);
    }

    public void increasePlayTime(int value) {
        setPlayedTime(getPlayedTime() + value);
    }

    public File getFile() {
        return file;
    }

    public FileConfiguration getCfg() {
        return cfg;
    }

    public Player getPlayer() {
        return player;
    }

    public int getCoins() {
        return coins;
    }

    public void setCoins(int coins) {
        this.coins = coins;
    }

    public int getKills() {
        return kills;
    }

    public int getPlayedTime() {
        return playedTime;
    }

    public int getTode() {
        return tode;
    }

    public void setKills(int kills) {
        this.kills = kills;
    }

    public void setPlayedTime(int playedTime) {
        this.playedTime = playedTime;
    }

    public void setTode(int tode) {
        this.tode = tode;
    }

    public List<Kit> getStoredPlayerKits() {
        return storedPlayerKits;
    }
}
