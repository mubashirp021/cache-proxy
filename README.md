# Caching Proxy Server (CLI Tool)

A simple command-line proxy server in Java.  
It forwards HTTP requests to real servers and caches responses.  
Repeated requests are served directly from the cache.

---

## 🚀 How to Run

### 1. Compile

```bash
javac src/*/*/*.java
```

### 2. Start Proxy (on port 8080)
```bash
java cli.ProxyCLI 8080
```
Expected output:
[INFO] Proxy Server started on port 8080

### 3. Test with curl
```bash
curl -x http://localhost:8080 http://example.com/
```
First request → Cache MISS (fetched from server)
Second request → Cache HIT (served from cache)
