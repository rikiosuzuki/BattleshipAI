package cs3500.pa03.Model;

import cs3500.pa03.View.GameResult;
import cs3500.pa03.controller.ReaderImpl;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Class that represents the user player in this game.
 */
public class ManuelPlayer implements Player {
  private String name;
  private ArrayList<Coord> coords;
  private List<Ship> shipList;
  private ArrayList<Coord> shots;
  private int count = 0;
  private ArrayList<Coord> shotList;
  private ReaderImpl reader;
  private List<Ship> user;

  public ManuelPlayer(String n) {
    name = n;
    coords = new ArrayList<>();
    shipList = new ArrayList<>();
  }

  public ManuelPlayer(ArrayList<Coord> l) {
    shotList = l;
    shipList = new ArrayList<>();
  }

  public ManuelPlayer(ArrayList<Ship> l, ArrayList<Coord> coords) {
    shipList = l;
    shots = coords;
  }


  /**
   * Get the player's name.
   *
   * @return the player's name
   */
  @Override
  public String name() {
    return name;
  }

  /**
   * Given the specifications for a BattleSalvo board, return a list of ships with their locations
   * on the board.
   *
   * @param height         the height of the board, range: [6, 15] inclusive
   * @param width          the width of the board, range: [6, 15] inclusive
   * @param specifications a map of ship type to the number of occurrences each ship should
   *                       appear on the board
   * @return the placements of each ship on the board
   */
  @Override
  public List<Ship> setup(int height, int width, Map<ShipType, Integer> specifications) {
    Random r = new Random();
    List<Ship> shipLocation = new ArrayList<>();
    ArrayList<Coord> allPossibleCoords = getAllCoords(height, width);
    if (allPossibleCoords.size() <= (specifications.get(ShipType.Carrier) * 6)
        + (specifications.get(ShipType.BattleShip) * 5)
        + (specifications.get(ShipType.Destroyer) * 4)
        + (specifications.get(ShipType.Submarine) * 3)) {
      throw new IllegalArgumentException(
          "Impossible to put fleet number on board. Too many ships. Run again");
    }

    for (int i = 0; i < specifications.get(ShipType.Carrier); i++) {
      count = 0;
      shipLocation.add(placeCarrier(height, width));
    }
    for (int j = 0; j < specifications.get(ShipType.BattleShip); j++) {
      count = 0;
      shipLocation.add(placeBattleShip(height, width));
    }
    for (int k = 0; k < specifications.get(ShipType.Destroyer); k++) {
      count = 0;
      shipLocation.add(placeDestroyer(height, width));
    }
    for (int l = 0; l < specifications.get(ShipType.Submarine); l++) {
      count = 0;
      shipLocation.add(placeSubmarine(height, width));
    }

    return shipLocation;
  }

  /**
   * setup to place Submarine
   *
   * @param y board size of vertical
   * @param x board size of horizontal
   * @return the object ship's coords
   */
  private Ship placeSubmarine(int y, int x) {
    if (count > 10) {
      throw new IllegalArgumentException("Impossible to put all ships. Rerun and try again.");
    }
    final int size = 3;
    Random r = new Random();
    ArrayList<Coord> shipCoords = new ArrayList<>();
    // if vertical
    if (r.nextInt(2) == 1) {
      int randomIndexY = r.nextInt(y - size + 1); // 0 1 2 3 4 5 6 7 8 9
      int randomIndexX = r.nextInt(x);
      for (int i = 0; i < size; i++) {
        if (coords.size() == 0) {
          coords.add(new Coord(randomIndexX, randomIndexY + i));
          shipCoords.add(new Coord(randomIndexX, randomIndexY + i));
        } else if (!checkCoordsDup(randomIndexX, randomIndexY + i)) {
          coords.add(new Coord(randomIndexX, randomIndexY + i));
          shipCoords.add(new Coord(randomIndexX, randomIndexY + i));

        } else { //there was a duplicate
          coords.removeAll(shipCoords);
          count++;
          return placeSubmarine(y, x);
        }
      }
      return new Ship(shipCoords, ShipType.Submarine, "VERTICAL");

    } else { // if horizontal
      int randomIndexX = r.nextInt(x - size + 1);
      int randomIndexY = r.nextInt(y);
      for (int i = 0; i < size; i++) {
        if (coords.size() == 0) {
          coords.add(new Coord(randomIndexX + i, randomIndexY));
          shipCoords.add(new Coord(randomIndexX + i, randomIndexY));
        } else if (!checkCoordsDup(randomIndexX + i, randomIndexY)) {
          coords.add(new Coord(randomIndexX + i, randomIndexY));
          shipCoords.add(new Coord(randomIndexX + i, randomIndexY));

        } else { //there was a duplicate
          coords.removeAll(shipCoords);
          count++;
          return placeSubmarine(y, x);
        }
      }
      return new Ship(shipCoords, ShipType.Submarine, "HORIZONTAL");
    }
  }

