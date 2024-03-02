package cs3500.pa04.MessageJson;

import com.fasterxml.jackson.annotation.JsonProperty;
import cs3500.pa04.json.CoordJson;
import java.util.List;

/**
 * Json for reportDamage
 *
 * @param coordList list of CoordJsons
 */
public record ReportDamageJson(
    @JsonProperty("coordinates") List<CoordJson> coordList) {
}
