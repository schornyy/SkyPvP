package de.schornyy.skypvp.npc;

import de.schornyy.skypvp.SkyPvPPlugin;

import java.io.File;
import java.util.HashMap;

public class NPCManager {

    private HashMap<Integer, NPC> storedNPCs = new HashMap<>();

    public NPCManager() {
        loadAllNPC();
    }

    private void loadAllNPC() {
        File file = new File(SkyPvPPlugin.getInstance().getDataFolder() + "/NPC/");

        if(file.listFiles() != null) {
            for(File files : file.listFiles()) {
                if(files == null) continue;
                NPC npc = new NPC(files.getName().replaceAll(".yml", ""));
                npc.load();
                getStoredNPCs().put(npc.getId(), npc);
            }
        }
    }

    public void saveAllNPCs() {
        if(getStoredNPCs() != null || !getStoredNPCs().isEmpty()) {
            for(NPC npc : getStoredNPCs().values()) {
                npc.save();
            }
        }
    }

    public NPC getNPCbyName(String name) {
        for (NPC names : getStoredNPCs().values()) {
            if (names.getName().equalsIgnoreCase(name)) {
                return names;
            }
        }
        return null;
    }

    public NPC getNPCgetByID(int id) {
        return getStoredNPCs().get(id);
    }

    public HashMap<Integer, NPC> getStoredNPCs() {
        return storedNPCs;
    }
}
