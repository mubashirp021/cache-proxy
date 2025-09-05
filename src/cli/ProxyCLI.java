package cli;

import server.ProxyServer;
import utils.Config;

public class ProxyCLI {
    public static void main(String[] args) {
        int port = Config.DEFAULT_PORT;
        if (args.length > 0) {
            try {
                port = Integer.parseInt(args[0]);
            } catch (NumberFormatException e) {
                System.out.println("Invalid port, using default " + port);
            }
        }

        ProxyServer server = new ProxyServer(port);
        server.start();
    }
}
