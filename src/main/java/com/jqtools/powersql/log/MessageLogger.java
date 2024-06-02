package com.jqtools.powersql.log;

public class MessageLogger {
	public static void debug(String message) {
		info(message);
	}

	public static void info(String message) {
		System.out.println(message);
	}

	public static void error(String message) {
		info(message);
	}

	public static void info(Throwable ex) {
		info(ex.toString());
	}

	public static void error(Throwable ex) {
		info(ex);
	}
}
