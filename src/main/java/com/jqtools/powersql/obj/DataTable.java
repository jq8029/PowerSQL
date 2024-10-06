package com.jqtools.powersql.obj;

import java.util.ArrayList;

import javax.swing.JTable;
import javax.swing.RowFilter;
import javax.swing.table.TableRowSorter;

public class DataTable extends JTable {

	private static final long serialVersionUID = -7489359896418415220L;

	private ResultTableModel resultTableModel = null;
	private TableRowSorter<ResultTableModel> sorter = null;

	public DataTable(ResultTableModel resultTableModel) {
		super(resultTableModel);

		this.resultTableModel = resultTableModel;
		this.sorter = new TableRowSorter<ResultTableModel>(resultTableModel);
	}

	public int addRow(int row) {
		return row;
	}

	public ArrayList<Integer> duplicateRows(int[] rows) {
		return new ArrayList<Integer>();
	}

	public ArrayList<Integer> deleteRows(int[] rows) {
		return new ArrayList<Integer>();
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
					if (entry != null && entry.getValueCount() > 0) {
						for (int i = entry.getValueCount() - 1; i >= 0; i--) {
							String filteredField = entry.getStringValue(i).toLowerCase();
							if (filteredField.contains(text)) {
								return true;
							}
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
