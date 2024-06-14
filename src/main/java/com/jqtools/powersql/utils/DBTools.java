package com.jqtools.powersql.utils;

import java.io.File;
import java.io.FileInputStream;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.Properties;
import java.util.TreeMap;

import com.jqtools.powersql.constants.Constants;
import com.jqtools.powersql.log.MessageLogger;
import com.jqtools.powersql.obj.DatabaseInfo;
import com.jsoft.dtsql.config.ColorOptions;
import com.jsoft.dtsql.control.Manager;
import com.jsoft.dtsql.db.DBConnection;
import com.jsoft.dtsql.util.CommonTools;

public class DBTools {
	private static final Class<?>[] parameters = new Class[] { URL.class };

	private static final int NAME = 0;
	private static final int USER = 1;
	private static final int PWD = 2;
	private static final int DRIVER = 3;
	private static final int JAR = 4;
	private static final int URL = 5;

	private static final String CON_COUNT = "connections";
	private static final String CON_STRINGS[] = { "_name", "_user", "_pwd", "_driver", "_jar", "_url" };

	private static TreeMap<String, DatabaseInfo> conMap = null;
	private static ArrayList<String> conNames = null;

	public static DatabaseInfo getDBConnection(String conName) {
		if (conName == null)
			return null;

		if (conMap == null) {
			buildDbMap();
		}

		return conMap.get(conName);
	}

	private static void buildDbMap() {
		conMap = new TreeMap<String, DatabaseInfo>();
		conNames = new ArrayList<String>();

		FileInputStream fis = new FileInputStream(Constants.DB_FILE);
		Properties properties = new Properties();
		try {
			properties.load(fis);
		} catch (Exception e) {
			MessageLogger.error(e);
		}

		int totalCons = properties.getInt(CON_COUNT, 0);

		for (int i = 0; i < totalCons; i++) {
			String prefix = i >= 9 ? String.valueOf(i) : "0" + (i + 1);
			DBConnection con = new DBConnection();

			con.setName(properties.getString(prefix + CON_STRINGS[NAME], ""));
			con.setUser(properties.getString(prefix + CON_STRINGS[USER], ""));
			con.setPassword(CommonTools.getS(properties.getString(prefix + CON_STRINGS[PWD], ""), false));
			con.setDriverName(properties.getString(prefix + CON_STRINGS[DRIVER], ""));
			con.setJarFiles(properties.getString(prefix + CON_STRINGS[JAR], ""));
			con.setUrl(properties.getString(prefix + CON_STRINGS[URL], ""));
//			con.setTransIsolation(properties.getInt(prefix + CON_STRINGS[ISO], 0));
//			con.setSqlRestrict(properties.getInt(prefix + CON_STRINGS[SQL], 0));
//			con.setAutoCommit(properties.getInt(prefix + CON_STRINGS[COMMIT], 0));
			con.setDbName(properties.getString(prefix + CON_STRINGS[DB_NAME], ""));
			con.setColor(ColorOptions.getColor(
					properties.getString(prefix + CON_STRINGS[COLOR],
							ColorOptions.getColorString(Manager.getRecycleBarPanel().getDefaultBGC())),
					Manager.getRecycleBarPanel().getDefaultBGC()));

			conMap.put(con.getName(), con);
			conNames.add(con.getName());

		}
	}

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
