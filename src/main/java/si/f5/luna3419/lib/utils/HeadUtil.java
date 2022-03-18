package si.f5.luna3419.lib.utils;

import java.lang.reflect.Field;
import java.util.List;
import java.util.UUID;

import com.google.gson.Gson;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import com.mojang.authlib.properties.PropertyMap;

import org.apache.commons.codec.binary.Base64;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

public class HeadUtil {
    private static final Base64 base64 = new Base64();

    private static final String profileUrl = "https://api.mojang.com/users/profiles/minecraft/${name}";
    private static final String propertiesUrl = "https://sessionserver.mojang.com/session/minecraft/profile/${uuid}";
    
    public static ItemStack getCustomSkull(String url) {
        return getFromBase64(new String(base64.encode(String.format("{textures:{SKIN:{url:\"%s\"}}}", url).getBytes())));
    }

    public static ItemStack getFromPlayerName(String name) {
        Profile profile = new Gson().fromJson(Utils.readUrl(profileUrl.replace("${name}", name)), Profile.class);

        return getFromUniqueId(UUID.fromString(profile.id));
    }

    public static ItemStack getFromUniqueId(UUID uuid) {
        Profile profile = new Gson().fromJson(Utils.readUrl(propertiesUrl.replace("${uuid}", uuid.toString())), Profile.class);

        return getFromBase64(profile.properties.get(0).value);
    }

    public static ItemStack getFromBase64(String encoded) {
        try {
            GameProfile profile = new GameProfile(UUID.randomUUID(), null);
            PropertyMap propertyMap = profile.getProperties();
            if (propertyMap == null) {
                throw new IllegalStateException("Profile doesn't contain a property map");
            }

            propertyMap.put("textures", new Property("textures", new String(encoded)));

            ItemStack head = new ItemStack(Material.PLAYER_HEAD, 1);
            SkullMeta headMeta;

            if (head.getItemMeta() instanceof SkullMeta) {
                headMeta = (SkullMeta) head.getItemMeta();
            } else {
                return new ItemStack(Material.PLAYER_HEAD, 1);
            }

            Class<?> headMetaClass = headMeta.getClass();

            Field field = headMetaClass.getDeclaredField("profile");
            field.setAccessible(true);
            field.set(headMeta, profile);

            head.setItemMeta(headMeta);
            return head;
        } catch (ReflectiveOperationException e) {
            e.printStackTrace();
        }

        return new ItemStack(Material.PLAYER_HEAD, 1);
    }

    public static class Profile {
        String name;
        String id;

        List<Property> properties;

        public static class Property {
            String name;
            String value;
        }
    }
}
