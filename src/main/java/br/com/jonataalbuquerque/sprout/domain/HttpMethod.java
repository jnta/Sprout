package br.com.jonataalbuquerque.sprout.domain;

public enum HttpMethod {
    POST, PUT, GET, PATCH, DELETE;

    public static HttpMethod from(String method) {
        return switch (method) {
            case "POST" -> POST;
            case "PUT" -> PUT;
            case "GET" -> GET;
            case "PATCH" -> PATCH;
            case "DELETE" -> DELETE;
            default -> throw new IllegalArgumentException("Invalid HTTP method: " + method);
        };
    }
}
