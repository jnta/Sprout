package br.com.jonataalbuquerque.sprout;

import br.com.jonataalbuquerque.sprout.explorer.ClassExplorer;
import br.com.jonataalbuquerque.sprout.util.Logger;
import br.com.jonataalbuquerque.sprout.web.SproutDispatchServlet;
import org.apache.catalina.Context;
import org.apache.catalina.connector.Connector;
import org.apache.catalina.startup.Tomcat;

import java.io.File;
import java.util.List;


public class SproutApplication {
    public static void run(Class<?> source) {
        Logger.showBanner();
        Logger.disableLogs("org.apache");
        extractMetadata(source);
        try {
            Logger.log("SproutApplication", "Starting Sprout Application");

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
            Logger.log("SproutApplication",
                    "Sprout application started in " +
                            (double) (end - init) / 1000 + " seconds");
            tomcat.getServer().await();

        } catch (Exception ex) {
            Logger.error(SproutApplication.class, ex.getMessage());
        }
    }

    private static void extractMetadata(Class<?> source) {
        List<Class<?>> classes = ClassExplorer.retrieveAllClasses(source);
        for (Class<?> className : classes) {
            Logger.log("Class Explorer", "Class found: " + className);
        }
    }

}
