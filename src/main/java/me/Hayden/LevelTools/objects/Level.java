package me.Hayden.LevelTools.objects;

import me.Hayden.LevelTools.Main;
import org.bukkit.configuration.Configuration;

import java.util.HashMap;

public class Level {

    private final LevelTool levelTool;
    private final HashMap<Integer, Integer> levels = new HashMap<>();
    private final Configuration config = Main.getInstance().getConfig();

    public Level(LevelTool levelTool) {
        this.levelTool = levelTool;
        for (String s : config.getConfigurationSection(levelTool.getToolType() + ".levels").getKeys(false)) {
            Integer level = Integer.valueOf(s);
            Integer xpNeeded = config.getInt(levelTool.getToolType() + ".levels.xp-needed");
            levels.put(level, xpNeeded);
        }
    }





}
