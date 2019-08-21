package utility;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Logger {
	public static void log(String message) {
		System.out.println("( "+ new SimpleDateFormat().format(new Date()) + " ) " + message);
	}
	
	public static void error(String message) {
		System.err.println("( "+ new SimpleDateFormat().format(new Date()) + " ) " + message);
	}
}
