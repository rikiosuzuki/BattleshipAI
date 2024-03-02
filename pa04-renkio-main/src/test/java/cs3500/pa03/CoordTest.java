package cs3500.pa03;

import static org.junit.jupiter.api.Assertions.assertEquals;

import cs3500.pa03.Model.Coord;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Test class for Coord
 */
public class CoordTest {
  private int x1;
  private int x2;
  private int y1;
  private int y2;
  private Coord c1;
  private Coord c2;

  /**
   * Initializes fields
   */
  @BeforeEach
  public void setUp(){
    x1 = 0;
    x2 = 1;
    y1 = 5;
    y2 = 6;
    c1 = new Coord(0, 5);
    c2 = new Coord(1, 6);
  }

  /**
   * Tests for getX
   */
  @Test
  public void testGetX(){
    assertEquals(c1.getX(), x1 );
    assertEquals(c2.getX(), x2);
  }

  /**
   * Tests for getY
   */
  @Test
  public void testGetY(){
    assertEquals(c1.getY(), y1 );
    assertEquals(c2.getY(), y2);
  }
}
