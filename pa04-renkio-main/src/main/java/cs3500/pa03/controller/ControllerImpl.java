package cs3500.pa03.controller;

import cs3500.pa03.Model.AiPlayer;
import cs3500.pa03.Model.BattleShip;
import cs3500.pa03.Model.Coord;
import cs3500.pa03.Model.ManuelPlayer;
import cs3500.pa03.Model.Ship;
import cs3500.pa03.View.PrintOutput;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

/**
 * Controller class that manages userInput
 */
public class ControllerImpl {
  private final Readable input;

  private Scanner sc;
  public BattleShip game1;
  public BattleShip game2;

  public ControllerImpl(Readable i) {
    input = i;
  }


  /**
   * Runs the game Battleship
   */
  public void run() {
    ReaderImpl reader = new ReaderImpl(input, "ok");
    String name = askUserName(reader);
    String gameSize = chooseSizeOfGame(reader);
    String boardX = gameSize.substring(0, 2).replace(" ", "");
    String boardY = gameSize.substring(2).replace(" ", "");
    ArrayList<String> fleet = chooseFleetInOrder(reader);
    String[][] board1 = startUp(Integer.parseInt(boardX), Integer.parseInt(boardY));
    String[][] board2 = startUp(Integer.parseInt(boardX), Integer.parseInt(boardY));
    game1 = new BattleShip(Integer.parseInt(boardX), Integer.parseInt(boardY), fleet, board1);
    game2 = new BattleShip(Integer.parseInt(boardX), Integer.parseInt(boardY), fleet, board2);
    List<Ship> listUser = game1.setFleetShips();
    List<Ship> listOpponent = game2.setFleetShips();
    PrintOutput p = new PrintOutput();
    String[][] setUpBoard = game1.setUpBoard(listUser, board1);
    String[][] setUpOpponent = game2.setUpBoard(listOpponent, board2);
    String[][] setUpForOpponent = opponentBoard(Integer.parseInt(boardX), Integer.parseInt(boardY));
    p.printOpponentBoard(setUpOpponent);
    p.printBoard(setUpBoard, name);
    ArrayList<Coord> opHitShots = new ArrayList<>();
    ArrayList<Coord> opMissedShots = new ArrayList<>();
    boolean running = true;
    while (running) {
      AiPlayer player2 =
          new AiPlayer(name, Integer.parseInt(boardX), Integer.parseInt(boardY), opMissedShots,
              opHitShots, listOpponent);
      ArrayList<Coord> userShots =
          askUserShots(reader, listUser, Integer.parseInt(boardX), Integer.parseInt(boardY));

      ArrayList<Coord> userHitShots = checkUserHitShots(userShots, listOpponent);
      ArrayList<Coord> userMissedShots = checkUserMissedShots(userHitShots, userShots);
      ArrayList<Coord> temp = new ArrayList<>(player2.takeShots());
      opHitShots.addAll(checkUserHitShots(temp, listUser));
      opMissedShots.addAll(checkUserMissedShots(opHitShots, temp));
      String[][] changedOpBoard = changeUserBoard(userHitShots, userMissedShots, setUpForOpponent);
      String[][] changedUsBoard = changeUserBoard(opHitShots, opMissedShots, setUpBoard);
      p.printBoard(changedOpBoard, "Opponent");
      p.printBoard(changedUsBoard, name);
      p.printHitOrMissMessages(userHitShots, userMissedShots);
      listUser = checkAnyDestroyedShips(listUser, changedUsBoard);
      listOpponent = checkAnyDestroyedShips(listOpponent, changedOpBoard);

      if (listUser.size() == 0 || listOpponent.size() == 0) {
        running = false;
        if (listUser.size() == 0) {
          System.out.println("The Opponent Won!");
        } else if (listOpponent.size() == 0) {
          System.out.println("You Won!");
        } else {
          System.out.println("It's a tie!");
        }
      }
    }
  }

  /**
   * Checks if there are any destroyed ships
   *
   * @param listUser List of ships
   * @param usBoard  see if any Hs are on the board
   * @return a new list of ships if ship is destroyed
   */
  public List<Ship> checkAnyDestroyedShips(List<Ship> listUser, String[][] usBoard) {
    List<Ship> result = new ArrayList<>();
    ArrayList<Integer> listOfHitX = new ArrayList<>();
    for (int i = 0; i < usBoard.length; i++) {
      for (int j = 0; j < usBoard[0].length; j++) {
        if (usBoard[i][j].equals("H")) {
          listOfHitX.add(i + j);
        }
      }
    }
    for (int i = 0; i < listUser.size(); i++) {
      ArrayList<Coord> shipCoords = listUser.get(i).getCoords();
      int shipSize = shipCoords.size();
      ArrayList<Integer> pos = new ArrayList<>();
      for (int j = 0; j < shipCoords.size(); j++) {
        pos.add(shipCoords.get(j).getX() + shipCoords.get(j).getY());
      }
      pos.addAll(listOfHitX);

      Set<Integer> set = new HashSet<Integer>(pos);
      if (pos.size() - set.size() != shipSize) {
        result.add(listUser.get(i));
      }
    }
    return result;

  }


