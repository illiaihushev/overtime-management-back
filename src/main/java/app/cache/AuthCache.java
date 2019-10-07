package app.cache;

import app.model.ExistingAppItem;

import java.util.HashMap;
import java.util.Map;

public class AuthCache<T extends ExistingAppItem<N>, N> {
    private Map<String, Map<N, T>> relevantCache = new HashMap<>();
    private Map<String, Map<N, T>> irrelevantCache = new HashMap<>();

    public Map<N, T> getRelevant(String key) {
        return relevantCache.computeIfAbsent(key, k -> new HashMap<>());
    }

    public Map<N, T> getIrrelevant(String key) {
        return irrelevantCache.computeIfAbsent(key, k -> new HashMap<>());
    }

}
