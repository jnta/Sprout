package br.com.jonataalbuquerque.sprout.datastructures;

import java.util.HashMap;

public class DependencyInjectionMap {
    private static final HashMap<Class<?>, Object> INSTANCE = new HashMap<>();

    public static void put(Class<?> dependency, Object implementation) {
        INSTANCE.put(dependency, implementation);
    }

    public static Object get(Class<?> dependency) {
        return INSTANCE.get(dependency);
    }
}
