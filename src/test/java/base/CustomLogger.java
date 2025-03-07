package base;

public class CustomLogger {

    public static void step(String message) {
        System.out.println("STEP: " + message);
    }

    public static void log(String message) {
        System.out.println(message);
    }
}
