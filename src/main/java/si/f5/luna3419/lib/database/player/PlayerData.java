package si.f5.luna3419.lib.database.player;

import java.util.HashMap;
import java.util.UUID;

import com.google.gson.Gson;

import lombok.Data;

@Data
public class PlayerData {
    private UUID uuid;
    private HashMap<String, Object> data;

    public PlayerData(UUID uuid) {
        this.uuid = uuid;
        this.data = new HashMap<>();
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}