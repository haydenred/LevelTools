package me.Hayden.LevelTools;

import java.util.Arrays;
import java.util.List;
import me.Hayden.LevelTools.objects.Handler;
import me.Hayden.LevelTools.objects.tools.BlockBreakTool;
import me.Hayden.LevelTools.objects.tools.DamageTool;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class LevelToolHandler {
   public static final List<String> damageItems = Arrays.asList("NETHERITE_SWORD");
   public static final List<String> blockBreakItems = Arrays.asList("NETHERITE_PICKAXE");

   public static Handler getLevelTool(Player player, ItemStack item) {
      Handler handler = null;
      String type = null;
      String itemName = item.getType().toString();
      byte var6 = -1;
      switch(itemName.hashCode()) {
      case -1850063282:
         if (itemName.equals("GOLD_PICKAXE")) {
            var6 = 1;
         }
         break;
      case -1474660721:
         if (itemName.equals("GOLD_AXE")) {
            var6 = 7;
         }
         break;
      case -955115213:
         if (itemName.equals("STONE_PICKAXE")) {
            var6 = 4;
         }
         break;
      case -487815164:
         if (itemName.equals("WOODEN_PICKAXE")) {
            var6 = 2;
         }
         break;
      case -170122909:
         if (itemName.equals("DIAMOND_AXE")) {
            var6 = 6;
         }
         break;
      case 70353908:
         if (itemName.equals("STONE_AXE")) {
            var6 = 10;
         }
         break;
      case 122966710:
         if (itemName.equals("IRON_PICKAXE")) {
            var6 = 5;
         }
         break;
      case 346690796:
         if (itemName.equals("WOODEN_SHOVEL")) {
            var6 = 14;
         }
         break;
      case 430758414:
         if (itemName.equals("DIAMOND_SHOVEL")) {
            var6 = 12;
         }
         break;
      case 470163933:
         if (itemName.equals("STONE_SHOVEL")) {
            var6 = 16;
         }
         break;
      case 473626359:
         if (itemName.equals("IRON_AXE")) {
            var6 = 11;
         }
         break;
      case 872992337:
         if (itemName.equals("NETHERITE_AXE")) {
            var6 = 9;
         }
         break;
      case 1263725840:
         if (itemName.equals("NETHERITE_PICKAXE")) {
            var6 = 3;
         }
         break;
      case 1336224762:
         if (itemName.equals("IRON_SHOVEL")) {
            var6 = 17;
         }
         break;
      case 1542325061:
         if (itemName.equals("WOODEN_AXE")) {
            var6 = 8;
         }
         break;
      case 1788665440:
         if (itemName.equals("NETHERITE_SHOVEL")) {
            var6 = 15;
         }
         break;
      case 2103862626:
         if (itemName.equals("GOLD_SHOVEL")) {
            var6 = 13;
         }
         break;
      case 2118280994:
         if (itemName.equals("DIAMOND_PICKAXE")) {
            var6 = 0;
         }
      }

      switch(var6) {
      case 0:
      case 1:
      case 2:
      case 3:
      case 4:
      case 5:
         handler = new BlockBreakTool("pickaxe", item, player);
         type = "pickaxe";
         break;
      case 6:
      case 7:
      case 8:
      case 9:
      case 10:
      case 11:
         handler = new BlockBreakTool("axe", item, player);
         type = "axe";
         break;
      case 12:
      case 13:
      case 14:
      case 15:
      case 16:
      case 17:
         handler = new BlockBreakTool("shovel", item, player);
         type = "shovel";
      }

      var6 = -1;
      switch(itemName.hashCode()) {
      case -1389040246:
         if (itemName.equals("CROSSBOW")) {
            var6 = 1;
         }
         break;
      case -1092765341:
         if (itemName.equals("STONE_SWORD")) {
            var6 = 6;
         }
         break;
      case -262752494:
         if (itemName.equals("DIAMOND_SWORD")) {
            var6 = 3;
         }
         break;
      case -94996570:
         if (itemName.equals("IRON_SWORD")) {
            var6 = 5;
         }
         break;
      case 65962:
         if (itemName.equals("BOW")) {
            var6 = 0;
         }
         break;
      case 206860606:
         if (itemName.equals("GOLD_SWORD")) {
            var6 = 4;
         }
         break;
      case 427272308:
         if (itemName.equals("WOODEN_SWORD")) {
            var6 = 7;
         }
         break;
      case 1443618944:
         if (itemName.equals("NETHERITE_SWORD")) {
            var6 = 2;
         }
      }

      switch(var6) {
      case 0:
         type = "bow";
         handler = new DamageTool("bow", item, player);
         break;
      case 1:
         type = "crossbow";
         handler = new DamageTool("crossbow", item, player);
         break;
      case 2:
      case 3:
      case 4:
      case 5:
      case 6:
      case 7:
         type = "sword";
         handler = new DamageTool("sword", item, player);
      }

      return (Handler)(Main.getInstance().getConfig().getBoolean(type + ".enabled") ? handler : null);
   }
}
