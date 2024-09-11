package com.jqtools.powersql.utils;

import javax.swing.ImageIcon;
import javax.swing.JButton;

import com.jqtools.powersql.constants.Constants;

public class Tools {
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
		button.setEnabled(enabled);
		button.setToolTipText(text);

		return button;
	}
}
