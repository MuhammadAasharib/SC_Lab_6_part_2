import java.sql.*;
import java.util.Scanner;
public class Lab_6_part_2 {
	static final String JDBC_Driver = "com.mysql.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql://localhost/";
	
	static final String PASSWORD = "root";
	static final String USERNAME = "1234";
	
	//This function insert data into database table
	public static void insertIntoTable(Statement stmt,String dbName,String tableName, String regn_no, String name, String classs, String section, String contact, String address){
		try {
			String sqlQuery = "Insert into " + dbName+ "." + tableName + " values (NULL,'"
							  + regn_no + "','" + name + "','" + classs+ "','"+ section + 
							  "','"+ contact+"','"+address+"');";
			stmt.executeUpdate(sqlQuery);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	//Main function
	public static void main(String[] args) {
		Connection conn = null;
		Statement stmt = null;
		
		try{
			Class.forName("com.mysql.jdbc.Driver");
			
			System.out.println("Connecting to database...");
			conn = DriverManager.getConnection(DB_URL,USERNAME,PASSWORD);
			
			System.out.println("Creating Database...");
			stmt = conn.createStatement();
			
			String sqlQuery = "CREATE DATABASE IF NOT EXISTS University";
			stmt.executeUpdate(sqlQuery);
			System.out.println("Database Created Successully");
			
			String sqlQuery1 = "CREATE TABLE IF NOT EXISTS University.Student("
					+ "id INT(64) NOT NULL AUTO_INCREMENT,"
					+ "regn_no VARCHAR(40),"
					+ "name VARCHAR(40),"
					+ "classs VARCHAR(40),"
					+ "section VARCHAR(40),"
					+ "contact VARCHAR(40),"
					+ "address VARCHAR(40)"
					+ ",PRIMARY KEY (`id`));";
			stmt.executeUpdate(sqlQuery1);
			System.out.println("Student Table Created");

			//Now inserting the data into Student table
			insertIntoTable(stmt,"University","Student","100","abc1","3","A","76623232","g-10/4");
			insertIntoTable(stmt,"University","Student","101","abc2","3","A","34323232","g-10/1");
			insertIntoTable(stmt,"University","Student","102","abc3","3","B","23323232","g-10/3");
			insertIntoTable(stmt,"University","Student","103","abc4","3","B","23454232","g-10/2");
			insertIntoTable(stmt,"University","Student","104","abc5","3","A","25323232","g-10/1");
		
			System.out.println("Data has been inserted Now Creating User Menu...");
			
			while(true){
			System.out.println("Enter 1 to display all the data\n"
								+ "Enter 2 to delete spectific record\n"
								+ "Enter 3 to search spectific record\n");
			
			Scanner scan = new Scanner(System.in);
			
			int choice = 1;
			String queryMenu="";
			while(choice!=0){
				choice = scan.nextInt();
				//Display all the data
				if(choice == 1){
					queryMenu = "Select * from University.Student";
					ResultSet rs = stmt.executeQuery(queryMenu);
					ResultSetMetaData rsmd = rs.getMetaData();
					int columnsNumber = rsmd.getColumnCount();
					while (rs.next()) {
					    for (int i = 1; i <= columnsNumber; i++) {
					        if (i > 1) System.out.print(",  ");
					        String columnValue = rs.getString(i);
					        System.out.print(columnValue);
					    }
					    System.out.println("");
					}
				}
				//Delete specific record from table
				else if(choice == 2){
					System.out.println("Please Enter registration number of student to be deleted");
					String regn_no = scan.next();
					queryMenu = "DELETE FROM University.Student WHERE regn_no = " + regn_no;
					stmt.executeUpdate(queryMenu);
					System.out.println("Record Deleted Successfully");
				}
				//Show only particular record based on registration number from the student table
				else if(choice == 3){
					System.out.println("Please Enter registration number of student whose record is to be Found");
					String regn_no = scan.next();
					queryMenu = "SELECT * FROM University.Student WHERE regn_no = " + regn_no;
					ResultSet rs = stmt.executeQuery(queryMenu);
					ResultSetMetaData rsmd = rs.getMetaData();
					int columnsNumber = rsmd.getColumnCount();
					while (rs.next()) {
					    for (int i = 1; i <= columnsNumber; i++) {
					        if (i > 1) System.out.print(",  ");
					        String columnValue = rs.getString(i);
					        System.out.print(columnValue);
					    }
					    System.out.println("");
					}
				}
				else {continue;}
			}
			}
		}
		catch(SQLException e){
			e.printStackTrace();
		}
		catch (ClassNotFoundException e) {
            System.out.println("An MySql drivers were not found");
        }
		catch(Exception e){
			e.printStackTrace();
		}
		finally{
			 try{
		         if(stmt!=null)
		         stmt.close();
		     }
			 catch(SQLException se2){
		     }
		     try{
		    	 if(conn!=null)
		    		 conn.close();
		     }
		     catch(SQLException se){
		         se.printStackTrace();
		      }
		   }
		   System.out.println("Program Ended!");
		}

}
