package cs3500.pa04.MessageJson;

import com.fasterxml.jackson.annotation.JsonProperty;
import cs3500.pa04.json.CoordJson;

/**
 * Json for TakeShots
 *
 * @param coordList list of Coordinates
 */
public record TakeShotsJson(
    @JsonProperty("coordinates") CoordJson[] coordList) {
}
