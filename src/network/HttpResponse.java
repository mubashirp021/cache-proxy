package network;

import java.util.*;

public class HttpResponse {
    private String statusLine;
    private Map<String, String> headers = new HashMap<>();
    private String body;

    public HttpResponse(String statusLine, String body) {
        this.statusLine = statusLine;
        this.body = body;
    }

    public String getStatusLine() {
        return statusLine;
    }

    public String getBody() {
        return body;
    }

    public void addHeader(String key, String value) {
        headers.put(key, value);
    }

    public String toRaw() {
        StringBuilder sb = new StringBuilder();
        sb.append(statusLine).append("\r\n");
        for (Map.Entry<String, String> entry : headers.entrySet()) {
            sb.append(entry.getKey()).append(": ").append(entry.getValue()).append("\r\n");
        }
        sb.append("\r\n").append(body);
        return sb.toString();
    }
}