package cs3500.pa03;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

import cs3500.pa03.Model.BattleShip;
import cs3500.pa03.Model.Coord;
import cs3500.pa03.Model.Ship;
import cs3500.pa03.Model.ShipType;
import cs3500.pa03.View.PrintOutput;
import cs3500.pa03.controller.ControllerImpl;
import cs3500.pa03.controller.ReaderImpl;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

/**
 * Tests for controller class
 */
public class ControllerImplTest {
  private Readable readable;
  private ControllerImpl controller1;
  private ControllerImpl controller2;
  private List<Ship> list;
  private Ship ship1;
  private ArrayList<Coord> coords;
  private ArrayList<Coord> hitShots1;
  private ArrayList<Coord> missShots1;
  private ReaderImpl reader;

  /**
   * Initializes the fields
   */
  @BeforeEach
  public void setUp(){
    readable = new InputStreamReader(System.in);
    controller1 = new ControllerImpl(readable);
    list = new ArrayList<>();
    coords = new ArrayList<>();
    coords.add(new Coord(0, 0));
    ship1 = new Ship(coords, ShipType.BattleShip, "VERTICAL" );
    list.add(ship1);
    hitShots1 = new ArrayList<>();
    hitShots1.add(new Coord(0, 0));
    missShots1 = new ArrayList<>();
    missShots1.add(new Coord(0, 1));
    reader = new ReaderImpl(readable, "ok");
  }
  /**
   * Tests for chooseSizeOfGame method
   */
  @Test
  public void testSizeGame(){
    StringReader sr = new StringReader("6 6\nok");
    ReaderImpl reader1 = new ReaderImpl(sr, "ok");
    ControllerImpl controller3 = new ControllerImpl(sr);

    String data = "6 6";
    InputStream inputStream = new ByteArrayInputStream(data.getBytes());
    System.setIn(inputStream);
    assertEquals(controller3.chooseSizeOfGame(reader1), "6 6");



    System.setIn(System.in);

  }
  /**
   * Tests for chooseFleetinOrder method
   */
  @Test
  public void testChooseFleet(){
    StringReader sr = new StringReader("0 0 0 1\nok");

    ReaderImpl reader1 = new ReaderImpl(sr, "ok");
    ControllerImpl controller3 = new ControllerImpl(sr);

    String data = "0 0 0 1";
    InputStream inputStream = new ByteArrayInputStream(data.getBytes());
    System.setIn(inputStream);
    ArrayList<String> list = new ArrayList<>();
    list.add("0");
    list.add("0");
    list.add("0");
    list.add("1");
    assertEquals(controller3.chooseFleetInOrder(reader1), list);




    System.setIn(System.in);

  }
  /**
   * Tests for askuserName
   */
  @Test
  public void testAskUserName(){
    assertEquals(controller1.askUserName(reader), "");
  }
  /**
   * Tests for askUserShots method
   */
  @Test
  public void testAskUserShots(){
    List<Ship> ex2 = new ArrayList<>();
    ex2.add(ship1);
    StringReader sr = new StringReader("0 0;\nok");
    ReaderImpl reader1 = new ReaderImpl(sr, "ok");
    ControllerImpl controller3 = new ControllerImpl(sr);
    String data = "";
    InputStream inputStream = new ByteArrayInputStream(data.getBytes());
    System.setIn(inputStream);
    ArrayList<Coord> list = controller3.askUserShots(reader1,ex2 , 3, 3);
    System.setIn(System.in);
    assertEquals(list.get(0).getX(), 0);





  }

  /**
   * Test for opponent board
   */
  @Test
  public void testOpponentBoard(){
    String[][] ex1 = controller1.opponentBoard(2, 2);
    assertEquals(ex1[0][0], "0");
  }
  /**
   * Tests for checkUserMissedShots
   */
  @Test
  public void checkUserMissedShotsTest(){
    ArrayList<Coord> ex1 = controller1.checkUserMissedShots(hitShots1, hitShots1);
    assertEquals(ex1, new ArrayList<>());
  }

  /**
   * Tests for checkuserHitShots method
   */
  @Test
  public void checkUserHitShotsTest(){
    ArrayList<Coord> ex1 = controller1.checkUserHitShots(missShots1, list);
    ArrayList<Coord> ex2 = controller1.checkUserHitShots(hitShots1, list);
    assertEquals(ex1, new ArrayList<>());
    assertEquals(ex2.get(0).getY(), 0);

  }

  /**
   * Tests for checkAnyDestroyedships method
   */
  @Test
  public void startUpTest(){
    String[][] ex1 = controller1.startUp(5, 5);
    assertEquals(ex1[0][0] ,"0"   );

  }

  /**
   * Tests for run
   */
  @Test
  public void runTest(){
    StringReader input = new StringReader("6 6\nok");
    ControllerImpl controller = new ControllerImpl(input);
    String input4 = "6 6\nok";
    InputStream inputStream1 = new ByteArrayInputStream(input4.getBytes());
    System.setIn(inputStream1);
    //controller.run();
    System.setIn(System.in);

  }

  /**
   * Tests for checkAnyDestroyedShips
   */
  @Test
  public void checkAnyDestroyedShipsTest(){
    String[][] ex1 = new String[3][3];
    assertNull(ex1[0][0]);
    for(int i=0; i<ex1.length; i++){
      for(int j=0; j<ex1[0].length; j++){
        ex1[i][j] = "0";
      }
    }
    assertEquals(controller1.checkAnyDestroyedShips(list, ex1).get(0).getType(), ShipType.BattleShip);
    ex1[0][0] = "H";
    assertEquals(controller1.checkAnyDestroyedShips(list, ex1), new ArrayList<>());
  }
  /**
   * Tests for changeUserBoard method
   */
  @Test
  public void changeUserBoardTest(){
    String[][] ex1 =controller1.changeUserBoard(hitShots1, missShots1, new String[2][2]);
    assertEquals(ex1[0][0], "H");
    assertEquals(ex1[0][1], "M");
    String[][] ex2 =controller1.changeUserBoard(new ArrayList<>(), missShots1, new String[2][2]);
    String[][] ex3 =controller1.changeUserBoard(hitShots1, new ArrayList<>(), new String[2][2]);
    String[][] ex4 =controller1.changeUserBoard(new ArrayList<>(), new ArrayList<>(), new String[2][2]);
    assertEquals(ex4[0][0], null);





  }



}
