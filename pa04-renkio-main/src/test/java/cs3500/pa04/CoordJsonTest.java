package cs3500.pa04;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import cs3500.pa04.json.CoordJson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CoordJsonTest {
  private String json;
  private ObjectMapper mapper;
  private CoordJson coordJson;

  @BeforeEach
  public void setUp(){
    json = "{\"x\": \"3\", \"y\": \"1\"}";
    mapper = new ObjectMapper();
  }

  @Test
  public void testCoordJson() throws JsonProcessingException {
    coordJson = mapper.readValue(json, CoordJson.class);
    int x = coordJson.x();
    int y = coordJson.y();
    assertEquals(3, x);
    assertEquals(1, y);
  }
}
