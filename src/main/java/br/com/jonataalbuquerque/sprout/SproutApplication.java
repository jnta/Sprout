package br.com.jonataalbuquerque.sprout;

import org.apache.catalina.Context;
import org.apache.catalina.connector.Connector;
import org.apache.catalina.startup.Tomcat;

import java.io.File;
import java.util.TooManyListenersException;

public class SproutApplication {
    public static void run() {
        try {
            Tomcat tomcat = new Tomcat();
            Connector connector = new Connector();
            connector.setPort(8080);
            tomcat.setConnector(connector);

            Context context = tomcat.addContext("", new File(".").getAbsolutePath());
            Tomcat.addServlet(context, "SproutDispatchServlet", new SproutDispatchServlet());
            context.addServletMappingDecoded("/*", "SproutDispatchServlet");

            tomcat.start();
            tomcat.getServer().await();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
