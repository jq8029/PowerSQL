package com.jqtools.powersql.obj;

import java.util.Vector;

import javax.swing.table.AbstractTableModel;

public class ResultTableModel extends AbstractTableModel {

	private static final long serialVersionUID = -8670835121747587879L;

	private Vector<Object[]> data = null;
	private String[] colNames = null;
	private int[] colTypes = null;

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
			Object values[] = data.elementAt(rowIndex);
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
}
