package cs3500.pa04.MessageJson;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Json for FleetSpec
 *
 * @param carrier    how many
 * @param battleship how many
 * @param destroyer  how many
 * @param submarine  how many
 */
public record FleetSpec(
    @JsonProperty("CARRIER") int carrier,
    @JsonProperty("BATTLESHIP") int battleship,
    @JsonProperty("DESTROYER") int destroyer,
    @JsonProperty("SUBMARINE") int submarine) {
}
