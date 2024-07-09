package br.com.jonataalbuquerque.sprout.datastructures;

import br.com.jonataalbuquerque.sprout.domain.ControllerHeader;
import br.com.jonataalbuquerque.sprout.domain.RequestHeader;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ControllerMap {
    private static final Map<RequestHeader, ControllerHeader> INSTANCE = new HashMap<>();

    private ControllerMap() {
    }

    public static void put(RequestHeader requestHeader, ControllerHeader controllerHeader) {
        INSTANCE.put(requestHeader, controllerHeader);
    }

    public static ControllerHeader get(RequestHeader requestHeader) {
        return INSTANCE.get(requestHeader);
    }

    public static List<ControllerHeader> getAll() {
        return List.copyOf(INSTANCE.values());
    }
}
