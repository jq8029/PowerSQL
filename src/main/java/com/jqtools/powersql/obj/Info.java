package com.jqtools.powersql.obj;

import com.jqtools.powersql.constants.Constants;
import com.jqtools.powersql.utils.Tools;

public class Info {
	private String catalog = null;
	private String schema = null;
	private String name = null;
	private int nodeType = Constants.NODE_TEXT;
	private String colName = null;

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

	public String getColName() {
		return colName;
	}

	public void setColName(String colName) {
		this.colName = colName;
	}

	public String toString() {
		return this.name;
	}

	public boolean equal(Info info) {
		if (info == null)
			return false;

		return Tools.isEqual(info.getCatalog(), this.getCatalog()) && Tools.isEqual(info.getSchema(), this.getSchema())
				&& Tools.isEqual(info.getName(), this.getName());
	}

	public Info clone() {
		Info info = new Info();
		info.setCatalog(catalog);
		info.setName(name);
		info.setNodeType(nodeType);
		info.setSchema(schema);

		return info;
	}
}
