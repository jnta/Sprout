package br.com.jonataalbuquerque.sprout.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Level;

public class Logger {
    public static final String BLUE = "\u001b[34m";
    public static final String YELLOW = "\u001b[33m";
    public static final String WHITE = "\u001b[37m";
    public static final String RED = "\u001b[31m";
    public static final String RESET = "\u001b[0m";
    public static final String GREEN = "\u001b[32m";
    public static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private Logger() {
    }

    public static void log(String module, String message) {
        LocalDateTime date = LocalDateTime.now();
        System.out.printf(BLUE + "%15s " + YELLOW + "%-30s" + WHITE + ":%s\n" + RESET,
                date.format(formatter), module, message);
    }

    public static void error(Class<?> module, String message) {

        LocalDateTime date = LocalDateTime.now();
        System.out.printf(RED + "%15s " + YELLOW + "%-30s" + WHITE + ":%s\n" + RESET,
                date.format(formatter), module.getName(), message);
    }

    public static void showBanner() {
        String banner = """
                ███████╗██████╗ ██████╗  ██████╗ ██╗   ██╗████████╗
                ██╔════╝██╔══██╗██╔══██╗██╔═══██╗██║   ██║╚══██╔══╝
                ███████╗██████╔╝██████╔╝██║   ██║██║   ██║   ██║
                ╚════██║██╔═══╝ ██╔══██╗██║   ██║██║   ██║   ██║
                ███████║██║     ██║  ██║╚██████╔╝╚██████╔╝   ██║
                ╚══════╝╚═╝     ╚═╝  ╚═╝ ╚═════╝  ╚═════╝    ╚═╝
                """;
        System.out.println();
        System.out.println(BLUE + banner + RESET);
        System.out.println();
    }

    public static void disableLogs(String packageName) {
        java.util.logging.Logger.getLogger(packageName).setLevel(Level.OFF);
    }
}
