package com.jqtools.powersql.utils;

import java.awt.Component;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Time;
import java.sql.Types;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.LookAndFeel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.filechooser.FileFilter;

import com.jqtools.powersql.constants.Constants;
import com.jqtools.powersql.gui.PFileFilter;
import com.jqtools.powersql.log.MessageLogger;
import com.jqtools.powersql.obj.Session;

import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

public class Tools {
	private static HashMap<Integer, Boolean> quotaMap = null;
	private static final SimpleDateFormat SDF = new SimpleDateFormat(Constants.TS01);
	private static JFileChooser fc = new JFileChooser();

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

	public static boolean isEqual(Object value1, Object value2) {
		if (value1 == null && value2 == null) {
			return true;
		} else if (value1 == null || value2 == null) {
			return false;
		} else {
			return isEqual(value1.toString(), value2.toString());
		}
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

	public static String typeToString(int columnType) {
		switch (columnType) {
		case Types.NULL:
			return Constants.TYPE_NULL_STR;
		case Types.BIT:
			return Constants.TYPE_BIT_STR;
		case Types.BOOLEAN:
			return Constants.TYPE_BOOLEAN_STR;
		case Types.TIME:
			return Constants.TYPE_TIME_STR;
		case Types.DATE:
			return Constants.TYPE_DATE_STR;
		case Types.TIMESTAMP:
		case -101: // Oracle's 'TIMESTAMP WITH TIME ZONE'
		case -102: // Oracle's 'TIMESTAMP WITH LOCAL TIME ZONE'
			return Constants.TYPE_TIMESTAMP_STR;
		case Types.BIGINT:
			return Constants.TYPE_BIGINT_STR;
		case Types.DOUBLE:
			return Constants.TYPE_DOUBLE_STR;
		case Types.FLOAT:
			return Constants.TYPE_FLOAT_STR;
		case Types.REAL:
			return Constants.TYPE_REAL_STR;
		case Types.DECIMAL:
			return Constants.TYPE_DECIMAL_STR;
		case Types.NUMERIC:
			return Constants.TYPE_NUMERIC_STR;
		case Types.INTEGER:
			return Constants.TYPE_INTEGER_STR;
		case Types.SMALLINT:
			return Constants.TYPE_SMALLINT_STR;
		case Types.TINYINT:
			return Constants.TYPE_TINYINT_STR;
		case Types.CHAR:
			return Constants.TYPE_CHAR_STR;
		case Types.VARCHAR:
			return Constants.TYPE_VARCHAR_STR;
		case Types.LONGVARCHAR:
			return Constants.TYPE_LONGVARCHAR_STR;
		case -9:
			return Constants.TYPE_NVARCHAR_STR;
		case -8:
			return Constants.TYPE_ROWID_STR;
		case Types.BINARY:
			return Constants.TYPE_BINARY_STR;
		case Types.VARBINARY:
			return Constants.TYPE_VARBINARY_STR;
		case Types.LONGVARBINARY:
			return Constants.TYPE_LONGVARBINARY_STR;
		case Types.BLOB:
			return Constants.TYPE_BLOB_STR;
		case Types.CLOB:
			return Constants.TYPE_CLOB_STR;
		case Types.JAVA_OBJECT:
			return Constants.TYPE_JAVA_OBJECT_STR;
		case Types.OTHER:
			return Constants.TYPE_OTHER_STR;
		default:
			return Constants.TYPE_UNKNOWN_STR;
		}
	}

	public static void exportDataToXLS(Session session, String sql, File file) throws Exception {
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String values[] = null;
		WritableWorkbook workbook = null;

		try {
			WorkbookSettings ws = new WorkbookSettings();
			workbook = Workbook.createWorkbook(file, ws);
			WritableFont wf = new WritableFont(WritableFont.ARIAL, Constants.FONT_10, WritableFont.NO_BOLD);
			WritableCellFormat cf = new WritableCellFormat(wf);
			WritableSheet sheet = workbook.createSheet("Result", 0);
			Label label = null;
			int total = 0, count = 0;

			cf.setWrap(false);

			MessageLogger.info("  Started to export. Please wait ...");
			stmt = session.getConnection().prepareStatement(sql);
			rs = stmt.executeQuery();

			while (rs.next()) {
				if (total == 0) {
					values = DBTools.getColumnNames(rs);
					// write column name to sheet
					count = 0;
					for (int x = 0; x < values.length; x++) {
						label = new Label(count++, total, values[x] == null ? "" : values[x], cf);
						sheet.addCell(label);
					}
					total++;
				}

				values = DBTools.getValues(rs);

				// skip it if there is no data
				if (values == null) {
					continue;
				}

				// write a row of records to sheet
				count = 0;
				for (int x = 0; x < values.length; x++) {
					label = new Label(count++, total, values[x] == null ? "" : values[x], cf);
					sheet.addCell(label);
				}

				// total control the row number
				total++;
			}
			workbook.write();

			MessageLogger.info("  Finished exporting " + (total - 1) + " records from result.");
		} finally {
			DBTools.close(stmt, rs);

			try {
				workbook.close();
			} catch (Exception e) {
			}
		}
	}

	public static void exportDataToCSV(Session session, String sql, File file) throws Exception {
		FileOutputStream out = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String values[] = null;
		int total = 0, count = 0;
		StringBuilder build = new StringBuilder();

		if (file.exists()) {
			file.delete();
		}

		try {
			file.createNewFile();
		} catch (Exception ex) {
			throw new Exception("Failed to create exporting data file " + file.getAbsolutePath());
		}

		try {
			MessageLogger.info("  Started to export. Please wait ...");

			out = new FileOutputStream(file);
			stmt = session.getConnection().prepareStatement(sql);
			rs = stmt.executeQuery();

			while (rs.next()) {
				if (total == 0) {
					values = DBTools.getColumnNames(rs);
					// write column name to file
					count = 0;
					for (int i = 0; i < values.length; i++) {
						if (i > 0) {
							build.append(Constants.LINE_SEPERATOR);
						}
						build.append(values[i]);
					}
					total++;
					out.write(build.toString().getBytes());
					out.write(Constants.LINE_SEPERATOR.getBytes());
				}

				values = DBTools.getValues(rs);
				build.delete(0, build.length());

				// skip it if there is no data
				if (values == null) {
					continue;
				}

				// write a row of records to file
				count = 0;
				for (int i = 0; i < values.length; i++) {
					if (i > 0) {
						build.append(Constants.LINE_SEPERATOR);
					}
					build.append(values[i]);
				}

				// total control the row number
				total++;
				out.write(build.toString().getBytes());
			}

			MessageLogger.info("  Finished exporting " + (total - 1) + " records from result.");
		} finally {
			DBTools.close(stmt, rs);

			try {
				out.close();
			} catch (Exception e) {
			}
		}
	}

	public static File chooseFiles(File oldFile, String[] fileExts, Component component) {
		LookAndFeel lookFeel = UIManager.getLookAndFeel();
		File newFile = null;

		try {
			UIManager.setLookAndFeel(Constants.WIN_CLASS_NAME);
		} catch (Exception ex) {
			MessageLogger.info(ex);
		}

		if (fc != null) {
			try {
				SwingUtilities.updateComponentTreeUI(fc);
			} catch (Exception ex) {
				MessageLogger.info(ex);
			}
			fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		}

		if (oldFile != null && oldFile.getName() != null && oldFile.getName().trim().length() > 0) {
			fc.setSelectedFile(oldFile.getAbsoluteFile());
		}

		if (fc.getChoosableFileFilters() != null && fc.getChoosableFileFilters().length > 0) {
			for (int i = 0; i < fc.getChoosableFileFilters().length; i++) {
				if (fc.getChoosableFileFilters()[i] instanceof FileFilter) {
					fc.removeChoosableFileFilter(fc.getChoosableFileFilters()[i]);
				}
			}
		}

		fc.addChoosableFileFilter(new PFileFilter(fileExts));
		fc.setMultiSelectionEnabled(false);

		int rc = fc.showOpenDialog(component);
		if (rc == JFileChooser.APPROVE_OPTION) {
			try {
				newFile = fc.getSelectedFile();
			} catch (Exception e1) {
				MessageLogger.info(e1);
				newFile = null;
			}
		}

		try {
			UIManager.setLookAndFeel(lookFeel);
		} catch (Exception ex) {
			MessageLogger.info(ex);
		}

		if (fc != null) {
			try {
				SwingUtilities.updateComponentTreeUI(fc);
			} catch (Exception ex) {
				MessageLogger.info(ex);
			}
		}

		return newFile;
	}

}
