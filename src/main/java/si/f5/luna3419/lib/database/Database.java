package si.f5.luna3419.lib.database;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.UUID;

import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import si.f5.luna3419.lib.Library;
import si.f5.luna3419.lib.database.player.PlayerData;

public class Database {
    private Connection connection;
    private IPlayerDatabase database;

    public Database(Connection connection, IPlayerDatabase database) {
        this.connection = connection;

        init();

        this.database = database == null ? new PlayerDatabase(connection) : database;

        this.database.init();
    }

    private void init() {
        if (this.connection == null) {
            try {
                Class.forName("org.sqlite.JDBC");
    
                connection = DriverManager.getConnection("jdbc:sqlite:" + new File(Library.getInstance().getDataFolder(), Library.getLoadedConfig().getDatabase()).getAbsolutePath());
            } catch (ClassNotFoundException | SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public PlayerData getPlayerData(Player player) {
        return getPlayerData(player.getUniqueId());
    }

    public PlayerData getPlayerData(OfflinePlayer player) {
        return getPlayerData(player.getUniqueId());
    }

    public PlayerData getPlayerData(UUID uuid) {
        return database.getPlayerData(uuid);
    }

    public void setPlayerData(PlayerData data) {
        database.setPlayerData(data);
    }
}
