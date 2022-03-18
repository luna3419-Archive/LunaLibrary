package si.f5.luna3419.lib.packet;

import java.util.HashMap;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.PackagePrivate;

public class PacketAPI {
    public static final Pipeline PIPELINE = new Pipeline();

    public Object send(Object packet) {
        PacketHolder<Object> holder = new PacketHolder<Object>(packet);
        PIPELINE.handlers.values().forEach(handler -> holder.setPacket(handler.write(packet)));

        return holder.getPacket();
    }

    public Object receive(Object packet) {
        PacketHolder<Object> holder = new PacketHolder<Object>(packet);
        PIPELINE.handlers.values().forEach(handler -> holder.setPacket(handler.read(packet)));

        return holder.getPacket();
    }

    @PackagePrivate
    public static class Pipeline {
        private HashMap<String, PacketHandler> handlers;

        public void add(String key, PacketHandler handler) {
            handlers.put(key, handler);
        }

        public void remove(String key) {
            handlers.remove(key);
        }
    }

    @PackagePrivate
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PacketHolder<T> {
        @Getter
        @Setter
        private T packet;
    }
}
