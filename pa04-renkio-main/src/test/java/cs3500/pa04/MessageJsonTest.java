package cs3500.pa04;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import cs3500.pa04.MessageJson.MessageJson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * MessageJson test class
 */
public class MessageJsonTest {
  private String json;
  private ObjectMapper mapper;
  private MessageJson messageJson;

  /**
   * Initialize the fields
   */
  @BeforeEach
  public void setUp(){
    json = "{\"method-name\": \"methodName\", \"arguments\": {}}";
    mapper = new ObjectMapper();
  }

  /**
   * Tests the messageJson
   * @throws JsonProcessingException when not possible
   */
  @Test
  public void testMessageJson() throws JsonProcessingException {
    messageJson = mapper.readValue(json, MessageJson.class);
    String methodName = messageJson.messageName();
    JsonNode arguments = messageJson.arguments();

    assertEquals("methodName", methodName);
    assertEquals("{}", arguments.toString());

  }



}
