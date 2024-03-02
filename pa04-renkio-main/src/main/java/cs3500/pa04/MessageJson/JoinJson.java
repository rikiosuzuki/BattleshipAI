package cs3500.pa04.MessageJson;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Json for Join
 *
 * @param username github username
 * @param gameType Single or Multi player
 */
public record JoinJson(
    @JsonProperty("name") String username,
    @JsonProperty("game-type") String gameType) {
}
