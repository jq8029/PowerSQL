package com.jqtools.powersql.db;

import com.jqtools.powersql.obj.Info;

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

	@Override
	public String getCatalogName() {
		return "TABLE_CATALOG";
	}

	@Override
	public String getSchemaName() {
		return "SCHEMA_NAME";
	}

	@Override
	public String getTableName() {
		return "TABLE_NAME";
	}

	@Override
	public String getViewName() {
		return "TABLE_NAME";
	}

	@Override
	public String getTableName(Info info) {
		StringBuilder tableName = new StringBuilder();

		if (info.getSchema() != null) {
			tableName.append("\"").append(info.getSchema()).append("\".\"");
		}
		tableName.append(info.getName()).append("\"");

		return tableName.toString();
	}
}
