package com.jqtools.powersql.db;

import com.jqtools.powersql.obj.Info;

public class H2Data extends DatabaseData {
	@Override
	public String getCatalogAllSQL() {
		return "SELECT distinct TABLE_CATALOG FROM information_schema.TABLES order by TABLE_CATALOG";
	}

	@Override
	public String getSchemaAllSQL(Info info) {
		return "SELECT SCHEMA_NAME FROM information_schema.SCHEMATA where CATALOG_NAME = '" + info.getCatalog()
				+ "' order by SCHEMA_NAME";
	}

	@Override
	public String getTableSchemaSQL(Info info) {
		return "SELECT TABLE_NAME FROM information_schema.TABLES where TABLE_SCHEMA = '" + info.getSchema()
				+ "' and TABLE_TYPE <> 'VIEW' order by TABLE_NAME";
	}

	@Override
	public String getViewSchemaSQL(Info info) {
		return "SELECT TABLE_NAME FROM information_schema.TABLES where TABLE_SCHEMA = '" + info.getSchema()
				+ "' and TABLE_TYPE = 'VIEW' order by TABLE_NAME";
	}

	@Override
	public String getColumnSQL(Info info) {
		return "SELECT TABLE_CATALOG, TABLE_SCHEMA, TABLE_NAME, COLUMN_NAME as PT_COLUMN_NAME, 'TYPE_NAME' as PT_TYPE_NAME, DATA_TYPE as PT_DATA_TYPE, ORDINAL_POSITION as PT_ORDINAL_POSITION, IS_NULLABLE as PT_IS_NULLABLE, CHARACTER_MAXIMUM_LENGTH as PT_MAX_LENGTH, NUMERIC_PRECISION as PT_NUM_LENGTH, NUMERIC_SCALE as PT_NUM_SCALE, COLUMN_DEFAULT as PT_COL_DEFAULT FROM information_schema.COLUMNS where TABLE_SCHEMA='"
				+ info.getSchema() + "' and TABLE_NAME='" + info.getName() + "' order by ORDINAL_POSITION";
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
