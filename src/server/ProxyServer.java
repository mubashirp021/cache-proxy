package server;

import cache.*;
import utils.*;
import java.net.*;

public class ProxyServer {
    private int port;
    private CacheManager cache;

    public ProxyServer(int port) {
        this.port = port;
        this.cache = new InMemoryCache();
    }

    public void start() {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            Logger.info("Proxy Server started on port " + port);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                ClientHandler handler = new ClientHandler(clientSocket, cache);
                handler.run(); // For now single-threaded
            }
        } catch (Exception e) {
            Logger.error("Failed to start proxy server", e);
        }
    }
}
