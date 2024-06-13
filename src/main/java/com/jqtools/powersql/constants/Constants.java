package com.jqtools.powersql.constants;

public class Constants {
	public static final String USER_NAME = System.getProperty("user.name");
	public static final String USER_PATH = System.getProperty("user.home");
	public static final String FILE_SEPERATOR = System.getProperty("file.separator");
	public static final String DB_FILE = USER_PATH + FILE_SEPERATOR + ".psql" + FILE_SEPERATOR + "dbinfo.txt";

	public static final String TITLE = "Power SQL";
	public static final String ROOT = "Databases";

	public static final String PANEL_CENTER = "Center";
	public static final int DIVIDER_SIZE = 2;
	public static final int LEFT_WIDE = 300;
	public static final int TOP_WIDE = 320;

	public static final String TAB_DATA = "Data";
	public static final String TAB_SQL = "SQL";
}
