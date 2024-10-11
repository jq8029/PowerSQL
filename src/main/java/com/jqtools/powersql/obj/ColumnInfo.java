package com.jqtools.powersql.obj;

import com.jqtools.powersql.utils.Tools;

public class ColumnInfo extends Info {

	private String tableType = null;
	private String columnName = null;
	private String typeName = null;
	private int dataType = 0;
	private int maxLength = 0;
	private int numericLen = 0;
	private int numericScale = 0;
	private boolean nullable = false;
	private int ordinalPosition = 0;
	private String defaultValue = null;
	private boolean primaryKey = false;

	public String getTableType() {
		return tableType;
	}

	public void setTableType(String tableType) {
		this.tableType = tableType;
	}

	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public int getDataType() {
		return dataType;
	}

	public void setDataType(int dataType) {
		this.dataType = dataType;
	}

	public int getMaxLength() {
		return maxLength;
	}

	public void setMaxLength(int maxLength) {
		this.maxLength = maxLength;
	}

	public int getNumericLen() {
		return numericLen;
	}

	public void setNumericLen(int numericLen) {
		this.numericLen = numericLen;
	}

	public int getNumericScale() {
		return numericScale;
	}

	public void setNumericScale(int numericScale) {
		this.numericScale = numericScale;
	}

	public boolean isNullable() {
		return nullable;
	}

	public void setNullable(boolean nullable) {
		this.nullable = nullable;
	}

	public int getOrdinalPosition() {
		return ordinalPosition;
	}

	public void setOrdinalPosition(int ordinalPosition) {
		this.ordinalPosition = ordinalPosition;
	}

	public String getDefaultValue() {
		return defaultValue;
	}

	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}

	public boolean isPrimaryKey() {
		return primaryKey;
	}

	public void setPrimaryKey(boolean primaryKey) {
		this.primaryKey = primaryKey;
	}

	public String toString() {
		if (columnName == null) {
			return "";
		} else {
			StringBuffer buffer = new StringBuffer().append(columnName).append("  ( ").append(typeName);
			if (this.numericLen > 0) {
				buffer.append("(").append(numericLen);
				if (this.numericScale > 0) {
					buffer.append(", ").append(this.numericScale);
				}
				buffer.append(")");
			} else if (this.maxLength > 0) {
				buffer.append("(").append(this.maxLength).append(")");
			}
			return buffer.append(this.nullable ? "  )" : "  not-null  )").toString();
		}
	}

	@Override
	public boolean equal(Info info) {
		if (info == null || !(info instanceof ColumnInfo))
			return false;

		return super.equal(info) && Tools.isEqual(((ColumnInfo) info).getColumnName(), this.getColumnName());
	}
}