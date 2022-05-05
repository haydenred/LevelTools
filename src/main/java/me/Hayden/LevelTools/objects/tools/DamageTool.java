package me.Hayden.LevelTools.objects.tools;

import de.tr7zw.changeme.nbtapi.NBTItem;
import me.Hayden.LevelTools.Main;
import me.Hayden.LevelTools.objects.Handler;
import me.Hayden.LevelTools.objects.LevelTool;
import me.Hayden.LevelTools.util.Util;
import org.bukkit.configuration.Configuration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class DamageTool extends LevelTool implements Handler {

    private final Configuration config = Main.getInstance().getConfig();
    private final String type;

    //damage will be referred to as "xp"
    public DamageTool(String type, ItemStack item, Player player) {
        super(type, item, player);
        this.type = type;
        NBTItem nbtItem = super.getNBTItem();
    }


    @Override
    public void handle(Object parameter) {
        int damage = (Integer) parameter;
        super.addXP(damage);
        saveItem();
    }

    @Override
    protected void setCustomLore() {
        List<String> lore = new ArrayList<>(config.getStringList(type + ".lore"));
        Util.replaceList(lore, "%damage%", String.valueOf(super.getXp()));
        Util.replaceList(lore, "%damage_needed%", String.valueOf(super.getXPNeeded()));
        super.setLore(lore);
    }


    @Override
    protected void saveItem() {
        super.saveItem();
    }
}
