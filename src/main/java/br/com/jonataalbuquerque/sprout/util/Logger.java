package br.com.jonataalbuquerque.sprout.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Logger {

    public static final String BLUE = "\u001b[34m";
    public static final String YELLOW = "\u001b[33m";
    public static final String WHITE = "\u001b[37m";
    public static final String RESET = "\u001b[0m";
    public static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static void log(String module, String message) {
        LocalDateTime date = LocalDateTime.now();
        System.out.printf(BLUE + "%15s " + YELLOW + "%-30s" + WHITE + "%s\n" + RESET,
                date.format(formatter), module, message);
    }

    public static void showBanner() {
        String banner = "\n" +
                "  __ __      _____  ____   ____   ____   __  __ ______   __ __  \n" +
                " / // /     / ___/ / __ \\ / __ \\ / __ \\ / / / //_  __/   \\ \\\\ \\ \n" +
                "/ // /      \\__ \\ / /_/ // /_/ // / / // / / /  / /       \\ \\\\ \\\n" +
                "\\ \\\\ \\     ___/ // ____// _, _// /_/ // /_/ /  / /        / // /\n" +
                " \\_\\\\_\\   /____//_/    /_/ |_| \\____/ \\____/  /_/        /_//_/ \n" +
                "                                                                \n";
        System.out.println();
        System.out.println(BLUE + banner + RESET);
        System.out.println();
    }
}
