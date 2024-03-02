package cs3500.pa04;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import cs3500.pa03.Model.Coord;
import cs3500.pa03.Model.Player;
import cs3500.pa03.Model.Ship;
import cs3500.pa03.Model.ShipType;
import cs3500.pa03.View.GameResult;
import cs3500.pa04.MessageJson.EndGameJson;
import cs3500.pa04.MessageJson.FleetSpec;
import cs3500.pa04.MessageJson.JoinJson;
import cs3500.pa04.MessageJson.MessageJson;
import cs3500.pa04.MessageJson.ReportDamageJson;
import cs3500.pa04.MessageJson.SetupInputJson;
import cs3500.pa04.MessageJson.SetupJson;
import cs3500.pa04.MessageJson.SuccessfulHitsJson;
import cs3500.pa04.MessageJson.TakeShotsJson;
import cs3500.pa04.json.CoordJson;
import cs3500.pa04.json.JsonUtils;
import cs3500.pa04.json.ShipJson;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Class for ProxyController
 */
public class ProxyController {
  private final Socket server;
  private final InputStream in;
  private final PrintStream out;
  private final ObjectMapper mapper = new ObjectMapper();
  private final Player player;

  private static final JsonNode VOID_RESPONSE =
      new ObjectMapper().getNodeFactory().textNode("void");

  /**
   * Creates a proxyControlller
   * @param server The server program runs
   * @param player the aiPlayer
   * @throws IOException when server cannot connect
   */
  public ProxyController(Socket server, Player player) throws IOException {
    this.server = server;
    this.in = server.getInputStream();
    this.out = new PrintStream(server.getOutputStream());
    this.player = player;
    //CoordJson c = new CoordJson(5 , 7);
  }

  /**
   * Runs using ProxyController
   */
  public void run() {
    try {
      JsonParser parser = this.mapper.getFactory().createParser(this.in);
      while (!this.server.isClosed()) {
        MessageJson message = parser.readValueAs(MessageJson.class);
        delegateMessage(message);
        if (message.messageName().equals("end-game")) {
          this.server.close();
        }
      }
    } catch (IOException e) {
      throw new IllegalArgumentException();
    }

  }


  /**
   * Depending on the method, handle the json
   *
   * @param message messageJson that takes iput and output
   */
  public void delegateMessage(MessageJson message) {
    String name = message.messageName();
    JsonNode arguments = message.arguments();
    if (name.equals("join")) {
      handleJoin(arguments);
    } else if (name.equals("setup")) {
      handleSetup(arguments);
    } else if (name.equals("take-shots")) {
      handleTakeShots(arguments);
    } else if (name.equals("report-damage")) {
      handleReportDamage(arguments);
    } else if (name.equals("successful-hits")) {
      handleSuccessfulHits(arguments);
    } else if (name.equals("end-game")) {
      handleEndGame(arguments);
    } else {
      throw new IllegalStateException("Invalid message name.");
    }
  }


  /**
   * Handles join
   *
   * @param arguments no arguments to be set in join
   */
  private void handleJoin(JsonNode arguments) {
    JoinJson joinJson = new JoinJson("rikiosuzuki", "SINGLE");
    JsonNode jsonNode = this.mapper.convertValue(joinJson, JsonNode.class);
    MessageJson messageJson = new MessageJson("join", jsonNode);
    JsonNode jsonResponse = JsonUtils.serializeRecord(messageJson);
    this.out.println(jsonResponse);
  }

  /**
   * Handles setup
   *
   * @param arguments based on the board size and fleet size
   */
  private void handleSetup(JsonNode arguments) {
    SetupInputJson setupArgs = this.mapper.convertValue(arguments, SetupInputJson.class);
    FleetSpec fleet = this.mapper.convertValue(setupArgs.fleetSpec(), FleetSpec.class);
    Map<ShipType, Integer> specifications = new HashMap<>();
    specifications.put(ShipType.Carrier, fleet.carrier());
    specifications.put(ShipType.BattleShip, fleet.battleship());
    specifications.put(ShipType.Destroyer, fleet.destroyer());
    specifications.put(ShipType.Submarine, fleet.submarine());
    List<Ship> shipList = player.setup(setupArgs.height(), setupArgs.width(), specifications);
    List<ShipJson> listOfShips = new ArrayList<>();
    for (Ship ship : shipList) {
      ArrayList<Coord> coordList = ship.getCoords();
      Coord startCoord = coordList.get(0);
      CoordJson coordJson = new CoordJson(startCoord.getX(), startCoord.getY());
      ShipJson shipJson = new ShipJson(coordJson, coordList.size(), ship.getDirection());

      listOfShips.add(shipJson);
    }
    ShipJson[] shipJsonList = new ShipJson[shipList.size()];
    for (int i = 0; i < listOfShips.size(); i++) {
      shipJsonList[i] = listOfShips.get(i);
    }
    SetupJson setupJson = new SetupJson(shipJsonList);
    JsonNode jsonNode = this.mapper.convertValue(setupJson, JsonNode.class);
    MessageJson messageJson = new MessageJson("setup", jsonNode);
    JsonNode jsonResponse = JsonUtils.serializeRecord(messageJson);
    this.out.println(jsonResponse);
  }


