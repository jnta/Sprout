package br.com.jonataalbuquerque.sprout.web;

import br.com.jonataalbuquerque.sprout.annotations.Controller;
import br.com.jonataalbuquerque.sprout.annotations.Service;
import br.com.jonataalbuquerque.sprout.config.SupportedRequestAnnotations;
import br.com.jonataalbuquerque.sprout.datastructures.ControllerMap;
import br.com.jonataalbuquerque.sprout.datastructures.DependencyImplementationMap;
import br.com.jonataalbuquerque.sprout.domain.ControllerHeader;
import br.com.jonataalbuquerque.sprout.domain.RequestHeader;
import br.com.jonataalbuquerque.sprout.explorer.ClassExplorer;
import br.com.jonataalbuquerque.sprout.util.Logger;
import org.apache.catalina.Context;
import org.apache.catalina.connector.Connector;
import org.apache.catalina.startup.Tomcat;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;


public class SproutApplication {

    private SproutApplication() {
    }

    public static void run(Class<?> source) {
        Logger.showBanner();
        Logger.disableLogs("org.apache");
        extractMetadata(source);
        Logger.log("SproutApplication", "Metadata extracted from source class + " + ControllerMap.getAll());
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

    /**
     * This method is used to extract metadata from the provided source class.
     * It retrieves all classes from the source, checks if they are annotated with @Controller,
     * and then processes their declared methods.
     *
     * @param source The source class from which to extract metadata.
     */
    private static void extractMetadata(Class<?> source) {
        List<Class<?>> classes = ClassExplorer.retrieveAllClasses(source);

        for (Class<?> classInstance : classes) {
            if (classInstance.isAnnotationPresent(Controller.class)) {
                Arrays.stream(classInstance.getDeclaredMethods())
                        .filter(method -> SupportedRequestAnnotations.get().stream().anyMatch(method::isAnnotationPresent))
                        .forEach(method -> Optional.of(RequestHeader.from(method))
                                .ifPresent(requestHeader -> ControllerMap.put(requestHeader, new ControllerHeader(classInstance, method))));
            } else if (classInstance.isAnnotationPresent(Service.class)) {
                Arrays.stream(classInstance.getInterfaces()).findFirst().ifPresent(dependency ->
                        DependencyImplementationMap.put(dependency, classInstance)
                );
            }
        }
    }
}
