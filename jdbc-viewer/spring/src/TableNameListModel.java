import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.AbstractListModel;

public class TableNameListModel extends AbstractListModel {
	private final List<String> listData = new ArrayList<String>();
	
	public TableNameListModel(Connection con) throws SQLException {
		String schema = con.getMetaData().getUserName();
		
		if (con.getSchema() != null & !con.getSchema().isEmpty()) {
			schema = con.getSchema();
		}

		System.out.println("Current schema being used: " + schema);
		ResultSet rs = con.getMetaData().getTables(null,schema,null,null);
		// you might need a filter here if your database mixes system
		// tables with user tables, e.g. Microsoft SQL Server
		while (rs.next()) {
			System.out.println("TableName Result: " + rs.getString("TABLE_NAME"));
			listData.add(rs.getString("TABLE_NAME"));
		}
		rs.close();
	}
	
	public int getSize() { return listData.size(); }
	
	public Object getElementAt(int i) { return listData.get(i); }
}