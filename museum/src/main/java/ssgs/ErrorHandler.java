package ssgs;

import java.io.PrintWriter;
import java.io.StringWriter;

public class ErrorHandler {
    public static void handleException(Exception e, String message) {
        logError(message);
        logError(getStackTrace(e));
    }

    public static void logError(String errorMessage) {
        System.err.println(errorMessage);

    }

    public static String getStackTrace(Exception e) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);
        return sw.toString();
    }
}
