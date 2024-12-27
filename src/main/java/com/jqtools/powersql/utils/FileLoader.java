package com.jqtools.powersql.utils;

import java.io.File;
import java.io.ObjectInputStream;

import com.jqtools.powersql.log.MessageLogger;

public class FileLoader {
	public static Object readObject(String fileName) {
		if (fileName == null) {
			return null;
		}

		Object object = null;
		File file = null;
		ObjectInputStream ois = null;

		try {
		} catch (Exception ex) {
			MessageLogger.error(ex);
		}

		return null;
	}

	public static boolean writeObject(Object object, String fileName) {
		return true;
	}
}
