package com.jqtools.powersql.utils;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JScrollPane;
import javax.swing.JTable;

import com.jqtools.powersql.constants.Constants;
import com.jqtools.powersql.log.MessageLogger;
import com.jqtools.powersql.log.NoticeMessage;
import com.jqtools.powersql.obj.ResultTableModel;
import com.jqtools.powersql.obj.Session;

public class ExecuteSQL {
	public static boolean execute(Session session, String sql, JScrollPane scroll) {
		PreparedStatement stat = null;
		ResultSet result = null;
		ResultTableModel tableModel = new ResultTableModel();
		tableModel.setData(new ArrayList<Object[]>());

		try {
			// execute sql
			stat = session.getConnection().prepareStatement(sql);
			if (stat.execute()) {
				result = stat.getResultSet();

				// retrieve result
				int colNum = -1;
				Object objs[] = null;
				if (result != null) {
					// load the result column number
					if (colNum < 0) {
						colNum = result.getMetaData().getColumnCount();
						updateSession(session, result.getMetaData(), tableModel);
					}

					while (result.next()) {
						// retrieve each column value
						objs = new Object[colNum];
						for (int i = 0; i < colNum; i++) {
							objs[i] = result.getObject(i + 1);
						}
						tableModel.getData().add(objs);
					}
				}
			}
		} catch (Throwable e) {
			NoticeMessage.showMessage(Constants.MSG_FAIL_EXECUTE + sql);
			e.printStackTrace();
			MessageLogger.error(e);
		} finally {
			tableModel.setTable(new JTable(tableModel));
			tableModel.resizeColumnWidth();
			scroll.setViewportView(tableModel.getTable());
			System.gc();
		}

		return true;
	}

	public static void updateSession(Session session, ResultSetMetaData metaData, ResultTableModel tableModel)
			throws SQLException {
		int colNum = metaData.getColumnCount();
		int colType[] = new int[colNum];
		String colNames[] = new String[colNum];
		for (int i = 0; i < colNum; i++) {
			colType[i] = metaData.getColumnType(i + 1);
			colNames[i] = metaData.getColumnName(i + 1);
		}

		tableModel.setColNames(colNames);
		tableModel.setColTypes(colType);
	}
}
