package com.jqtools.powersql.utils;

import java.io.File;
import java.io.ObjectInputStream;

public class FileLoader {
	public static Object readObject(String fileName) {
		if (fileName == null) {
			return null;
		}

		Object object = null;
		File file = null;
		ObjectInputStream ois = null;

		return null;
	}

	public static boolean writeObject(Object object, String fileName) {
		return true;
	}
}
