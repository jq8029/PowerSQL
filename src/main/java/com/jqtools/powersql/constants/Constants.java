package com.jqtools.powersql.constants;

import javax.swing.ImageIcon;

public class Constants {
	public static final String USER_NAME = System.getProperty("user.name");
	public static final String USER_PATH = System.getProperty("user.home");
	public static final String FILE_SEPERATOR = System.getProperty("file.separator");
	public static final String LINE_SEPERATOR = System.getProperty("line.separator");
	public static final String DB_FILE = "data" + FILE_SEPERATOR + "dbinfo.txt";

	public static final String TITLE = "Power SQL";
	public static final String TITLE_FILTER_SORT = "Filter & Sort Table";

	public static final String ROOT = "Databases";

	public static final String PANEL_NORTH = "North";
	public static final String PANEL_CENTER = "Center";
	public static final String PANEL_SOUTH = "South";

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

	// Supported Databases
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
	public static String CONNECTION_DB_NAME = "Database Name";

	// Button
	public static String BUTTON_TEST = "Test";
	public static String BUTTON_CLOSE = "Close";
	public static String BUTTON_SAVE = "Save";

	// Title
	public static String TITLE_CONNECTION = "Connection";

	// Messages
	public static String MSG_SAVE_CHANGE = "Do you want to save the change?";
	public static String MSG_FAIL_CONNECT = "Failed to connect database : ";
	public static String MSG_FAIL_EXECUTE = "Failed to execute sql : \n";
	public static String MSG_CONN_SUCCESS = "Connect database successfully.";
	public static String MSG_UNABLE_CONN = "Unable to connect database.";
	public static String MSG_OVERWRITE_CONN = "Do you want to overwrite current connection ?";
	public static String MSG_DELETE_CONN = "Do you want to delete current connection ?";

	// Menu
	public static String MENU_CREATE = "Create";
	public static String MENU_EDIT = "Edit";
	public static String MENU_DELETE = "Delete";
	public static String MENU_DUPLICATE = "Duplicate";

	// Images ICON
	public static ImageIcon ICON_DATABASES = new ImageIcon("images/databases.png");
	public static ImageIcon ICON_DATABASE = new ImageIcon("images/database.png");
	public static ImageIcon ICON_CATALOG = new ImageIcon("images/catalog.png");
	public static ImageIcon ICON_SCHEMA = new ImageIcon("images/schema.png");
	public static ImageIcon ICON_TABLES = new ImageIcon("images/table.png");
	public static ImageIcon ICON_TABLE = new ImageIcon("images/table.png");
	public static ImageIcon ICON_VIEWS = new ImageIcon("images/view.png");
	public static ImageIcon ICON_VIEW = new ImageIcon("images/view.png");
	public static ImageIcon ICON_EXECUTE = new ImageIcon("images/execute.png");

	public static String ICON_PATH = "images/";
	public static String ICON_EXTENSION = ".png";

	// Tool bar
	public static final int SQL_TOOLBAR_EXECUTE = 0;
	public static final int SQL_TOOLBAR_EXECUTE_STOP = 1;
	public static final int SQL_TOOLBAR_UNDO = 2;
	public static final int SQL_TOOLBAR_REDO = 3;
	public static final int SQL_TOOLBAR_CUT = 4;
	public static final int SQL_TOOLBAR_COPY = 5;
	public static final int SQL_TOOLBAR_PASTE = 6;
	public static int SQL_TOOLBAR_SEPARATOR[] = { SQL_TOOLBAR_EXECUTE_STOP, SQL_TOOLBAR_REDO };
	public static String SQL_TEXTS[] = { "execute", "stop", "undo", "redo", "cut", "copy", "paste" };
	public static boolean SQL_ENABLES[] = { true, true, false, false, false, false, true };

	public static final int DATA_TOOLBAR_REFRESH = 0;
	public static final int DATA_TOOLBAR_FILTER = 1;
	public static final int DATA_TOOLBAR_EXPORT = 2;
	public static final int DATA_TOOLBAR_ADD = 3;
	public static final int DATA_TOOLBAR_DELETE = 4;
	public static final int DATA_TOOLBAR_DUPLICATE = 5;
	public static final int DATA_TOOLBAR_SAVE = 6;
	public static final int DATA_TOOLBAR_SEARCH = 7;
	public static String DATA_TEXTS[] = { "refresh", "filter", "export", "add", "delete", "duplicate", "save",
			"search" };
	public static boolean DATA_ENABLES[] = { true, true, true, false, false, false, false, true };

	public static final int SPACE_02 = 2;
	public static final int SPACE_03 = 3;
	public static final int SPACE_10 = 10;
	public static final int SPACE_21 = 21;
	public static final int SPACE_23 = 23;
	public static final int SPACE_24 = 24;
	public static final int SPACE_100 = 100;

}
