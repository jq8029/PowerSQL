package com.jqtools.powersql.obj;

import com.jqtools.powersql.constants.Constants;

public class Info {
	private String tableCatalog = null;
	private String tableSchema = null;
	private String name = null;
	private int nodeType = Constants.NODE_TEXT;

	public String getTableCatalog() {
		return tableCatalog;
	}

	public void setTableCatalog(String tableCatalog) {
		this.tableCatalog = tableCatalog;
	}

	public String getTableSchema() {
		return tableSchema;
	}

	public void setTableSchema(String tableSchema) {
		this.tableSchema = tableSchema;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getNodeType() {
		return nodeType;
	}

	public void setNodeType(int nodeType) {
		this.nodeType = nodeType;
	}

}