  /**
   * changes the userBoard based on shots and hits
   *
   * @param hitShots    list of hit shots
   * @param missedShots list of missed shots
   * @param setup       original board without anythign
   * @return update every shot on the new board
   */
  public String[][] changeUserBoard(ArrayList<Coord> hitShots, ArrayList<Coord> missedShots,
                                    String[][] setup) {
    if (hitShots.size() == 0 && missedShots.size() == 0) {
      return setup;
    } else if (hitShots.size() == 0) {
      for (int j = 0; j < missedShots.size(); j++) {
        int x2 = missedShots.get(j).getX();
        int y2 = missedShots.get(j).getY();
        setup[x2][y2] = "M";
      }
    } else if (missedShots.size() == 0) {
      for (int i = 0; i < hitShots.size(); i++) {
        int x = hitShots.get(i).getX();
        int y = hitShots.get(i).getY();
        setup[x][y] = "H";
      }
    } else {

      for (int i = 0; i < hitShots.size(); i++) {
        int x = hitShots.get(i).getX();
        int y = hitShots.get(i).getY();
        setup[x][y] = "H";
      }
      for (int j = 0; j < missedShots.size(); j++) {
        int x2 = missedShots.get(j).getX();
        int y2 = missedShots.get(j).getY();
        setup[x2][y2] = "M";
      }
    }
    return setup;
  }

  /**
   * Checks if the shots hit the opponent
   *
   * @param userShots     given list of coordinates of shots
   * @param opponentShips list of ships
   * @return the coordinates of the hits
   */
  public ArrayList<Coord> checkUserHitShots(ArrayList<Coord> userShots, List<Ship> opponentShips) {
    ArrayList<Coord> result = new ArrayList<>();
    for (int i = 0; i < opponentShips.size(); i++) {
      Ship ship = opponentShips.get(i);
      for (int j = 0; j < userShots.size(); j++) {
        Coord coords = userShots.get(j);
        for (int k = 0; k < ship.getCoords().size(); k++) {
          if (coords.getX() == ship.getCoords().get(k).getX()) {
            if (coords.getY() == ship.getCoords().get(k).getY()) {
              result.add(coords);
            }
          }
        }
      }
    }
    return result;
  }

  /**
   * Checks if the shots missed the opponent
   *
   * @param hitShots all the hitshots
   * @param shots    all the shots
   * @return the list of all the shots not in hitshots
   */
  public ArrayList<Coord> checkUserMissedShots(ArrayList<Coord> hitShots, ArrayList<Coord> shots) {
    ArrayList<Coord> result = shots;
    result.removeAll(hitShots);
    return result;
  }

  /**
   * Creates the opponent board
   *
   * @param x board size x
   * @param y board size y
   * @return a 2d array of string that represents the opponent board
   */
  public String[][] opponentBoard(int x, int y) {
    String[][] result = new String[x][y];
    for (int k = 0; k < x; k++) {
      for (int l = 0; l < y; l++) {
        result[k][l] = "0";
      }
    }
    return result;
  }

  /**
   * Asks the user to shoot
   *
   * @param reader   readable that checks user input
   * @param listUser see how many shots you need
   * @param boardX   board size
   * @param boardY   board size
   * @return a list of coords of shots
   */
  public ArrayList<Coord> askUserShots(ReaderImpl reader, List<Ship> listUser, int boardX,
                                       int boardY) {
    System.out.println();
    System.out.println("Shoot " + listUser.size() + " shots!");
    System.out.println("Vertical Horizontal");
    System.out.println("ex: 0 0;");
    System.out.println("    1 1;");
    System.out.println("--------------------------------------");
    String userIn = reader.read();


    ArrayList<Coord> listOfShots = new ArrayList<>();
    String[] temp = userIn.split(";");
    ArrayList<String> list = new ArrayList<>(Arrays.asList(temp));
    if (list.size() != listUser.size()) {
      System.out.println("Invalid input! Try again.");
      return askUserShots(reader, listUser, boardX, boardY);
    }
    for (int i = 0; i < listUser.size(); i++) {
      if (list.get(i).length() == 3) {
        if (isUserInputForShots(list.get(i), boardX, boardY)) {
          String x = list.get(i).substring(0, 2).replaceAll(" ", "");
          String y = list.get(i).substring(2).replaceAll(" ", "");
          if (Integer.parseInt(x) >= boardX || Integer.parseInt(y) >= boardY) {
            System.out.println("Input out of range, try again");
            return askUserShots(reader, listUser, boardX, boardY);
          }
          listOfShots.add(new Coord(Integer.parseInt(x), Integer.parseInt(y)));
        }
      } else {
        System.out.println("Invalid input, try again");
        return askUserShots(reader, listUser, boardX, boardY);
      }
    }
    if (listOfShots.size() != listUser.size()) {
      System.out.println("Need more shots, try again");
      return askUserShots(reader, listUser, boardX, boardY);
    }
    ManuelPlayer player1 = new ManuelPlayer(listOfShots);
    ArrayList<Coord> result = new ArrayList<>(player1.takeShots());
    return result;
  }

