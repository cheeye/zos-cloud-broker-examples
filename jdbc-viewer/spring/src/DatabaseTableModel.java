import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;

@SuppressWarnings("serial")
public class DatabaseTableModel extends DefaultTableModel {
	private final QueryRunner queryRunner = new QueryRunner();
	@SuppressWarnings("unchecked")
	public DatabaseTableModel(Connection con, Object tableName)
			throws SQLException {
		// might need to delimit table names
		String schema = "DPL100S";
//		String sql = "SELECT * FROM " + tableName + " where owner = '" + schema + "' and type = 'T'";
		String sql = "SELECT * FROM " + tableName;
//		System.out.println(sql);
		queryRunner.query(con, sql, new ResultSetHandler() {
			public Object handle(ResultSet rs) throws SQLException {
				// extract the column names
				int numColumns = rs.getMetaData().getColumnCount();
				Vector column = new Vector();
				for (int i = 1; i <= numColumns; i++) {
					column.add(rs.getMetaData().getColumnName(i));
				}
				// extract the data
				Vector data = new Vector();
				while (rs.next()) {
					Vector row = new Vector();
					for (int i = 1; i <= numColumns; i++) {
						row.add(rs.getString(i));
					}
					data.add(row);
				}
				setDataVector(data, column);
				return null;
			}
		});
	}
}