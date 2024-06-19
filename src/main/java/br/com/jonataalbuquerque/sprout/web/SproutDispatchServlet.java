package br.com.jonataalbuquerque.sprout.web;

import br.com.jonataalbuquerque.sprout.datastructures.ControllerInstances;
import br.com.jonataalbuquerque.sprout.datastructures.ControllerMap;
import br.com.jonataalbuquerque.sprout.domain.ControllerHeader;
import br.com.jonataalbuquerque.sprout.domain.RequestHeader;
import com.google.gson.Gson;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.util.Optional;

public class SproutDispatchServlet extends HttpServlet {

    private static void sendResponse(HttpServletResponse resp, int status, Object body) {
        PrintWriter out = null;
        try {
            out = new PrintWriter(resp.getWriter());
        } catch (IOException e) {
            resp.setStatus(500);
            throw new UnsupportedOperationException(e);
        }
        resp.setStatus(status);
        out.println(new Gson().toJson(body));
    }

    /**
     * Overrides the service method of HttpServlet to handle HTTP requests.
     * This method is the entry point for all requests coming to the servlet.
     *
     * @param req  the HttpServletRequest object that contains the request the client made of the servlet
     * @param resp the HttpServletResponse object that contains the response the servlet returns to the client
     */
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) {
        // Ignore requests for favicon.ico
        if (req.getRequestURL().toString().endsWith("favicon.ico")) {
            return;
        }

        RequestHeader requestHeader = new RequestHeader(req);

        Optional<ControllerHeader> controllerHeader = ControllerMap.get(requestHeader);
        if (controllerHeader.isEmpty()) {
            sendResponse(resp, 404, "404 - Not Found");
            return;
        }

        // Retrieve the controller instance associated with the RequestHeader from the ControllerInstances
        // If no instance is found, create a new one and add it to the ControllerInstances
        Object instance = ControllerInstances.get(requestHeader).orElseGet(() -> {
            try {
                Object newInstance = controllerHeader.get().controller().getDeclaredConstructor().newInstance();
                ControllerInstances.put(requestHeader, newInstance);
                return newInstance;
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                     NoSuchMethodException e) {
                throw new RuntimeException(e);
            }
        });

        // Invoke the method associated with the ControllerHeader on the controller instance
        // and send the result as a 200 response
        // If an exception occurs during invocation, send a 500 response
        try {
            sendResponse(resp, 200, controllerHeader.get().method().invoke(instance));
        } catch (IllegalAccessException | InvocationTargetException e) {
            sendResponse(resp, 500, "500 - Internal Server Error");
        }
    }
}
