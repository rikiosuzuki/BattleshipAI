package cs3500.pa03.View;

import cs3500.pa03.Model.Coord;
import java.util.ArrayList;

/**
 * Prints the output for user
 */
public class PrintOutput {


  /**
   * Prints the opponent board to user
   *
   * @param opponentBoard setup
   */
  public void printOpponentBoard(String[][] opponentBoard) {
    System.out.println("Opponent Board");
    for (int k = 0; k < opponentBoard.length; k++) {
      for (int l = 0; l < opponentBoard[0].length; l++) {
        System.out.print(" " + 0 + " ");
      }
      System.out.println();
    }
  }

  /**
   * Prints the Board to the user
   *
   * @param userBoard
   * @param player
   */
  public void printBoard(String[][] userBoard, String player) {
    System.out.println(player + " board");
    for (int k = 0; k < userBoard.length; k++) {
      for (int l = 0; l < userBoard[0].length; l++) {
        System.out.print(" " + userBoard[k][l] + " ");
      }
      System.out.println();
    }
  }

  /**
   * Checks if the shot is a hit or miss and print message.
   *
   * @param userHitShots    list of all hit shots
   * @param userMissedShots list of all missed shots
   */
  public void printHitOrMissMessages(ArrayList<Coord> userHitShots,
                                     ArrayList<Coord> userMissedShots) {
    for (int i = 0; i < userHitShots.size(); i++) {
      System.out.println("You Hit at: " + userHitShots.get(i).getX() + userHitShots.get(i).getY());
    }
    for (int i = 0; i < userMissedShots.size(); i++) {
      System.out.println(
          "You Missed at: " + userMissedShots.get(i).getX() + userMissedShots.get(i).getY());
    }
  }
}
