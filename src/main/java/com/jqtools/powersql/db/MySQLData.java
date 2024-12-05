package com.jqtools.powersql.db;

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
		return null;
	}

	@Override
	public String getConstraintSQL(Info info) {
		return null;
	}
}
