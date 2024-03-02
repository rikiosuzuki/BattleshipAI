package cs3500.pa03;

import static org.junit.jupiter.api.Assertions.assertEquals;

import cs3500.pa03.controller.ReaderImpl;
import java.io.StringReader;
import org.junit.jupiter.api.Test;

public class ReaderImplTest {

  @Test
  public void testRead() {
    String input = "Hello, World!";
    StringReader stringReader = new StringReader(input);
    ReaderImpl reader = new ReaderImpl(stringReader, "ok");

    String result = reader.read();

    assertEquals(input, result);
  }
}