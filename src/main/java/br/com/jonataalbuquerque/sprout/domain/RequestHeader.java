package br.com.jonataalbuquerque.sprout.domain;

import br.com.jonataalbuquerque.sprout.annotations.*;
import jakarta.servlet.http.HttpServletRequest;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

public record RequestHeader(HttpMethod httpMethod, String path) {

    public RequestHeader(HttpServletRequest httpServletRequest) {
        this(HttpMethod.from(httpServletRequest.getMethod()), httpServletRequest.getRequestURI());
    }

    public static RequestHeader from(Method method) {
        Annotation[] annotations = method.getAnnotations();
        for (Annotation annotation : annotations) {
            if (annotation instanceof Get get) {
                return new RequestHeader(HttpMethod.GET, get.path());
            }
            if (annotation instanceof Post post) {
                return new RequestHeader(HttpMethod.POST, post.path());
            }
            if (annotation instanceof Put put) {
                return new RequestHeader(HttpMethod.PUT, put.path());
            }
            if (annotation instanceof Delete delete) {
                return new RequestHeader(HttpMethod.DELETE, delete.path());
            }
            if (annotation instanceof Patch patch) {
                return new RequestHeader(HttpMethod.PATCH, patch.path());
            }
        }
        throw new UnsupportedOperationException("Unsupported HTTP method");
    }
}
