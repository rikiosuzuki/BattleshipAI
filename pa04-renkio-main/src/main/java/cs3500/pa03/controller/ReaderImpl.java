package cs3500.pa03.controller;

import java.util.Objects;
import java.util.Scanner;

public class ReaderImpl {
  private Readable reader;
  String escapeSequence;

  public ReaderImpl(Readable r, String es) {
    reader = Objects.requireNonNull(r);
    escapeSequence = Objects.requireNonNull(es);
  }

  public String read() {
    Scanner sc = new Scanner(reader);
    StringBuilder sb = new StringBuilder();


    while (sc.hasNext() && !sc.hasNext(escapeSequence)) {
      sb.append(sc.nextLine());
    }
    return sb.toString();
  }
}
