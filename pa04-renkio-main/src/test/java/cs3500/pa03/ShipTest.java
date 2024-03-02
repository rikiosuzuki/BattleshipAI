package cs3500.pa03;

import static org.junit.jupiter.api.Assertions.assertEquals;

import cs3500.pa03.Model.Coord;
import cs3500.pa03.Model.Ship;
import cs3500.pa03.Model.ShipType;
import java.util.ArrayList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests for ship
 */
public class ShipTest {
  private ShipType type;
  private ArrayList<Coord> coords;
  private Coord coord1;
  private Coord coord2;
  private Ship ship;

  /**
   * Initializes the fields for testing
   */
  @BeforeEach
  public void setUp(){
    type = ShipType.Carrier;
    coord1 = new Coord(1, 2);
    coord2 = new Coord(0 , 0);
    coords = new ArrayList<>();
    coords.add(coord1);
    ship = new Ship(coords, type, "VERTICAL");
  }

  /**
   * Tests for getCoords method
   */
  @Test
  public void testGetCoords(){
    ArrayList<Coord> list = new ArrayList<>();
    list.add(coord1);
    assertEquals(ship.getCoords(), list );
  }

  /**
   * Tests for getType method
   */
  @Test
  public void testGetType(){
    assertEquals(ship.getType(), ShipType.Carrier);
    assertEquals(ship.getType().getSize(), ShipType.Carrier.getSize());
  }

}
