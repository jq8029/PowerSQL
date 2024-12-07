package com.jqtools.powersql.obj;

import com.jqtools.powersql.constants.Constants;

public class IndexInfo extends Info {

	private String indexName = null;
	private String nonUniqueStr = null;
	private String type = null;
	private String columnName = null;
	private String ascOrDesc = null;
	private boolean nonUnique = false;

	public String getIndexName() {
		return indexName;
	}

	public void setIndexName(String indexName) {
		this.indexName = indexName;
	}

	public String getNonUniqueStr() {
		return nonUniqueStr;
	}

	public void setNonUniqueStr(String nonUniqueStr) {
		this.nonUniqueStr = nonUniqueStr;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public String getAscOrDesc() {
		return ascOrDesc;
	}

	public void setAscOrDesc(String ascOrDesc) {
		this.ascOrDesc = ascOrDesc;
	}

	public boolean isNonUnique() {
		return nonUnique;
	}

	public void setNonUnique(boolean nonUnique) {
		this.nonUnique = nonUnique;
	}

	public String toString() {
		if (getIndexName() == null) {
			return "";
		} else {
			if (getNodeType() == Constants.NODE_TABLE_INDEX_KEY) {
				if (getName() == null || columnName == null) {
					return "";
				} else {
					return columnName + "  ( " + (nonUnique ? " : non-unique" : " : unique") + " )";
				}
			} else {
				return getIndexName();
			}
		}
	}
}
