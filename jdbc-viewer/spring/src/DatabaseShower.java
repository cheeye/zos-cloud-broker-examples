import java.sql.Connection;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

public class DatabaseShower extends JFrame {
  private final JList names;
  private final JTable data = new JTable();
  
  public DatabaseShower(final Connection con, String title) throws SQLException {
    super(title);
    names = new JList(new TableNameListModel(con));
    
    names.addListSelectionListener(new ListSelectionListener() {
      public void valueChanged(ListSelectionEvent e) {
        if (!e.getValueIsAdjusting()) {
          Object tableName = names.getSelectedValue();
          if (tableName != null) {
            try {
              data.setModel(new DatabaseTableModel(con, tableName));
            } catch (SQLException ex) {
              ex.printStackTrace();
              data.setModel(new DefaultTableModel());
            }
          }
        }
      }
    });
    
    getContentPane().add(new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
        new JScrollPane(names), new JScrollPane(data)));
  }
}