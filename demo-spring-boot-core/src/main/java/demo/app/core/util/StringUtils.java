package demo.app.core.util;

/**
 * Utility methods for String manipulation.
 */
public class StringUtils {

    private static final String CHARS_ACCENT    = "çÇáéíóúýÁÉÍÓÚÝâêîôûÂÊÎÔÛãõñÃÕÑäëïöüÿÄËÏÖÜàèìòùÀÈÌÒÙ";
    private static final String CHARS_NO_ACCENT = "cCaeiouyAEIOUYaeiouAEIOUaonAONaeiouyAEIOUaeiouAEIOU";

    private StringUtils() {
    }

    /**
     * Normalize a String to replace its characters with accent to one without accent.
     * 
     * @param text
     *            text that may contain accent letters
     * @return normalized text with no accent letter
     */
    public static String normalizeAccentedLetters(String text) {
        if (text == null) {
            return null;
        }

        char[] table;
        table = new char[256];
        for (int i = 0; i < table.length; ++i) {
            table[i] = (char) i;
        }

        for (int i = 0; i < CHARS_ACCENT.length(); ++i) {
            table[CHARS_ACCENT.charAt(i)] = CHARS_NO_ACCENT.charAt(i);
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < text.length(); ++i) {
            char ch = text.charAt(i);
            if (ch < 256) {
                sb.append(table[ch]);
            } else {
                sb.append(ch);
            }
        }
        return sb.toString();
    }

    /**
     * Remove the indicated character from the left side of the String if any.
     * 
     * @param text
     *            String to be modified
     * @param c
     *            character to be remove from the left side if present
     * @return String without any character <code>c</code> in the left side
     */
    public static String removeCharsFromLeft(String text, char c) {
        if (text == null) {
            return null;
        }
        StringBuilder newString = new StringBuilder();
        boolean finite = false;
        for (int i = 0; i < text.length(); i++) {
            if (text.charAt(i) == c && !finite) {
                continue;
            } else {
                finite = true;
                newString.append(text.charAt(i));
            }
        }
        return newString.toString();
    }

    /**
     * Remove the indicated character from the right side of the String if any.
     * 
     * @param text
     *            String to be modified
     * @param c
     *            character to be remove from the right side if present
     * @return String without any character <code>c</code> in the right side
     */
    public static String removeCharsFromRight(String text, char c) {
        if (text == null) {
            return null;
        }
        StringBuilder newString = new StringBuilder();
        boolean finito = false;
        for (int i = text.length() - 1; i >= 0; i--) {
            if (text.charAt(i) == c && !finito) {
                continue;
            } else {
                finito = true;
                newString.insert(0, text.charAt(i));
            }
        }
        return newString.toString();
    }

    /**
     * Evaluates the String argument and returns an empty String if the argument is null or returns the argument itself
     * if not null trimming the String before returning.
     * 
     * @param str
     *            String to be evaluated
     * 
     * @return a String based on the evaluation
     */
    public static String nvl(String str) {
        return ((str == null) ? "" : str.trim());
    }

    /**
     * Evaluates the Object argument and returns an empty String if the argument is null or returns the
     * <code>toString{}</code> of the argument itself if not null trimming the String before returning.
     * 
     * @param str
     *            String to be evaluated
     * 
     * @return a String based on the evaluation
     */
    public static String nvl(Object obj) {
        return ((obj == null) ? "" : obj.toString().trim());
    }

    /**
     * Evaluates the first argument and returns it in case first argument is not null. If the first argument is null,
     * then returns the second argument.
     * 
     * @param one
     *            first String to be evaluated
     * @param another
     *            second String to be evaluated if the first one is null
     * 
     * @return a String based on the evaluation
     */
    public static String decode(String one, String another) {
        return ((one != null) ? one : another);
    }

    /**
     * Evaluates the first argument and returns the second argument in case the first one is true. otherwiese returns
     * the third argument. argument.
     * 
     * @param test
     *            value to be evaluated
     * @param one
     *            String returned case test value is true
     * @param another
     *            String returned case test value is false
     * 
     * @return a String based on the evaluation
     */
    public static String decode(boolean test, String one, String another) {
        return test ? one : another;
    }

    /**
     * Returns a String that is left-padded to the total number of characters specified by length.
     * 
     * @param text
     *            Text to be evaluated
     * @param fill
     *            Character that specifies the pad character
     * @param length
     *            Total number of characters in the returned string
     * 
     * @return left-padded String
     */
    public static String lpad(String text, char fill, int length) {
        return pad(text, fill, length, true);
    }

    /**
     * Returns a String that is right-padded to the total number of characters specified by length.
     * 
     * @param text
     *            Text to be evaluated
     * @param fill
     *            Character that specifies the pad character
     * @param length
     *            Total number of characters in the returned string
     * 
     * @return right-padded String
     */
    public static String rpad(String text, char fill, int length) {
        return pad(text, fill, length, false);
    }

    /**
     * Returns a String that is right or left-padded to the total number of characters specified by length.
     * 
     * @param text
     *            Text to be evaluated
     * @param fill
     *            Character that specifies the pad character
     * @param length
     *            Total number of characters in the returned string
     * @param left
     *            Indicate if the method pads to the left (true) or to the right (false)
     * 
     * @return right or left-padded String
     */
    public static String pad(String text, char fill, int length, boolean left) {
        if (text.length() >= length) {
            return text;
        }
        StringBuilder complete = new StringBuilder();
        for (int i = 0; i < (length - text.length()); i++) {
            complete.append(Character.toString(fill));
        }
        if (left) {
            return complete.append(text).toString();
        } else {
            return text + complete.toString();
        }
    }

    /**
     * Evaluates if the argument is null, is an empty String or if it contains only blank spaces.
     * 
     * @param str
     *            String to be evaluated
     * 
     * @return true if empty, null or with blanks only, false otherwise
     */
    public static boolean isEmpty(String str) {
        return (str == null || str.trim().length() <= 0);
    }

    /**
     * Evaluates if the argument is an empty String or if it contains only blank spaces.
     * 
     * @param str
     *            String to be evaluated
     * 
     * @return true if empty or with blanks only, false otherwise
     */
    public static boolean isBlank(String str) {
        return (str != null && str.trim().length() <= 0);
    }

    /**
     * Evaluates the String argument and check if it is not empty and its length is between <code>min</code> and
     * <code>max</code> size.
     * 
     * @param str
     *            String to be evaluated
     * @param min
     *            min length
     * @param max
     *            max length
     * @return true if not empty and length is between min and max limits, false otherwise
     */
    public static boolean validLength(String str, int min, int max) {
        if (min < 0) {
            throw new IllegalArgumentException("min cannot be negative");
        }
        if (max < 0) {
            throw new IllegalArgumentException("max cannot be negative");
        }
        if (min > max) {
            throw new IllegalArgumentException("min cannot be higher than max");
        }
        if (min != 0 && isEmpty(str)) {
            return false;
        }
        return str.length() >= min && str.length() <= max;
    }

    /**
     * Evaluates the String argument and check if it is not empty and its length is at least <code>min</code> characters
     * long.
     * 
     * @param str
     *            String to be evaluated
     * @param min
     *            min length
     * @return true if not empty and length is at least <code>min</code> characters long, false otherwise
     */
    public static boolean validLength(String str, int min) {
        if (min < 0) {
            throw new IllegalArgumentException("min cannot be negative");
        }
        if (min != 0 && isEmpty(str)) {
            return false;
        }
        return str.length() >= min;
    }
}
