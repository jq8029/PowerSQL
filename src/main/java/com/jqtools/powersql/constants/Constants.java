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

	public static final String DB_H2 = "H2";
	public static final String DB_MYSQL = "MySQL";

	public static final String KEY_EXEC_CURRENT_SQL = "Execute Current SQL";

	// message
	public static String MESSAGE_INFO = "Info";
	public static String MESSAGE_CONFIRM = "Confirm";
	public static String MESSAGE_CLOSE = "Close";

	// Connections
	public static String CONNECTION_NAME = "Connection Name";
	public static String CONNECTION_USER = "User Id";
	public static String CONNECTION_PWD = "Password";
	public static String CONNECTION_URL = "URL";
	public static String CONNECTION_DRIVER_NAME = "Driver Name";
	public static String CONNECTION_DRIVER_JAR = "Driver Jar Files";

	// Button
	public static String BUTTON_TEST = "Test";
	public static String BUTTON_CLOSE = "Close";
	public static String BUTTON_SAVE = "Save";

	// Title
	public static String TITLE_CONNECTION = "Connection";

	// Messages
	public static String MSG_SAVE_CHANGE = "Do you want to save the change?";
}
