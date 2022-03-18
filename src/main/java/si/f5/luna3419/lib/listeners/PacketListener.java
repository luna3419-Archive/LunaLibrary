package si.f5.luna3419.lib.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import io.netty.channel.Channel;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import si.f5.luna3419.lib.Library;


public class PacketListener implements Listener {
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        Player player = e.getPlayer();
        ChannelDuplexHandler channelDuplexHandler = new ChannelDuplexHandler() {
            @Override
            public void channelRead(ChannelHandlerContext context, Object packet) throws Exception {
                packet = Library.getInstance().getPacketPipeline().receive(packet);

                if (packet != null) {
                    super.channelRead(context, packet);
                }
            }

            @Override
            public void write(ChannelHandlerContext context, Object message, ChannelPromise promise) throws Exception {
                message = Library.getInstance().getPacketPipeline().send(message);
                
                if (message != null) {
                    super.write(context, message, promise);
                }
            }
        };

        try {
            Object handle = player.getClass().getMethod("getHandle").invoke(player);

            Object playerConnection = handle.getClass().getDeclaredField("playerConnection").get(handle);

            Object networkManager = playerConnection.getClass().getDeclaredField("networkManager").get(playerConnection);

            final Channel channel = (Channel) networkManager.getClass().getField("channel").get(networkManager);

            channel.pipeline().addBefore("packet_handler", player.getName(), channelDuplexHandler);
        } catch (ReflectiveOperationException exc) {
            exc.printStackTrace();
        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent e) {
        Player player = e.getPlayer();

        try {
            Object handle = player.getClass().getMethod("getHandle").invoke(player);

            Object playerConnection = handle.getClass().getDeclaredField("playerConnection").get(handle);

            Object networkManager = playerConnection.getClass().getDeclaredField("networkManager").get(playerConnection);

            final Channel channel = (Channel) networkManager.getClass().getField("channel").get(networkManager);

            channel.eventLoop().submit(() -> channel.pipeline().remove(player.getName()));
        } catch (ReflectiveOperationException exc) {
            exc.printStackTrace();
        }
    }
}
