package cs3500.pa04;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import cs3500.pa04.MessageJson.JoinJson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class JoinJsonTest {
  private String json;
  private ObjectMapper mapper;
  private JoinJson joinJson;

  @BeforeEach
  public void setUp(){
    json = "{\"name\": \"rikiosuzuki\", \"game-type\": \"SINGLE\"}";
    mapper = new ObjectMapper();

  }

  @Test
  public void testJoinJson() throws JsonProcessingException {
    joinJson = mapper.readValue(json, JoinJson.class);
    String name = joinJson.username();
    String gameType = joinJson.gameType();

    assertEquals("rikiosuzuki", name);

    assertEquals("SINGLE", gameType);

  }


}
