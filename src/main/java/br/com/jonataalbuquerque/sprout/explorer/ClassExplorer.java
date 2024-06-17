package br.com.jonataalbuquerque.sprout.explorer;

import br.com.jonataalbuquerque.sprout.util.Logger;
import br.com.jonataalbuquerque.sprout.util.PackageUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class ClassExplorer {

    public static List<Class<?>> retrieveAllClasses(Class<?> source) {
        return findClassesByPackageName(source.getPackage().getName());
    }

    private static List<Class<?>> findClassesByPackageName(String packageName) {
        List<Class<?>> classes = new ArrayList<>();

        try {
            InputStream stream = ClassLoader.getSystemClassLoader()
                    .getResourceAsStream(packageName.replaceAll("\\.", File.separator));
            assert stream != null;
            BufferedReader bf = new BufferedReader(new InputStreamReader(stream));
            String row;

            while ((row = bf.readLine()) != null) {
                if (row.endsWith(".class")) classes.add(PackageUtils.getClass(packageName, row));
                else classes.addAll(findClassesByPackageName(packageName + "." + row));
            }
        } catch (Exception ex) {
            Logger.error(ClassExplorer.class, ex.getMessage());
        }
        return classes;
    }
}
