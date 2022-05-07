package me.Hayden.LevelTools.objects;

import com.cryptomorin.xseries.XEnchantment;
import de.tr7zw.changeme.nbtapi.NBTItem;
import me.Hayden.LevelTools.Main;
import me.Hayden.LevelTools.util.Util;
import net.advancedplugins.ae.api.AEAPI;
import org.bukkit.Bukkit;
import org.bukkit.configuration.Configuration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public abstract class LevelTool {

    private final Configuration configuration = Main.getInstance().getConfig();
    private final NBTItem nbtItem;
    private final String toolType;
    private final Player player;
    //The old lore is the lore that was previously set by the plugin
    //and needs to be cleared before the new lore is added to the item again
    //This method is used to assure that the lore is preserved from the item
    //and not cleared which happened in LevelTools 1.0 and caused many issues
    private final List<String> oldLore;
    private ItemStack item;
    private int xp;
    private int level;

    public LevelTool(String toolType, ItemStack item, Player player) {
        this.nbtItem = new NBTItem(item, true);
        this.item = new ItemStack(item);
        this.toolType = toolType;
        this.xp = nbtItem.getInteger("xp");
        this.level = nbtItem.getInteger("level");
        this.player = player;
        this.oldLore = nbtItem.getStringList("lore");
    }

    public int getXp() {
        return xp;
    }

    public int getLevel() {
        return level;
    }

    protected abstract void setCustomLore();


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
            lore.removeIf(newLoreLine -> newLoreLine.equals(oldLoreLine));
        }
        meta.setLore(lore);
        this.item.setItemMeta(meta);

    }

    public void setLore(List<String> newLore) {

        this.processPlaceholders(newLore);
        this.removeOldLore();

        ItemMeta meta = item.getItemMeta();

        List<String> l;
        if (meta.getLore() == null) {
            l = new ArrayList<>();
        } else {
            l = meta.getLore();
        }


        l.addAll(newLore);
        meta.setLore(l);

        this.setOldLore(newLore);
        this.item.setItemMeta(meta);


        //Setting the lore...
    }

    private void processPlaceholders(List<String> list) {
        //Replace list from the Util class is not used here to minimize for loops
        for (int i = 0; i < list.size(); i++) {
            String s = list.get(i);
            s = s.replace("%level%", String.valueOf(this.level));
            s = s.replace("%xp%", String.valueOf(this.xp));
            s = s.replace("%xp_needed%", String.valueOf(this.getXPNeeded()));
            s = s.replace("%progressbar%", Util.getProgressBar(this.xp, this.getXPNeeded()));
            if (this.getXPNeeded() == 0) {
                s = s.replace("%percentage%", Integer.toString(100));
            } else {
                s = s.replace("%xp_needed%", Integer.toString(this.getXPNeeded()));
                int percentage = (this.xp * 100 + (this.getXPNeeded() >> 1)) / this.getXPNeeded();
                s = s.replace("%percentage%", Integer.toString(percentage));
            }
            list.set(i, Util.translateHexCodes(s));
        }
    }


    protected void saveItem() {
        //Check for the next level first
        this.checkForNextLevel();
        //Set item NBT based on recent changes by the checkForNextLevel method
        this.nbtItem.setInteger("xp", this.xp);
        this.nbtItem.setInteger("level", this.level);
        setCustomLore();

        this.nbtItem.mergeCustomNBT(item);
        this.player.getItemInHand().setItemMeta(item.getItemMeta());

    }

    public int getXPNeeded() {
        return getXPNeeded(this.level + 1);
    }

    private int getXPNeeded(int level) {
        int xpneeded = 0;
        if (configuration.contains(toolType + ".levels." + level + ".xp-needed")) {
            xpneeded = configuration.getInt(toolType + ".levels." + level + ".xp-needed");
        } else {
            xpneeded = configuration.getInt(toolType + ".levels." + level + ".damage-needed");
        }
        return xpneeded;
    }

    private void addEnchantment(Enchantment e, int a) {

        ItemMeta meta = item.getItemMeta();
        int existingLevel = meta.getEnchantLevel(e);
        meta.addEnchant(e, existingLevel + a, true);
        this.item.setItemMeta(meta);

    }

    public void checkForNextLevel() {
        for (String s : configuration.getConfigurationSection(toolType + ".levels").getKeys(false)) {
            int xpNeeded = getXPNeeded(Integer.parseInt(s));
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
                        if (Main.advancedEnchantments && AEAPI.isAnEnchantment(splitench[0]) && AEAPI.getMaterialsForEnchantment(splitench[0]).contains(this.item.getType())) {
                            int currentLevel = AEAPI.getEnchantLevel(splitench[0], this.item);
                            this.item = AEAPI.applyEnchant(splitench[0], Integer.parseInt(splitench[1]) + currentLevel, this.item);
                        } else {
                            addEnchantment(XEnchantment.matchXEnchantment(splitench[0]).get().getEnchant(), Integer.parseInt(splitench[1]));
                        }
                    }
                }
            }
        }
    }


}
