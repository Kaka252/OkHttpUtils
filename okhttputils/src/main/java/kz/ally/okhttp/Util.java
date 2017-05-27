package kz.ally.okhttp;

/**
 * 作者：ZhouYou
 * 日期：2017/3/13.
 */
public class Util {

    public static String castString(Object o) {
        String value;
        if (o instanceof String) {
            value = (String) o;
        } else if (o instanceof Integer) {
            value = String.valueOf(o);
        } else if (o instanceof Boolean) {
            value = String.valueOf(o);
        } else if (o instanceof Long) {
            value = String.valueOf(o);
        } else if (o instanceof Float) {
            value = String.valueOf(o);
        } else if (o instanceof Short) {
            value = String.valueOf(o);
        } else {
            value = o.toString();
        }
        return value;
    }
}


