package com.jqtools.powersql.constants;

public class Constants {
	public static final String USER_NAME = System.getProperty("user.name");
	public static final String USER_PATH = System.getProperty("user.home");
	public static final String FILE_SEPERATOR = System.getProperty("file.separator");
	public static final String LINE_SEPERATOR = System.getProperty("line.separator");
	public static final String DB_FILE = "data" + FILE_SEPERATOR + "dbinfo.txt";

	public static final String TITLE = "Power SQL";
	public static final String ROOT = "Databases";

	public static final String PANEL_CENTER = "Center";
	public static final int DIVIDER_SIZE = 2;
	public static final int LEFT_WIDE = 300;
	public static final int TOP_WIDE = 320;

	public static final String TAB_DATA = "Data";
	public static final String TAB_SQL = "SQL";

	public static final int NODE_TEXT = 0;
	public static final int NODE_CONNECTIONS = 1;
	public static final int NODE_CONNECTION = 2;
	public static final int NODE_CATALOG = 3;
	public static final int NODE_SCHEMA = 4;
	public static final int NODE_TABLES = 5;
	public static final int NODE_VIEWS = 6;
	public static final int NODE_TABLE = 501;
	public static final int NODE_VIEW = 601;

	public static final String NAME_TABLES = "Tables";
	public static final String NAME_VIEWS = "Views";

	public static final String MY_CATALOG = "MY_CATALOG";
	public static final String MY_SCHEMA = "MY_SCHEMA";
	public static final String MY_TABLE = "MY_TABLE";
	public static final String MY_VIEW = "MY_VIEW";
}
