package demo.app.core.util;

/**
 * Utility methods for Thread manipulation.
 */
public class ThreadUtils {

    private ThreadUtils() {
    }

    public static final void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
