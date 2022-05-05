package me.Hayden.LevelTools.events;

import com.vk2gpz.tokenenchant.event.TEBlockExplodeEvent;
import me.Hayden.LevelTools.LevelToolHandler;
import me.Hayden.LevelTools.objects.Handler;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class TEExplodeEvent implements Listener {

    @EventHandler
    public void explodeEvent(TEBlockExplodeEvent event) {
        if (event.isCancelled()) {
            return;
        }
        String material = event.getPlayer().getItemInHand().getType().toString();
        if (!LevelToolHandler.blockBreakItems.contains(material)) {
            return;
        }

        Handler handler = LevelToolHandler.getLevelTool(event.getPlayer(), event.getPlayer().getItemInHand());
        handler.handle(event.blockList());
    }


}
