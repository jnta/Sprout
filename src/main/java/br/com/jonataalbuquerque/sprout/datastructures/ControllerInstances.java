package br.com.jonataalbuquerque.sprout.datastructures;

import br.com.jonataalbuquerque.sprout.domain.ControllerHeader;

import java.util.HashMap;
import java.util.Map;

public class ControllerInstances {
    private static final Map<ControllerHeader, Object> INSTANCE = new HashMap<>();

    private ControllerInstances() {
    }

    public static void put(ControllerHeader controllerHeader, Object controller) {
        INSTANCE.put(controllerHeader, controller);
    }

    public static Object get(ControllerHeader controllerHeader) {
        return INSTANCE.get(controllerHeader);
    }
}