  /**
   * setup to place Destroyer
   *
   * @param y board size of vertical
   * @param x board size of horizontal
   * @return the object ship's coords
   */
  private Ship placeDestroyer(int y, int x) {
    final int size = 4;
    Random r = new Random();
    ArrayList<Coord> shipCoords = new ArrayList<>();
    // if vertical
    if (r.nextInt(2) == 1) {
      int randomIndexY = r.nextInt(y - size + 1); // 0 1 2 3 4 5 6 7 8 9
      int randomIndexX = r.nextInt(x);
      for (int i = 0; i < size; i++) {
        if (coords.size() == 0) {
          coords.add(new Coord(randomIndexX, randomIndexY + i));
          shipCoords.add(new Coord(randomIndexX, randomIndexY + i));
        } else if (!checkCoordsDup(randomIndexX, randomIndexY + i)) {
          coords.add(new Coord(randomIndexX, randomIndexY + i));
          shipCoords.add(new Coord(randomIndexX, randomIndexY + i));

        } else { //there was a duplicate
          coords.removeAll(shipCoords);
          count++;
          return placeDestroyer(y, x);
        }
      }
      return new Ship(shipCoords, ShipType.Destroyer, "VERTICAL");

    } else { // if horizontal
      int randomIndexX = r.nextInt(x - size + 1);
      int randomIndexY = r.nextInt(y);
      for (int i = 0; i < size; i++) {
        if (coords.size() == 0) {
          coords.add(new Coord(randomIndexX + i, randomIndexY));
          shipCoords.add(new Coord(randomIndexX + i, randomIndexY));
        } else if (!checkCoordsDup(randomIndexX + i, randomIndexY)) {
          coords.add(new Coord(randomIndexX + i, randomIndexY));
          shipCoords.add(new Coord(randomIndexX + i, randomIndexY));

        } else { //there was a duplicate
          coords.removeAll(shipCoords);
          count++;
          return placeDestroyer(y, x);
        }
      }
      return new Ship(shipCoords, ShipType.Destroyer, "HORIZONTAL");
    }
  }

  /**
   * setup to place Battleship
   *
   * @param y board size of vertical
   * @param x board size of horizontal
   * @return the object ship's coords
   */
  private Ship placeBattleShip(int y, int x) {
    final int size = 5;
    Random r = new Random();
    ArrayList<Coord> shipCoords = new ArrayList<>();
    // if vertical
    if (r.nextInt(2) == 1) {
      int randomIndexY = r.nextInt(y - size + 1); // 0 1 2 3 4 5 6 7 8 9
      int randomIndexX = r.nextInt(x);
      for (int i = 0; i < size; i++) {
        if (coords.size() == 0) {
          coords.add(new Coord(randomIndexX, randomIndexY + i));
          shipCoords.add(new Coord(randomIndexX, randomIndexY + i));
        } else if (!checkCoordsDup(randomIndexX, randomIndexY + i)) {
          coords.add(new Coord(randomIndexX, randomIndexY + i));
          shipCoords.add(new Coord(randomIndexX, randomIndexY + i));

        } else { //there was a duplicate
          coords.removeAll(shipCoords);
          return placeBattleShip(y, x);
        }
      }
      return new Ship(shipCoords, ShipType.BattleShip, "VERTICAL");

    } else { // if horizontal
      int randomIndexX = r.nextInt(x - size + 1);
      int randomIndexY = r.nextInt(y);
      for (int i = 0; i < size; i++) {
        if (coords.size() == 0) {
          coords.add(new Coord(randomIndexX + i, randomIndexY));
          shipCoords.add(new Coord(randomIndexX + i, randomIndexY));
        } else if (!checkCoordsDup(randomIndexX + i, randomIndexY)) {
          coords.add(new Coord(randomIndexX + i, randomIndexY));
          shipCoords.add(new Coord(randomIndexX + i, randomIndexY));

        } else { //there was a duplicate
          coords.removeAll(shipCoords);
          return placeBattleShip(y, x);
        }
      }
      return new Ship(shipCoords, ShipType.BattleShip, "HORIZONTAL");
    }
  }

