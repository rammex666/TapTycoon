package org.rammex.taptycoon;

import lombok.Getter;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.rammex.taptycoon.event.EntityDamageListener;
import org.rammex.taptycoon.event.MobLoader;
import org.rammex.taptycoon.event.PlayerJoin;

import java.io.File;
import java.io.IOException;

public final class TapTycoon extends JavaPlugin {

    private File dir = getDataFolder();

    @Getter
    private FileConfiguration entityConf;

    @Getter
    private FileConfiguration dataPlayer;

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(new PlayerJoin(this),  this);
        getServer().getPluginManager().registerEvents(new MobLoader(this),  this);
        getServer().getPluginManager().registerEvents(new EntityDamageListener(this), this);
        loadDataFiles();
        loadConfigFiles();
        dir.mkdirs();
        saveDefaultConfig();
        reloadConfig();
        this.getLogger().info("Plugin TapTycoon has been enabled, by .rammex");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    private void loadDataFiles() {
        loadDataFile("dataplayer");
    }

    private void loadDataFile(String fileName) {
        File file = new File(getDataFolder()+"/data", fileName + ".yml");
        if (!file.exists()) {
            file.getParentFile().mkdirs();
            saveResource("data/"+fileName + ".yml", false);
            this.getLogger().info("File " + fileName + ".yml has been loaded.");
        }

        FileConfiguration fileConf = new YamlConfiguration();
        try {
            fileConf.load(file);
            this.getLogger().info("File " + fileName + ".yml has been loaded.");
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }


        switch (fileName) {
            case "dataplayer":
                dataPlayer = fileConf;
                break;
        }
    }

    private void loadConfigFiles() {
        loadConfigFile("entity");
    }

    private void loadConfigFile(String fileName) {
        File file = new File(getDataFolder()+"/config", fileName + ".yml");
        if (!file.exists()) {
            file.getParentFile().mkdirs();
            saveResource("config/"+fileName + ".yml", false);
            this.getLogger().info("File " + fileName + ".yml has been loaded.");
        }

        FileConfiguration fileConf = new YamlConfiguration();
        try {
            fileConf.load(file);
            this.getLogger().info("File " + fileName + ".yml has been loaded.");
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }

        switch (fileName) {
            case "entity":
                entityConf = fileConf;
                break;
        }
    }

    public void reloadEntityConfig() {
        entityConf = YamlConfiguration.loadConfiguration(new File(getDataFolder()+"/config", "entity.yml"));
    }

    public void reloadDataPlayer() {
        entityConf = YamlConfiguration.loadConfiguration(new File(getDataFolder()+"/data", "dataplayer.yml"));
    }
}
