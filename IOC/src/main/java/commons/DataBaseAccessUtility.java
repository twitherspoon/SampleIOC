package commons;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

public class DataBaseAccessUtility {

	public static Connection connection=null;

	public DataBaseAccessUtility(String databaseName) {

		try {
			Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
		}catch(ClassNotFoundException cnfex) {
			System.out.println("Problem in loading or "
					+ "registering MS Access JDBC driver");
			cnfex.printStackTrace();
		}try {
			String currentdir=System.getProperty("user.dir")+"\\Data\\"+databaseName+".accdb"; 
			System.out.println("Current data dir="+currentdir);
			String dbUrl="jdbc:ucanaccess://"+currentdir;
			connection =DriverManager.getConnection(dbUrl);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

	/*
	 * public Connection getConnection(String databaseName) {
	 * 
	 * try { Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
	 * }catch(ClassNotFoundException cnfex) {
	 * System.out.println("Problem in loading or " +
	 * "registering MS Access JDBC driver"); cnfex.printStackTrace(); }try { String
	 * currentdir=System.getProperty("user.dir")+"\\Data\\"+databaseName+".accdb";
	 * System.out.println("Current data dir="+currentdir); String
	 * dbUrl="jdbc:ucanaccess://"+currentdir; connection
	 * =DriverManager.getConnection(dbUrl); }catch(Exception e) {
	 * e.printStackTrace(); } return connection;
	 * 
	 * }
	 */

	public HashMap<String,String> readAccessDB(String query){
		Statement statement=null;
		ResultSet resultSet=null;
		HashMap<String, String> values=new HashMap<String,String>();
		try {
			//Connection connection=getConnection(databaseName);
			statement=connection.createStatement();
			statement.execute(query);
			resultSet=statement.getResultSet();
			ResultSetMetaData rsmd=resultSet.getMetaData();
			int colnum=rsmd.getColumnCount();
			System.out.println("****"+colnum);

			while(resultSet.next()){
				for(int i=1;i<=rsmd.getColumnCount();i++) {
					String colName=rsmd.getColumnName(i);
					String colValue=resultSet.getString(i);
					values.put(colName, colValue);

				}
			}
			System.out.println();
		}catch(SQLException sqlex) {
			sqlex.printStackTrace();
		}
		return values;

	}

	public void writeValuesToDb(String tablename,String colName,String id,String value) {
		try {
			//Connection connection=getConnection(databaseName);
			Statement statement=connection.createStatement();
			statement.execute("Update "+ tablename + "SET" + colName + " =" + value + " where ID=" + id + "");
		}catch(SQLException sqlex) {
			sqlex.printStackTrace();
		}

	}

	public void insertValue(String query) {
		try {
			//Connection connection=getConnection(databaseName);
			Statement statement=connection.createStatement();
			statement.execute(query);
		}catch(SQLException sqlex) {
			sqlex.printStackTrace();
		}

	}

	public ArrayList<String> readAccessDBCol(String query){
		Statement statement=null;
		ResultSet resultSet=null;
		ArrayList<String> values=new ArrayList<String>();
		try {
			//Connection connection=getConnection(databaseName);
			statement=connection.createStatement();

			resultSet=statement.executeQuery(query);

			ResultSetMetaData rsmd1=resultSet.getMetaData();
			int colnum=rsmd1.getColumnCount();
			System.out.println("****"+colnum);


			while(resultSet.next()){
				for(int i=1;i<=rsmd1.getColumnCount();i++) {

					String colValue=resultSet.getString(i);
					System.out.println(colValue+"col value");
					values.add(colValue);

				}
			}
			System.out.println();
		}catch(SQLException sqlex) {
			sqlex.printStackTrace();
		}
		return values;

	}

	public int getNumberOfCols(String query) {

		Statement statement=null;
		ResultSet resultSet=null;
		int count = 0;
		try {
			//Connection connection=getConnection(databaseName);
			statement=connection.createStatement();

			resultSet=statement.executeQuery(query);

			ResultSetMetaData rsmd1=resultSet.getMetaData();
			count =  rsmd1.getColumnCount();
		}
		catch(SQLException sqlex) {
			sqlex.printStackTrace();
		}
		return count;

	}

}