  /**
   * setup to place carrier
   *
   * @param y board size of vertical
   * @param x board size of horizontal
   * @return the object ship's coords
   */
  private Ship placeCarrier(int y, int x) {
    final int size = 6;
    Random r = new Random();
    ArrayList<Coord> shipCoords = new ArrayList<>();
    // if vertical
    if (r.nextInt(2) == 1) {
      int randomIndexY = r.nextInt(y - size + 1); // 0 1 2 3 4 5 6 7 8 9
      int randomIndexX = r.nextInt(x);
      for (int i = 0; i < size; i++) {
        if (coords.size() == 0) {
          coords.add(new Coord(randomIndexX, randomIndexY + i));
          shipCoords.add(new Coord(randomIndexX, randomIndexY + i));
        } else if (!checkCoordsDup(randomIndexX, randomIndexY + i)) {
          coords.add(new Coord(randomIndexX, randomIndexY + i));
          shipCoords.add(new Coord(randomIndexX, randomIndexY + i));

        } else { //there was a duplicate
          coords.removeAll(shipCoords);
          return placeCarrier(y, x);
        }
      }
      return new Ship(shipCoords, ShipType.Carrier, "VERTICAL");

    } else { // if horizontal
      int randomIndexX = r.nextInt(x - size + 1);
      int randomIndexY = r.nextInt(y);
      for (int i = 0; i < size; i++) {
        if (coords.size() == 0) {
          coords.add(new Coord(randomIndexX + i, randomIndexY));
          shipCoords.add(new Coord(randomIndexX + i, randomIndexY));
        } else if (!checkCoordsDup(randomIndexX + i, randomIndexY)) {
          coords.add(new Coord(randomIndexX + i, randomIndexY));
          shipCoords.add(new Coord(randomIndexX + i, randomIndexY));

        } else { //there was a duplicate
          coords.removeAll(shipCoords);
          return placeCarrier(y, x);
        }
      }
      return new Ship(shipCoords, ShipType.Carrier, "HORIZONTAL");
    }
  }



  /**
   * Checks if there are dupplicate coordinates
   *
   * @param x possible dup x
   * @param y possible dup y
   * @return if it is a duplicate
   */
  private boolean checkCoordsDup(int x, int y) {
    for (int i = 0; i < coords.size(); i++) {
      if (coords.get(i).getX() == x && coords.get(i).getY() == y) {
        return true;
      }
    }
    return false;
  }


  private ArrayList<Coord> getAllCoords(int height, int width) {
    ArrayList<Coord> result = new ArrayList<>();

    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        result.add(new Coord(j, i));
      }
    }

    return result;
  }


  /**
   * Returns this player's shots on the opponent's board. The number of shots returned should
   * equal the number of ships on this player's board that have not sunk.
   *
   * @return the locations of shots on the opponent's board
   */
  @Override
  public List<Coord> takeShots() {

    return shotList;
  }

  /**
   * Given the list of shots the opponent has fired on this player's board, report which
   * shots hit a ship on this player's board.
   *
   * @param opponentShotsOnBoard the opponent's shots on this player's board
   * @return a filtered list of the given shots that contain all locations of shots that hit a
   * ship on this board
   */
  @Override
  public List<Coord> reportDamage(List<Coord> opponentShotsOnBoard) {
    return null;
  }

  /**
   * Reports to this player what shots in their previous volley returned from takeShots()
   * successfully hit an opponent's ship.
   *
   * @param shotsThatHitOpponentShips the list of shots that successfully hit the opponent's ships
   */
  @Override
  public void successfulHits(List<Coord> shotsThatHitOpponentShips) {
    for (int i = 0; i < shipList.size(); i++) {
      Ship ship = shipList.get(i);
      for (int j = 0; j < shots.size(); j++) {
        Coord coords = shots.get(j);
        for (int k = 0; k < ship.getCoords().size(); k++) {
          if (coords.getX() == ship.getCoords().get(k).getX()) {
            if (coords.getY() == ship.getCoords().get(k).getY()) {
              shotsThatHitOpponentShips.add(coords);
            }
          }
        }
      }
    }
  }

  /**
   * Notifies the player that the game is over.
   * Win, lose, and draw should all be supported
   *
   * @param result if the player has won, lost, or forced a draw
   * @param reason the reason for the game ending
   */
  @Override
  public void endGame(GameResult result, String reason) {
  }
}
