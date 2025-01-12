package com.jqtools.powersql.db;

import com.jqtools.powersql.constants.Constants;
import com.jqtools.powersql.obj.ColumnInfo;
import com.jqtools.powersql.obj.Info;

public class MySQLData extends DatabaseData {

	@Override
	public String getSchemaAllSQL(Info info) {
		return "SELECT SCHEMA_NAME as MY_SCHEMA FROM information_schema.SCHEMATA order by SCHEMA_NAME";
	}

	@Override
	public String getTableSchemaSQL(Info info) {
		return "SELECT TABLE_NAME as MY_TABLE FROM information_schema.TABLES where TABLE_SCHEMA = '" + info.getSchema()
				+ "' and TABLE_TYPE <> 'VIEW' and TABLE_TYPE <> 'SYSTEM VIEW'";
	}

	@Override
	public String getViewSchemaSQL(Info info) {
		return "SELECT TABLE_NAME as MY_VIEW FROM information_schema.TABLES where TABLE_SCHEMA = '" + info.getSchema()
				+ "' and (TABLE_TYPE = 'VIEW' or TABLE_TYPE = 'SYSTEM VIEW')";
	}

	@Override
	public String getColumnSQL(Info info) {
		return "SELECT TABLE_SCHEMA, TABLE_NAME, COLUMN_NAME, COLUMN_TYPE as TYPE_NAME, ORDINAL_POSITION, IS_NULLABLE, COLUMN_DEFAULT as COL_DEFAULT FROM information_schema.COLUMNS where TABLE_SCHEMA='"
				+ info.getSchema() + "' and TABLE_NAME='" + info.getName() + "' order by ORDINAL_POSITION";
	}

	@Override
	public String getIndexSQL(Info info) {
		return "SELECT TABLE_SCHEMA, TABLE_NAME, INDEX_NAME, NON_UNIQUE, INDEX_TYPE, SEQ_IN_INDEX as ORDINAL_POSITION, COLUMN_NAME, COLLATION as ASC_OR_DESC, CARDINALITY FROM information_schema.STATISTICS where TABLE_SCHEMA='"
				+ info.getSchema() + "' and TABLE_NAME='" + info.getName()
				+ "'  ORDER BY TABLE_SCHEMA, TABLE_NAME, INDEX_NAME, ORDINAL_POSITION";
	}

	@Override
	public String getConstraintSQL(Info info) {
		return "SELECT kcu.TABLE_SCHEMA, kcu.TABLE_NAME, kcu.CONSTRAINT_NAME, tc.CONSTRAINT_TYPE, kcu.COLUMN_NAME, kcu.ORDINAL_POSITION, kcu.REFERENCED_TABLE_SCHEMA as REF_SCHEMA, kcu.REFERENCED_TABLE_NAME as REF_TABLE_NAME, kcu.REFERENCED_COLUMN_NAME as REF_COLUMN_NAME, kcu.POSITION_IN_UNIQUE_CONSTRAINT as REF_POSITION from information_schema.KEY_COLUMN_USAGE as kcu, information_schema.TABLE_CONSTRAINTS as tc where kcu.TABLE_SCHEMA=tc.TABLE_SCHEMA and kcu.CONSTRAINT_NAME=tc.CONSTRAINT_NAME and kcu.TABLE_NAME=tc.TABLE_NAME and kcu.TABLE_SCHEMA='"
				+ info.getSchema() + "' and kcu.TABLE_NAME='" + info.getName()
				+ "' ORDER BY kcu.TABLE_SCHEMA, kcu.TABLE_NAME, kcu.CONSTRAINT_NAME, kcu.ORDINAL_POSITION";
	}

	@Override
	public String renameColumnName(ColumnInfo oldInfo, ColumnInfo newInfo) {
		StringBuffer buffer = new StringBuffer().append("ALTER TABLE ").append("    ").append(oldInfo.getSchema())
				.append(".").append(oldInfo.getName()).append(Constants.LINE_SEPERATOR).append(" CHANGE COLUMN ")
				.append("  ").append(oldInfo.getColumnName()).append(Constants.LINE_SEPERATOR).append("  ")
				.append(newInfo.getColumnName()).append(" ").append(newInfo.getTypeName());

		if (newInfo.getNumericLen() > 0) {
			buffer.append(" ( ").append(newInfo.getNumericLen());

			if (newInfo.getNumericScale() > 0) {
				buffer.append(", ").append(newInfo.getNumericScale());
			}

			buffer.append(" ) ");
		}

		return buffer.toString();
	}

	@Override
	public String changeColumn(ColumnInfo source, ColumnInfo dest) {
		return "";
	}
}
