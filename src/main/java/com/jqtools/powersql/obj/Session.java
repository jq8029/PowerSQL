package com.jqtools.powersql.obj;

import java.sql.Connection;
import java.util.HashMap;

import com.jqtools.powersql.db.DatabaseData;

public class Session {
	private TreeNode DbNode = null;
	private TreeNode currentNode = null;
	private Connection connection = null;
	private DatabaseInfo dbInfo = null;
	private DatabaseData dbData = null;
	private HashMap<String, String[]> filterMap = new HashMap<String, String[]>();

	/**
	 * @return the connection
	 */
	public Connection getConnection() {
		return connection;
	}

	/**
	 * @param connection the connection to set
	 */
	public void setConnection(Connection connection) {
		this.connection = connection;
	}

	/**
	 * @return the dbInfo
	 */
	public DatabaseInfo getDbInfo() {
		return dbInfo;
	}

	/**
	 * @param dbInfo the dbInfo to set
	 */
	public void setDbInfo(DatabaseInfo dbInfo) {
		this.dbInfo = dbInfo;
	}

	public TreeNode getDbNode() {
		return DbNode;
	}

	public void setDbNode(TreeNode dbNode) {
		DbNode = dbNode;
	}

	public DatabaseData getDbData() {
		return dbData;
	}

	public void setDbData(DatabaseData dbData) {
		this.dbData = dbData;
	}

	public TreeNode getCurrentNode() {
		return currentNode;
	}

	public void setCurrentNode(TreeNode currentNode) {
		this.currentNode = currentNode;
	}

	public HashMap<String, String[]> getFilterMap() {
		return filterMap;
	}
}
