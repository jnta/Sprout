package br.com.jonataalbuquerque.sprout.web;

import br.com.jonataalbuquerque.sprout.annotations.HttpBody;
import br.com.jonataalbuquerque.sprout.datastructures.ControllerInstances;
import br.com.jonataalbuquerque.sprout.datastructures.ControllerMap;
import br.com.jonataalbuquerque.sprout.datastructures.DependencyImplementationMap;
import br.com.jonataalbuquerque.sprout.datastructures.DependencyInjectionMap;
import br.com.jonataalbuquerque.sprout.domain.ControllerHeader;
import br.com.jonataalbuquerque.sprout.domain.HttpMethod;
import br.com.jonataalbuquerque.sprout.domain.RequestHeader;
import br.com.jonataalbuquerque.sprout.util.Logger;
import com.google.gson.Gson;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

public class SproutDispatchServlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) {
        if (req.getRequestURL().toString().endsWith("/favicon.ico")) {
            return;
        }
        PrintWriter out;
        try {
            out = new PrintWriter(resp.getWriter());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Gson gson = new Gson();

        String url = req.getRequestURI();
        var httpMethod = HttpMethod.from(req.getMethod());
        RequestHeader key = new RequestHeader(httpMethod, url);
        ControllerHeader data = ControllerMap.get(key);

        Object controller;
        try {
            controller = ControllerInstances.get(data);
            if (controller == null) {
                controller = data.controller().getDeclaredConstructor().newInstance();
                ControllerInstances.put(data, controller);

                injectDependencies(controller);
            }

            Method controllerMethod = null;
            for (Method method : controller.getClass().getMethods()) {
                if (method.equals(data.method())) {
                    controllerMethod = method;
                    break;
                }
            }

            assert controllerMethod != null;
            if (controllerMethod.getParameterCount() > 0) {
                Object arg;
                Parameter parameter = controllerMethod.getParameters()[0];
                if (parameter.getAnnotations()[0].annotationType().equals(HttpBody.class)) {
                    String body = readBytesFromRequest(req);
                    arg = gson.fromJson(body, parameter.getType());

                    out.println(gson.toJson(controllerMethod.invoke(controller, arg)));
                }
            } else {
                out.write(gson.toJson(controllerMethod.invoke(controller)));
            }
            out.close();
        } catch (Exception ex) {
            Logger.error(SproutDispatchServlet.class, ex.getMessage());
        }
    }

    private static String readBytesFromRequest(HttpServletRequest req) {
        StringBuilder sb = new StringBuilder();
        try {
            req.getReader().lines().forEach(sb::append);
        } catch (IOException e) {
            throw new UnsupportedOperationException(e);
        }
        return sb.toString();
    }

    private void injectDependencies(Object client) throws Exception {
        for (Field field : client.getClass().getDeclaredFields()) {
            Class<?> dependency = field.getType();
            Object implementationInstance;
            if (DependencyInjectionMap.get(dependency) == null) {
                Class<?> implementation = DependencyImplementationMap.get(dependency);
                if (implementation != null) {
                    implementationInstance = DependencyInjectionMap.get(implementation);
                    if (implementationInstance == null) {
                        implementationInstance = implementation.getDeclaredConstructor().newInstance();
                        DependencyInjectionMap.put(implementation, implementationInstance);
                    }

                    field.setAccessible(true);
                    field.set(client, implementationInstance);
                }
            }
        }
    }
}
