package com.jqtools.powersql.db;

public class H2Data extends DatabaseData {
	@Override
	public String getCatalogAllSQL() {
		return "SELECT distinct CATALOG_NAME as MY_CATALOG FROM information_schema.CATALOGS order by MY_CATALOG";
	}

	@Override
	public String getSchemaAllSQL() {
		return "SELECT CATALOG_NAME as MY_CATALOG, SCHEMA_NAME as MY_SCHEMA FROM information_schema.SCHEMATA order by MY_SCHEMA";
	}

	@Override
	public String getTableSchemaSQL() {
		return "SELECT * FROM information_schema.TABLES where TABLE_SCHEMA = ? and TABLE_TYPE = 'TABLE' order by TABLE_NAME";
	}

	@Override
	public String getViewSchemaSQL() {
		return "SELECT * FROM information_schema.VIEWS where TABLE_SCHEMA = ? order by TABLE_NAME";
	}
}
