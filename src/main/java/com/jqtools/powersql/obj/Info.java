package com.jqtools.powersql.obj;

import com.jqtools.powersql.constants.Constants;

public class Info {
	private String catalog = null;
	private String schema = null;
	private String name = null;
	private int nodeType = Constants.NODE_TEXT;

	public String getCatalog() {
		return catalog;
	}

	public void setCatalog(String catalog) {
		this.catalog = catalog;
	}

	public String getSchema() {
		return schema;
	}

	public void setSchema(String schema) {
		this.schema = schema;
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

	public String toString() {
		return this.name;
	}
}
