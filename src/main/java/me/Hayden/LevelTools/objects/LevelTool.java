package me.Hayden.LevelTools.objects;

import de.tr7zw.changeme.nbtapi.NBTItem;
import me.Hayden.LevelTools.Main;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.List;

public class LevelTool {

    //Tool type as defined in the config (ex pickaxe, bow, sword)
    private String toolType;
    private ItemStack item;
    private NBTItem nbtItem;
    private Integer toolXP;
    private boolean nextLevel; //Is there a next level?
    private Integer levelXP; //XP Needed to get to the next level
    private Integer toolLevel;
    private final FileConfiguration config = Main.getInstance().getConfig();

    public LevelTool(String toolType, ItemStack item) {
        this.toolType = toolType;
        this.item = item;
        this.nbtItem = new NBTItem(item);
        this.toolXP = nbtItem.getInteger("xp");
        this.toolLevel = nbtItem.getInteger("level");
        this.nextLevel = config.contains(toolType + ".levels." + this.toolLevel + 1);
        if (this.nextLevel) {
            this.levelXP = config.getInt(toolType + ".levels." + (this.toolLevel + 1) + ".xp-needed");
        }
    }

    public Integer getToolXP() {
        return toolXP;
    }

    public void addToolXP(Integer integer) {
        this.toolXP = this.toolXP + integer;
    }

    public Integer getToolLevel() {
        return toolLevel;
    }

    private void addToolLevel(Integer integer) {
        this.toolLevel = this.toolLevel + integer;
    }

    public NBTItem getNBTItem() {
        return this.nbtItem;
    }

    public void saveItem() {
        this.nbtItem.applyNBT(this.item);
        System.out.println(this.nbtItem.toString());
    }

}
