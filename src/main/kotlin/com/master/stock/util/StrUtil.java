package com.master.stock.util;

public class StrUtil {


    public static boolean startWith(CharSequence str, char c) {
        return c == str.charAt(0);
    }

    public static boolean startWith(CharSequence str, CharSequence prefix, boolean isIgnoreCase) {
        if (null != str && null != prefix) {
            return isIgnoreCase ? str.toString().toLowerCase().startsWith(prefix.toString().toLowerCase()) : str.toString().startsWith(prefix.toString());
        } else {
            return null == str && null == prefix;
        }
    }

    public static boolean startWith(CharSequence str, CharSequence prefix) {
        return startWith(str, prefix, false);
    }

    public static boolean startWithIgnoreCase(CharSequence str, CharSequence prefix) {
        return startWith(str, prefix, true);
    }

    public static boolean startWithAny(CharSequence str, CharSequence... prefixes) {
        if (!isEmpty(str) && !(prefixes==null||prefixes.length==0)) {
            CharSequence[] var2 = prefixes;
            int var3 = prefixes.length;

            for(int var4 = 0; var4 < var3; ++var4) {
                CharSequence suffix = var2[var4];
                if (startWith(str, suffix, false)) {
                    return true;
                }
            }

            return false;
        } else {
            return false;
        }
    }

    public static boolean isEmpty(CharSequence str) {
        return str == null || str.length() == 0;
    }
}
