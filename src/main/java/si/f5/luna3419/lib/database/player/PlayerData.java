package si.f5.luna3419.lib.database.player;

import java.util.UUID;

import com.google.gson.Gson;

import lombok.Data;

@Data
public class PlayerData {
    private UUID uuid;

    public PlayerData(UUID uuid) {
        this.uuid = uuid;
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}