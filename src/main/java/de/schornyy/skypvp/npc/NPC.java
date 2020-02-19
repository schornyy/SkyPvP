package de.schornyy.skypvp.npc;

import de.schornyy.skypvp.SkyPvPPlugin;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;

import java.io.File;
import java.io.IOException;

public class NPC {

    private String name, uuid;
    private EntityType entityType;
    private Location location;
    private Entity entity;
    private int id;

    private File file;
    private FileConfiguration cfg;

    public NPC(String uuid) {
        this.uuid = uuid;
        file = new File(SkyPvPPlugin.getInstance().getDataFolder() + "/NPC/" + uuid + ".yml");
        cfg = YamlConfiguration.loadConfiguration(file);
    }

    public void load() {
        setUuid(getFile().getName().replaceAll(".yml", ""));
        setId(getCfg().getInt("ID"));
        setName(getCfg().getString("Name").replaceAll("&", "§"));
        setEntityType(EntityType.fromName(getCfg().getString("EntityType")));
        Location loc = new Location(Bukkit.getWorld(getCfg().getString("Location.World")),
                getCfg().getInt("Location.x"),
                getCfg().getInt("Location.y"),
                getCfg().getInt("Location.z"));
        setLocation(loc);
        spawn(loc);
    }

    public void save() {
        getCfg().set("ID", getId());
        getCfg().set("Name", getName().replaceAll("§", "&"));
        getCfg().set("EntityType", getEntityType().getName());
        getCfg().set("Location.World", getLocation().getWorld().getName());
        getCfg().set("Location.x", (int)getLocation().getX());
        getCfg().set("Location.y", (int)getLocation().getY());
        getCfg().set("Location.z", (int)getLocation().getZ());
        getEntity().remove();

        try {
            getCfg().save(getFile());
        }catch (IOException e){}
    }

    public void create(Location loc, String name) {
        setId((SkyPvPPlugin.getInstance().getNpcManager().getStoredNPCs().size() + 1));
        setLocation(loc);
        setName(name.replaceAll("&", "§"));
        setEntityType(EntityType.VILLAGER);
        spawn(loc);
        SkyPvPPlugin.getInstance().getNpcManager().getStoredNPCs().put(getId(), this);
    }

    public void changeName(String name) {
        setName(name.replaceAll("&", "§"));
        LivingEntity livingEntity = (LivingEntity) getEntity();
        livingEntity.setCustomName(name.replaceAll("&", "§"));
    }

    private void spawn(Location loc) {
        Entity entity = loc.getWorld().spawnEntity(loc, getEntityType());
        setEntity(entity);
        entity.setCustomName(getName().replaceAll("&", "§"));
        LivingEntity livingEntity = (LivingEntity) entity;
        livingEntity.setAI(false);
    }

    public void changeEntityType(String entityType) {
        getEntity().remove();
        setEntityType(EntityType.fromName(entityType));
        spawn(getLocation());
    }

    public void changeLocation(Location loc) {
        setLocation(loc);
        getEntity().teleport(loc);
    }

    public void delete() {
        getFile().delete();
        SkyPvPPlugin.getInstance().getNpcManager().getStoredNPCs().remove(ChatColor.stripColor(getName()));
        getEntity().remove();
    }

    private void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getUuid() {
        return uuid;
    }

    private void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public FileConfiguration getCfg() {
        return cfg;
    }

    public File getFile() {
        return file;
    }

    public String getName() {
        return name;
    }

    public Location getLocation() {
        return location;
    }

    public EntityType getEntityType() {
        return entityType;
    }

    public Entity getEntity() {
        return entity;
    }

    public void setName(String name) {
        this.name = name;
    }

    private void setLocation(Location location) {
        this.location = location;
    }

    private void setEntityType(EntityType entityType) {
        this.entityType = entityType;
    }

    private void setEntity(Entity entity) {
        this.entity = entity;
    }
}
