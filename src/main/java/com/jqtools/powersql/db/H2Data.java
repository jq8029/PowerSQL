package com.jqtools.powersql.db;

public class H2Data extends DatabaseData {
	@Override
	public String getCatalogAllSQL() {
		return "SELECT distinct CATALOG_NAME as MY_CATALOG FROM information_schema.CATALOGS order by CATALOG_NAME";
	}

	@Override
	public String getSchemaAllSQL() {
		return "SELECT SCHEMA_NAME FROM information_schema.SCHEMATA where CATALOG_NAME = ? order by SCHEMA_NAME";
	}

	@Override
	public String getTableSchemaSQL() {
		return "SELECT TABLE_NAME as MY_TABLE FROM information_schema.TABLES where TABLE_SCHEMA = ? and TABLE_TYPE = 'TABLE' order by TABLE_NAME";
	}

	@Override
	public String getViewSchemaSQL() {
		return "SELECT TABLE_NAME as MY_VIEW FROM information_schema.VIEWS where TABLE_SCHEMA = ? order by TABLE_NAME";
	}

	public String getCatalogName() {
		return "CATALOG_NAME";
	}

}
