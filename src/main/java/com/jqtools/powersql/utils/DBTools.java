package com.jqtools.powersql.utils;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Properties;
import java.util.TreeMap;

import com.jqtools.powersql.constants.Constants;
import com.jqtools.powersql.log.MessageLogger;
import com.jqtools.powersql.obj.DatabaseInfo;

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
			try {
				buildDbMap();
			} catch (Exception e) {
				MessageLogger.error(e);
			}
		}

		return conMap.get(conName);
	}

	public static void addDBConnection(String conName, DatabaseInfo dbCon) {
		updateDBConnection(conName, dbCon);
	}

	public static void updateDBConnection(String conName, DatabaseInfo dbCon) {
		if (conMap == null) {
			try {
				buildDbMap();
			} catch (Exception e) {
				MessageLogger.error(e);
			}
		}

		conMap.put(conName, dbCon);
		if (!conNames.contains(conName)) {
			conNames.add(conName);
		}

		save();
	}

	private static void buildDbMap() throws Exception {
		conMap = new TreeMap<String, DatabaseInfo>();
		conNames = new ArrayList<String>();

		FileInputStream fis = new FileInputStream(Constants.DB_FILE);
		Properties prop = new Properties();
		try {
			prop.load(fis);
		} catch (Exception e) {
			MessageLogger.error(e);
		}

		int totalCons = Tools.getInt(prop.getProperty(CON_COUNT), 0);
		String prefix = null;
		for (int i = 0; i < totalCons; i++) {
			prefix = Tools.getFixString(i + 1, 2);
			DatabaseInfo con = new DatabaseInfo();

			con.setName(getProp(prop, prefix + CON_STRINGS[NAME]));
			con.setUser(getProp(prop, prefix + CON_STRINGS[USER]));
			con.setPassword(getProp(prop, prefix + CON_STRINGS[PWD]));
			con.setDriverName(getProp(prop, prefix + CON_STRINGS[DRIVER]));
			con.setJarFiles(getProp(prop, prefix + CON_STRINGS[JAR]));
			con.setUrl(getProp(prop, prefix + CON_STRINGS[URL]));

			conMap.put(con.getName(), con);
			conNames.add(con.getName());
		}

		fis.close();
	}

	private static String getProp(Properties prop, String key) {
		return Tools.getString(prop.getProperty(key), "");
	}

	private static void save() {
		byte valueB[] = getDBInfo().getBytes();

		InputStream enInput = new ByteArrayInputStream(valueB);

		int readB = 0;
		FileOutputStream out = null;
		try {
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

	private static String getDBInfo() {
		StringBuilder data = new StringBuilder();
		data.append("#").append(Constants.LINE_SEPERATOR).append("# Connections").append(Constants.LINE_SEPERATOR)
				.append("#").append(Constants.LINE_SEPERATOR).append(Tools.getFixString(CON_COUNT, 20)).append(" = ")
				.append(conMap.size()).append(Constants.LINE_SEPERATOR).append(Constants.LINE_SEPERATOR);

		if (conNames != null && conNames.size() > 0) {
			for (int i = 0; i < conNames.size(); i++) {
				String prefix = i >= 9 ? String.valueOf(i) : "0" + (i + 1);
				DatabaseInfo con = conMap.get(conNames.get(i));

				if (con != null) {
					data.append("#").append(Constants.LINE_SEPERATOR).append("# Connection ").append(prefix)
							.append(Constants.LINE_SEPERATOR).append("#").append(Constants.LINE_SEPERATOR)
							.append(Tools.getFixString(prefix + CON_STRINGS[NAME], 20)).append(" = ")
							.append(con.getName()).append(Constants.LINE_SEPERATOR)
							.append(Tools.getFixString(prefix + CON_STRINGS[USER], 20)).append(" = ")
							.append(con.getUser()).append(Constants.LINE_SEPERATOR)
							.append(Tools.getFixString(prefix + CON_STRINGS[PWD], 20)).append(" = ")
							.append(con.getPassword()).append(Constants.LINE_SEPERATOR)
							.append(Tools.getFixString(prefix + CON_STRINGS[DRIVER], 20)).append(" = ")
							.append(con.getDriverName()).append(Constants.LINE_SEPERATOR)
							.append(Tools.getFixString(prefix + CON_STRINGS[JAR], 20)).append(" = ")
							.append(Tools.replaceBackward(con.getJarFiles())).append(Constants.LINE_SEPERATOR)
							.append(Tools.getFixString(prefix + CON_STRINGS[URL], 20)).append(" = ")
							.append(Tools.replaceBackward(con.getUrl())).append(Constants.LINE_SEPERATOR)
							.append(Constants.LINE_SEPERATOR);
				}
			}
		}

		return data.toString();
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

	public static PreparedStatement getStatement(Connection conn, String sql) throws Exception {
		if (sql == null || sql.trim().length() == 0) {
			return null;
		} else {
			return conn.prepareStatement(sql);
		}
	}

	public static void close(Statement stmt, ResultSet rs) {
		if (rs != null) {
			try {
				rs.close();
			} catch (Exception e) {
			}
		}
		if (stmt != null) {
			try {
				stmt.close();
			} catch (Exception e) {
			}
		}
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
