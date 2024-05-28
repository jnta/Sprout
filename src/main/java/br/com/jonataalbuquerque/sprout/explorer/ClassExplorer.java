package br.com.jonataalbuquerque.sprout.explorer;

import br.com.jonataalbuquerque.sprout.util.Logger;
import br.com.jonataalbuquerque.sprout.util.PackageUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ClassExplorer {

    public static List<String> retrieveAllClasses(Class<?> source) {
        return findClassesByPackageName(source.getPackage().getName());
    }

    private static List<String> findClassesByPackageName(String packageName) {
        List<String> classes = new ArrayList<>();

        try {
            InputStream stream = ClassLoader.getSystemClassLoader()
                    .getResourceAsStream(packageName.replaceAll("\\.", File.separator));
            BufferedReader bf = new BufferedReader(new InputStreamReader(stream));
            String row;

            while ((row = bf.readLine()) != null) {
                if (row.endsWith(".class")) classes.add(PackageUtils.getFullClassName(packageName, row));
                else classes.addAll(findClassesByPackageName(packageName + "." + row));
            }
        } catch (Exception ex) {
            Logger.log("Class Explorer",
                    ex.getMessage() + " Stack Trace: " + Arrays.toString(ex.getStackTrace()));
        }
        return classes;
    }
}
