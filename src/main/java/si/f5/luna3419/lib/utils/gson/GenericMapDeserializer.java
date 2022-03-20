package si.f5.luna3419.lib.utils.gson;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Set;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

/**
 * GenericMapDeserializer<T> : Map<String, T> へのデシリアライザ
 */
public final class GenericMapDeserializer<T> implements JsonDeserializer<HashMap<String, T>> {
   @Override
   @SuppressWarnings("unchecked")
   public HashMap<String, T> deserialize(JsonElement jsonElement, Type typeOfT, JsonDeserializationContext context)
         throws JsonParseException {

      if (!jsonElement.isJsonObject()) {
         return null;
      }
      JsonObject jsonObject = jsonElement.getAsJsonObject();
      Set<Entry<String, JsonElement>> jsonEntrySet = jsonObject.entrySet();
      HashMap<String, T> deserializedMap = new HashMap<String, T>();

      for (Entry<String, JsonElement> entry : jsonEntrySet) {
         try {
            if (entry.getValue().isJsonNull()) {
               deserializedMap.put(entry.getKey(), null);
            } else if (entry.getValue().isJsonArray()) {
               deserializedMap.put(entry.getKey(), (T) entry.getValue());
            } else if (entry.getValue().isJsonObject()) {
               deserializedMap.put(entry.getKey(), (T) entry.getValue());
            } else if (entry.getValue().isJsonPrimitive()) {
               deserializedMap.put(entry.getKey(), context.deserialize(entry.getValue(), String.class));
            }
         } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
         }
      }
      return deserializedMap;
   }
}