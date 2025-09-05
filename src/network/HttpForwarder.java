package network;

import utils.Logger;
import java.io.*;
import java.net.*;

public class HttpForwarder {

    public static HttpResponse forward(HttpRequest request) {
        try {
            URL targetUrl = new URL(request.getUrl());
            int port = targetUrl.getPort() == -1 ? 80 : targetUrl.getPort();

            Socket socket = new Socket(targetUrl.getHost(), port);
            PrintWriter out = new PrintWriter(socket.getOutputStream());
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            // Send request line
            out.println(request.getMethod() + " " + targetUrl.getFile() + " HTTP/1.0");
            out.println("Host: " + targetUrl.getHost());
            out.println("Connection: close");
            out.println();
            out.flush();

            // Read response
            String statusLine = in.readLine();
            if (statusLine == null) {
                socket.close();
                return null;
            }

            HttpResponse response = new HttpResponse(statusLine, "");

            String line;
            StringBuilder bodyBuilder = new StringBuilder();

            // Headers
            while ((line = in.readLine()) != null && !line.isEmpty()) {
                String[] header = line.split(":", 2);
                if (header.length == 2) {
                    response.addHeader(header[0].trim(), header[1].trim());
                }
            }

            // Body
            while ((line = in.readLine()) != null) {
                bodyBuilder.append(line).append("\n");
            }

            response = new HttpResponse(statusLine, bodyBuilder.toString());

            socket.close();
            return response;
        } catch (Exception e) {
            Logger.error("Error forwarding request", e);
            return null;
        }
    }
}
