package cs3500.pa04;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import cs3500.pa04.MessageJson.ReportDamageJson;
import cs3500.pa04.json.CoordJson;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ReportDamageJsonTest {
  private String json;
  private ObjectMapper mapper;
  private ReportDamageJson reportDamageJson;

  @BeforeEach
  public void setUp(){
    json = "{\"coordinates\": [{\"x\": 10, \"y\": 20}, {\"x\": 30, \"y\": 40}]}";
    mapper = new ObjectMapper();
  }
  @Test
  public void testReportDamageJson() throws JsonProcessingException {
    reportDamageJson = mapper.readValue(json, ReportDamageJson.class);
    List<CoordJson> list = reportDamageJson.coordList();
    List<CoordJson> compareList = new ArrayList<>();
    compareList.add(new CoordJson(10, 20));
    compareList.add(new CoordJson(30, 40));
    assertEquals(compareList, list );
  }
}
