package cs3500.pa04.MessageJson;

import com.fasterxml.jackson.annotation.JsonProperty;
import cs3500.pa04.json.CoordJson;
import java.util.List;

/**
 * Json for SuccessfulHits
 *
 * @param coordList list of CoordJson
 */
public record SuccessfulHitsJson(
    @JsonProperty("coordinates") List<CoordJson> coordList) {
}
