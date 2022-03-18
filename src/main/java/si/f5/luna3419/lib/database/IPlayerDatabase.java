package si.f5.luna3419.lib.database;

import java.util.UUID;

import si.f5.luna3419.lib.database.player.PlayerData;

public interface IPlayerDatabase {
    void init();

    PlayerData getPlayerData(UUID uuid);

    void setPlayerData(PlayerData playerData);
}
