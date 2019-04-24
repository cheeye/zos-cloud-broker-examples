# JDBC Viewer

This project allows you to quickly browse the contents of a JDBC datasource to visualize the tables and data.


### Running
This application launches as a Java Swing applet. You can import the project into your Eclipse environment and set the run configurations for the `Main.java`.

**Run Configuration**
`com.ibm.db2.jcc.DB2Driver jdbc:db2://<ip.address>:<port>/<location>:currentSchema=<schema-name>; user password`

The `currentSchema` value is not always needed if your database exists under the user directly. 