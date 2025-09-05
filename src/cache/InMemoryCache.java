package cache;

import network.HttpResponse;
import java.util.*;

public class InMemoryCache implements CacheManager {
    private final Map<String, HttpResponse> cache = new HashMap<>();

    @Override
    public HttpResponse get(String key) {
        return cache.get(key);
    }

    @Override
    public void put(String key, HttpResponse response) {
        cache.put(key, response);
    }

    @Override
    public void clear() {
        cache.clear();
    }
}