  /**
   * Checks the takeShots method and handle it
   *
   * @param arguments takeshots do not return any output
   */
  private void handleTakeShots(JsonNode arguments) {
    // Ai.takeShots() -> list of shots<Coord>
    // loop through list of Coord -> list of CoordJson
    // create a new TakeShotJson with new list of CoordJson ( takeShotJson object )
    // convert this TakeShotJson into a JsonNode
    // create a new messageJson object
    // serialize this messageJson
    // calls serializeRecord(messageJson)

    List<Coord> listOfShots = player.takeShots();
    List<CoordJson> resultShots = new ArrayList<>();
    // creates a listof CoordJson
    for (int i = 0; i < listOfShots.size(); i++) {
      resultShots.add(new CoordJson(listOfShots.get(i).getX(), listOfShots.get(i).getY()));
    }
    CoordJson[] listOfCoords = new CoordJson[resultShots.size()];
    for (int i = 0; i < listOfShots.size(); i++) {
      listOfCoords[i] = resultShots.get(i);
    }
    TakeShotsJson takeShots = new TakeShotsJson(listOfCoords);
    JsonNode jsonNode = this.mapper.convertValue(takeShots, JsonNode.class);
    MessageJson message = new MessageJson("take-shots", jsonNode);
    JsonNode jsonResponse = JsonUtils.serializeRecord(message);
    this.out.println(jsonResponse);

  }

  /**
   * Handles the reportdamage json. Converts it to message
   *
   * @param arguments gets the hit coords of ships
   */
  private void handleReportDamage(JsonNode arguments) {
    ReportDamageJson reportDamageJson = this.mapper.convertValue(arguments, ReportDamageJson.class);
    List<CoordJson> opCoordList = reportDamageJson.coordList();
    List<Coord> tempList = new ArrayList<>();
    for (int i = 0; i < opCoordList.size(); i++) {
      CoordJson coordinates = opCoordList.get(i);
      tempList.add(new Coord(coordinates.x(), coordinates.y()));
    }
    List<Coord> opShots = this.player.reportDamage(tempList);
    List<CoordJson> hitCoords = new ArrayList<>();
    for (int j = 0; j < opShots.size(); j++) {
      Coord coord = opShots.get(j);
      hitCoords.add(new CoordJson(coord.getX(), coord.getY()));
    }
    ReportDamageJson reportDamageJson2 = new ReportDamageJson(hitCoords);
    JsonNode node = this.mapper.convertValue(reportDamageJson2, JsonNode.class);
    MessageJson message = new MessageJson("report-damage", node);
    JsonNode jsonResponse = JsonUtils.serializeRecord(message);
    this.out.println(jsonResponse);

  }

  /**
   * Checks if any successful hits
   *
   * @param arguments a list of hit coordinates
   */
  private void handleSuccessfulHits(JsonNode arguments) {
    SuccessfulHitsJson successfulHitsJson =
        this.mapper.convertValue(arguments, SuccessfulHitsJson.class);
    List<CoordJson> hitShots = successfulHitsJson.coordList();
    List<Coord> listOfHitShots = new ArrayList<>();
    for (int i = 0; i < hitShots.size(); i++) {
      CoordJson coordJson = hitShots.get(i);
      listOfHitShots.add(new Coord(coordJson.x(), coordJson.y()));
    }
    this.player.successfulHits(listOfHitShots);

    MessageJson message = new MessageJson("successful-hits", mapper.createObjectNode());
    JsonNode jsonResponse = JsonUtils.serializeRecord(message);
    this.out.println(jsonResponse);

  }

  /**
   * Handles the endgame json
   *
   * @param arguments whether the player won or lost
   */
  private void handleEndGame(JsonNode arguments) {
    EndGameJson endGameJson = this.mapper.convertValue(arguments, EndGameJson.class);
    String result = endGameJson.result();
    String reason = endGameJson.reason();
    this.player.endGame(GameResult.valueOf(result), reason);
    MessageJson message = new MessageJson("end-game", mapper.createObjectNode());
    JsonNode jsonResponse = JsonUtils.serializeRecord(message);
    this.out.println(jsonResponse);

  }


}
