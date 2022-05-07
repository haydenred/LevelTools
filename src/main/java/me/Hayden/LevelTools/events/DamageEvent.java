package me.Hayden.LevelTools.events;

import me.Hayden.LevelTools.LevelToolHandler;
import me.Hayden.LevelTools.Main;
import me.Hayden.LevelTools.objects.Handler;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class DamageEvent implements Listener {

    @EventHandler
    public void damage(EntityDamageByEntityEvent event) {
        if (event.isCancelled()) {
            return;
        }
        if (Main.getInstance().getConfig().getBoolean("settings.onlyplayerdamage")) {
            if (!(event.getEntity() instanceof Player)) {
                return;
            }
        }
        if (getDamager(event) == null) {
            return;
        }
        Player player = getDamager(event);
        if (!LevelToolHandler.damageItems.contains(player.getItemInHand().getType().toString())) {
            return;
        }
        Double d = event.getDamage();
        Handler handler = LevelToolHandler.getLevelTool(player, player.getItemInHand());
        handler.handle(d.intValue());

    }

    private Player getDamager(EntityDamageByEntityEvent event) {
        Player player = null;

        if (event.getDamager().getType().toString().toLowerCase().contains("arrow")) {
            if (!((((Arrow) event.getDamager()).getShooter()) instanceof Player)) {
                return null;
            }
            player = (Player) ((Arrow) event.getDamager()).getShooter();
        }

        if (event.getDamager() instanceof Player) {
            return (Player) event.getDamager();
        }

        return null;
    }

}
