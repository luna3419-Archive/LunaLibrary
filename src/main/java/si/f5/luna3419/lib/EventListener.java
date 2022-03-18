package si.f5.luna3419.lib;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityToggleGlideEvent;

import si.f5.luna3419.lib.events.PlayerStartGlidingEvent;
import si.f5.luna3419.lib.events.PlayerStopGlidingEvent;

/**
 * Listener to post event
 */
public class EventListener implements Listener {
    @EventHandler
    public void onPlayerToggleFly(EntityToggleGlideEvent e) {
        if (e.getEntity() instanceof Player player) {
            if (!player.isGliding()) {
                Bukkit.getPluginManager().callEvent(new PlayerStartGlidingEvent(player));
            } else {
                Bukkit.getPluginManager().callEvent(new PlayerStopGlidingEvent(player));
            }
        }
    }
}
