package br.com.jonataalbuquerque.sprout.datastructures;

import java.util.HashMap;
import java.util.Map;

public class DependencyImplementationMap {
    private static final Map<Class<?>, Class<?>> INSTANCE = new HashMap<>();


    public static void put(Class<?> dependency, Class<?> implementation) {
        INSTANCE.put(dependency, implementation);
    }

    public static Class<?> get(Class<?> dependency) {
        return INSTANCE.get(dependency);
    }
}
