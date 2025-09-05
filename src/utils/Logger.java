package utils;

public class Logger {
    public static void info(String msg) {
        System.out.println("[INFO] " + msg);
    }

    public static void error(String msg, Exception e) {
        System.err.println("[ERROR] " + msg);
        if (e != null) e.printStackTrace();
    }
}