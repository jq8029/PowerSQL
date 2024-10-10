package com.jqtools.powersql.db;

import java.util.ArrayList;
import java.util.HashMap;

import com.jqtools.powersql.obj.ColumnInfo;
import com.jqtools.powersql.obj.Info;
import com.jqtools.powersql.obj.Session;
import com.jqtools.powersql.utils.ExecuteSQL;

public class DatabaseData {
	public static final String MY_CATALOG = "MY_CATALOG";
	public static final String MY_SCHEMA = "MY_SCHEMA";
	public static final String MY_TABLE = "MY_TABLE";
	public static final String MY_VIEW = "MY_VIEW";

	public String getCatalogAllSQL() {
		return null;
	}

	public String getSchemaAllSQL() {
		return null;
	}

	public String getTableSchemaSQL() {
		return null;
	}

	public String getViewSchemaSQL() {
		return null;
	}

	public String getCatalogName() {
		return MY_CATALOG;
	}

	public String getSchemaName() {
		return MY_SCHEMA;
	}

	public String getTableName() {
		return MY_TABLE;
	}

	public String getViewName() {
		return MY_VIEW;
	}

	public String getTableSQL(Info info) {
		StringBuilder sql = new StringBuilder().append("select * from ");
		if (info.getSchema() != null) {
			sql.append(info.getSchema()).append(".");
		}
		sql.append(info.getName());

		return sql.toString();
	}

	public String getFilterSQL(String sql, String filterSQL) {
		return new StringBuffer().append("select * from (").append(sql).append(") as filter_sort_tb where ")
				.append(filterSQL).toString();
	}

	public void saveTableData(Session session, Info info, ArrayList<ColumnInfo> colInfo,
			HashMap<Object, Object> changedData, HashMap<Object, Integer> rowStatus) {
		if (session != null) {
			if (ExecuteSQL.execute(session.getConnection(), getSQL(session, info, colInfo, changedData, rowStatus))) {

			}
		}
	}

	private String getSQL(Session session, Info info, ArrayList<ColumnInfo> colInfo,
			HashMap<Object, Object> changedData, HashMap<Object, Integer> rowStatus) {
		if (session == null || info == null || colInfo == null || changedData == null) {
			return "";
		}

		StringBuffer buffer = new StringBuffer();

		if (changedData.size() > 0) {

		}

		return buffer.toString();
	}
}
