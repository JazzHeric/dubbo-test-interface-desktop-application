package tools;

import java.io.PrintWriter;
import java.io.StringWriter;

public class ExceptionTools {

	public static String getStackTrace(Throwable throwable) {
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		try {
			throwable.printStackTrace(pw);
			return sw.toString();
		} finally {
			pw.close();
		}
	}

}
