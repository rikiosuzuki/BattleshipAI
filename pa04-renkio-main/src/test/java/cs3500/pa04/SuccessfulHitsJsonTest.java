package cs3500.pa04;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import cs3500.pa04.MessageJson.SuccessfulHitsJson;
import cs3500.pa04.json.CoordJson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SuccessfulHitsJsonTest {
  private String json;
  private ObjectMapper mapper;
  private SuccessfulHitsJson successfulHitsJson;
  @BeforeEach
  public void setUp(){
    json = "{\"coordinates\": [{\"x\": 1, \"y\": 2}, {\"x\": 3, \"y\": 4}]}";
    mapper = new ObjectMapper();

  }
  @Test
  public void testSuccessfulHitsJson() throws JsonProcessingException {
    successfulHitsJson = mapper.readValue(json, SuccessfulHitsJson.class);
    CoordJson[] list = successfulHitsJson.coordList().toArray(new CoordJson[0]);
    assertEquals(list[0].x(), 1);
    assertEquals(list[0].y(), 2);
    assertEquals(list[1].x(), 3);
    assertEquals(list[1].y(), 4);
  }
}
