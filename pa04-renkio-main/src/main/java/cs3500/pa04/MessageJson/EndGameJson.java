package cs3500.pa04.MessageJson;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Json for EndGame
 *
 * @param result Win, Loss, Tie
 * @param reason of the result
 */
public record EndGameJson(
    @JsonProperty("result") String result,
    @JsonProperty("reason") String reason) {
}
