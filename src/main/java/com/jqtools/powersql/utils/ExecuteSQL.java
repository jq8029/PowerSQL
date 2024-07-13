package com.jqtools.powersql.utils;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;

import com.jqtools.powersql.log.MessageLogger;
import com.jqtools.powersql.obj.Info;
import com.jqtools.powersql.obj.Session;

public class ExecuteSQL {
	public static boolean loadTableData(Session session, Info info) {
		StringBuilder sql = new StringBuilder().append("select * from ");
		if (info.getSchema() != null) {
			sql.append(info.getSchema()).append(".");
		}
		sql.append(info.getName());

		return execute(session, sql.toString());
	}

	public static boolean execute(Session session, String sql) {
		PreparedStatement stat = null;
		ResultSet result = null;
		ArrayList<Object[]> data = new ArrayList<Object[]>();

		try {
			// execute sql
			stat = session.getConnection().prepareStatement(sql);
			if (stat.execute()) {
				result = stat.getResultSet();

				// retrieve result
				int colNum = -1;
				Object objs[] = null;
				if (result != null) {
					while (result.next()) {
						// load the result column number
						if (colNum < 0) {
							colNum = result.getMetaData().getColumnCount();
							updateSession(session, result.getMetaData(), data);
						}

						// retrieve each column value
						objs = new Object[colNum];
						for (int i = 0; i < colNum; i++) {
							objs[i] = result.getObject(i);
						}
						data.add(objs);
					}
				}
			}
		} catch (Throwable e) {
			MessageLogger.error(e);
		} finally {
			session.getTableModel().setData(data);
			session.getTableModel().resizeColumnWidth();
			System.gc();
		}

		return true;
	}

	public static void updateSession(Session session, ResultSetMetaData metaData, ArrayList<Object[]> data)
			throws SQLException {
		int colNum = metaData.getColumnCount();
		int colType[] = new int[colNum];
		String colNames[] = new String[colNum];
		for (int i = 0; i < colNum; i++) {
			colType[i] = metaData.getColumnType(i);
			colNames[i] = metaData.getColumnName(i);
		}

		session.getTableModel().setColNames(colNames);
		session.getTableModel().setColTypes(colType);
		session.getTableModel().setData(data);
	}
}
