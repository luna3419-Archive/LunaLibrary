package si.f5.luna3419.lib.utils.generator;

import java.util.UUID;

public class RandomNameGenerator {
    public static String generateRandomNames() {
        return UUID.randomUUID().toString().substring(0, 8);
    }
}
