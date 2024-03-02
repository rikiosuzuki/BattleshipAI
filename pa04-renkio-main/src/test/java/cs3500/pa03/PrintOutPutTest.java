package cs3500.pa03;

import static org.junit.jupiter.api.Assertions.assertEquals;

import cs3500.pa03.Model.Coord;
import cs3500.pa03.Model.Ship;
import cs3500.pa03.View.GameResult;
import cs3500.pa03.View.PrintOutput;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Test class for printoutput
 */
public class PrintOutPutTest {

  private String[][] board1;
  private String[][] board2;
  private String[][] board3;
  private PrintOutput p;
  private ArrayList<Coord> userHits;
  private ArrayList<Coord> userMisses;


  /**
   * Setup for fields
   */
  @BeforeEach
  public void setUp(){
    board1 = new String[2][2];
    p = new PrintOutput();
    userHits = new ArrayList<>();
    userMisses = new ArrayList<>();
    userMisses.add(new Coord(0, 0));
    userHits.add(new Coord(1, 1));

  }
  /**
   * Tests for printopponentBoard method
   */
  @Test
  public void testPrintOpBoard(){
    ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    System.setOut(new PrintStream(outContent));

    p.printOpponentBoard(board1);
    assertEquals("Opponent Board\n 0  0 \n 0  0 \n", outContent.toString());

    System.setOut(System.out);

  }
  /**
   * Tests for printBoard
   */
  @Test
  public void testPrintBoard(){
    ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    System.setOut(new PrintStream(outContent));

    p.printBoard(board1, "rikio");
    assertEquals("rikio board\n null  null \n null  null \n", outContent.toString());
    System.setOut(System.out);

  }
  /**
   * Tests
   * for printHitOrmissMessages method
   */
  @Test
  public void testPrintHitOrMissMessages(){
    ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    System.setOut(new PrintStream(outContent));
    p.printHitOrMissMessages(userHits, userMisses);
    assertEquals("You Hit at: 11\nYou Missed at: 00\n", outContent.toString());
    System.setOut(System.out);


  }
  /**
   * Tests for GameResult enum
   */
  @Test
  public void testGameResult(){
    GameResult g = GameResult.valueOf("TIE");
    assertEquals(GameResult.TIE, g);

    GameResult h = GameResult.valueOf("WIN");
    assertEquals(GameResult.WIN, h);

    GameResult k = GameResult.valueOf("LOSE");
    assertEquals(GameResult.LOSE, k);

  }



}
