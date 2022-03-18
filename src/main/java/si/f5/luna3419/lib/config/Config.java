package si.f5.luna3419.lib.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import com.google.gson.Gson;

import lombok.Getter;
import lombok.experimental.PackagePrivate;
import si.f5.luna3419.lib.Library;
import si.f5.luna3419.lib.config.gson.GsonConfig;

public class Config {
    private final GsonConfig config;

    @Getter
    private String timezone;

    @Getter
    private String database;

    @PackagePrivate
    public Config(GsonConfig config) {
        this.config = config;

        init();
    }

    private void init() {
        this.timezone = config.getTimezone();
        this.database = config.getDatabase();
    }

    /**
     * コンフィグを取得します。
     * @return コンフィグ
     */
    public static Config loadConfig() {
        Gson gson = new Gson();

        try {
            GsonConfig config = gson.fromJson(new InputStreamReader(new FileInputStream(
                new File(Library.getInstance().getDataFolder(), "config.json")
            )), GsonConfig.class);

            return new Config(config);
        } catch(IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * カスタムされたGsonConfigからConfigを生成します
     * @param config カスタムされたコンフィグ
     * @return コンフィグ
     */
    @SuppressWarnings("unuse")
    public static Config getCustomedConfig(GsonConfig config) {
        return new Config(config);
    }
}
