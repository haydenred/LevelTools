package me.Hayden.LevelTools.objects.tools;

import de.tr7zw.changeme.nbtapi.NBTItem;
import me.Hayden.LevelTools.Main;
import me.Hayden.LevelTools.objects.LevelTool;
import me.Hayden.LevelTools.util.UMaterial;
import me.Hayden.LevelTools.util.Util;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.io.ObjectInputFilter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class Pickaxe extends LevelTool {

    private static final HashMap<Material, Integer> blockXP = new HashMap<>();
    private static final List<String> configLore = new ArrayList<>();
    private static final Configuration config = Main.getInstance().getConfig();
    private Integer blocksBroken;
    private final NBTItem nbtItem;

    static {
        FileConfiguration config = Main.getInstance().getConfig();
        for (String s : config.getStringList("pickaxe.blocks")) {
            String[] split = s.split(":", 2);
            Material material = UMaterial.valueOf(split[0]).getMaterial();
            Integer worth = Integer.valueOf(split[1]);
            blockXP.put(material, worth);
        }
        configLore.addAll(config.getStringList("pickaxe.lore"));
    }

    public Pickaxe(ItemStack item, Player player) {
        super("pickaxe", item, player);
        this.nbtItem = super.getNBTItem();
        this.blocksBroken = nbtItem.getInteger("blocks");
        //Load blocks and their respective XP values
    }


    @Override
    public void handle(Object parameter) {
        List<Block> blockList = (List<Block>) parameter;
        for (Block block : blockList) {
            super.addXP(blockXP.getOrDefault(block.getType(), 0));
        }
        this.blocksBroken += 1;
        saveItem();
    }

    @Override
    protected void setCustomLore() {
        List<String> lore = new ArrayList<>();
        List<String> newLore = new ArrayList<>();
        lore.addAll(configLore);
        lore.forEach(s -> {
            s = s.replace("%blocks%", String.valueOf(blocksBroken));
            s = s.replace("%level%", String.valueOf(super.getLevel()));
            s = s.replace("%xp%", String.valueOf(super.getXp()));
            s = s.replace("%xp_needed%", String.valueOf(super.getXPNeeded()));
            s = s.replace("%progressbar%", Util.getProgressBar(super.getXp(), super.getXPNeeded()));
            if (super.getXPNeeded() == 0) {
                s = s.replace("%xp_needed%", Util.translateHexCodes(config.getString("settings.maxlevel")));
                s = s.replace("%percentage%", Integer.toString(100));
            } else {
                s = s.replace("%xp_needed%", Integer.toString(super.getXPNeeded()));
                int percentage = (super.getXp() * 100 + (super.getXPNeeded() >> 1)) / super.getXPNeeded();
                s = s.replace("%percentage%", Integer.toString(percentage));
            }
            newLore.add(Util.translateHexCodes(s));
        });
        super.setLore(newLore);
    }


    @Override
    protected void saveItem() {
        super.getNBTItem().setInteger("blocks", blocksBroken);
        super.saveItem();
    }
}
