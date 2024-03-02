package cs3500.pa03.Model;

import cs3500.pa03.View.GameResult;
import cs3500.pa04.RandomDecorator;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Class that represents the AI of opponent
 */
public class AiPlayer implements Player {
  private ArrayList<Coord> prevMissedShots;
  private ArrayList<Coord> prevHitShots;
  private List<Ship> ships;
  private Coord prevHitShot;
  private Coord prevMissShot;
  private int lowXbound;
  private int highXbound;
  private int lowYbound;
  private int highYbound;
  private int boardX;
  private int boardY;
  private String name;
  private int size;
  private List<Coord> result;
  private int count = 0;
  private final ArrayList<Coord> coords = new ArrayList<>();

  /**
   * Creates aiplayer for manuelPlayer
   *
   * @param name        name
   * @param boardX      size
   * @param boardY      size
   * @param missedShots player missedshots
   * @param hitShots    player hitshots
   * @param ships       player ships
   */
  public AiPlayer(String name, int boardX, int boardY, ArrayList<Coord> missedShots,
                  ArrayList<Coord> hitShots, List<Ship> ships) {
    prevMissedShots = missedShots;
    prevHitShots = hitShots;
    lowXbound = 0;
    highXbound = boardX - 1;
    lowYbound = 0;
    highYbound = boardY - 1;
    this.ships = ships;
    this.boardX = boardX;
    this.boardY = boardY;
    prevHitShot = new Coord(0, 0);
    prevMissShot = new Coord(0, 0);
    size = 0;
    result = new ArrayList<>();
    this.name = name;
  }

  private int width;
  private int height;
  private List<Coord> playerMissedShots;
  private List<Coord> playerHitShots;
  private List<Coord> shotsCoords;
  private Random r;
  private int numberOfShips;
  private List<Ship> shipList;
  private List<Coord> totalOpShots;
  private List<Ship> destroyedShips;


  /**
   * Creates an aiplayer without any specifications
   */
  public AiPlayer() {
    width = 0;
    height = 0;
    playerMissedShots = new ArrayList<>();
    playerHitShots = new ArrayList<>();
    r = new Random();
    highXbound = boardX;
    highYbound = boardY;
    numberOfShips = 0;
    shotsCoords = new ArrayList<>();
    shipList = new ArrayList<>();
    totalOpShots = new ArrayList<>();
    destroyedShips = new ArrayList<>();


  }

