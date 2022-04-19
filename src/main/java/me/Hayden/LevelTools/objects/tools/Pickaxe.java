package me.Hayden.LevelTools.objects.tools;

import de.tr7zw.changeme.nbtapi.NBTItem;
import me.Hayden.LevelTools.Main;
import me.Hayden.LevelTools.objects.Handler;
import me.Hayden.LevelTools.objects.LevelTool;
import me.Hayden.LevelTools.util.UMaterial;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.List;

public class Pickaxe extends LevelTool implements Handler {

    private final HashMap<Material, Integer> blockXP = new HashMap<>();
    private final Integer blocksBroken;
    private final ItemStack item;
    private final NBTItem nbtItem;

    public Pickaxe(ItemStack item, Player player) {
        super("pickaxe", item, player);
        this.item = item;
        this.nbtItem = getNBTItem();
        this.blocksBroken = nbtItem.getInteger("blocks");
        //Load blocks and their respective XP values
        FileConfiguration config = Main.getInstance().getConfig();
        for (String s : config.getStringList("pickaxe.blocks")) {
            String[] split = s.split(":", 2);
            Material material = UMaterial.valueOf(split[0]).getMaterial();
            Integer worth = Integer.valueOf(split[1]);
            blockXP.put(material, worth);
        }
    }

    @Override
    public void handle(Object parameter) {
        List<Block> blockList = (List<Block>) parameter;
        for (Block block : blockList) {
            super.addXP(blockXP.getOrDefault(block.getType(), 0));
        }
        nbtItem.setInteger("blocks", blocksBroken + 1);
        super.saveItem();
    }


    @Override
    protected void saveItem() {
        super.getNBTItem().setInteger("blocks", blocksBroken);
        super.saveItem();
    }
}
