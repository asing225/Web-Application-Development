package edu.asupoly.ser422.lab3.services.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import edu.asupoly.ser422.lab3.model.PhoneEntry;
import edu.asupoly.ser422.lab3.services.PhoneBookService;

public class RDBMPhoneBookServiceImpl implements PhoneBookService {
	
	private static Properties __dbProperties;
	private static String __jdbcUrl;
	private static String __jdbcUser;
	private static String __jdbcPasswd;
	private static String __jdbcDriver;
	// This class is going to look for a file named rdbm.properties in the classpath
	// to get its initial settings
	static {
		try {
			__dbProperties = new Properties();
			__dbProperties.load(RDBMPhoneBookServiceImpl.class.getClassLoader().getResourceAsStream("rdbm.properties"));
			__jdbcUrl    = __dbProperties.getProperty("jdbcUrl");
			__jdbcUser   = __dbProperties.getProperty("jdbcUser");
			__jdbcPasswd = __dbProperties.getProperty("jdbcPasswd");
			__jdbcDriver = __dbProperties.getProperty("jdbcDriver");
		} catch (Throwable t) {
			t.printStackTrace();
		} finally {
		}
	}
	
	private Connection getConnection() throws Exception {
		try {
			Class.forName(__jdbcDriver);
			return DriverManager.getConnection(__jdbcUrl, __jdbcUser, __jdbcPasswd);
		} catch (Exception exc) {
			throw exc;
		}
	}
	
	// PhoneBook methods

	public List<PhoneEntry> getPhoneBookSubset(int bookId, String fname, String lname) {
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		List<PhoneEntry> res = new ArrayList<PhoneEntry>();
		try {
			conn = getConnection();
			stmt = conn.prepareStatement(__dbProperties.getProperty("sql.getSubPhoneBook"));
			stmt.setString(1, "%" + fname + "%");
			stmt.setString(2, "%" + lname + "%");
			stmt.setInt(3,  bookId);
			rs = stmt.executeQuery();
			while(rs.next()) {
				res.add(new PhoneEntry(rs.getString(1), rs.getString(2), rs.getInt(3)));
			}
		}
		catch(Exception exc) {
			exc.printStackTrace();
			return null;
		}
		finally {
			try {
				if(rs != null) {
					rs.close();
				}
			}
			catch(Exception exc1) {
				exc1.printStackTrace();
			}
			finally {
				try {
					if(stmt != null) {
						stmt.close();
					}
				}
				catch(Exception exc2) {
					exc2.printStackTrace();
				}
				finally {
					try {
						if(conn != null) {
							conn.close();
						}
					}
					catch(Exception exc3) {
						exc3.printStackTrace();
					}
				}
			}
		}
		return res;
	}
	
    @SuppressWarnings("resource")
	public int addPhoneBookEntry(int bookNo, PhoneEntry entry) {
    	Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			conn = getConnection();
			stmt = conn.prepareStatement(__dbProperties.getProperty("sql.getPhoneEntry"));
			stmt.setInt(1,  entry.getPhone());
			rs = stmt.executeQuery();
			// check if the entry exists
			if(rs.first()) {
				// if exists but not in phonebook, add into phonebook
				int value = rs.getInt(1);
				if(!rs.getString(2).equalsIgnoreCase(entry.getFirstname()) || 
						!rs.getString(3).equalsIgnoreCase(entry.getLastname())) {
					return -5;
				}
				if(value == bookNo) {
					return -2;
				}
				// if into phonebook, return duplicate error
				else if(value == 0) {
					stmt = conn.prepareStatement(__dbProperties.getProperty("sql.updateEntryInBook"));
					stmt.setInt(1,  bookNo);
					stmt.setString(2,  entry.getFirstname());
					stmt.setString(3,  entry.getLastname());
					stmt.setInt(4,  entry.getPhone());
					int res1 = stmt.executeUpdate();
					if(res1 > 0) {
						return -6;
					}
					else {
						return -1;
					}
				}
				else {
					return value;
				}
			}
			// if not present
			else {
				return -4;
			}
		}
		catch(Exception exc) {
			exc.printStackTrace();
			return -3;
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
    }

    //PhoneEntry methods
    
