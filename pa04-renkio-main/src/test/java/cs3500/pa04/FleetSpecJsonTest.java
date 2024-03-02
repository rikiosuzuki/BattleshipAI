package cs3500.pa04;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import cs3500.pa04.MessageJson.FleetSpec;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Test class for fleetspecjson
 */
public class FleetSpecJsonTest {

  private String json;
  private ObjectMapper mapper;
  private FleetSpec fleetSpecJson;

  /**
   * Initialize the fields
   */
  @BeforeEach
  public void setUp() {
    json =
        "{\"CARRIER\": \"2\", \"BATTLESHIP\": \"3\", \"DESTROYER\": \"1\", \"SUBMARINE\": \"5\"}";
    mapper = new ObjectMapper();
  }

  /**
   * Tests for fleetspec json
   *
   * @throws JsonProcessingException when not possible
   */
  @Test
  public void testFleecSpecJson() throws JsonProcessingException {
    fleetSpecJson = mapper.readValue(json, FleetSpec.class);
    int carrier = fleetSpecJson.carrier();
    int battleship = fleetSpecJson.battleship();
    int destroyer = fleetSpecJson.destroyer();
    int submarine = fleetSpecJson.submarine();
    assertEquals(2, carrier);
    assertEquals(3, battleship);
    assertEquals(1, destroyer);
    assertEquals(5, submarine);
  }


}
