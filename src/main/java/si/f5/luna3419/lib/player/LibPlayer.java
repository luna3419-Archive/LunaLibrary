package si.f5.luna3419.lib.player;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import lombok.Getter;
import lombok.experimental.PackagePrivate;
import si.f5.luna3419.lib.Library;
import si.f5.luna3419.lib.database.player.PlayerData;

public class LibPlayer {
    private static HashMap<UUID, LibPlayer> playerMap = new HashMap<>();

    @Getter
    private final UUID uuid;

    @Getter
    private final PlayerData data;

    @PackagePrivate
    public LibPlayer(UUID uuid) {
        this.uuid = uuid;

        this.data = Library.getInstance().getDatabase().getPlayerData(uuid);
    }

    public Player getPlayer() {
        return Bukkit.getPlayer(uuid);
    }

    public static void registerPlayer(Player player) {
        playerMap.put(player.getUniqueId(), new LibPlayer(player.getUniqueId()));
    }

    public static void unregisterPlayer(Player player) {
        PlayerData data = playerMap.get(player.getUniqueId()).getData();

        Library.getInstance().getDatabase().setPlayerData(data);

        playerMap.remove(player.getUniqueId());
    }

    public static void registerPlayers(Player... players) {
        Arrays.stream(players).forEach(LibPlayer::registerPlayer);
    }

    public static void registerPlayers(Collection<? extends Player> players) {
        players.forEach(LibPlayer::registerPlayer);
    }

    public static void refreshPlayers() {
        playerMap.clear();

        registerPlayers(Bukkit.getOnlinePlayers());
    }

    public static void saveAll() {
        playerMap.values().stream().map(LibPlayer::getData).forEach(Library.getInstance().getDatabase()::setPlayerData);
    }
}