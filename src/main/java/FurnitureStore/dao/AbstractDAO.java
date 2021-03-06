/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FurnitureStore.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import FurnitureStore.dbconnection.DBConnectionService;
import java.io.*;
import java.net.URI;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.*;

/**
 *
 * @author Max
 */
public abstract class AbstractDAO {
	private static Boolean databaseChecked = false;
	protected Connection connection;

	public AbstractDAO() throws Exception {
		if (!databaseChecked) {
			if (!CheckDatabaseExists()) {
				try {
					URL resourceCMD = getClass().getClassLoader().getResource("");
					Path pathCMD = Paths.get(URI.create(resourceCMD.toString()));
					File dir = new File(pathCMD.toString());
					Process p = Runtime.getRuntime().exec("sqlcmd -S 127.0.0.1 -E -i Database.sql", null, dir);
					p.waitFor();
				} catch (final Exception err) {
					err.printStackTrace();
				}
			}
		}
		try {
			connection = DBConnectionService.getConnection();
		} catch (final Exception e) {
			System.err.println("Failed in constructor method in AbstractDAO:" + e);
			e.printStackTrace();
			throw e;
		}
	}

	public AbstractDAO(final Connection conn) {
		connection = conn;
	}

	public void setConnection(final Connection conn) {
		connection = conn;
	}

	public Connection getConnection() {
		return connection;
	}

	public void closeConnection() throws Exception {
		try {
			getConnection().close();
		} catch (final Exception e) {
			System.err.println("Failed in closeConnection method in MapperDB:" + e);
			e.printStackTrace();
			throw e;
		}
	}

	private Boolean CheckDatabaseExists() {
		databaseChecked = true;
		try {
			final Connection con = DBConnectionService.getConnection();
			ResultSet rs = null;
			if (con != null) {
				rs = con.getMetaData().getCatalogs();
				while (rs.next()) {
					final String catalogs = rs.getString(1);

				
						return true;
					
				}
			}
		} catch (final Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public ResultSet ExecuteQuery(final String query, final Object[] params) {
		ResultSet rs;
		try {
			final Connection conn = getConnection();
			final PreparedStatement ps = conn.prepareStatement(query);
			if (params != null) {
				final String[] listParas = query.split(" ");
				int i = 0;
				for (final String item : listParas) {
					if (item.contains("?")) {
						ps.setObject(i + 1, params[i]);
						i++;
					}
				}
			}
			rs = ps.executeQuery();
			// ps.close();
		} catch (final Exception e) {
			e.printStackTrace();
			rs = null;
		}
		return rs;
	}

	// return number of affected row
	public int ExecuteNonQuery(final String query, final Object[] params) {
		int rowAffected = 0;
		try {
			final Connection conn = getConnection();
			final PreparedStatement ps = conn.prepareStatement(query);
			if (params != null) {
				final String[] listParas = query.split(" ");
				int i = 0;
				for (final String item : listParas) {
					if (item.contains("?")) {
					
						if (params[i] != null) {
							ps.setObject(i + 1, params[i]);
						} else
							ps.setNull(i + 1, Types.FLOAT);
						i++;
					}
				}
			}
			rowAffected = ps.executeUpdate();
			// ps.close();
		} catch (final Exception e) {
			e.printStackTrace();
		}
		return rowAffected;
	}

	// returns the first column of the first row in the result set
	public Object ExecuteScalar(final String query, final Object[] params) {
		ResultSet rs;
		Object result = null;
		try {
			final Connection conn = getConnection();
			final PreparedStatement ps = conn.prepareStatement(query);
			if (params != null) {
				final String[] listParas = query.split(" ");
				int i = 0;
				for (final String item : listParas) {
					if (item.contains("?")) {
						ps.setObject(i + 1, params[i]);
						i++;
					}
				}
			}
			rs = ps.executeQuery();
			while (rs.next()) {
				result = rs.getObject(1);
			}
			// ps.close();
			return result;
		} catch (final Exception e) {
			e.printStackTrace();
			return -1;
		}
	}
}
