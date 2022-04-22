package me.Hayden.LevelTools.objects;

import de.tr7zw.changeme.nbtapi.NBTItem;
import me.Hayden.LevelTools.Main;
import me.Hayden.LevelTools.util.Util;
import org.bukkit.Bukkit;
import org.bukkit.configuration.Configuration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class LevelTool {

    private final Configuration configuration = Main.getInstance().getConfig();

    private final ItemStack item;

    private final NBTItem nbtItem;
    private final String toolType;
    private final Player player;

    private int xp;
    private int level;
    //The old lore is the lore that was previously set by the plugin
    //and needs to be cleared before the new lore is added to the item again
    //This method is used to assure that the lore is preserved from the item
    //and not cleared which happened in LevelTools 1.0 and caused many issues
    private List<String> oldLore;

    public LevelTool(String toolType, ItemStack item, Player player) {
        this.nbtItem = new NBTItem(item, true);
        this.item = item;
        this.toolType = toolType;
        this.xp = nbtItem.getInteger("xp");
        this.level = nbtItem.getInteger("level");
        this.player = player;
        this.oldLore = nbtItem.getStringList("lore");
    }

    public NBTItem getNBTItem() {
        return this.nbtItem;
    }

    public void addXP(int amt) {
        this.xp += amt;
    }

    protected void setOldLore(List<String> list) {
        this.oldLore.clear();
        this.oldLore.addAll(list);
    }

    protected void removeOldLore() {
        ItemMeta meta = item.getItemMeta();
        List<String> lore = meta.getLore();
        for (String oldLoreLine : oldLore) {
            lore.removeIf(newLoreLine -> {
                if (newLoreLine.equals(oldLoreLine)) {
                    return true;
                }
                return false;
            });
        }
        meta.setLore(lore);
        this.item.setItemMeta(meta);
    }

    public void setLore() {
        this.removeOldLore();
        List<String> newLore = new ArrayList<>();
        newLore.add(String.valueOf(new Random().nextInt(10)));
        //Add new lore to item lore and to the oldLore;
        ItemMeta meta = item.getItemMeta();

        List<String> l = meta.getLore();
        l.addAll(newLore);
        meta.setLore(l);

        this.setOldLore(newLore);
        this.item.setItemMeta(meta);
        //Setting the lore...
    }

    protected void saveItem() {
        //Check for the next level first
        this.checkForNextLevel();
        //Set item NBT based on recent changes by the checkForNextLevel method
        this.nbtItem.setInteger("xp", this.xp);
        this.nbtItem.setInteger("level", this.level);
        //Finish off by setting the lore since everything has been set in stone for the item
        setLore();
        this.nbtItem.mergeCustomNBT(item);

    }

    public void checkForNextLevel() {
        for (String s : configuration.getConfigurationSection(toolType + ".levels").getKeys(false)) {
            int xpNeeded = configuration.getInt(toolType + ".levels." + s + ".xp-needed");
            if (xp >= xpNeeded) {
                int nextLevel = Integer.parseInt(s);
                if (level == nextLevel)
                    continue;
                if (level > nextLevel)
                    continue;
                this.level = nextLevel;
                for (String reward : configuration.getStringList(toolType + ".levels." + s + ".rewards")) {
                    String[] splits = reward.split(" ", 2);
                    String prefix = splits[0];
                    if (prefix.equalsIgnoreCase("[cmd]"))
                        Bukkit.dispatchCommand(Bukkit.getServer().getConsoleSender(), splits[1]
                                .replace("%player%", player.getName()));
                    if (prefix.equalsIgnoreCase("[message]"))
                        player.sendMessage(Util.translateHexCodes(splits[1].replace("%player%", player.getName())));
                    if (prefix.equalsIgnoreCase("[enchant]")) {
                        String[] splitench = splits[1].split(" ");
                        //int existingLevel = item.getEnchantmentLevel(Enchantment.getByName(splitench[0]));
                        //meta.addEnchant(Enchantment.getByName(splitench[0]), existingLevel + Integer.parseInt(splitench[1]), true);
                    }
                }
            }
        }
    }



}
