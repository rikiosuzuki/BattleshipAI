package cs3500.pa03;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import cs3500.pa03.Model.AiPlayer;
import cs3500.pa03.Model.Coord;
import cs3500.pa03.Model.Player;
import cs3500.pa03.Model.Ship;
import cs3500.pa03.Model.ShipType;
import cs3500.pa03.View.GameResult;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.management.remote.rmi.RMIConnectionImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Test class for AIPlayer
 */
public class AIPlayerTest {
  private String name;
  private int boardX;
  private int boardY;
  private ArrayList<Coord> missedShots;
  private ArrayList<Coord> hitShots;
  private List<Ship> ships;
  private Ship ship1;
  private Coord pos;

  private AiPlayer player;
  private AiPlayer player2;
  private Map<ShipType, Integer> map3;

  private Map<ShipType, Integer> map;
  private List<Coord> listEx1;
  private List<Coord> listEx2;

  /**
   * Initializes the fields
   */
  @BeforeEach
  public void setUp() {
    name = "rikio";
    boardX = 2;
    boardY = 2;
    missedShots = new ArrayList<>();
    hitShots = new ArrayList<>();
    pos = new Coord(0, 0);
    ArrayList<Coord> temp = new ArrayList<>();
    temp.add(pos);
    ship1 = new Ship(temp, ShipType.Carrier, "VERTICAL");
    ships = new ArrayList<>();
    ships.add(ship1);

    player = new AiPlayer(name, boardX, boardY, missedShots, hitShots, ships);
    player2 = new AiPlayer();
    map = new HashMap<>();
    map.put(ShipType.Carrier, 1);
    map.put(ShipType.BattleShip, 0);
    map.put(ShipType.Destroyer, 0);
    map.put(ShipType.Submarine, 1);

    map3 = new HashMap<>();
    map3.put(ShipType.Carrier, 1);
    map3.put(ShipType.BattleShip, 1);
    map3.put(ShipType.Destroyer, 1);
    map3.put(ShipType.Submarine, 1);
    listEx1 = new ArrayList<>();
    listEx1.add(new Coord(0, 0));
    listEx1.add(new Coord(1, 0));
    listEx1.add(new Coord(2, 0));
    listEx1.add(new Coord(3, 0));
    listEx1.add(new Coord(4, 0));
    listEx1.add(new Coord(5, 0));
    listEx1.add(new Coord(6, 0));
    listEx1.add(new Coord(7, 0));
    listEx1.add(new Coord(8, 0));
    listEx1.add(new Coord(9, 0));
    listEx1.add(new Coord(10, 0));
    listEx1.add(new Coord(11, 0));
    listEx1.add(new Coord(1, 1));
    listEx1.add(new Coord(1, 2));
    listEx1.add(new Coord(2, 3));
    listEx1.add(new Coord(3, 4));
    listEx1.add(new Coord(4, 5));
    listEx1.add(new Coord(0, 6));
    listEx1.add(new Coord(4, 7));
    listEx1.add(new Coord(5, 8));
    listEx1.add(new Coord(6, 9));
    listEx1.add(new Coord(6, 10));
    listEx1.add(new Coord(4, 3));
    listEx1.add(new Coord(6, 2));
    listEx1.add(new Coord(8, 1));

    listEx2 = new ArrayList<>();
    listEx2.add(new Coord(0, 0));
    listEx2.add(new Coord(1, 0));
    listEx2.add(new Coord(2, 0));
    listEx2.add(new Coord(3, 0));
    listEx2.add(new Coord(4, 0));
    listEx2.add(new Coord(0, 1));
    listEx2.add(new Coord(1, 1));
    listEx2.add(new Coord(2, 1));
    listEx2.add(new Coord(3, 1));
    listEx2.add(new Coord(4, 1));
    listEx2.add(new Coord(0, 2));
    listEx2.add(new Coord(1, 2));
    listEx2.add(new Coord(2, 2));
    listEx2.add(new Coord(3, 2));
    listEx2.add(new Coord(4, 2));
    listEx2.add(new Coord(0, 3));
    listEx2.add(new Coord(1, 3));
    listEx2.add(new Coord(2, 3));
    listEx2.add(new Coord(3, 3));
    listEx2.add(new Coord(4, 3));
    listEx2.add(new Coord(0, 4));
    listEx2.add(new Coord(1, 4));
    listEx2.add(new Coord(2, 4));
    listEx2.add(new Coord(3, 4));
    listEx2.add(new Coord(4, 4));


  }


