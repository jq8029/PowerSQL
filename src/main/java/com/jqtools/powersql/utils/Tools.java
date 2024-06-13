package com.jqtools.powersql.utils;

public class Tools {
	public boolean getBoolean(String key, boolean defaultValue) {
		try {
			return Boolean.parseBoolean(key);
		} catch (Exception ex) {
		}

		return defaultValue;
	}

	public int getInt(String key, int defaultValue) {
		try {
			return Integer.parseInt(key);
		} catch (Exception ex) {
		}

		return defaultValue;
	}

	public long getLong(String key, long defaultValue) {
		try {
			return Long.parseLong(key);
		} catch (Exception ex) {
		}

		return defaultValue;
	}

	public String getString(String key, String defaultValue) {
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

}
