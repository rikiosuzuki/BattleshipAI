package cs3500.pa04;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import cs3500.pa04.MessageJson.MessageJson;
import cs3500.pa04.json.JsonUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests for JsonUtils
 */
public class JsonUtilsTest {

  private ObjectMapper mapper;
  /**
   * Initialize fields
   */
  @BeforeEach
  public void setup(){
    mapper = new ObjectMapper();
  }

  /**
   * Tests for serializeRecord
   */
  @Test
  void testSerializeRecord() {
    MessageJson record = new MessageJson("join", mapper.createArrayNode());
    JsonNode jsonNode = JsonUtils.serializeRecord(record);
    String expectedJson = "{\"method-name\":\"join\",\"arguments\":[]}";
    assertEquals(expectedJson, jsonNode.toString());
    MessageJson record2 = new MessageJson("joifdasfdsagn", mapper.createArrayNode());
  }
}