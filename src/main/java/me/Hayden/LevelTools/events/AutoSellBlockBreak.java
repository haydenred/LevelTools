package me.Hayden.LevelTools.events;

import me.Hayden.LevelTools.LevelToolHandler;
import me.Hayden.LevelTools.objects.Handler;
import me.clip.autosell.events.DropsToInventoryEvent;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.ArrayList;
import java.util.List;

public class AutoSellBlockBreak implements Listener {

    @EventHandler
    public void onBreak(DropsToInventoryEvent event) {
        if (event.isCancelled()) {
            return;
        }
        String material = event.getPlayer().getItemInHand().getType().toString();
        if (!LevelToolHandler.blockBreakItems.contains(material)) {
            return;
        }

        List<Block> blockList = new ArrayList<>();
        Handler handler = LevelToolHandler.getLevelTool(event.getPlayer(), event.getPlayer().getItemInHand());
        blockList.add(event.getBlock());
        handler.handle(blockList);
    }

}
