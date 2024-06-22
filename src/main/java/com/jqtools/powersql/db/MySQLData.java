package com.jqtools.powersql.db;

public class MySQLData extends DatabaseData {

	@Override
	public String getSchemaAllSQL() {
		return "SELECT CATALOG_NAME as MY_CATALOG, SCHEMA_NAME as MY_SCHEMA FROM information_schema.SCHEMATA order by SCHEMA_NAME";
	}

	@Override
	public String getTableSchemaSQL() {
		return "SELECT TABLE_NAME as MY_TABLE FROM information_schema.TABLES where TABLE_SCHEMA = ? and TABLE_TYPE <> 'VIEW' and TABLE_TYPE <> 'SYSTEM VIEW'";
	}

	@Override
	public String getViewSchemaSQL() {
		return "SELECT TABLE_NAME as MY_VIEW FROM information_schema.TABLES where TABLE_SCHEMA = ? and (TABLE_TYPE = 'VIEW' or TABLE_TYPE = 'SYSTEM VIEW')";
	}
}
