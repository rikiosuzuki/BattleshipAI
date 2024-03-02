package cs3500.pa04;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import cs3500.pa04.MessageJson.EndGameJson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class EndGameJsonTest {

  private String json;
  private ObjectMapper mapper;
  private EndGameJson endGameJson;

  @BeforeEach
  public void setUp(){
    json = "{\"result\": \"WIN\", \"reason\": \"Player 1 won\"}";
    mapper = new ObjectMapper();
  }

  @Test
  public void testEndGameJson() throws JsonProcessingException {
    endGameJson = mapper.readValue(json, EndGameJson.class);
    String result = endGameJson.result();
    String reason = endGameJson.reason();

    assertEquals("WIN", result);
    assertEquals("Player 1 won", reason);
  }



}