  /**
   * Creates an aiPlayer with coords
   *
   * @param coords hitcoords
   */
  public AiPlayer(List<Coord> coords) {
    playerHitShots = coords;
    r = new Random();
    highXbound = boardX;
    highYbound = boardY;
    numberOfShips = 0;
    shotsCoords = new ArrayList<>();
    shipList = new ArrayList<>();
    totalOpShots = new ArrayList<>();
    destroyedShips = new ArrayList<>();
    width = 0;
    height = 0;
    playerMissedShots = new ArrayList<>();
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
    this.width = width;
    this.height = height;
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
      shipLocation.add(placeShip(width, height, 6));
    }
    for (int j = 0; j < specifications.get(ShipType.BattleShip); j++) {
      count = 0;
      shipLocation.add(placeShip(width, height, 5));
    }
    for (int k = 0; k < specifications.get(ShipType.Destroyer); k++) {
      count = 0;
      shipLocation.add(placeShip(width, height, 4));
    }
    for (int l = 0; l < specifications.get(ShipType.Submarine); l++) {
      count = 0;
      shipLocation.add(placeShip(width, height, 3));
    }
    this.numberOfShips = shipLocation.size();
    this.shipList = shipLocation;
    return shipLocation;
  }

  private Ship placeShip(int width, int height, int size) {
    if (count > 10) {
      throw new IllegalArgumentException("Impossible to put all ships. Rerun and try again");

    }
    ArrayList<Coord> shipCoords = new ArrayList<>();

    if (r.nextInt(2) == 1) {
      int randomIndexY = r.nextInt(height - size + 1); // 0 1 2 3 4 5 6 7 8 9
      int randomIndexX = r.nextInt(width);
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
          return placeShip(width, height, size);
        }
      }
      return getCorrectShip(shipCoords, size, "VERTICAL");

    } else { // if horizontal
      int randomIndexX = r.nextInt(width - size + 1);
      int randomIndexY = r.nextInt(height);
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
          return placeShip(width, height, size);
        }
      }
      return getCorrectShip(shipCoords, size, "HORIZONTAL");
    }
  }

  private Ship getCorrectShip(ArrayList<Coord> coordList, int size, String direction) {
    if (size == 3) {
      return new Ship(coordList, ShipType.Submarine, direction);
    } else if (size == 4) {
      return new Ship(coordList, ShipType.Destroyer, direction);
    } else if (size == 5) {
      return new Ship(coordList, ShipType.BattleShip, direction);
    } else {
      return new Ship(coordList, ShipType.Carrier, direction);
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
    List<Coord> result = new ArrayList<>();
    if (playerHitShots.isEmpty() && playerMissedShots.isEmpty()) {
      for (int i = 0; i < numberOfShips; i++) {
        Coord shot = generateRandomValidShot();
        result.add(shot);
        shotsCoords.add(shot);
      }
    } else if (playerHitShots.isEmpty()) {
      for (int j = 0; j < numberOfShips; j++) {
        Coord shot = generateRandomValidShot();
        result.add(shot);
        shotsCoords.add(shot);
      }
    } else {
      result.addAll(generateNextShot(numberOfShips));
    }
    shotsCoords.addAll(result);
    while (result.size() < numberOfShips) {
      Coord shot = generateRandomValidShot();
      result.add(shot);
      shotsCoords.add(shot);
    }
    return result;

  }

  private Coord generateRandomValidShot() {
    Coord shot;
    int randomX = r.nextInt(width);
    int randomY = r.nextInt(height);
    shot = new Coord(randomX, randomY);
    while (!isValidShot(shot)) {
      shot = new Coord(r.nextInt(width), r.nextInt(height));
    }
    shotsCoords.add(shot);
    return shot;
  }

  private List<Coord> generateNextShot(int shipSize) {
    List<Coord> result = new ArrayList<>();
    int playerHitShotsIndex = 0;
    for (int j = 1; j < shipSize; j++) {
      Coord hitShot = playerHitShots.get(playerHitShotsIndex);
      Coord nextShotRight = new Coord(hitShot.getX() + j, hitShot.getY());
      Coord nextShotLeft = new Coord(hitShot.getX() - j, hitShot.getY());
      Coord nextShotUp = new Coord(hitShot.getX(), hitShot.getY() + j);
      Coord nextShotBottom = new Coord(hitShot.getX(), hitShot.getY() - j);
      if (isValidShot(nextShotRight)) {
        result.add(nextShotRight);
        shotsCoords.add(nextShotRight);
      } else if (isValidShot(nextShotLeft)) {
        result.add(nextShotLeft);
        shotsCoords.add(nextShotLeft);
      } else if (isValidShot(nextShotUp)) {
        result.add(nextShotUp);
        shotsCoords.add(nextShotUp);
      } else if (isValidShot(nextShotBottom)) {
        result.add(nextShotBottom);
        shotsCoords.add(nextShotBottom);
      } else {
        result.add(generateRandomValidShot());
        playerHitShotsIndex++;
      }
    }

    return result;
  }

  private boolean isValidShot(Coord shot) {
    return shot.getX() >= 0 && shot.getX() < width && shot.getY() >= 0 && shot.getY() < height
        && !isShotContained(shot);
  }

  private boolean isShotContained(Coord shot) {
    for (int i = 0; i < playerHitShots.size(); i++) {
      if (shot.getX() == playerHitShots.get(i).getX()
          && shot.getY() == playerHitShots.get(i).getY()) {
        return true;
      }
    }
    for (int i = 0; i < playerMissedShots.size(); i++) {
      if (shot.getX() == playerMissedShots.get(i).getX()
          && shot.getY() == playerMissedShots.get(i).getY()) {
        return true;
      }
    }
    for (int i = 0; i < shotsCoords.size(); i++) {
      if (shot.getX() == shotsCoords.get(i).getX() && shot.getY() == shotsCoords.get(i).getY()) {
        return true;
      }
    }

    return false;
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
    List<Coord> result = new ArrayList<>();

    for (int i = 0; i < opponentShotsOnBoard.size(); i++) {
      Coord opCoord = opponentShotsOnBoard.get(i);
      for (int j = 0; j < shipList.size(); j++) {
        Ship ship = shipList.get(j);
        List<Coord> shipCoords = ship.getCoords();
        for (int k = 0; k < shipCoords.size(); k++) {
          if (opCoord.getY() == shipCoords.get(k).getY()
              && opCoord.getX() == shipCoords.get(k).getX()) {
            result.add(opCoord);
          }
        }
      }
    }
    totalOpShots.addAll(opponentShotsOnBoard);
    checkShipDestroyed();
    if (!destroyedShips.isEmpty()) {
      numberOfShips = shipList.size() - destroyedShips.size();
    }

    return result;
  }

  private void checkShipDestroyed() {
    for (int i = 0; i < shipList.size(); i++) {
      Ship ship = shipList.get(i);
      List<Coord> shipCoords = ship.getCoords();
      int shipLength = shipCoords.size();
      List<Coord> opShotList = new ArrayList<>();
      for (int j = 0; j < totalOpShots.size(); j++) {
        Coord opShot = totalOpShots.get(j);
        for (int k = 0; k < shipCoords.size(); k++) {
          if (opShot.getY() == shipCoords.get(k).getY()
              && opShot.getX() == shipCoords.get(k).getX()) {
            //adds the hit coords of ship
            opShotList.add(shipCoords.get(k));
          }
        }
      }
      // means that all the coordinates were hit
      if (opShotList.size() == shipLength && !isShipInDestroyed(ship)) {
        destroyedShips.add(ship);
      }
    }

  }

  private boolean isShipInDestroyed(Ship ship) {
    for (int i = 0; i < destroyedShips.size(); i++) {
      if (ship.getType().equals(destroyedShips.get(i).getType())
          && ship.getDirection().equals(destroyedShips.get(i).getDirection())
          && ship.getCoords().get(0).getX() == destroyedShips.get(i).getCoords().get(0).getX()
          && ship.getCoords().get(0).getY() == destroyedShips.get(i).getCoords().get(0).getY()) {
        return true;
      }
    }
    return false;
  }

  /**
   * Reports to this player what shots in their previous volley returned from takeShots()
   * successfully hit an opponent's ship.
   *
   * @param shotsThatHitOpponentShips the list of shots that successfully hit the opponent's ships
   */
  @Override
  public void successfulHits(List<Coord> shotsThatHitOpponentShips) {
    playerHitShots.addAll(shotsThatHitOpponentShips);

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
    if (result == GameResult.WIN) {
      System.out.println("Player 1 won! " + reason);
    } else if (result == GameResult.LOSE) {
      System.out.println("Player 2 won! " + reason);
    } else {
      System.out.println("You tied!");
    }

  }
}
