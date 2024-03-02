package cs3500.pa04;

import cs3500.pa03.Model.AiPlayer;
import cs3500.pa03.Model.Player;
import cs3500.pa03.controller.ControllerImpl;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * This is the main driver of this project.
 */
public class Driver {


  /**
   * Project entry point
   *
   * @param args - no command line args required
   */
  public static void main(String[] args) throws IOException {
    if (args.length == 0) {
      Readable input = new InputStreamReader(System.in);
      ControllerImpl controller = new ControllerImpl(input);
      controller.run();
    } else {
      Socket socket = new Socket(args[0], Integer.parseInt(args[1]));
      Player player = new AiPlayer();

      ProxyController proxyController = new ProxyController(socket, player);
      proxyController.run();
    }
  }
}

/**
 * String host = "0.0.0.0";
 *     int port = 35001;
 *     Driver.runClient(host, port);
 */