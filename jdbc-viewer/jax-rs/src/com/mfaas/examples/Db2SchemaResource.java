package com.mfaas.examples;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import com.ibm.json.java.JSONArray;
import com.ibm.json.java.JSONObject;

@Path("/schema")
public class Db2SchemaResource {
	
//	private static final String hostname = System.getenv("HOSTNAME");
//	private static final int port = Integer.parseInt(System.getenv("DB2PORT"));
//	private static final String location = System.getenv("DB2LOCATION");
//	private static final String schema = System.getenv("SCHEMANAME");
//	private static final String user = System.getenv("ZOSMF_USER");
//	private static final String password = System.getenv("ZOSMF_PASS");
//    
//	private static final String jdbcUrl = String.format("jdbc:db2://%s:%d/%s:currentSchema=%s;", hostname, port, location, schema);
	
	private String schema = null;
	
	private Connection getJdbcConnection() throws Exception {
//		System.out.println("jdbcURL: " + jdbcUrl);
//        Class.forName("com.ibm.db2.jcc.DB2Driver");
//        Connection con = DriverManager.getConnection(jdbcUrl, user, password);
		
		final Context ctx = new InitialContext();
		final DataSource ds = (DataSource) ctx.lookup("jdbc/jdbcViewerDb2");
		if (ds != null) {
			Connection con =ds.getConnection(); 
			schema = con.getSchema();
			return con;
		}
		else {
			System.err.println("Unable to find JNDI resource");
			throw new Exception("Unable to find JNDI resource");
		}
	}
	
	@GET
	@Produces("application/json")
	public String getSchemaTables() throws Exception {
		JSONObject respJson = new JSONObject();
		List<String> tableList = new ArrayList<String>();
		JSONArray tables = new JSONArray();

		Connection con = getJdbcConnection();
		ResultSet rs = con.getMetaData().getTables(null,schema,null,null);
		while (rs.next()) {
			System.out.println("TableName Result: " + rs.getString("TABLE_NAME"));
			tableList.add(rs.getString("TABLE_NAME"));
			
		}
		rs.close();
		
		System.out.println("Returning tableList: " + tableList);
		tables.addAll(tableList);
		respJson.put("tables", tables);
//		respJson.put("jdbcUrl", jdbcUrl);
        
        return respJson.toString();
	}
	
	@GET
	@Path("/tables/{table}")
	@Produces("application/json")
	public String getTableData(@PathParam("table") String table) throws Exception {
		Connection con = getJdbcConnection();
		String sql = "SELECT * FROM " + table;
		
		ResultSet rs = con.createStatement().executeQuery(sql);
		
		JSONArray json = new JSONArray();
		ResultSetMetaData rsmd = rs.getMetaData();
		while(rs.next()) {
		  int numColumns = rsmd.getColumnCount();
		  JSONObject obj = new JSONObject();
		  for (int i=1; i<=numColumns; i++) {
		    String column_name = rsmd.getColumnName(i);
		    obj.put(column_name, rs.getObject(column_name));
		  }
		  json.add(obj);
		}
		return json.toString();
	}
	
	@GET
	@Path("/tables/{table}/addData")
	public void addTableData(@PathParam("table") String table, @QueryParam("col1") String col1, @QueryParam("col2") String c2, @QueryParam("col3") String c3) throws SQLException, ClassNotFoundException {
		String sql = "INSERT INTO " + table + "(COL1,COL2,COL3) VALUES(?,?,?)";
		try (Connection conn = getJdbcConnection();
              PreparedStatement pstmt = conn.prepareStatement(sql)) {
			
			Long col2 = Long.parseLong(c2);
			Long col3 = Long.parseLong(c3);
			
			pstmt.setString(1, col1);
			pstmt.setLong(2, col2);
			pstmt.setLong(3, col3);
			pstmt.executeUpdate();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	@POST
	@Path("/tables/{table}")
	@Consumes("application/json")
	public void insert(@PathParam("table") String table, String json) throws ClassNotFoundException {
//        String sql = "INSERT INTO " + table + "(name,capacity) VALUES(?,?)";
// 
//        try (Connection conn = getJdbcConnection();
//                PreparedStatement pstmt = conn.prepareStatement(sql)) {
//            pstmt.setString(1, name);
//            pstmt.setInt(2, age);
//            pstmt.executeUpdate();
//        } catch (SQLException e) {
//            System.out.println(e.getMessage());
//        }
		
		String sql = "INSERT INTO " + table + "(COL1,COL2,COL3) VALUES(?,?,?)";
		try (Connection conn = getJdbcConnection();
              PreparedStatement pstmt = conn.prepareStatement(sql)) {
			JSONObject obj = JSONObject.parse(json);
			String col1 = (String) obj.get("COL1");
			long col2 = (Long) obj.get("COL2");
			long col3 = (Long) obj.get("COL3");
          pstmt.setString(1, col1);
          pstmt.setLong(2, col2);
          pstmt.setLong(3, col3);
          pstmt.executeUpdate();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	
    }
}
