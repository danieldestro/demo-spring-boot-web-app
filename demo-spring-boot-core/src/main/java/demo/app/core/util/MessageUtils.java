package demo.app.core.util;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

/**
 * Utility bean to handle internationalized messages in the application.
 */
@Component
public class MessageUtils {

    @Autowired
    private MessageSource messageSource;

    /**
     * Returns a message based on the key provided. User locale is used.
     * 
     * @param key
     *            message key
     * @return message associated to that key and based on the user locale.
     */
    public String getMessage(String key) {
        return getMessage(key, (Object[]) null);
    }

    /**
     * Returns a message based on the key provided using parameters. User locale is used.
     * 
     * @param key
     *            message key
     * @param args
     *            parameters to be replaced in the message
     * @return parameterized message associated to that key and based on the user locale
     */
    public String getMessage(String key, Object... args) {
        Locale locale = LocaleContextHolder.getLocale();
        return getMessage(key, locale, args);
    }

    /**
     * Returns a message based on the key and locale provided.
     * 
     * @param key
     *            message key
     * @param locale
     *            locale
     * @return message associated to that key and locale.
     */
    public String getMessage(String key, Locale locale) {
        return getMessage(key, locale, (Object[]) null);
    }

    /**
     * Returns a message based on the key and locale provided using parameters.
     * 
     * @param key
     *            message key
     * @param locale
     *            locale
     * @param args
     *            parameters to be replaced in the message
     * @return parameterized message associated to that key based and locale.
     */
    public String getMessage(String key, Locale locale, Object... args) {
        return messageSource.getMessage(key, args, locale);
    }

    /**
     * Returns a message based on the enum fully qualified class name and its item name.
     * 
     * For example: <br/>
     * Enum class: my.very.important.ProductType <br/>
     * Enum item: DVD <br/>
     * Key will be: my.very.important.ProductType.DVD
     * 
     * @param anEnum
     *            an enum item
     * @return parameterized message associated to that key and based on the user locale
     */
    public String getMessage(Enum<?> anEnum) {
        return getMessage(anEnum.getClass().getName() + "." + anEnum.toString());
    }
}
