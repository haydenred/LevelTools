package me.Hayden.LevelTools;

import me.Hayden.LevelTools.objects.Handler;
import me.Hayden.LevelTools.objects.tools.BlockBreakTool;
import me.Hayden.LevelTools.objects.tools.DamageTool;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.List;

public class LevelToolHandler {

    public static final List<String> damageItems = Arrays.asList("DIAMOND_SWORD", "NETHERITE_SWORD", "GOLD_SWORD", "IRON_SWORD", "STONE_SWORD", "WOODEN_SWORD",
            "BOW", "CROSSBOW");
    public static final List<String> blockBreakItems = Arrays.asList("DIAMOND_PICKAXE", "NETHERITE_PICKAXE", "GOLD_PICKAXE", "IRON_PICKAXE", "STONE_PICKAXE", "WOODEN_PICKAXE",
            "DIAMOND_AXE", "NETHERITE_AXE", "GOLD_AXE", "IRON_AXE", "STONE_AXE", "WOODEN_AXE",
            "DIAMOND_SHOVEL", "NETHERITE_SHOVEL", "GOLD_SHOVEL", "IRON_SHOVEL", "STONE_SHOVEL", "WOODEN_SHOVEL");


    public static Handler getLevelTool(Player player, ItemStack item) {
        Handler handler = null;
        String type = null;
        String itemName = item.getType().toString();

        //NOTE: ADD AXE,SHOVEL
        switch (itemName) {
            case "DIAMOND_PICKAXE":
            case "GOLD_PICKAXE":
            case "WOODEN_PICKAXE":
            case "NETHERITE_PICKAXE":
            case "STONE_PICKAXE":
            case "IRON_PICKAXE":
                handler = new BlockBreakTool("pickaxe", item, player);
                type = "pickaxe";
                break;
            case "DIAMOND_AXE":
            case "GOLD_AXE":
            case "WOODEN_AXE":
            case "NETHERITE_AXE":
            case "STONE_AXE":
            case "IRON_AXE":
                handler = new BlockBreakTool("axe", item, player);
                type = "axe";
                break;
            case "DIAMOND_SHOVEL":
            case "GOLD_SHOVEL":
            case "WOODEN_SHOVEL":
            case "NETHERITE_SHOVEL":
            case "STONE_SHOVEL":
            case "IRON_SHOVEL":
                handler = new BlockBreakTool("shovel", item, player);
                type = "shovel";
                break;
        }

        switch (itemName) {
            case "BOW":
                type = "bow";
                handler = new DamageTool("bow", item, player);
                break;
            case "CROSSBOW":
                type = "crossbow";
                handler = new DamageTool("crossbow", item, player);
                break;
            case "NETHERITE_SWORD":
            case "DIAMOND_SWORD":
            case "GOLD_SWORD":
            case "IRON_SWORD":
            case "STONE_SWORD":
            case "WOODEN_SWORD":
                type = "sword";
                handler = new DamageTool("sword", item, player);
                break;
        }

        if (Main.getInstance().getConfig().getBoolean(type + ".enabled")) {
            return handler;
        }

        return null;
    }

}
