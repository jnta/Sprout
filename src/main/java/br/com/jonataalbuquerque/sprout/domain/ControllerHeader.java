package br.com.jonataalbuquerque.sprout.domain;

import java.lang.reflect.Method;

public record ControllerHeader(Class<?> controller, Method method) {
}
