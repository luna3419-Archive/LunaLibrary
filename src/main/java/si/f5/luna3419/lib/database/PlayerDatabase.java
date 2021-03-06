package si.f5.luna3419.lib.database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.UUID;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import si.f5.luna3419.lib.database.player.PlayerData;
import si.f5.luna3419.lib.utils.gson.GenericMapDeserializer;

public record PlayerDatabase(Connection connection) implements IPlayerDatabase {
    public void init() {
        try (Statement statement = newStatement()) {
            statement.execute("CREATE TABLE IF NOT EXISTS player(uuid TEXT, data TEXT)");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public PlayerData getPlayerData(UUID uuid) {
        String s = null;
        Gson gson = new GsonBuilder().registerTypeAdapter(new TypeToken<HashMap<String, Object>>(){}.getType(), new GenericMapDeserializer<Object>()).serializeNulls().create();
        try (Statement statement = newStatement()) {
            ResultSet set = statement.executeQuery("SELECT * FROM player WHERE uuid = '" + uuid + "';");
            while (set.next()) {
                if (set.getString("uuid").equals(uuid.toString())) {
                    s = set.getString("data");
                }
            }

            if (s == null) {
                statement.executeUpdate("INSERT INTO player VALUES('" + uuid.toString() + "', '" + new PlayerData(uuid).toString() + "');");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return s == null ? new PlayerData(uuid) : gson.fromJson(s, PlayerData.class);
    }

    @Override
    public void setPlayerData(PlayerData playerData) {
        try (Statement statement = newStatement()) {
            statement.executeUpdate("UPDATE player SET data = '" + playerData.toString() + "' WHERE uuid = '" + playerData.getUuid().toString() + "';");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Statement newStatement() throws SQLException {
        return connection.createStatement();
    }
}
