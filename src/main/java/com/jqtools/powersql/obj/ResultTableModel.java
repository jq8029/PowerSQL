package com.jqtools.powersql.obj;

import java.sql.Types;
import java.util.ArrayList;

import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import com.jqtools.powersql.constants.Constants;
import com.jqtools.powersql.utils.Tools;

public class ResultTableModel extends AbstractTableModel {

	private static final long serialVersionUID = -8670835121747587879L;

	private ArrayList<Object[]> data = null;
	private String[] colNames = null;
	private int[] colTypes = null;
	private DataTable table = null;
	private ArrayList<ColumnInfo> colInfoList = null;
	private boolean tableEditable = false;

	public ResultTableModel() {
		this.data = new ArrayList<Object[]>();
		Object[] obj = { "" };
		this.data.add(obj);
		this.colNames = new String[1];
		this.colNames[0] = "Column";
		this.colTypes = new int[1];
		this.colTypes[0] = Types.CHAR;
		this.resizeColumnWidth();
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

	@Override
	public void setValueAt(Object newValue, int row, int column) {
		Object values[] = data.get(row);

		if (values != null) {
			if (!Tools.isEqual(values[column], newValue)) {

				Object originValues[] = new Object[values.length];
				System.arraycopy(values, 0, originValues, 0, values.length);

				if (this.table.getRowStatus(values) == Constants.REC_STATUS_NONE) {
					this.table.changeRow(values, originValues);
				}

				values[column] = newValue == null ? null : newValue;
			}
		}

		fireTableCellUpdated(row, column);

		if (table != null) {
			table.repaint();
		}
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return this.tableEditable;
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

	public void resizeColumnWidth() {
		if (this.table == null || colNames == null)
			return;

		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

		TableColumnModel columnModel = table.getColumnModel();

		TableColumn tbColumn = null;

		int charNum = 0;
		int width = 0;
		for (int i = 0; i < colNames.length; i++) {
			tbColumn = columnModel.getColumn(i);

			width = tbColumn.getWidth();
			charNum = 0;
			if (i < colNames.length) {
				if (colNames[i] != null) {
					charNum = colNames[i].length();
					if (data != null && data.size() > 0) {
						for (int k = 0; k < data.size(); k++) {
							if (getValueAt(k, i) != null && getValueAt(k, i).toString().length() > charNum) {
								charNum = getValueAt(k, i).toString().length();
							}
						}
					}

					if (charNum * 10 > width) {
						width = charNum * 10;
					}
				}

			}

			tbColumn.setPreferredWidth(width);

		}

		table.repaint();
	}

	public ArrayList<Object[]> getData() {
		return data;
	}

	public void setData(ArrayList<Object[]> data) {
		this.data = data;
	}

	public void setColNames(String[] colNames) {
		this.colNames = colNames;
	}

	public void setColTypes(int[] colTypes) {
		this.colTypes = colTypes;
	}

	public JTable getTable() {
		return table;
	}

	public void setTable(DataTable table) {
		this.table = table;
	}

	public ArrayList<ColumnInfo> getColInfoList() {
		return colInfoList;
	}

	public void setColInfo(ArrayList<ColumnInfo> colInfoList) {
		this.colInfoList = colInfoList;
	}

	public boolean isTableEditable() {
		return tableEditable;
	}

	public void setTableEditable(boolean tableEditable) {
		this.tableEditable = tableEditable;
	}

}
