package cs3500.pa04.MessageJson;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Json for setupInput
 *
 * @param width     boardX
 * @param height    boardY
 * @param fleetSpec how many ships there are
 */
public record SetupInputJson(
    @JsonProperty("width") int width,
    @JsonProperty("height") int height,
    @JsonProperty("fleet-spec") FleetSpec fleetSpec) {
}
