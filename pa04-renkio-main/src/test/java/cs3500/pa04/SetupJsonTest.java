package cs3500.pa04;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import cs3500.pa04.MessageJson.SetupJson;
import cs3500.pa04.json.CoordJson;
import cs3500.pa04.json.ShipJson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SetupJsonTest {
  private String json;
  private ObjectMapper mapper;
  private SetupJson setUpJson;
  @BeforeEach
  public void setUp(){
    json = "{\"fleet\": [" +
        "{\"coord\": {\"x\": 1, \"y\": 2}, \"length\": 3, \"direction\": \"horizontal\"}," +
        "{\"coord\": {\"x\": 4, \"y\": 5}, \"length\": 2, \"direction\": \"vertical\"}" +
        "]}";
    mapper = new ObjectMapper();
  }
  @Test
  public void testSetupJson() throws JsonProcessingException {
    setUpJson = mapper.readValue(json, SetupJson.class);
    ShipJson[] fleet = setUpJson.shipList();

    assertEquals(fleet[0].length(), 3 );
    assertEquals(fleet[0].coord(), new CoordJson(1, 2));
    assertEquals(fleet[0].direction(), "horizontal" );
    assertEquals(fleet[1].length(), 2);
    assertEquals(fleet[1].coord(), new CoordJson(4, 5));
    assertEquals(fleet[1].direction(), "vertical" );

  }
}
