package cs3500.pa03;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import cs3500.pa03.Model.Coord;
import cs3500.pa03.Model.ManuelPlayer;
import cs3500.pa03.Model.Ship;
import cs3500.pa03.Model.ShipType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * ManuelPlayer test class
 */
public class ManuelPlayerTest {

  private ManuelPlayer player1;
  private ArrayList<Coord> list1;
  private Coord coord;
  private ManuelPlayer player2;
  private ArrayList<Ship> shipList;
  private Ship ship1;
  private ArrayList<Coord> list2;
  private ManuelPlayer player3;
  private ArrayList<Coord> list3;
  private Map<ShipType, Integer> specs;


  /**
   * Sets up the fields used in testing
   */
  @BeforeEach
  public void setUp(){
    coord = new Coord(0, 0);
    list1 = new ArrayList<>();
    list1.add(coord);
    player1 = new ManuelPlayer(list1);

    ship1 = new Ship(list1, ShipType.BattleShip, "VERTICAL");
    shipList= new ArrayList<>();
    list2 = new ArrayList<>();
    list2.add(new Coord(0,0));

    shipList.add(new Ship(list2, ShipType.Carrier, "HORIZONTAL"));
    player2 = new ManuelPlayer(shipList, list2);

    list3 = new ArrayList<>();
    player3 = new ManuelPlayer("");
    specs =new HashMap<>();
    specs.put(ShipType.Carrier, 0);
    specs.put(ShipType.BattleShip, 1);
    specs.put(ShipType.Destroyer, 1);
    specs.put(ShipType.Submarine, 1);



  }
  /**
   * Test for Successful hits
   */
  @Test
  public void testSuccessfulHits(){
    player1.successfulHits(list1);
    assertEquals(list1.size(), 1);

    player2.successfulHits(list1);
    assertEquals(list1.size(), 2);
    list2 = new ArrayList<>();
    list2.add(new Coord(0, 1));
    player2 = new ManuelPlayer(shipList, list2);
    player2.successfulHits(list1);
    assertEquals(list1.size(), 2);
    list2 = new ArrayList<>();
    list2.add(new Coord(1, 0));
    player2 = new ManuelPlayer(shipList, list2);
    player2.successfulHits(list1);
    assertEquals(list1.size(), 2);
  }
  /**
   * Test name method
   */
  @Test
  public void testName(){
    assertNull(player1.name());
  }
  /**
   * Test takeShots method
   */
  @Test
  public void testTakeShots(){
    assertEquals(player1.takeShots(), list1);
  }
  /**
   * Tests for setUp
   */
  @Test
  public void testSetUp(){

    assertEquals(player3.setup(100, 100,specs ).size(), 3);
    assertEquals(player3.setup(100, 100,specs ).size(), 3);
    assertEquals(player3.setup(100, 100,specs ).size(), 3);
    assertEquals(player3.setup(100, 100,specs ).size(), 3);
    assertEquals(player3.setup(100, 100,specs ).size(), 3);
    assertEquals(player3.setup(100, 100,specs ).size(), 3);
    assertEquals(player3.setup(100, 100,specs ).size(), 3);
    assertEquals(player3.setup(100, 100,specs ).size(), 3);
    assertEquals(player3.setup(100, 100,specs ).size(), 3);

    specs =new HashMap<>();
    specs.put(ShipType.Carrier, 0);
    specs.put(ShipType.BattleShip, 0);
    specs.put(ShipType.Destroyer, 1);
    specs.put(ShipType.Submarine, 0);
    assertEquals(player3.setup(100, 100,specs ).size(), 1);
    assertEquals(player3.setup(100, 100,specs ).size(), 1);
    assertEquals(player3.setup(100, 100,specs ).size(), 1);
    assertEquals(player3.setup(100, 100,specs ).size(), 1);
    assertEquals(player3.setup(100, 100,specs ).size(), 1);
    assertEquals(player3.setup(100, 100,specs ).size(), 1);
    assertEquals(player3.setup(100, 100,specs ).size(), 1);
    assertEquals(player3.setup(100, 100,specs ).size(), 1);


    specs =new HashMap<>();
    specs.put(ShipType.Carrier, 0);
    specs.put(ShipType.BattleShip, 0);
    specs.put(ShipType.Destroyer, 0);
    specs.put(ShipType.Submarine, 1);

    assertEquals(player3.setup(100, 100,specs ).size(), 1);
    assertEquals(player3.setup(100, 100,specs ).size(), 1);
    assertEquals(player3.setup(100, 100,specs ).size(), 1);
    assertEquals(player3.setup(100, 100,specs ).size(), 1);
    assertEquals(player3.setup(100, 100,specs ).size(), 1);
    assertEquals(player3.setup(100, 100,specs ).size(), 1);
    assertEquals(player3.setup(100, 100,specs ).size(), 1);
    assertEquals(player3.setup(100, 100,specs ).size(), 1);

    specs =new HashMap<>();
    specs.put(ShipType.Carrier, 1);
    specs.put(ShipType.BattleShip, 1);
    specs.put(ShipType.Destroyer, 1);
    specs.put(ShipType.Submarine, 1);
    assertEquals(player3.setup(100, 100,specs ).size(), 4);
    assertEquals(player3.setup(100, 100,specs ).size(), 4);
    assertEquals(player3.setup(100, 100,specs ).size(), 4);
    assertEquals(player3.setup(100, 100,specs ).size(), 4);
    assertEquals(player3.setup(100, 100,specs ).size(), 4);
    assertEquals(player3.setup(100, 100,specs ).size(), 4);
    assertEquals(player3.setup(100, 100,specs ).size(), 4);


    assertThrows(IllegalArgumentException.class,
        () -> player3.setup(1, 1, specs),
        "Impossible to put fleet number on board. Too many ships. Run again");

  }




}
