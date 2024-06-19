package br.com.jonataalbuquerque.sprout;

import br.com.jonataalbuquerque.sprout.annotations.*;
import br.com.jonataalbuquerque.sprout.config.SupportedRequestMapping;
import br.com.jonataalbuquerque.sprout.datastructures.ControllerMap;
import br.com.jonataalbuquerque.sprout.domain.ControllerHeader;
import br.com.jonataalbuquerque.sprout.domain.RequestHeader;
import br.com.jonataalbuquerque.sprout.explorer.ClassExplorer;
import br.com.jonataalbuquerque.sprout.util.HTTPMethod;
import br.com.jonataalbuquerque.sprout.util.Logger;
import br.com.jonataalbuquerque.sprout.web.SproutDispatchServlet;
import org.apache.catalina.Context;
import org.apache.catalina.connector.Connector;
import org.apache.catalina.startup.Tomcat;

import java.io.File;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
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
                        .filter(method -> SupportedRequestMapping.get().stream().anyMatch(method::isAnnotationPresent))
                        .forEach(method -> Optional.ofNullable(getRequestHeaderFrom(method))
                                .ifPresent(requestHeader -> ControllerMap.put(requestHeader, new ControllerHeader(classInstance, method))));
            }
        }
    }

    private static RequestHeader getRequestHeaderFrom(Method method) {
        Annotation[] annotations = method.getAnnotations();
        for (Annotation annotation : annotations) {
            if (annotation instanceof Get get) {
                return new RequestHeader(HTTPMethod.GET, get.path());
            }
            if (annotation instanceof Post post) {
                return new RequestHeader(HTTPMethod.POST, post.path());
            }
            if (annotation instanceof Put put) {
                return new RequestHeader(HTTPMethod.PUT, put.path());
            }
            if (annotation instanceof Delete delete) {
                return new RequestHeader(HTTPMethod.DELETE, delete.path());
            }
        }
        return null;
    }

}
