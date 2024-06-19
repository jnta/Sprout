package br.com.jonataalbuquerque.sprout.domain;

import br.com.jonataalbuquerque.sprout.util.HTTPMethod;

public record RequestHeader(HTTPMethod httpMethod, String path) {
}
