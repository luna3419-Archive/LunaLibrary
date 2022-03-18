package si.f5.luna3419.lib.utils;

import java.util.Arrays;
import java.util.function.Supplier;

import com.google.gson.Gson;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import si.f5.luna3419.lib.Library;
import si.f5.luna3419.lib.date.DateTime;

public class TimeUtil {
    private static Supplier<String> TIME_ZONE = Library.getLoadedConfig()::getTimezone;

    public static DateTime getCurrentTime() {
        String jsonData = Utils.readUrl("https://worldtimeapi.org/api/timezone/" + TIME_ZONE.get());

        WorldTime worldTime = new Gson().fromJson(jsonData, WorldTime.class);

        int[] date = Arrays.stream(worldTime.datetime.split("T")[0].split("-"))
                .mapToInt(Integer::parseInt).toArray();
        int[] time = Arrays.stream(worldTime.datetime.split("T")[1].split(".")[0].split(":"))
                .mapToInt(Integer::parseInt).toArray();

        return new DateTime(date[0], date[1], date[2], time[0], time[1], time[2]);
    }
    
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class WorldTime {
        String abbreviation;
        String client_ip;
        String datetime;
        int day_of_week;
        int day_of_year;
        boolean dest;
        String dst_from;
        int dst_offset;
        String dst_until;
        int raw_offset;
        String timezone;
        long unixtime;
        String utc_datetime;
        String utc_offset;
        int week_number;
    } 
}
