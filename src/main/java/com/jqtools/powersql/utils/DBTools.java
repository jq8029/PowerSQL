package com.jqtools.powersql.utils;

import java.io.File;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.sql.Connection;
import java.sql.DriverManager;

import com.jqtools.powersql.obj.DatabaseInfo;

public class DBTools {
	private static final Class<?>[] parameters = new Class[] { URL.class };

	public static Connection getConnection(DatabaseInfo dbInfo) throws Exception {
		if (dbInfo == null) {
			return null;
		}

		Connection con = null;

		addFile(dbInfo.getJarFiles());

		Class.forName(dbInfo.getDriverName());

		if (dbInfo.getUser() == null || dbInfo.getUser().trim().length() == 0) {
			con = DriverManager.getConnection(dbInfo.getUrl());
		} else {
			con = DriverManager.getConnection(dbInfo.getUrl(), dbInfo.getUser(), dbInfo.getPassword());
		}

		return con;
	}

	public static boolean addFile(String fileName) throws Exception {
		if (fileName == null)
			return false;

		File file = new File(fileName);

		if (!file.exists()) {
			return false;
		}

		URL url = file.toURI().toURL();
		URLClassLoader sysLoader = (URLClassLoader) ClassLoader.getSystemClassLoader();
		URL urls[] = sysLoader.getURLs();

		for (int i = 0; i < urls.length; i++) {
			if (urls[i] == null || urls[i].toString() == null)
				continue;

			// skip if the file does exist.
			if (urls[i].toString().equalsIgnoreCase(url.toString())) {
				return true;
			}
		}

		Class<URLClassLoader> sysclass = URLClassLoader.class;
		Method method = sysclass.getDeclaredMethod("addURL", parameters);
		method.setAccessible(true);
		method.invoke(sysLoader, new Object[] { url });

		return true;
	}

}
