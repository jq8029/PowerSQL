package com.jqtools.powersql.constants;

import java.awt.Color;

import javax.swing.ImageIcon;

public class Constants {
	public static final String USER_NAME = System.getProperty("user.name");
	public static final String USER_PATH = System.getProperty("user.home");
	public static final String FILE_SEPERATOR = System.getProperty("file.separator");
	public static final String LINE_SEPERATOR = System.getProperty("line.separator");
	public static final String BAK_EXT = ".bak";
	public static final String DB_FILE = "data" + FILE_SEPERATOR + "dbinfo.txt";
	public static final String CACHE_FILE = "data" + FILE_SEPERATOR + "cache.txt";
	public static final char ESCAPE_CHAR = '\'';

	public static final String TITLE = "Power SQL";
	public static final String TITLE_FILTER_SORT = "Filter & Sort Table";
	public static final String TITLE_FILTER = "Filter";
	public static String TITLE_SORT = "Sort";

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
	public static final int NODE_TABLE_COLUMNS = 60101;
	public static final int NODE_TABLE_INDEXES = 60102;
	public static final int NODE_TABLE_CONSTRAINTS = 60103;
	public static final int NODE_COLUMN = 7;

	public static final String NAME_TABLES = "Tables";
	public static final String NAME_VIEWS = "Views";
	public static final String NAME_COLS = "Columns";
	public static final String NAME_IDXS = "Indexes";
	public static final String NAME_CONSTRAINTS = "Constraints";

	// Column Info Key Words
	public static final String COL_NAME = "COLUMN_NAME";
	public static final String COL_ORDINAL_POSITION = "ORDINAL_POSITION";
	public static final String COL_IS_NULLABLE = "IS_NULLABLE";
	public static final String COL_TYPE_NAME = "TYPE_NAME";
	public static final String COL_DATA_TYPE = "DATA_TYPE";
	public static final String COL_MAX_LENGTH = "MAX_LENGTH";
	public static final String COL_NUM_LENGTH = "NUM_LENGTH";
	public static final String COL_NUM_SCALE = "NUM_SCALE";
	public static final String COL_COL_DEFAULT = "COL_DEFAULT";

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
	public static String BUTTON_ADD = "Add";
	public static String BUTTON_APPLY = "Apply";
	public static String BUTTON_RESET = "Reset";
	public static String BUTTON_CANCEL = "Cancel";
	public static String BUTTON_TEST = "Test";
	public static String BUTTON_CLOSE = "Close";
	public static String BUTTON_SAVE = "Save";
	public static final Color DEFAULT_BACKGROUND = new Color(238, 238, 238);

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
	public static String MSG_SELECT_COL = "Please select column name.";
	public static String MSG_ENTER_TWO = "Please enter two values separated by comma(,).";

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

	// filter condition
	public static final String CONDITION_EQUAL = "=";
	public static final String CONDITION_NOT_EQUAL_S = "!=";
	public static final String CONDITION_NOT_EQUAL_N = "<>";
	public static final String CONDITION_NOT_MORE = ">";
	public static final String CONDITION_NOT_LESS = "<";
	public static final String CONDITION_NOT_MORE_E = ">=";
	public static final String CONDITION_NOT_LESS_E = "<=";
	public static final String CONDITION_LIKE = "LIKE";
	public static final String CONDITION_NOT_LIKE = "NOT LIKE";
	public static final String CONDITION_IN = "IN";
	public static final String CONDITION_NOT_IN = "NOT IN";
	public static final String CONDITION_BETWEEN = "BETWEEN";
	public static final String CONDITION_NOT_BETWEEN = "NOT BETWEEN";
	public static final String CONDITION_IS_NULL = "IS NULL";
	public static final String CONDITION_IS_NOT_NULL = "IS NOT NULL";
	public static final String CONDITIONS[] = { CONDITION_EQUAL, CONDITION_NOT_EQUAL_S, CONDITION_NOT_EQUAL_N,
			CONDITION_NOT_MORE, CONDITION_NOT_LESS, CONDITION_NOT_MORE_E, CONDITION_NOT_LESS_E, CONDITION_LIKE,
			CONDITION_NOT_LIKE, CONDITION_IN, CONDITION_NOT_IN, CONDITION_BETWEEN, CONDITION_NOT_BETWEEN,
			CONDITION_IS_NULL, CONDITION_IS_NOT_NULL };

	// and/or options
	public static final String OPTION_AND = "AND";
	public static final String OPTION_OR = "OR";
	public static final String AND_OR[] = { OPTION_AND, OPTION_OR };

	// asc/desc
	public static final String ORDER_ASC = "ASC";
	public static final String ORDER_DESC = "DESC";
	public static final String ORDERS[] = { ORDER_ASC, ORDER_DESC };

	// table record status
	public static final int REC_STATUS_NONE = 0;
	public static final int REC_STATUS_ADD = 1;
	public static final int REC_STATUS_DEL = 2;
	public static final int REC_STATUS_CHANGED = 3;

	// Boolean pattern
	public static final String BOOL_UNFORMATTED = "Unformatted";
	public static final String BOOL_TRUE_FALSE = "true/false";
	public static final String BOOL_YES_NO = "yes/no";
	public static final String BOOL_ON_OFF = "on/off";
	public static final String BOOL_0_1 = "1/0";

	// Date/Time pattern
	public static final String TIME01 = "HH:mm:ss";
	public static final String TS01 = "yyyy-MM-dd HH:mm:ss.SSS";
	public static final String DATE01 = "yyyy-MM-dd";

	// Data type names
	public static final String TYPE_NULL_STR = "NULL";
	public static final String TYPE_BIT_STR = "BIT";
	public static final String TYPE_BOOLEAN_STR = "BOOLEAN";
	public static final String TYPE_TIME_STR = "TIME";
	public static final String TYPE_DATE_STR = "DATE";
	public static final String TYPE_TIMESTAMP_STR = "TIMESTAMP";
	public static final String TYPE_BIGINT_STR = "BIGINT";
	public static final String TYPE_DOUBLE_STR = "DOUBLE";
	public static final String TYPE_FLOAT_STR = "FLOAT";
	public static final String TYPE_REAL_STR = "REAL";
	public static final String TYPE_DECIMAL_STR = "DECIMAL";
	public static final String TYPE_NUMERIC_STR = "NUMERIC";
	public static final String TYPE_INTEGER_STR = "INTEGER";
	public static final String TYPE_SMALLINT_STR = "SMALLINT";
	public static final String TYPE_TINYINT_STR = "TINYINT";
	public static final String TYPE_CHAR_STR = "CHAR";
	public static final String TYPE_VARCHAR_STR = "VARCHAR";
	public static final String TYPE_LONGVARCHAR_STR = "LONGVARCHAR";
	public static final String TYPE_NVARCHAR_STR = "NVARCHAR";
	public static final String TYPE_ROWID_STR = "ROWID";
	public static final String TYPE_BINARY_STR = "BINARY";
	public static final String TYPE_VARBINARY_STR = "VARBINARY";
	public static final String TYPE_LONGVARBINARY_STR = "LONGVARBINARY";
	public static final String TYPE_BLOB_STR = "BLOB";
	public static final String TYPE_CLOB_STR = "CLOB";
	public static final String TYPE_JAVA_OBJECT_STR = "JAVA_OBJECT";
	public static final String TYPE_OTHER_STR = "OTHER";
	public static final String TYPE_UNKNOWN_STR = "UNKNOWN";

}
