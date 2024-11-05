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
		return "SELECT CL.TABLE_CATALOG, CL.TABLE_SCHEMA, CL.TABLE_NAME, CL.COLUMN_NAME, CL.ORDINAL_POSITION, CL.IS_NULLABLE, CL.TYPE_NAME, CL.CHARACTER_MAXIMUM_LENGTH as MAX_LENGTH, CL.NUMERIC_PRECISION as NUM_LENGTH, CL.NUMERIC_SCALE as NUM_SCALE, CL.COLUMN_DEFAULT FROM information_schema.COLUMNS as CL, information_schema.TABLES as TB where CL.TABLE_CATALOG=TB.TABLE_CATALOG and CL.TABLE_SCHEMA=TB.TABLE_SCHEMA and CL.TABLE_NAME=TB.TABLE_NAME and CL.TABLE_SCHEMA='"
				+ info.getSchema() + "' and CL.TABLE_NAME='" + info.getSchema() + "' order by CL.ORDINAL_POSITION";
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
