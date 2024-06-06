package com.jqtools.powersql.obj;

import java.sql.Types;
import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

public class ResultTableModel extends AbstractTableModel {

	private static final long serialVersionUID = -8670835121747587879L;

	private ArrayList<Object[]> data = null;
	private String[] colNames = null;
	private int[] colTypes = null;

	public ResultTableModel(ArrayList<Object[]> data, String[] colNames, int[] colTypes) {
		super();

		this.data = data;
		this.colNames = colNames;
		this.colTypes = colTypes;
	}

	public ResultTableModel() {
	}

	@Override
	public int getRowCount() {
		return data == null ? 0 : data.size();
	}

	@Override
	public int getColumnCount() {
		return colNames == null ? 0 : colNames.length;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		if (data == null || rowIndex < 0 || rowIndex >= data.size()) {
			return "";
		} else {
			Object values[] = data.get(rowIndex);
			if (values == null || columnIndex < 0 || columnIndex >= values.length) {
				return "";
			} else {
				if (values[columnIndex] == null) {
					return "";
				} else {
					return values[columnIndex].toString();
				}
			}
		}
	}

	public String getColumnName(int columnIndex) {
		if (colNames == null || columnIndex >= colNames.length || columnIndex < 0) {
			return "";
		} else {
			return colNames[columnIndex];
		}
	}

	public int getColumnType(int column) {
		if (colTypes == null || colTypes.length <= column || column < 0)
			return Types.CHAR;

		return colTypes[column];
	}
}
