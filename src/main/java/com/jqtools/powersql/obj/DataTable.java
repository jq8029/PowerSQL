package com.jqtools.powersql.obj;

import java.util.ArrayList;

import javax.swing.JTable;

public class DataTable extends JTable {

	private static final long serialVersionUID = -7489359896418415220L;

	public int addRow(int row) {
		return row;
	}

	public ArrayList<Integer> duplicateRows(int[] rows) {
		return new ArrayList<Integer>();
	}

	public ArrayList<Integer> deleteRows(int[] rows) {
		return new ArrayList<Integer>();
	}
}
