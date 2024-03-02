package cs3500.pa04;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import cs3500.pa03.Model.AiPlayer;
import cs3500.pa03.Model.Player;
import cs3500.pa03.Model.ShipType;
import cs3500.pa04.MessageJson.EndGameJson;
import cs3500.pa04.MessageJson.FleetSpec;
import cs3500.pa04.MessageJson.MessageJson;
import cs3500.pa04.MessageJson.ReportDamageJson;
import cs3500.pa04.MessageJson.SetupInputJson;
import cs3500.pa04.MessageJson.SuccessfulHitsJson;
import cs3500.pa04.json.CoordJson;
import cs3500.pa04.json.JsonUtils;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.ConnectException;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

/**
 * Tests for proxyController
 */
public class ProxyControllerTest {
  private ProxyController proxyController;
  private Socket mockSocket;
  private InputStream inputStream;
  private PrintStream outputStream;
  private String input;
  private ByteArrayOutputStream testLog;
  private Player player;
  private List<CoordJson> listEx2;

  /**
   * Initializes fields
   *
   * @throws IOException throws when cannot
   */
  @BeforeEach
  public void setUp() throws IOException {
    mockSocket = Mockito.mock(Socket.class);
    input = "{\"method-name\": \"end-game\", \"arguments\": {}}";

    inputStream = new ByteArrayInputStream(input.getBytes());
    outputStream = new PrintStream(new ByteArrayOutputStream());
    when(mockSocket.getInputStream()).thenReturn(inputStream);
    when(mockSocket.getOutputStream()).thenReturn(outputStream);
    player = new AiPlayer();
    Map<ShipType, Integer> map = new HashMap<>();
    map.put(ShipType.Carrier, 0);
    map.put(ShipType.BattleShip, 1);
    map.put(ShipType.Destroyer, 1);
    map.put(ShipType.Submarine, 0);
    player.setup(6, 6, map);
    player.takeShots();
    listEx2 = new ArrayList<>();
    listEx2.add(new CoordJson(0, 0));
    listEx2.add(new CoordJson(1, 0));
    listEx2.add(new CoordJson(2, 0));
    listEx2.add(new CoordJson(3, 0));
    listEx2.add(new CoordJson(4, 0));
    listEx2.add(new CoordJson(0, 1));
    listEx2.add(new CoordJson(1, 1));
    listEx2.add(new CoordJson(2, 1));
    listEx2.add(new CoordJson(3, 1));
    listEx2.add(new CoordJson(4, 1));
    listEx2.add(new CoordJson(0, 2));
    listEx2.add(new CoordJson(1, 2));
    listEx2.add(new CoordJson(2, 2));
    listEx2.add(new CoordJson(3, 2));
    listEx2.add(new CoordJson(4, 2));
    listEx2.add(new CoordJson(0, 3));
    listEx2.add(new CoordJson(1, 3));
    listEx2.add(new CoordJson(2, 3));
    listEx2.add(new CoordJson(3, 3));
    listEx2.add(new CoordJson(4, 3));
    listEx2.add(new CoordJson(0, 4));
    listEx2.add(new CoordJson(1, 4));
    listEx2.add(new CoordJson(2, 4));
    listEx2.add(new CoordJson(3, 4));
    listEx2.add(new CoordJson(4, 4));

    player.reportDamage(new ArrayList<>());
    player.successfulHits(new ArrayList<>());

    proxyController = new ProxyController(mockSocket, player);
    testLog = new ByteArrayOutputStream(2048);
    assertEquals("", logToString());
  }

