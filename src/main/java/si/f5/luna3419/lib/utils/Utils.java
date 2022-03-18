package si.f5.luna3419.lib.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

public class Utils {
    public static String readUrl(String url) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(
            new URL(url).openStream()
        ))) {
            StringBuffer buffer = new StringBuffer();
            int read;
            char[] chars = new char[1024];
            while ((read = reader.read(chars)) != -1) {
                buffer.append(chars, 0, read);
            }

            return buffer.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }
}
