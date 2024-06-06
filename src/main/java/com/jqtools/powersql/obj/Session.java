package com.jqtools.powersql.obj;

import java.sql.Connection;

public class Session {
	private Connection connection = null;
	private DatabaseInfo dbInfo = null;

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

}
