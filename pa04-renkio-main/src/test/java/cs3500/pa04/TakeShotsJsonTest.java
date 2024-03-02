package cs3500.pa04;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import cs3500.pa04.MessageJson.TakeShotsJson;
import cs3500.pa04.json.CoordJson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TakeShotsJsonTest {
  private String json;
  private ObjectMapper mapper;
  private TakeShotsJson takeShotsJson;

  @BeforeEach
  public void setUp(){
    json ="{\"coordinates\": [{\"x\": 1, \"y\": 2}, {\"x\": 3, \"y\": 4}]}";
    mapper = new ObjectMapper();

  }
  @Test
  public void testTakeShotsJson() throws JsonProcessingException {
    takeShotsJson = mapper.readValue(json, TakeShotsJson.class);
    CoordJson[] list = takeShotsJson.coordList();
    assertEquals(list[0].x(), 1);
    assertEquals(list[0].y(), 2);
    assertEquals(list[1].x(), 3);
    assertEquals(list[1].y(), 4);
  }
}
