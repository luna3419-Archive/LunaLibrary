package si.f5.luna3419.lib.utils;

import java.util.Arrays;
import java.util.List;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;

public class ComponentUtil {
    /**
     * Component to String
     * @param component Component
     * @return String
     */
    public static String fromComponent(Component component) {
        return PlainTextComponentSerializer.plainText().serializeOrNull(component);
    }

    /**
     * String to Component
     * @param string String
     * @return Component
     */
    public static Component fromString(String string) {
        return Component.text(string);
    }

    /**
     * String array to Component array
     * @param strings String array
     * @return Component array
     */
    public static Component[] fromStringArray(String[] strings) {
        List<Component> list = Arrays.stream(strings).map(ComponentUtil::fromString).toList();
        Component[] components = new Component[list.size()];
        return list.toArray(components);
    }
}