  /**
   * Tests the run method
   */
  @Test
  public void run() {
    assertThrows(NullPointerException.class,
        () -> proxyController.run());
    ObjectMapper mapper = new ObjectMapper();
    MessageJson messageJson1 = new MessageJson("join", mapper.createObjectNode());
    proxyController.delegateMessage(messageJson1);
    assertEquals(messageJson1.messageName(), "join");
    FleetSpec fleetSpec = new FleetSpec(1, 0, 0, 1);
    SetupInputJson setupInputJson = new SetupInputJson(10, 10, fleetSpec);
    JsonNode jsonNode = mapper.convertValue(setupInputJson, JsonNode.class);
    MessageJson messageJson2 = new MessageJson("setup", jsonNode);
    proxyController.delegateMessage(messageJson2);
    assertEquals(messageJson2.messageName(), "setup");
    MessageJson messageJson3 = new MessageJson("take-shots", mapper.createObjectNode());
    proxyController.delegateMessage(messageJson3);
    assertEquals(messageJson3.messageName(), "take-shots");

    List<CoordJson> list = new ArrayList<>();
    list.add(new CoordJson(1, 1));

    SuccessfulHitsJson successfulHitsJson = new SuccessfulHitsJson(list);
    JsonNode jsonNode1 = mapper.convertValue(successfulHitsJson, JsonNode.class);
    MessageJson messageJson4 = new MessageJson("successful-hits", jsonNode1);
    proxyController.delegateMessage(messageJson4);
    assertEquals(messageJson4.messageName(), "successful-hits");

    ReportDamageJson reportDamageJson = new ReportDamageJson(list);
    JsonNode jsonNode3 = mapper.convertValue(reportDamageJson, JsonNode.class);
    MessageJson messageJson = new MessageJson("report-damage", jsonNode3);
    proxyController.delegateMessage(messageJson);
    assertEquals(messageJson.messageName(), "report-damage");

    ReportDamageJson reportDamageJson1 = new ReportDamageJson(listEx2);
    JsonNode jsonNode4 = mapper.convertValue(reportDamageJson1, JsonNode.class);
    MessageJson messageJson7 = new MessageJson("report-damage", jsonNode4);
    assertEquals(messageJson7.messageName(), "report-damage");


    EndGameJson endGameJson = new EndGameJson("WIN", "Player 1 won");
    JsonNode jsonNode2 = mapper.convertValue(endGameJson, JsonNode.class);


    MessageJson messageJson5 = new MessageJson("end-game", jsonNode2);
    proxyController.delegateMessage(messageJson5);
    assertEquals(messageJson5.messageName(), "end-game");
    MessageJson messageJson6 = new MessageJson("enadagda", mapper.createObjectNode());

    assertThrows(IllegalStateException.class,
        () -> proxyController.delegateMessage(messageJson6),
        "Invalid message name.");


  }

  /**
   * Tests for random
   */
  @Test
  public void testRandom() {
    RandomDecorator random = new RandomDecorator(20);
    assertFalse(random.nextInt() >= 0);
  }

  /**
   * Tests for driver
   */
  @Test
  public void testDriver() throws IOException {
    Driver driver = new Driver();
    String[] args = new String[2];
    args[0] = "0.0.0.0";
    args[1] = "35001";

    assertThrows(ConnectException.class,
        () -> Driver.main(args), "port out of range:12341124");


  }

  /**
   * Converts the ByteArrayOutputStream log to a string in UTF_8 format
   *
   * @return String representing the current log buffer
   */
  private String logToString() {
    return testLog.toString(StandardCharsets.UTF_8);
  }

  /**
   * Try converting the current test log to a string of a certain class.
   *
   * @param classRef Type to try converting the current test stream to.
   * @param <T>      Type to try converting the current test stream to.
   */
  private <T> void responseToClass(@SuppressWarnings("SameParameterValue") Class<T> classRef) {
    try {
      JsonParser jsonParser = new ObjectMapper().createParser(logToString());
      jsonParser.readValueAs(classRef);
      // No error thrown when parsing to a GuessJson, test passes!
    } catch (IOException e) {
      // Could not read
      // -> exception thrown
      // -> test fails since it must have been the wrong type of response.
      fail();
    }
  }

  /**
   * Create a MessageJson for some name and arguments.
   *
   * @param messageName   name of the type of message; "hint" or "win"
   * @param messageObject object to embed in a message json
   * @return a MessageJson for the object
   */
  private JsonNode createSampleMessage(String messageName, Record messageObject) {
    MessageJson messageJson =
        new MessageJson(messageName, JsonUtils.serializeRecord(messageObject));
    return JsonUtils.serializeRecord(messageJson);
  }
}