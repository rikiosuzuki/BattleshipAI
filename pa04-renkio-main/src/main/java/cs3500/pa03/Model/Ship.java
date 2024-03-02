package cs3500.pa03.Model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;

public class Ship {

  private ArrayList<Coord> pos;
  private ShipType type;

  private String direction;

  @JsonCreator
  public Ship(ArrayList<Coord> p, ShipType type, String direction) {
    pos = p;
    this.type = type;
    this.direction = direction;
  }

  // example of ship
  // vertical
  // (0, 0), (0, 1), (0, 2), (0, 3)
  // horizontal
  // (0, 0), (1, 0), (2, 0), (3, 0)

  public ArrayList<Coord> getCoords() {
    return pos;
  }

  public ShipType getType() {
    return type;
  }


  public String getDirection() {
    return direction;
  }
}