  /**
   * Checks the validity of userInput
   *
   * @param userIn user input
   * @param boardX boardsize
   * @param boardY boardSize
   * @return true or false if its valid
   */
  private boolean isUserInputForShots(String userIn, int boardX, int boardY) {
    if (userIn.indexOf(" ") == 2 || userIn.indexOf(" ") == 1) {
      String x = userIn.substring(0, 2).replace(" ", "");
      String y = userIn.substring(2).replace(" ", "");
      if (Integer.parseInt(x) >= 0 && Integer.parseInt(x) < boardX && Integer.parseInt(y) >= 0 &&
          Integer.parseInt(y) <= boardY) {
        return true;
      }
    }
    return false;
  }


  /**
   * Asks the user for the name
   *
   * @param reader reads the userinput
   * @return the user input
   */
  public String askUserName(ReaderImpl reader) {
    System.out.println("Enter your name");
    String userIn = reader.read();
    return userIn;
  }

  /**
   * Asks the user for order
   *
   * @param reader to be able to read userinput
   * @return list of number of ships user wants to play with
   */
  public ArrayList<String> chooseFleetInOrder(ReaderImpl reader) {
    System.out.println(
        "----------------------------------------------------------------------------");
    System.out.println(
        "Enter fleet in order(Carrier, Battleship, Destroyer, Submarine) and enter ok:");
    System.out.println("Remember that fleet cannot exceed 8");
    System.out.println(
        "----------------------------------------------------------------------------");
    String userIn = reader.read();
    ArrayList<String> fleetNumbers = new ArrayList<>();
    if (userIn.length() == 7) {
      if (isUserInputForFleetValid(userIn, reader)) {
        String numCarrier = userIn.substring(0, 1);
        String numBattleship = userIn.substring(2, 3);
        String numDestroyer = userIn.substring(4, 5);
        String numSubmarine = userIn.substring(6);
        fleetNumbers.add(numCarrier);
        fleetNumbers.add(numBattleship);
        fleetNumbers.add(numDestroyer);
        fleetNumbers.add(numSubmarine);
        return fleetNumbers;
      }
    }
    System.out.println("You've entered invalid fleet numbers! Try again!");
    return chooseFleetInOrder(reader);

  }

  /**
   * Checks the validity of userinput
   *
   * @param userIn user input
   * @param reader reader that reads string
   * @return true or false if userinput is valid
   */
  private boolean isUserInputForFleetValid(String userIn, ReaderImpl reader) {
    String[] temp = userIn.split("");
    List<String> list = Arrays.asList(temp);
    for (int i = 1; i < 6; i += 2) {
      if (!list.get(i).equals(" ")) {
        return false;
      }
    }
    for (int i = 0; i <= 6; i += 2) {
      try {
        int number = Integer.parseInt(list.get(i));
      } catch (NumberFormatException e) {
        System.out.println("You've entered invalid numbers! Retry!");
        chooseFleetInOrder(reader);
      }
      if (Integer.parseInt(list.get(i)) > 8 && Integer.parseInt(list.get(i)) < 0) {
        return false;
      }
    }
    return true;


  }

  /**
   * Asks the user for size of game
   *
   * @param reader that reads the userinput
   * @return the Size of the game
   */
  public String chooseSizeOfGame(ReaderImpl reader) {
    System.out.println(
        "----------------------------------------------------------------------------");
    System.out.println("Choose size of game and enter ok:");
    System.out.println(
        "----------------------------------------------------------------------------");
    String userIn = reader.read();
    if (isUserInputForSizeValid(userIn)) {
      String x = userIn.substring(0, 2).replace(" ", "");
      String y = userIn.substring(2).replace(" ", "");
      return x + " " + y;
    }
    System.out.println("You've entered invalid dimensions! Retry!");
    return chooseSizeOfGame(reader);
  }

  /**
   * Checks the validity of User input
   *
   * @param userIn user input
   * @return true or false if user input is valid
   */
  private boolean isUserInputForSizeValid(String userIn) {
    if (userIn.indexOf(" ") == 2 || userIn.indexOf(" ") == 1) {
      String x = userIn.substring(0, 2).replace(" ", "");
      String y = userIn.substring(2).replace(" ", "");
      if (Integer.parseInt(x) >= 6 && Integer.parseInt(x) <= 15 && Integer.parseInt(y) >= 6 &&
          Integer.parseInt(y) <= 15) {
        return true;
      }
    }
    return false;
  }

  /**
   * Start up that creates a board with no values
   *
   * @param x board size
   * @param y board size
   * @return new 2d array of string with no values
   */
  public String[][] startUp(int x, int y) {
    String[][] userBoard = new String[x][y];
    String[][] opponentBoard = new String[x][y];
    for (int i = 0; i < userBoard.length; i++) {
      for (int j = 0; j < userBoard[0].length; j++) {
        userBoard[i][j] = "0";
        opponentBoard[i][j] = "0";
      }
    }
    return userBoard;
  }


}
