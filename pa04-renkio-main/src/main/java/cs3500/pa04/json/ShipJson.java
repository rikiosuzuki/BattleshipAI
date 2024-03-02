package cs3500.pa04.json;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Json for Ship
 *
 * @param coord     ship coords
 * @param length    ship size
 * @param direction ship direction
 */
public record ShipJson(
    @JsonProperty("coord") CoordJson coord,
    @JsonProperty("length") int length,
    @JsonProperty("direction") String direction) {
}
