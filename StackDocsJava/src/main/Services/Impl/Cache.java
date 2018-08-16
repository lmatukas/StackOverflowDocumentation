package Services.Impl;

import Models.CONS.Settings;
import Services.ICache;
import com.google.inject.Inject;
import com.google.inject.Provides;
import com.google.inject.Singleton;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Cache implements ICache {

    private Map<String, Object> cachedObjects = Collections.synchronizedMap(new HashMap<>());
    private Map<String, Long> cachedLiveTime = Collections.synchronizedMap(new HashMap<>());
    private static Cache instance = null;

    @Inject
    private Cache() {}

    @Provides
    @Singleton
    public static Cache getInstance() {
        if(instance == null) {
            instance = new Cache();
            return instance;
        }
        return instance;
    }

    @Override
    public void put(String key, Object obj) {
        cachedObjects.put(key, obj);
        cachedLiveTime.put(key, System.currentTimeMillis() + Settings.LIVE_TIME);
    }

    @Override
    public Object get(String key) {
        if (!cachedObjects.containsKey(key)) {
            return null;
        }
        if (System.currentTimeMillis() > cachedLiveTime.get(key)) {
            remove(key);
            return null;
        }
        return cachedObjects.get(key);
    }

    @Override
    public void remove(String key) {
        cachedObjects.remove(key);
        cachedLiveTime.remove(key);
    }
}
