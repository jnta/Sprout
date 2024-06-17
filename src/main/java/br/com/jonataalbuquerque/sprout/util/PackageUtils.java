package br.com.jonataalbuquerque.sprout.util;

public class PackageUtils {
    private PackageUtils() {
        throw new UnsupportedOperationException();
    }

    public static Class<?> getClass(String packageName, String className) {
        try {
            return Class.forName(packageName + "." + className.substring(0, className.lastIndexOf(".")));
        } catch (ClassNotFoundException ex) {
            Logger.error(PackageUtils.class, "Class not found: " +
                    packageName + "." + className);
            throw new RuntimeException();
        }
    }
}