    @SuppressWarnings("null")
	public Map<String, PhoneEntry> getPhoneEntry(int phone) {
    	Connection conn = null;
    	PreparedStatement stmt = null;
    	ResultSet rs = null;
    	PhoneEntry entry = null;
    	Map<String, PhoneEntry> res = new HashMap<String, PhoneEntry>();
    	try {
    		conn = getConnection();
			stmt = conn.prepareStatement(__dbProperties.getProperty("sql.getPhoneEntry"));
			stmt.setInt(1, phone);
			rs = stmt.executeQuery();
			if(rs.next()) {
				entry = new PhoneEntry(rs.getString(2), rs.getString(3), rs.getInt(4));
				res.put("Data", entry);
				return res;
			}
			else {
				res.put("Empty", null);
				return res;
			}
    	}
    	catch(Exception sqe){
    		sqe.printStackTrace();
    		res.put("Exception", null);
    		return res;
    	}
    	finally {
    		try {
    			rs.close();
    		}
    		catch(Exception e2) {
    			e2.printStackTrace();
    		}
    		finally {
    			try {
    				if(conn != null) {
    					conn.close();
    				}
    			}
    			catch(Exception e3) {
    				e3.printStackTrace();
    			}
    		}
    	}
    }
    
    public int createPhoneEntry(String fname, String lname, int phone) {
		Connection conn = null;
		PreparedStatement stmt = null;
		try {
			conn = getConnection();
			stmt = conn.prepareStatement(__dbProperties.getProperty("sql.createPhoneEntry")); 
			stmt.setString(1, fname);
			stmt.setString(2, lname);
			stmt.setInt(3, phone);
			int updatedRows = stmt.executeUpdate();
			if(updatedRows > 0){
				return phone;
			}else{
				return -1;
			}
		} catch (Exception sqe) {
			sqe.printStackTrace();
			if(sqe.toString().contains("Duplicate")) {
				return -3;
			}
			return -2;
		} finally {
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
    
    @SuppressWarnings("resource")
	public int updatePhoneEntry(PhoneEntry updatedEntry) {
    	Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			conn = getConnection();
			stmt = conn.prepareStatement(__dbProperties.getProperty("sql.getPhoneEntry"));
			stmt.setInt(1,  updatedEntry.getPhone());
			rs = stmt.executeQuery();
			if(rs.first()) {
				stmt = conn.prepareStatement(__dbProperties.getProperty("sql.updateEntry"));
				stmt.setString(1,  updatedEntry.getFirstname());
				stmt.setString(2,  updatedEntry.getLastname());
				stmt.setInt(3,  updatedEntry.getPhone());
				if(stmt.executeUpdate() > 0) {
					return 1;
				}
			}return 0;
		}
		catch(Exception exc) {
			exc.printStackTrace();
			return -1;
		}
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
    
    public int deletePhoneEntry(int toBeDeletedEntry) {
    	Connection conn = null;
		PreparedStatement stmt = null;
		try {
			conn = getConnection();
			stmt = conn.prepareStatement(__dbProperties.getProperty("sql.deleteEntry"));
			stmt.setInt(1,  toBeDeletedEntry);
			if(stmt.executeUpdate() > 0) {
				return 1;
			}
			else {
				return -1;
			}
		}
		catch(Exception exc) {
			exc.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return -2;
		}
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
    
    public List<PhoneEntry> getUnlistedEntries(){
    	Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		List<PhoneEntry> res = new ArrayList<PhoneEntry>();
		try {
			conn = getConnection();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(__dbProperties.getProperty("sql.getUnlistedEntries"));
			while(rs.next()) {
				res.add(new PhoneEntry(rs.getString(1), rs.getString(2), rs.getInt(3)));
			}
		}
		catch(Exception exc) {
			exc.printStackTrace();
			return null;
		}
		finally {
			try {
				if(rs != null) {
					rs.close();
				}
			}
			catch(Exception exc1) {
				exc1.printStackTrace();
			}
			finally {
				try {
					if(stmt != null) {
						stmt.close();
					}
				}
				catch(Exception exc2) {
					exc2.printStackTrace();
				}
				finally {
					try {
						if(conn != null) {
							conn.close();
						}
					}
					catch(Exception exc3) {
						exc3.printStackTrace();
					}
				}
			}
		}
		return res;
    }
}
