package cs3500.pa03;

import static org.junit.jupiter.api.Assertions.assertEquals;

import cs3500.pa03.Model.BattleShip;
import cs3500.pa03.Model.Coord;
import cs3500.pa03.Model.Ship;
import cs3500.pa03.Model.ShipType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests for Battleship
 */
public class BattleShipTest {
  private List<Ship> ships1;
  private Ship ship1;
  private Ship ship2;
  private BattleShip b1;
  private BattleShip b2;

  private ArrayList<Coord> coords1;
  private Map<ShipType, Integer> mapOfFleet;
  private ArrayList<String> fleetNumber;
  private ArrayList<String> fleetNumber2;

  private ArrayList<Ship> ships2;

  /**
   * Sets up the fields
   */
  @BeforeEach
  public void setUp(){
    coords1 = new ArrayList<>();
    coords1.add(new Coord(0, 0));
    ship1 = new Ship(coords1, ShipType.BattleShip, "HORIZONTAL");
    mapOfFleet = new HashMap<>();
    fleetNumber = new ArrayList<>();
    fleetNumber.add("1");
    fleetNumber.add("1");
    fleetNumber.add("1");
    fleetNumber.add("1");

    b1 = new BattleShip(10, 10, fleetNumber, new String[1][1]);
    ships2 = new ArrayList<>();
    ships2.add(ship1);

    fleetNumber2 = new ArrayList<>();
    fleetNumber2.add("3");
    fleetNumber2.add("3");
    fleetNumber2.add("4");
    fleetNumber2.add("3");
    b2 = new BattleShip(15, 15, fleetNumber2, new String[1][1]);

  }

  /**
   * Tests for setFleet method
   */
  @Test
  public void testSetFleet(){
    assertEquals(b1.setFleetShips().get(0).getType(), ShipType.Carrier);
    assertEquals(b1.setFleetShips().get(1).getType(), ShipType.BattleShip);
    assertEquals(b1.setFleetShips().get(1).getType(), ShipType.BattleShip);
    assertEquals(b1.setFleetShips().get(3).getType(), ShipType.Submarine);

    assertEquals(b2.setFleetShips().get(0).getType(), ShipType.Carrier);
    assertEquals(b1.setFleetShips().get(1).getType(), ShipType.BattleShip);
    assertEquals(b1.setFleetShips().get(2).getType(), ShipType.Destroyer);
    assertEquals(b1.setFleetShips().get(3).getType(), ShipType.Submarine);


  }

  /**
   * Tests for setUp Board method
   */
  @Test
  public void testSetUpBoard(){
    String[][] ex1 = b1.setUpBoard(ships2, new String[3][3]);
    String[][] ex2 = new String[3][3];
    ex2[0][0] = "B";
    assertEquals(ex1[0][0], "B");
    ships2.add( new Ship(coords1, ShipType.Carrier, "HORIZONTAL"));
    ships2.add( new Ship(coords1, ShipType.Destroyer, "VERTICAL"));
    ships2.add( new Ship(coords1, ShipType.Submarine, "VERTICAL"));
    ex1 = b1.setUpBoard(ships2, new String[6][6]);
    assertEquals(ex1[0][0], "S"  );


  }
}












