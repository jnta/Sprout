package br.com.jonataalbuquerque.sprout.datastructures;

import br.com.jonataalbuquerque.sprout.util.HTTPMethod;

public record RequestControllerData(HTTPMethod httpMethod, String path, String controllerMethod,
                                    String controllerClass) {
}
