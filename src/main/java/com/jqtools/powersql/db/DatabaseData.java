package com.jqtools.powersql.db;

import java.util.ArrayList;
import java.util.HashMap;

import com.jqtools.powersql.constants.Constants;
import com.jqtools.powersql.obj.ColumnInfo;
import com.jqtools.powersql.obj.Info;
import com.jqtools.powersql.obj.Session;
import com.jqtools.powersql.utils.ExecuteSQL;
import com.jqtools.powersql.utils.Tools;

public class DatabaseData {
	private ArrayList<String> dataTypesWithQuota = new ArrayList<String>();

	public String getCatalogAllSQL() {
		return null;
	}

	public String getSchemaAllSQL(Info info) {
		return null;
	}

	public String getTableSchemaSQL(Info info) {
		return null;
	}

	public String getViewSchemaSQL(Info info) {
		return null;
	}

	public String getColumnSQL(Info info) {
		return null;
	}

	public String getIndexSQL(Info info) {
		return null;
	}

	public ArrayList<String> getDataTypesWithQuota() {
		return dataTypesWithQuota;
	}

	public String getTableSQL(Info info) {
		return new StringBuilder().append("select * from ").append(getTableName(info)).toString();
	}

	public String getTableName(Info info) {
		StringBuilder tableName = new StringBuilder();

		if (info.getSchema() != null) {
			tableName.append(info.getSchema()).append(".");
		}
		tableName.append(info.getName());

		return tableName.toString();
	}

	public String getFilterSQL(String sql, String filterSQL) {
		return new StringBuffer().append("select * from (").append(sql).append(") as filter_sort_tb where ")
				.append(filterSQL).toString();
	}

	public boolean saveTableData(Session session, Info info, ArrayList<ColumnInfo> colInfoList,
			HashMap<Object, Object> changedData, HashMap<Object, Integer> rowStatus) {
		if (session != null) {
			if (ExecuteSQL.execute(session.getConnection(),
					getSQL(session, info, colInfoList, changedData, rowStatus))) {
				return true;
			}
		}

		return false;
	}

	public String[] getSQL(Session session, Info info, ArrayList<ColumnInfo> colInfoList,
			HashMap<Object, Object> changedData, HashMap<Object, Integer> rowStatus) {
		if (session == null || info == null || colInfoList == null || changedData == null) {
			return null;
		}

		StringBuffer buffer = new StringBuffer();
		int status = Constants.REC_STATUS_NONE;
		Object[] keys = changedData.keySet().toArray();
		Object originValues[] = null;
		Object values[] = null;
		String tableName = getTableName(info);
		String sqls[] = null;

		if (keys.length > 0) {
			sqls = new String[keys.length];
			for (int i = 0; i < keys.length; i++) {
				buffer.delete(0, buffer.length());
				if (keys[i] == null || changedData.get(keys[i]) == null) {
					continue;
				}

				originValues = (Object[]) changedData.get(keys[i]);
				values = (Object[]) keys[i];
				status = rowStatus.get(keys[i]);

				if (status == Constants.REC_STATUS_ADD) {
					if (values != null) {
						buffer.append(getInsertSQL(tableName, colInfoList, originValues));
					}
				} else if (status == Constants.REC_STATUS_CHANGED) {
					buffer.append(getUpdateSQL(tableName, colInfoList, values, originValues));
				} else if (status == Constants.REC_STATUS_DEL) {
					buffer.append(getDeleteSQL(tableName, colInfoList, originValues));
				}

				sqls[i] = buffer.toString();
			}
		}

		return sqls;
	}

	public String getInsertSQL(String tableName, ArrayList<ColumnInfo> colInfo, Object values[]) {

		if (tableName == null || values == null || colInfo == null) {
			return "";
		}

		StringBuffer buffer = new StringBuffer().append("INSERT INTO ").append(tableName).append("(");

		for (int i = 0; i < colInfo.size(); i++) {
			if (i > 0) {
				buffer.append(", ");
			}
			buffer.append(colInfo.get(i).getColumnName());
		}
		buffer.append(") VALUES (");

		for (int i = 0; i < values.length; i++) {
			if (i > 0) {
				buffer.append(", ");
			}

			buffer.append(getFormatValue(values[i], colInfo.get(i)));
		}

		buffer.append(")");

		return buffer.toString();
	}

	public String getUpdateSQL(String tableName, ArrayList<ColumnInfo> colInfo, Object[] values,
			Object[] originValues) {

		StringBuffer buffer = new StringBuffer();

		if (values != null && originValues != null && values.length == originValues.length
				&& originValues.length == colInfo.size()) {

			buffer.append("UPDATE ").append(tableName).append(" SET ");

			ColumnInfo cInfo = null;
			int add = 0;
			for (int i = 0; i < values.length; i++) {
				if (!Tools.isEqual(values[i], originValues[i]) || (add == 0 && i == values.length - 1)) {
					if (add > 0) {
						buffer.append(", ");
					}
					cInfo = colInfo.get(i);

					if (values[i] == null) {
						buffer.append(cInfo.getColumnName()).append(" IS NULL ");
					} else {
						buffer.append(cInfo.getColumnName()).append(" = ").append(getFormatValue(values[i], cInfo));
					}

					add++;
				}
			}

			buffer.append(" WHERE ");

			add = 0;
			for (int i = 0; i < colInfo.size(); i++) {
				cInfo = colInfo.get(i);
				if (add > 0) {
					buffer.append(" AND ");
				}

				if (originValues[i] == null) {
					buffer.append(colInfo.get(i).getColumnName()).append(" IS NULL ");
				} else {
					buffer.append(cInfo.getColumnName()).append(" = ").append(getFormatValue(originValues[i], cInfo));
				}

				add++;
			}
		}

		return buffer.toString();
	}

	public String getDeleteSQL(String tableName, ArrayList<ColumnInfo> colInfo, Object[] originValues) {

		StringBuffer buffer = new StringBuffer();

		if (originValues != null && originValues.length == colInfo.size()) {

			buffer.append("DELETE FROM ").append(tableName).append(" WHERE ");

			int add = 0;
			ColumnInfo cInfo = null;
			for (int i = 0; i < colInfo.size(); i++) {
				if (add > 0) {
					buffer.append(" AND ");
				}
				cInfo = colInfo.get(i);

				if (originValues[i] == null) {
					buffer.append(cInfo.getColumnName()).append(" IS NULL ");
				} else {
					buffer.append(cInfo.getColumnName()).append(" = ").append(getFormatValue(originValues[i], cInfo));
				}

				add++;
			}
		}

		return buffer.toString();
	}

	public String getFormatValue(Object value, ColumnInfo cInfo) {
		boolean quote = this.getDataTypesWithQuota().contains(cInfo.getTypeName().toLowerCase());

		StringBuffer buffer = new StringBuffer();

		if (value == null) {
			buffer.append("null");
		} else {
			if (quote) {
				buffer.append(Constants.ESCAPE_CHAR);
			}
			buffer.append(value);
			if (quote) {
				buffer.append(Constants.ESCAPE_CHAR);
			}
		}

		return buffer.toString();
	}

}
