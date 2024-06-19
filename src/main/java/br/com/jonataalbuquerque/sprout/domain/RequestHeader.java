package br.com.jonataalbuquerque.sprout.domain;

import jakarta.servlet.http.HttpServletRequest;

public record RequestHeader(HttpMethod httpMethod, String path) {

    public RequestHeader(HttpServletRequest httpServletRequest) {
        this(HttpMethod.from(httpServletRequest.getMethod()), httpServletRequest.getRequestURI());
    }
}
