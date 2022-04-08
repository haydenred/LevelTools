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
    private final ItemStack item;
    private final NBTItem nbtItem;
    private final String toolType;
    private Integer toolXP;
    private boolean nextLevel; //Is there a next level?
    private Integer levelXP; //XP Needed to get to the next level
    private boolean isMaxLevel = false;
    private Integer toolLevel;
    private final FileConfiguration config = Main.getInstance().getConfig();

    public LevelTool(String toolType, ItemStack item) {
        this.item = item;
        this.nbtItem = new NBTItem(item);
        this.toolXP = nbtItem.getInteger("xp");
        this.toolLevel = nbtItem.getInteger("level");
        this.toolType = toolType;

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

    public String getToolType() {
        return toolType;
    }

    public void saveItem() {
        this.nbtItem.applyNBT(this.item);
        System.out.println(this.nbtItem.toString());
    }

    public void checkForNextLevel() {
        if (this.isMaxLevel) {
            //Tool is max level, cannot level up anymore.
        }
        if (nbtItem.getInteger("xp") >= levelXP) {

        }
    }


}
