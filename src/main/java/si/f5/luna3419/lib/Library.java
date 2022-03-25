package si.f5.luna3419.lib;

import java.io.File;
import java.sql.Connection;
import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import lombok.Getter;
import lombok.Setter;
import si.f5.luna3419.lib.config.Config;
import si.f5.luna3419.lib.database.Database;
import si.f5.luna3419.lib.database.IPlayerDatabase;
import si.f5.luna3419.lib.listeners.LogInOutListener;
import si.f5.luna3419.lib.packet.PacketAPI;
import si.f5.luna3419.lib.player.LibPlayer;

/**
 * LunaLibrary 1.0.0
 * 
 * @author luna3419
 */
public class Library extends JavaPlugin {
    @Getter private static Library instance;
    @Getter private static Config loadedConfig;

    @Setter private static IPlayerDatabase playerDatabase = null;
    @Setter private static Connection connection = null;

    @Getter private Database database;
    @Getter private PacketAPI packetPipeline;

    @Override
    public void onEnable() {
        instance = this;

        this.packetPipeline = new PacketAPI();

        registerListeners(
            new EventListener(),
            new LogInOutListener()
        );

        saveDefaultConfig();
        loadedConfig = Config.loadConfig();

        this.database = new Database(connection, playerDatabase);
    }

    @Override
    public void onDisable() {
        LibPlayer.saveAll();
    }

    private void registerListeners(Listener... listeners) {
        Arrays.stream(listeners).forEach(listener -> Bukkit.getPluginManager().registerEvents(listener, this));
    }

    @Override
    public void saveDefaultConfig() {
        if (!new File(getDataFolder(), "config.json").exists()) {
            saveResource("config.json", false);
        }
    }

    @Override
    public void reloadConfig() {
        loadedConfig = Config.loadConfig();
    }
}
