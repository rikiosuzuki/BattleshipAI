package cs3500.pa03.Model;

public enum ShipType {
  Carrier(6), BattleShip(5), Destroyer(4), Submarine(3);
  private int size;

  ShipType(int h) {
    size = h;
  }

  public int getSize() {
    return size;
  }
}