  /**
   * Tests for name method
   */
  @Test
  public void testName() {
    assertEquals(player.name(), "rikio");
  }

  /**
   * Tests for Setup method
   */
  @Test
  public void testSetUp() {
    assertEquals(player2.setup(6, 6, map).get(0).getType(), ShipType.Carrier);
    assertEquals(player2.setup(10, 10, map).get(1).getType(), ShipType.Submarine);

    Map<ShipType, Integer> map2 = new HashMap<>();
    map2.put(ShipType.Carrier, 111);
    map2.put(ShipType.BattleShip, 21);
    map2.put(ShipType.Destroyer, 123);
    map2.put(ShipType.Submarine, 11);
    assertThrows(IllegalArgumentException.class,
        () -> player2.setup(10, 10, map2),
        "Impossible to put fleet number on board. Too many ships. Run again");

    Player player1 = new AiPlayer();
    assertEquals(player1.setup(100, 100, map3).get(0).getType(), ShipType.Carrier);
    assertEquals(player1.setup(100, 100, map3).get(1).getType(), ShipType.BattleShip);
    assertEquals(player1.setup(100, 100, map3).get(2).getType(), ShipType.Destroyer);
    assertEquals(player1.setup(100, 100, map3).get(3).getType(), ShipType.Submarine);
    assertThrows(IllegalArgumentException.class,
        () -> player2.setup(1, 1, map3),
        "Impossible to put all ships. Rerun and try again");
    assertThrows(IllegalArgumentException.class,
        () -> player2.setup(6, 6, map3),
        "Impossible to put all ships. Rerun and try again");


    Player player3 = new AiPlayer();
    Map<ShipType, Integer> map4 = new HashMap<>();
    map4.put(ShipType.Carrier, 8);
    map4.put(ShipType.BattleShip, 8);
    map4.put(ShipType.Destroyer, 8);
    map4.put(ShipType.Submarine, 8);
    List<Ship> list = player3.setup(100, 100, map4);
    assertEquals(list.get(0).getType(), ShipType.Carrier);
    Map<ShipType, Integer> finalMap = map4;
    assertThrows(IllegalArgumentException.class,
        () -> player2.setup(6, 6, finalMap),
        "Impossible to put all ships. Rerun and try again");

    player3 = new AiPlayer();
    map4 = new HashMap<>();
    map4.put(ShipType.Carrier, 8);
    map4.put(ShipType.BattleShip, 8);
    map4.put(ShipType.Destroyer, 8);
    map4.put(ShipType.Submarine, 8);
    list = player3.setup(100, 100, map4);
    assertEquals(list.get(0).getType(), ShipType.Carrier);

  }

  /**
   * Tests for takeShots method
   */
  @Test
  public void testTakeShots() {
    assertEquals(player2.takeShots(), new ArrayList<>());
    player2 = new AiPlayer();
    player2.setup(12, 12, map3);
    assertTrue(player2.takeShots().get(0).getX() >= 0);
    assertTrue(player2.takeShots().get(0).getY() < 12);

    List<Coord> list1 = new ArrayList<>();
    list1.add(new Coord(1, 0));
    assertTrue(player2.takeShots().get(0).getY() >= 0);

    player2.successfulHits(listEx1);
    assertTrue(player2.takeShots().get(0).getX() >= 0);
    assertTrue(player2.takeShots().get(0).getX() >= 0);

    Player aiPlayer = new AiPlayer(list1);
    assertEquals(aiPlayer.takeShots(), new ArrayList<>());


  }


  /**
   * Tests for reportDamage method
   */
  @Test
  public void testReportDamage() {
    List<Coord> opList = new ArrayList<>();
    opList.add(new Coord(1, 1));
    assertEquals(player2.reportDamage(opList), new ArrayList<>());
    Player player1 = new AiPlayer();
    player1.setup(6, 6, map);
    assertTrue(player1.reportDamage(listEx2).get(0).getX() >= 0);
    assertTrue(player1.reportDamage(listEx2).get(0).getY() < 6);

  }

  /**
   * Tests for EndGame method
   */
  @Test
  public void testEndGame() {
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    PrintStream printStream = new PrintStream(outputStream);
    System.setOut(printStream);
    player2.endGame(GameResult.LOSE, "Player 1 lost");
    player2.endGame(GameResult.WIN, "Player 1 won");
    player2.endGame(GameResult.TIE, "Player 1 tied");

    String actual = outputStream.toString().trim();
    String expected = "Player 2 won! Player 1 lost\nPlayer 1 won! Player 1 won\nYou tied!";

    assertEquals(expected, actual);
  }


}
