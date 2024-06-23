package com.jqtools.powersql.utils;

import java.sql.Connection;

import com.jqtools.powersql.log.MessageLogger;
import com.jqtools.powersql.obj.Info;
import com.jqtools.powersql.obj.Session;
import com.jqtools.powersql.obj.TreeNode;

public class DBLoader {
	public static boolean loadDBNode(Session session) {
		if (session == null || session.getDbInfo() == null || session.getDbNode() == null
				|| session.getDbData() == null) {
			return false;
		}

		Connection conn = null;

		try {

			conn = DBTools.getConnection(session.getDbInfo());
			Info info = session.getDbNode().getInfo();

		} catch (Throwable e) {
			MessageLogger.error(e);

			return false;
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (Exception e) {
				}
			}
		}

		return true;
	}

	private static boolean loadCatalogNode(Session session, Connection conn, TreeNode root) {
		if (session.getDbData().getCatalogAllSQL() == null) {

		}

		return true;
	}

}
