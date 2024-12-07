package com.jqtools.powersql.obj;

import com.jqtools.powersql.constants.Constants;

public class ConstraintInfo extends Info {
	public static final String CONSTRAINT_PK = "Primary Key";
	public static final String CONSTRAINT_FK = "Foreign Key";
	public static final String CONSTRAINT_CK = "Check";
	public static final String CONSTRAINT_UQ = "Unique";

	private String constraintName = null;
	private String constraintType = null;
	private String columnName = null;
	private int ordinalPosition = 0;
	private String referenceSchema = null;
	private String referenceTable = null;
	private String referenceColName = null;
	private int referencePosition = 0;

	public String getConstraintName() {
		return constraintName;
	}

	public void setConstraintName(String constraintName) {
		this.constraintName = constraintName;
	}

	public String getConstraintType() {
		return constraintType;
	}

	public void setConstraintType(String constraintType) {
		this.constraintType = constraintType;
	}

	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public int getOrdinalPosition() {
		return ordinalPosition;
	}

	public void setOrdinalPosition(int ordinalPosition) {
		this.ordinalPosition = ordinalPosition;
	}

	public String getReferenceSchema() {
		return referenceSchema;
	}

	public void setReferenceSchema(String referenceSchema) {
		this.referenceSchema = referenceSchema;
	}

	public String getReferenceTable() {
		return referenceTable;
	}

	public void setReferenceTable(String referenceTable) {
		this.referenceTable = referenceTable;
	}

	public String getReferenceColName() {
		return referenceColName;
	}

	public void setReferenceColName(String referenceColName) {
		this.referenceColName = referenceColName;
	}

	public int getReferencePosition() {
		return referencePosition;
	}

	public void setReferencePosition(int referencePosition) {
		this.referencePosition = referencePosition;
	}

	public String toString() {
		if (constraintName == null) {
			return "";
		} else {
			if (getNodeType() == Constants.NODE_TABLE_CONSTRAINT_KEY) {
				if (CONSTRAINT_FK.equals(this.getConstraintType())) {
					return columnName + " => " + this.referenceColName + " (" + this.referenceTable + ")";
				} else {
					return columnName;
				}
			} else {
				return constraintName + "  (" + constraintType + ")";
			}
		}
	}
}
