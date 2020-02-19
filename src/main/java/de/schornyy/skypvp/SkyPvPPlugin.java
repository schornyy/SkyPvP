package de.schornyy.skypvp;

import de.schornyy.skypvp.commands.KitCommand;
import de.schornyy.skypvp.commands.NPCCommand;
import de.schornyy.skypvp.configs.KitConfig;
import de.schornyy.skypvp.configs.MessagesConfig;
import de.schornyy.skypvp.configs.SettingsConfig;
import de.schornyy.skypvp.configs.SignConfig;
import de.schornyy.skypvp.inventorys.KitSelectorInventory;
import de.schornyy.skypvp.item.ItemIDIndicator;
import de.schornyy.skypvp.kit.KitManager;
import de.schornyy.skypvp.listener.*;
import de.schornyy.skypvp.npc.NPCManager;
import de.schornyy.skypvp.player.SkyPvPPlayerManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class SkyPvPPlugin extends JavaPlugin {

    private SkyPvPPlayerManager playerManager;
    private KitManager kitManager;
    private MessagesConfig messagesConfig;
    private SettingsConfig settingsConfig;
    private NPCManager npcManager;
    private KitConfig kitConfig;
    private SignConfig signConfig;
    private KitSelectorInventory kitSelectorInventory;
    private ItemIDIndicator itemIDIndicator;
    private static SkyPvPPlugin instance;

    @Override
    public void onEnable() {
        loadInits();
        loadCommands();
        loadListener();

        Bukkit.getConsoleSender().sendMessage(getMessagesConfig().prefix + "§awurde geladen!");
    }

    @Override
    public void onDisable() {
        getNpcManager().saveAllNPCs();
        getPlayerManager().saveOnlinePlayers();
        getKitManager().saveAllKits();
        Bukkit.getConsoleSender().sendMessage(getMessagesConfig().prefix + "§cwurde deaktiviert");
    }

    private void loadInits() {
        instance = this;
        signConfig = new SignConfig();
        itemIDIndicator = new ItemIDIndicator();
        npcManager = new NPCManager();
        kitConfig = new KitConfig();
        messagesConfig = new MessagesConfig();
        settingsConfig = new SettingsConfig();
        playerManager = new SkyPvPPlayerManager();
        kitManager = new KitManager();
        kitSelectorInventory = new KitSelectorInventory();
    }

    private void loadListener() {
        PluginManager pluginManager = Bukkit.getPluginManager();
        pluginManager.registerEvents(new PlayerJoinListener(), this);
        pluginManager.registerEvents(new PlayerQuitListener(), this);
        pluginManager.registerEvents(new PlayerInteractEntityListener(), this);
        pluginManager.registerEvents(new InventoryClickListener(), this);
        pluginManager.registerEvents(new EntityDamageListener(), this);
        pluginManager.registerEvents(new WeatherChangeListener(), this);
        pluginManager.registerEvents(new PlayerDeathListener(), this);
        pluginManager.registerEvents(new SignChangeListener(), this);
        pluginManager.registerEvents(new PlayerInteractListener(), this);
    }

    private void loadCommands() {
        getCommand("npc").setExecutor(new NPCCommand());
        getCommand("kit").setExecutor(new KitCommand());

        getCommand("npc").setTabCompleter(new NPCCommand());
        getCommand("kit").setTabCompleter(new KitCommand());
    }

    public SkyPvPPlayerManager getPlayerManager() {
        return playerManager;
    }

    public KitManager getKitManager() {
        return kitManager;
    }

    public static SkyPvPPlugin getInstance() {
        return instance;
    }

    public MessagesConfig getMessagesConfig() {
        return messagesConfig;
    }

    public SettingsConfig getSettingsConfig() {
        return settingsConfig;
    }

    public KitConfig getKitConfig() {
        return kitConfig;
    }

    public NPCManager getNpcManager() {
        return npcManager;
    }

    public KitSelectorInventory getKitSelectorInventory() {
        return kitSelectorInventory;
    }

    public SignConfig getSignConfig() {
        return signConfig;
    }

    public ItemIDIndicator getItemIDIndicator() {
        return itemIDIndicator;
    }
}
