package com.jqtools.powersql.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.Time;
import java.sql.Types;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;

import com.jqtools.powersql.constants.Constants;
import com.jqtools.powersql.log.MessageLogger;

public class Tools {
	private static HashMap<Integer, Boolean> quotaMap = null;
	private static final SimpleDateFormat SDF = new SimpleDateFormat(Constants.TS01);

	public static boolean getBoolean(String key, boolean defaultValue) {
		try {
			return Boolean.parseBoolean(key);
		} catch (Exception ex) {
		}

		return defaultValue;
	}

	public static int getInt(String key, int defaultValue) {
		try {
			return Integer.parseInt(key);
		} catch (Exception ex) {
		}

		return defaultValue;
	}

	public static long getLong(String key, long defaultValue) {
		try {
			return Long.parseLong(key);
		} catch (Exception ex) {
		}

		return defaultValue;
	}

	public static String getString(String key, String defaultValue) {
		try {
			if (key == null) {
				return defaultValue;
			} else {
				return key;
			}
		} catch (Exception ex) {
		}

		return defaultValue;
	}

	public static String getFixString(long value, int len) {
		return getFixString(String.valueOf(value), len, '0', false);
	}

	public static String getFixString(String value, int len) {
		return getFixString(value, len, ' ', true);
	}

	public static String getFixString(String str, int len, char pad, boolean padRight) {
		StringBuffer fixStr = new StringBuffer();

		if (padRight) {
			fixStr.append(str);
			while (fixStr.length() < len)
				fixStr.append(pad);

			return fixStr.substring(0, len);
		} else {
			while (fixStr.length() < len - str.length())
				fixStr.append(pad);
			fixStr.append(str);

			return fixStr.substring(fixStr.length() - len, fixStr.length());
		}

	}

	public static String replaceBackward(String value) {
		if (value == null)
			return "";
		if (value.indexOf("\\") > 0) {
			value = value.replaceAll("\\\\", "\\\\\\\\");
		}

		return value;
	}

	public static JButton getImageButton(String text, boolean enabled) {
		JButton button = new JButton(new ImageIcon(Constants.ICON_PATH + text + Constants.ICON_EXTENSION));
		button.setActionCommand(text);
		button.setEnabled(enabled);
		button.setToolTipText(text);

		return button;
	}

	public static String getSelectedItem(JComboBox<String> box) {
		if (box == null || box.getSelectedItem() == null)
			return null;

		return box.getSelectedItem().toString();
	}

	public static boolean isEqual(String value1, String value2) {
		if (value1 == null && value2 == null) {
			return true;
		} else if (value1 != null && value2 == null) {
			return false;
		} else if (value1 == null && value2 != null) {
			return false;
		} else {
			return value1.equals(value2);
		}
	}

	public static boolean isQuote(int type) {
		if (quotaMap == null) {
			quotaMap = new HashMap<Integer, Boolean>();

			quotaMap.put(Types.ARRAY, false);
			quotaMap.put(Types.BIGINT, false);
			quotaMap.put(Types.BINARY, false);
			quotaMap.put(Types.BIT, false);
			quotaMap.put(Types.BLOB, false);
			quotaMap.put(Types.BOOLEAN, false);
			quotaMap.put(Types.CHAR, true);
			quotaMap.put(Types.CLOB, true);
			quotaMap.put(Types.DATALINK, false);
			quotaMap.put(Types.DATE, true);
			quotaMap.put(Types.DECIMAL, false);
			quotaMap.put(Types.DISTINCT, false);
			quotaMap.put(Types.DOUBLE, false);
			quotaMap.put(Types.FLOAT, false);
			quotaMap.put(Types.INTEGER, false);
			quotaMap.put(Types.JAVA_OBJECT, false);
			quotaMap.put(Types.LONGNVARCHAR, true);
			quotaMap.put(Types.LONGVARBINARY, false);
			quotaMap.put(Types.LONGVARCHAR, true);
			quotaMap.put(Types.NCHAR, true);
			quotaMap.put(Types.NCLOB, true);
			quotaMap.put(Types.NULL, false);
			quotaMap.put(Types.NUMERIC, false);
			quotaMap.put(Types.NVARCHAR, true);
			quotaMap.put(Types.OTHER, false);
			quotaMap.put(Types.REAL, false);
			quotaMap.put(Types.REF, false);
			quotaMap.put(Types.ROWID, false);
			quotaMap.put(Types.SMALLINT, false);
			quotaMap.put(Types.SQLXML, true);
			quotaMap.put(Types.STRUCT, false);
			quotaMap.put(Types.TIME, true);
			quotaMap.put(Types.TIMESTAMP, true);
			quotaMap.put(Types.TINYINT, false);
			quotaMap.put(Types.VARBINARY, false);
			quotaMap.put(Types.VARCHAR, true);
		}

		if (quotaMap.containsKey(type)) {
			return quotaMap.get(type);
		} else {
			return false;
		}
	}

	public static boolean hasValue(String value) {
		if (value == null || value.trim().length() == 0) {
			return false;
		} else {
			return true;
		}
	}

	public static boolean saveObject(Object object, String fileName) {
		if (object == null || fileName == null)
			return false;

		ObjectOutputStream oos = null;

		File file = new File(fileName);
		File bakFile = new File(fileName + Constants.BAK_EXT);

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
			MessageLogger.info(ex);
		} finally {
			try {
				if (oos != null)
					oos.close();
			} catch (Exception e) {
			}
		}

		return false;
	}

	public static Object loadObject(String fileName) {
		if (fileName == null)
			return null;

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
			MessageLogger.info(ex);
		} finally {
			try {
				if (ois != null)
					ois.close();
			} catch (Exception e) {
			}
		}

		return object;
	}

	// format data
	public static String formatBoolean(String pattern, Boolean b) {
		if (pattern == null || b == null)
			return null;

		if (pattern.equals(Constants.BOOL_TRUE_FALSE)) {
			return b ? "true" : "false";
		} else if (pattern.equals(Constants.BOOL_YES_NO)) {
			return b ? "yes" : "no";
		} else if (pattern.equals(Constants.BOOL_ON_OFF)) {
			return b ? "on" : "off";
		} else if (pattern.equals(Constants.BOOL_0_1)) {
			return b ? "1" : "0";
		} else {
			return b.toString();
		}
	}

	public static String formatDate(String pattern, Date date) {
		if (pattern == null || date == null) {
			return null;
		}

		if (!pattern.equals(SDF.toPattern())) {
			SDF.applyPattern(pattern);
		}

		return SDF.format(date);
	}

	public static String formatCell(int colType, Object object) {
		if (object == null)
			return null;

		String value = null;
		switch (colType) {
		case Types.NULL:
			value = null;
			break;

		case Types.BIT:
		case Types.BOOLEAN:
			value = object.toString();
			break;

		case Types.TIME:
			value = formatDate(Constants.TIME01, (Time) object);
			break;

		case Types.DATE:
			value = formatDate(Constants.DATE01, (Date) object);
			break;

		case Types.TIMESTAMP:
		case -101: // Oracle Time Zone
		case -102: // Oracle Local Time Zone
			value = formatDate(Constants.TS01, (Date) object);
			break;

		default:
			value = object.toString();
		}

		return value;
	}
}
