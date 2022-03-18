package si.f5.luna3419.lib.config.gson;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class GsonConfig {
    private String timezone;
    private String database;
}
