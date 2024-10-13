package com.jqtools.powersql.obj;

import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JTable;
import javax.swing.RowFilter;
import javax.swing.table.TableRowSorter;

import com.jqtools.powersql.constants.Constants;

public class DataTable extends JTable {

	private static final long serialVersionUID = -7489359896418415220L;

	private ResultTableModel resultTableModel = null;
	private TableRowSorter<ResultTableModel> sorter = null;
	private HashMap<Object, Object> changedData = new HashMap<Object, Object>();
	private HashMap<Object, Integer> rowStatus = new HashMap<Object, Integer>();

	public DataTable(ResultTableModel resultTableModel) {
		super(resultTableModel);

		this.resultTableModel = resultTableModel;
		this.sorter = new TableRowSorter<ResultTableModel>(resultTableModel);
		this.setRowSorter(sorter);
	}

	public int addRow(int row) {
		return addRow(row, new Object[getColumnCount()]);
	}

	public int addRow(int row, Object values[]) {
		if ((this.getRowCount() > 0 && row < 1) || row > resultTableModel.getData().size()) {
			row = resultTableModel.getData().size();
		}

		resultTableModel.getData().add(row, values);
		resultTableModel.fireTableRowsInserted(row + 1, row + 1);
		rowStatus.put(values, Constants.REC_STATUS_ADD);
		changedData.put(values, values);

		return row;
	}

	public ArrayList<Integer> duplicateRows(int rows[]) {
		ArrayList<Integer> results = new ArrayList<Integer>();
		for (int i = rows.length - 1; i >= 0; i--) {
			if (dupRow(rows[i])) {
				results.add(rows[i]);
			}
		}

		return results;
	}

	public boolean dupRow(int row) {
		Object values[] = resultTableModel.getData().get(row);
		if (values != null) {
			Object newValues[] = new Object[values.length];
			System.arraycopy(values, 0, newValues, 0, values.length);

			return addRow(row, newValues) > 0;
		}

		return false;
	}

	public ArrayList<Integer> deleteRows(int[] rows) {
		ArrayList<Integer> results = new ArrayList<Integer>();
		for (int row : rows) {
			if (deleteRow(row)) {
				results.add(row);
			}
		}

		return results;
	}

	public boolean deleteRow(int row) {
		if (row < 0 || row >= resultTableModel.getData().size()) {
			return false;
		}

		resultTableModel.getData().remove(row);
		resultTableModel.fireTableRowsDeleted(row - 1, row - 1);
		this.repaint();

		return true;
	}

	public ResultTableModel getResultTableModel() {
		return resultTableModel;
	}

	public void filterData(final String text) {
		RowFilter<ResultTableModel, Object> rf = null;

		try {
			rf = new RowFilter<ResultTableModel, Object>() {
				@Override
				public boolean include(Entry<? extends ResultTableModel, ? extends Object> entry) {
					for (int i = entry.getValueCount() - 1; i >= 0; i--) {
						String filteredField = entry.getStringValue(i).toLowerCase();
						if (filteredField.contains(text)) {
							return true;
						}
					}

					return false;
				}
			};
		} catch (Exception e) {
			return;
		}

		this.sorter.setRowFilter(rf);

		this.repaint();
	}
}
