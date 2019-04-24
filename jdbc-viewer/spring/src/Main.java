import java.sql.Connection;
import java.sql.DriverManager;

public class Main {
	
	public static void main(String[] args) throws Exception {
	    if (args.length != 4) {
	      System.err.println("Usage: java DatabaseShower " +
	          "driver url user password");
	      System.exit(1);
	    }
	    Class.forName(args[0]);
	    Connection con = DriverManager.getConnection(args[1], args[2], args[3]);
	    String title = "Database Shower  ->  " + args[1];
	    DatabaseShower frame = new DatabaseShower(con, title);
	    frame.setSize(1024, 768);
	    frame.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
	    frame.setLocationRelativeTo(null);
	    frame.setVisible(true);
	  }
}
