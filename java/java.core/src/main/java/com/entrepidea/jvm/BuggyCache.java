package com.entrepidea.jvm;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@SuppressWarnings({"StatementWithEmptyBody", "WeakerAccess", "unused", "ResultOfMethodCallIgnored"})
public class BuggyCache {

    public static class ObjectInCache {
        private final String key;

        public ObjectInCache(final String key) { this.key = key; }

        public String getKey() {
            return key;
        }
    }

    private static Map<String, ObjectInCache> cache = Collections.synchronizedMap(new HashMap<>());

    public static void computeForKey(final String key) {
        if (!cache.containsKey(key)) {
            final ObjectInCache loadedObject = complexObjectFinder(key);
            cache.put(loadedObject.toString(), loadedObject);
        } else {
            //Cheap computation here...
        }
    }

    private static ObjectInCache complexObjectFinder(final String key) {
        try {
            Thread.sleep(10);
            return new ObjectInCache(key);
        } catch (final InterruptedException e) { throw new RuntimeException(); }
    }


    public static void main(final String []args) throws IOException {

        System.out.println("Press Enter to start...");
        System.in.read();

        System.out.println("Starting...");
        final long startTime = System.currentTimeMillis();
        for (int i = 0; i < 500; i++) {
            computeForKey("the-same-key");
        }
        System.out.println("Done! Time = " + (System.currentTimeMillis() - startTime));
    }
}