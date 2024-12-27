package com.jqtools.powersql.utils;

import java.io.File;
import java.io.FileInputStream;
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
			file = new File(fileName);

			if (file != null && file.isFile() && file.exists()) {
				ois = new ObjectInputStream(new FileInputStream(file));
				object = ois.readObject();
			}
		} catch (Exception ex) {
			MessageLogger.error(ex);
		} finally {
			try {
				if (ois != null) {
					ois.close();
				}
			} catch (Exception e) {
			}
		}

		return null;
	}

	public static boolean writeObject(Object object, String fileName) {
		return true;
	}
}
