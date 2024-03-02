package cs3500.pa04;

import java.util.Random;

/**
 * Class that implements Randomable
 */
public class RandomDecorator implements Randomable {
  private final Random rand;

  public RandomDecorator() {
    this.rand = new Random();
  }

  public RandomDecorator(int seed) {
    this.rand = new Random(seed);
  }

  /**
   * Simulate random
   *
   * @return nextInt
   */
  @Override
  public int nextInt() {
    return this.rand.nextInt();
  }

  /**
   * Simulate random
   *
   * @return nextInt
   */
  @Override
  public int nextInt(int size) {
    return this.rand.nextInt(size);
  }
}
