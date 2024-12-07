package com.jqtools.powersql.obj;

public class ConstraintInfo extends Info {
	public static final String CONSTRAINT_PK = "Primary Key";
	public static final String CONSTRAINT_FK = "Foreign Key";
	public static final String CONSTRAINT_CK = "Check";
	public static final String CONSTRAINT_UQ = "Unique";

	private String constraintName = null;
	private String constraintType = null;
	private String columnName = null;

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

	public String toString() {
		if (constraintName == null) {
			return "";
		} else {
			return constraintName + "  (" + constraintType + ", " + this.getName() + ")";
		}
	}
}
