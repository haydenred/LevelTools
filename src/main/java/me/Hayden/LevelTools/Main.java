package me.Hayden.LevelTools;

import com.tchristofferson.configupdater.ConfigUpdater;
import me.Hayden.LevelTools.events.BlockBreakEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class Main extends JavaPlugin {

    private static Main instance;

    @Override
    public void onEnable() {
        instance = this;
        saveDefaultConfig();
        File configFile = new File(getDataFolder(), "config.yml");
        List<String> ignoredSections = Arrays.asList("pickaxe", "axe", "bow", "crossbow", "sword", "shovel");
        updateConfig(configFile, ignoredSections);
        reloadConfig();
        getServer().getPluginManager().registerEvents(new BlockBreakEvent(), this);
    }

    public static Main getInstance() {
        return instance;
    }

    private void updateConfig(File file, List<String> ignoredSections) {
        try {
            ConfigUpdater.update(this, file.getName(), file, ignoredSections);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
