package br.com.jonataalbuquerque.sprout;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

public class SproutDispatchServlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        if (req.getRequestURL().toString().endsWith("favicon.ico")) {
            return;
        }

        PrintWriter out = new PrintWriter(resp.getWriter());
        out.println("Hello World!! ======== SPROUT ========");
        out.close();
    }
}
