package br.com.jonataalbuquerque.sprout.datastructures;

import br.com.jonataalbuquerque.sprout.domain.RequestHeader;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class ControllerInstances {
    private static final Map<RequestHeader, Object> INSTANCE = new HashMap<>();

    private ControllerInstances() {
    }

    public static void put(RequestHeader requestHeader, Object controller) {
        INSTANCE.put(requestHeader, controller);
    }

    public static Optional<Object> get(RequestHeader requestHeader) {
        return Optional.ofNullable(INSTANCE.get(requestHeader));
    }
}
