package com.jqtools.powersql.db;

public class H2Data extends DatabaseData {
	@Override
	public String getCatalogAllSQL() {
		return "SELECT distinct CATALOG_NAME as MY_CATALOG FROM information_schema.CATALOGS order by MY_CATALOG";
	}

	@Override
	public String getSchemaAllSQL() {
		return null;
	}

	@Override
	public String getTableSchemaSQL() {
		return null;
	}

	@Override
	public String getViewSchemaSQL() {
		return null;
	}
}
