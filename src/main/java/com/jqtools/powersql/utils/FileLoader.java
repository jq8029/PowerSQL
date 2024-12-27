package com.jqtools.powersql.utils;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import com.jqtools.powersql.constants.Constants;
import com.jqtools.powersql.log.MessageLogger;

public class FileLoader {
	public static void saveToFile(String fileName, byte[] data) {
		InputStream enInput = new ByteArrayInputStream(data);

		int readB = 0;
		FileOutputStream out = null;
		try {
			// write database to prop file
			out = new FileOutputStream(Constants.DB_FILE);
			while ((readB = enInput.read()) != -1) {
				out.write(readB);
			}
		} catch (Exception e) {
			MessageLogger.error(e);
		} finally {
			try {
				enInput.close();
			} catch (Exception e) {
			}
			if (out != null) {
				try {
					out.flush();
				} catch (Exception e) {
				}
				try {
					out.close();
				} catch (IOException e) {
				}
			}
		}
	}

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
			if (file.exists()) {
				if (bakFile.exists()) {
					bakFile.delete();
				}
				file.renameTo(bakFile);
			} else {
				file.createNewFile();
			}

			oos = new ObjectOutputStream(new FileOutputStream(file));
			oos.writeObject(object);
			oos.flush();

			if (bakFile.exists()) {
				bakFile.delete();
			}

			return true;
		} catch (Exception ex) {
			MessageLogger.error(ex);
		} finally {
			try {
				if (oos != null)
					oos.close();
			} catch (Exception e) {
			}
		}

		return false;
	}
}
