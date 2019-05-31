package tools;

public class StringUtils {

	public static boolean isEmpty(String str) {
		return str == null || str.length() == 0 || str.equalsIgnoreCase("null");
	}

	public static boolean isNotEmpty(String str) {
		return !StringUtils.isEmpty(str);
	}
}
