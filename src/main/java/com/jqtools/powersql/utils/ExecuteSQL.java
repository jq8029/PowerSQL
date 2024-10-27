package com.jqtools.powersql.utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.jqtools.powersql.constants.Constants;
import com.jqtools.powersql.log.MessageLogger;
import com.jqtools.powersql.log.NoticeMessage;
import com.jqtools.powersql.obj.ColumnInfo;
import com.jqtools.powersql.obj.DataTable;
import com.jqtools.powersql.obj.DataToolBar;
import com.jqtools.powersql.obj.ResultTableModel;
import com.jqtools.powersql.obj.Session;

public class ExecuteSQL {
	public static boolean execute(Connection conn, String sql) {
		String sqls[] = new String[1];
		sqls[0] = sql;

		return execute(conn, sqls);
	}

	public static boolean execute(Connection conn, String sqls[]) {
		PreparedStatement stat = null;
		boolean autoCommit = false;
		boolean success = false;

		try {
			if (conn == null || conn.isClosed() || sqls == null || sqls.length == 0) {
				return false;
			}

			autoCommit = conn.getAutoCommit();
			conn.setAutoCommit(false);

			for (String sql : sqls) {
				if (sql == null || sql.trim().length() == 0)
					continue;

				MessageLogger.info("Execute sql = " + sql);
				stat = conn.prepareStatement(sql);
				stat.execute();
			}
			success = true;
		} catch (Exception e) {
			MessageLogger.error(e);
			return false;
		} finally {
			close(stat, null);

			if (conn != null) {
				try {
					if (success) {
						conn.commit();
					} else {
						conn.rollback();
					}
				} catch (Exception ex) {
					MessageLogger.error(ex);
					return false;
				}

				try {
					conn.setAutoCommit(autoCommit);
				} catch (SQLException ex) {
					MessageLogger.error(ex);
					return false;
				}
			}
		}

		return true;
	}

	public static boolean execute(DataToolBar dataToolBar) {
		Session session = dataToolBar.getSession();
		String sql = dataToolBar.getExecSQL();
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
						updateSession(dataToolBar, result.getMetaData(), tableModel);
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
			MessageLogger.error(e);
		} finally {
			dataToolBar.setDataTable(new DataTable(tableModel));
			tableModel.setTable(dataToolBar.getDataTable());
			tableModel.resizeColumnWidth();
			dataToolBar.getDataScroll().setViewportView(dataToolBar.getDataTable());
			close(stat, result);
			System.gc();
		}

		return true;
	}

	public static void updateSession(DataToolBar dataToolBar, ResultSetMetaData metaData, ResultTableModel tableModel)
			throws SQLException {
		int colNum = metaData.getColumnCount();
		int colType[] = new int[colNum];
		String colNames[] = new String[colNum];
		ArrayList<ColumnInfo> colInfoList = new ArrayList<ColumnInfo>();
		ColumnInfo colInfo = null;
		for (int i = 0; i < colNum; i++) {
			colInfo = new ColumnInfo();
			colInfoList.add(colInfo);
			colInfo.setColumnName(metaData.getColumnName(i + 1));
			colInfo.setDataType(metaData.getColumnType(i + 1));
			colInfo.setTypeName(metaData.getColumnTypeName(i + 1));
			colType[i] = colInfo.getDataType();
			colNames[i] = colInfo.getColumnName();
		}

		tableModel.setColNames(colNames);
		tableModel.setColTypes(colType);
		tableModel.setColInfo(colInfoList);
		dataToolBar.getFilterSortFrame().setColData(colNames, colType);
	}

	public static void close(Statement stat, ResultSet resultSet) {
		if (stat != null) {
			try {
				stat.close();
			} catch (SQLException e) {
				MessageLogger.error(e);
			}
		}

		if (resultSet != null) {
			try {
				resultSet.close();
			} catch (SQLException e) {
				MessageLogger.error(e);
			}
		}
	}
}
