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
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getColumnCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		// TODO Auto-generated method stub
		return null;
	}

}
