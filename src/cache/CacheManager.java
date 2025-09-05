package cache;

import network.HttpResponse;

public interface CacheManager {
    HttpResponse get(String key);
    void put(String key, HttpResponse response);
    void clear();
}