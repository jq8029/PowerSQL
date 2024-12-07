package com.jqtools.powersql.obj;

import com.jqtools.powersql.constants.Constants;
import com.jqtools.powersql.utils.Tools;

public class Info {
	private String catalog = null;
	private String schema = null;
	private String name = null;
	protected int nodeType = Constants.NODE_TEXT;

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
		if (nodeType == Constants.NODE_TABLE_COLUMNS) {
			return Constants.NAME_COLUMNS;
		} else if (nodeType == Constants.NODE_TABLE_INDEXES) {
			return Constants.NAME_INDEXES;
		} else if (nodeType == Constants.NODE_TABLE_CONSTRAINTS) {
			return Constants.NAME_CONSTRAINTS;
		} else {
			return this.name;
		}
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
