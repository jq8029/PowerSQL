package com.jqtools.powersql.db;

public class H2Data extends DatabaseData {
	@Override
	public String getCatalogAllSQL() {
		return "SELECT distinct TABLE_CATALOG FROM information_schema.TABLES order by TABLE_CATALOG";
	}

	@Override
	public String getSchemaAllSQL() {
		return "SELECT SCHEMA_NAME FROM information_schema.SCHEMATA where CATALOG_NAME = '?' order by SCHEMA_NAME";
	}

	@Override
	public String getTableSchemaSQL() {
		return "SELECT TABLE_NAME FROM information_schema.TABLES where TABLE_SCHEMA = '?' and TABLE_TYPE <> 'VIEW' order by TABLE_NAME";
	}

	@Override
	public String getViewSchemaSQL() {
		return "SELECT TABLE_NAME FROM information_schema.TABLES where TABLE_SCHEMA = '?' and TABLE_TYPE = 'VIEW' order by TABLE_NAME";
	}

	public String getCatalogName() {
		return "TABLE_CATALOG";
	}

	public String getSchemaName() {
		return "SCHEMA_NAME";
	}

	public String getTableName() {
		return "TABLE_NAME";
	}

	public String getViewName() {
		return "TABLE_NAME";
	}

}
