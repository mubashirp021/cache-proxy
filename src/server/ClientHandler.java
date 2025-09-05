package server;

import cache.CacheManager;
import network.*;
import utils.Logger;

import java.io.*;
import java.net.Socket;

public class ClientHandler implements Runnable {
    private Socket clientSocket;
    private CacheManager cache;

    public ClientHandler(Socket clientSocket, CacheManager cache) {
        this.clientSocket = clientSocket;
        this.cache = cache;
    }

    @Override
    public void run() {
        try (
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()))
        ) {
            // Read request line
            String requestLine = in.readLine();
            if (requestLine == null || requestLine.isEmpty()) return;

            Logger.info("Request: " + requestLine);
            String[] parts = requestLine.split(" ");
            if (parts.length < 2) return;

            String method = parts[0];
            String url = parts[1];

            // Check cache
            HttpResponse cached = cache.get(url);
            if (cached != null) {
                Logger.info("Cache HIT: " + url);
                out.write(cached.toRaw());
                out.flush();
            } else {
                Logger.info("Cache MISS: " + url);
                HttpRequest req = new HttpRequest(method, url, "HTTP/1.0");
                HttpResponse resp = HttpForwarder.forward(req);

                if (resp != null) {
                    cache.put(url, resp);
                    out.write(resp.toRaw());
                    out.flush();
                }
            }
        } catch (Exception e) {
            Logger.error("Error handling client", e);
        }
    }
}
