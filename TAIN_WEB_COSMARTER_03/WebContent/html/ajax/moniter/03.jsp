<%@ page contentType="text/plain; charset=euc-kr" %>
<%@ page import = "java.sql.Connection" %>
<%@ page import = "java.sql.DriverManager" %>
<%@ page import = "java.sql.ResultSet" %>
<%@ page import = "java.sql.SQLException" %>
<%@ page import = "java.sql.Statement" %>
<%@ page import = "java.util.Properties" %>
<%
	boolean flag = true;

	String driver = "org.apache.derby.jdbc.ClientDriver";
	String protocol = "jdbc:derby://localhost:1527/";
	String database = "taindb01";
	String create = "false";
	String user = "kang";
	String password = "kang123!";
	
	Connection conn = null;
	Statement stmt = null;
	
	StringBuffer sbResult = new StringBuffer("['F_DTTM', 'F_USR', 'F_SYS', 'F_IDL', ],\n");
	
	try {
		Class.forName(driver).newInstance();
		
		Properties prop = new Properties();
		prop.put("create", create);
		prop.put("user", user);
		prop.put("password", password);
		
		conn = DriverManager.getConnection(protocol + database, prop);
		stmt = conn.createStatement();
		
		ResultSet rs = stmt.executeQuery(""
				+ "select "
				+ "    TIME(F_DTTM) AS TIME_DTTM , "
				+ "    F_CPUNM       , "
				+ "    F_USR         , "
				+ "    F_SYS         , "
				+ "    F_IDL         , "
				+ "    F_WAIT        , "
				+ "    F_NCE         , "
				+ "    F_CMB         , "
				+ "    F_IRQ         , "
				+ "    DTTM_REG        "
				+ "from "
				+ "    KANG.TB_CPUREC "
				+ "where "
				+ "    F_CPUNM = 'TOTAL' "
				+ "order by "
				+ "    F_DTTM desc "
				+ "offset 0 rows fetch next 10 rows only"
				);
		
		for (int i=0; rs.next(); i++) {
			sbResult.append("[");
			sbResult.append("'").append(rs.getTime("TIME_DTTM")).append("',");
			sbResult.append("").append(rs.getDouble("F_USR")).append(",");
			sbResult.append("").append(rs.getDouble("F_SYS")).append(",");
			sbResult.append("").append(rs.getDouble("F_IDL")).append(",");
			sbResult.append("]").append(",\n");
		}
		
	} catch (Exception e) {
		e.printStackTrace();
	} finally {
		if (stmt != null) try { stmt.close(); } catch (Exception e) {}
		if (conn != null) try { conn.close(); } catch (Exception e) {}
	}
%>
<%= sbResult.toString() %>
