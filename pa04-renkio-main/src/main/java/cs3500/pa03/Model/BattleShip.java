package cs3500.pa03.Model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Class that represents the game Battleship
 */
public class BattleShip {

  private String[][] board;
  private final int boardX;
  private final int boardY;
  private ArrayList<String> fleetNumber;
  private ManuelPlayer manualPlayer;
  private Map<ShipType, Integer> mapOfFleet;
  private int numberOfShips;

  public BattleShip(int x, int y, ArrayList<String> f, String[][] b) {
    boardX = x;
    boardY = y;
    fleetNumber = f;
    board = b;
  }

  /**
   * Sets the ships for setup
   *
   * @return a list of all the ships on the board
   */
  public List<Ship> setFleetShips() {
    mapOfFleet = new HashMap<>();
    mapOfFleet.put(ShipType.Carrier, Integer.parseInt(fleetNumber.get(0)));
    mapOfFleet.put(ShipType.BattleShip, Integer.parseInt(fleetNumber.get(1)));
    mapOfFleet.put(ShipType.Destroyer, Integer.parseInt(fleetNumber.get(2)));
    mapOfFleet.put(ShipType.Submarine, Integer.parseInt(fleetNumber.get(3)));

    manualPlayer = new ManuelPlayer("");
    List<Ship> listOfShipCoords = manualPlayer.setup(boardY, boardX, mapOfFleet);
    numberOfShips = listOfShipCoords.size();
    return listOfShipCoords;

  }

  /**
   * Sets up the board of a 2D array
   *
   * @param ships  list of ships on the baord
   * @param result given board from setup
   * @return newly created board
   */
  public String[][] setUpBoard(List<Ship> ships, String[][] result) {
    for (int i = 0; i < ships.size(); i++) {
      ArrayList<Coord> coords = ships.get(i).getCoords();
      for (int j = 0; j < coords.size(); j++) {
        int x = coords.get(j).getX();
        int y = coords.get(j).getY();
        if (ships.get(i).getType() == ShipType.Carrier) {
          result[x][y] = "C";
        } else if (ships.get(i).getType() == ShipType.BattleShip) {
          result[x][y] = "B";
        } else if (ships.get(i).getType() == ShipType.Destroyer) {
          result[x][y] = "D";
        } else {
          result[x][y] = "S";
        }
      }
    }
    return result;
  }


}
