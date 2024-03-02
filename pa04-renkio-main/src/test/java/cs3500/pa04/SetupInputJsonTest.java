package cs3500.pa04;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import cs3500.pa04.MessageJson.FleetSpec;
import cs3500.pa04.MessageJson.SetupInputJson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SetupInputJsonTest {
  private String json;
  private ObjectMapper mapper;
  private SetupInputJson setupInputJson;
  @BeforeEach
  public void setUp(){
    json ="{\"width\": \"10\",\"height\": \"20\",\"fleet-spec\":{\"CARRIER\": 1, \"BATTLESHIP\": 2, \"DESTROYER\":3, \"SUBMARINE\":7}}";
    mapper = new ObjectMapper();

  }
  @Test
  public void testSetUpInputJson() throws JsonProcessingException {
    setupInputJson = mapper.readValue(json, SetupInputJson.class);
    int width = setupInputJson.width();
    int height = setupInputJson.height();
    FleetSpec fleetSpec = setupInputJson.fleetSpec();
    assertEquals(width, 10);
    assertEquals(height, 20);
    assertEquals(fleetSpec.battleship(), 2  );
    assertEquals(fleetSpec.carrier(), 1);
    assertEquals(fleetSpec.destroyer(), 3);
    assertEquals(fleetSpec.submarine(), 7);
  }
}
