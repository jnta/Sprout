package br.com.jonataalbuquerque.sprout.util;

public class PackageUtils {
    private PackageUtils() {
        throw new UnsupportedOperationException();
    }

    public static String getFullClassName(String packageName, String className) {
        return packageName + "." + className;
    }
}
