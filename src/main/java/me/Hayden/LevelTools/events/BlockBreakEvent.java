package me.Hayden.LevelTools.events;

import me.Hayden.LevelTools.objects.tools.Pickaxe;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.Arrays;
import java.util.List;

public class BlockBreakEvent implements Listener {

    @EventHandler
    public void blockBreakEvent(org.bukkit.event.block.BlockBreakEvent event) {
        new Pickaxe(event.getPlayer().getItemInHand()).handle(Arrays.asList(event.getBlock()));
    }

}