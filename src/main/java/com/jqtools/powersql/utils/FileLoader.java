package com.jqtools.powersql.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import com.jqtools.powersql.constants.Constants;
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

		return object;
	}

	public static boolean writeObject(Object object, String fileName) {
		if (object == null || fileName == null) {
			return false;
		}

		ObjectOutputStream oos = null;

		File file = new File(fileName);
		File bakFile = new File(fileName + Constants.EXT_BAK);

		try {
		} catch (Exception ex) {
			MessageLogger.error(ex);
		} finally {
			try {
				if (oos != null) {
					oos.close();
				}
			} catch (Exception e) {
			}
		}

		return false;
	}
}
