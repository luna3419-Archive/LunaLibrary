package si.f5.luna3419.lib.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import si.f5.luna3419.lib.player.LibPlayer;

public class LogInOutListener implements Listener {
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        LibPlayer.registerPlayer(e.getPlayer());
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent e) {
        LibPlayer.unregisterPlayer(e.getPlayer());
    }
}
