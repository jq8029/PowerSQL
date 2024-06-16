package com.jqtools.powersql.db;

public class DatabaseData {
	private String catalogAllSQL = null;
	private String schemaAllSQL = null;
	private String tableSchemaSQL = null;
	private String viewSchemaSQL = null;

	public String getCatalogAllSQL() {
		return catalogAllSQL;
	}

	public void setCatalogAllSQL(String catalogAllSQL) {
		this.catalogAllSQL = catalogAllSQL;
	}

	public String getSchemaAllSQL() {
		return schemaAllSQL;
	}

	public void setSchemaAllSQL(String schemaAllSQL) {
		this.schemaAllSQL = schemaAllSQL;
	}

	public String getTableSchemaSQL() {
		return tableSchemaSQL;
	}

	public void setTableSchemaSQL(String tableSchemaSQL) {
		this.tableSchemaSQL = tableSchemaSQL;
	}

	public String getViewSchemaSQL() {
		return viewSchemaSQL;
	}

	public void setViewSchemaSQL(String viewSchemaSQL) {
		this.viewSchemaSQL = viewSchemaSQL;
	}
}
