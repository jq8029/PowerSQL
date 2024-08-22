package com.jqtools.powersql.obj;

public class DatabaseInfo {
	private String name = null;
	private String user = null;
	private String password = null;
	private String driverName = null;
	private String url = null;
	private String dbName = null;

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the user
	 */
	public String getUser() {
		return user;
	}

	/**
	 * @param user the user to set
	 */
	public void setUser(String user) {
		this.user = user;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the driverName
	 */
	public String getDriverName() {
		return driverName;
	}

	/**
	 * @param driverName the driverName to set
	 */
	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}

	/**
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * @param url the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	public String getDbName() {
		return dbName;
	}

	public void setDbName(String dbName) {
		this.dbName = dbName;
	}

	public String toString() {
		StringBuffer buffer = new StringBuffer().append("DatabaseInfo : ").append("name = ").append(name).append(", ")
				.append("user = ").append(user).append(", ").append("password = ").append(password).append(", ")
				.append("driverName = ").append(driverName).append(", ").append("url = ").append(url).append(", ")
				.append("dbName = ").append(dbName).append(")");

		return buffer.toString();
	}

	public DatabaseInfo clone() {
		DatabaseInfo dbInfo = new DatabaseInfo();
		dbInfo.setName(this.getName());
		dbInfo.setUser(this.getUser());
		dbInfo.setPassword(this.getPassword());
		dbInfo.setUrl(this.getUrl());
		dbInfo.setDriverName(this.getDriverName());
		dbInfo.setDbName(this.getDbName());

		return dbInfo;
	}
}
