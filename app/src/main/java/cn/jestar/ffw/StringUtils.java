package cn.jestar.ffw;

import java.util.Arrays;
import java.util.List;

/**
 * Created by 花京院 on 2019/3/29.
 */

public class StringUtils {
    private static final String NULL_TEXT = "";
    private static final String EMPTY_TEXT = "无";

    public static String getString(List<? extends Object> list) {
        if (list == null || list.isEmpty()) {
            return NULL_TEXT;
        } else {
            String s = list.toString();
            return s.substring(1, s.length() - 1);
        }
    }

    public static <T> String getString(T[] list) {
        if (list == null || list.length == 0) {
            return NULL_TEXT;
        } else {
            String s = Arrays.toString(list);
            return s.substring(1, s.length() - 1);
        }
    }

    public static CharSequence getString(CharSequence text) {
        return text == null || text.length() == 0 ? EMPTY_TEXT : text;
    }
}
