package br.com.jonataalbuquerque.sprout;

import br.com.jonataalbuquerque.sprout.util.Logger;
import br.com.jonataalbuquerque.sprout.web.SproutDispatchServlet;
import org.apache.catalina.Context;
import org.apache.catalina.connector.Connector;
import org.apache.catalina.startup.Tomcat;

import java.io.File;
import java.util.logging.Level;

public class SproutApplication {
    public static void run() {
        java.util.logging.Logger.getLogger("org.apache").setLevel(Level.OFF);

        try {
            Logger.showBanner();
            Logger.log("Main Module", "Starting Sprout Application");

            long init = System.currentTimeMillis();

            Tomcat tomcat = new Tomcat();
            Connector connector = new Connector();
            connector.setPort(8080);
            tomcat.setConnector(connector);

            Context context = tomcat.addContext("", new File(".").getAbsolutePath());
            Tomcat.addServlet(context, "SproutDispatchServlet", new SproutDispatchServlet());
            context.addServletMappingDecoded("/*", "SproutDispatchServlet");

            tomcat.start();
            long end = System.currentTimeMillis();
            Logger.log("Main Module",
                    "Sprout application started in " +
                            (double) (end - init) / 1000 + " seconds");
            tomcat.getServer().await();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
