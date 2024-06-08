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
			stat = session.getConnection().prepareStatement(sql);
			if (stat.execute()) {
				result = stat.getResultSet();
				int colNum = -1;
				ArrayList<Object[]> data = new ArrayList<Object[]>();
				Object objs[] = null;
				if (result != null) {
					while (result.next()) {
						if (colNum < 0) {
							colNum = result.getMetaData().getColumnCount();
						}

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
