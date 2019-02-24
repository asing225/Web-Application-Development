package edu.asu.ser422.phone.services.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import edu.asu.ser422.phone.model.PhoneEntry;
import edu.asu.ser422.phone.services.PhoneBookService;

/**
 * @author amanjotsingh
 * Class to implement the add, delete and list functionalities for phone entry
 * using mysql as data store.
 * */

public class RDBMPhoneBookServiceImpl implements PhoneBookService{
	
	private static String __jdbcUrl;
	private static String __jdbcUser;
	private static String __jdbcPasswd;
	private static String __jdbcDriver;
	private static String __insertQuery;
	private static String __deleteQuery;
	private static String __selectQuery;
	
	static {
		try {
			Properties dbProperties = new Properties();
			dbProperties.load(RDBMPhoneBookServiceImpl.class.getClassLoader().getResourceAsStream("/resources/rdbm.properties"));
			__jdbcUrl = dbProperties.getProperty("jdbcUrl");
			__jdbcUser = dbProperties.getProperty("jdbcUser");
			__jdbcPasswd = dbProperties.getProperty("jdbcPasswd");
			__jdbcDriver = dbProperties.getProperty("jdbcDriver");
			__insertQuery = dbProperties.getProperty("insertQuery");
			__deleteQuery = dbProperties.getProperty("deleteQuery");
			__selectQuery = dbProperties.getProperty("selectQuery");
		} catch (Throwable t) {
			t.printStackTrace();
		} finally {
		}
	}
	
	public List<PhoneEntry> listEntries() {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		List<PhoneEntry> bookList = new ArrayList<PhoneEntry>();
		try {
			try {
				Class.forName(__jdbcDriver);
			}
			catch(Throwable t) {
				t.printStackTrace();
				return null;
			}
			conn = DriverManager.getConnection(__jdbcUrl, __jdbcUser, __jdbcPasswd);
			
			stmt = conn.createStatement();
			rs = stmt.executeQuery(__selectQuery);
			while(rs.next()) {
				bookList.add(new PhoneEntry(rs.getString(1), rs.getString(2), rs.getString(3)));
			}
		}
		catch(SQLException se) {
			se.printStackTrace();
			return null;
		}
		finally {
			try {
				if (rs != null) { rs.close(); }
			} catch (Exception e1) { e1.printStackTrace(); }
			finally {
				try {
					if (stmt != null) { stmt.close(); }
				} catch (Exception e2) { e2.printStackTrace(); }
				finally {
					try {
						if (conn != null) { conn.close(); }
					} catch (Exception e3) { e3.printStackTrace(); }
				}
			}
		}
		return bookList;
	}
	
	public int addEntry(String fname, String lname, String num) {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			try {
				Class.forName(__jdbcDriver);
			}
			catch (Throwable t) {
				t.printStackTrace();
				return -1;
			}
			conn = DriverManager.getConnection(__jdbcUrl, __jdbcUser, __jdbcPasswd);
			ps = conn.prepareStatement(__insertQuery);
			ps.setString(1, fname);
			ps.setString(2, lname);
			ps.setString(3, num);
			ps.executeUpdate();
			return 1;
		} catch (SQLException sqe) {
			sqe.printStackTrace();
			return -1;
		} finally {
			try {
				if (ps != null) { ps.close(); }
			} catch (Exception e2) { e2.printStackTrace(); }
			finally {
				try {
					if (conn != null) { conn.close(); }
				} catch (Exception e3) { e3.printStackTrace(); }
			}
		}
	}
	
	public int removeEntry(String num) {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			try {
				Class.forName(__jdbcDriver);
			}
			catch (Throwable t) {
				t.printStackTrace();
			}
			conn = DriverManager.getConnection(__jdbcUrl, __jdbcUser, __jdbcPasswd);
			ps =  conn.prepareStatement(__deleteQuery);
			ps.setString(1, num);
			return ps.executeUpdate();
		} catch (SQLException sqe) {
			sqe.printStackTrace();
		} finally {
			try {
				if (ps != null) { ps.close(); }
			} catch (Exception e2) { e2.printStackTrace(); }
			finally {
				try {
					if (conn != null) { conn.close(); }
				} catch (Exception e3) { e3.printStackTrace(); }
			}
		}
		return 0;
	}
}
