package cs3500.pa04.MessageJson;

import com.fasterxml.jackson.annotation.JsonProperty;
import cs3500.pa04.json.ShipJson;

/**
 * Json for setup
 *
 * @param shipList array of ShipJson
 */
public record SetupJson(
    @JsonProperty("fleet") ShipJson[] shipList) {
}
