package com.jqtools.powersql.utils;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import com.jqtools.powersql.log.MessageLogger;
import com.jqtools.powersql.obj.Session;

public class ExecuteSQL {
	public static boolean execute(Session session, String sql) {
		PreparedStatement stat = null;
		ResultSet result = null;

		try {
			// execute sql
			stat = session.getConnection().prepareStatement(sql);
			if (stat.execute()) {
				result = stat.getResultSet();

				// retrieve result
				int colNum = -1;
				ArrayList<Object[]> data = new ArrayList<Object[]>();
				Object objs[] = null;
				if (result != null) {
					while (result.next()) {
						// load the result column number
						if (colNum < 0) {
							colNum = result.getMetaData().getColumnCount();
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
		} catch (Exception e) {
			MessageLogger.error(e);
			;
		}

		return true;
	}
}
