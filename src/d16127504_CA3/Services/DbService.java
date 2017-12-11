package d16127504_CA3.Services;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Logger;

import d16127504_CA3.AeRecord;
import d16127504_CA3.DoublyLinkedList;
import d16127504_CA3.Node;
import d16127504_CA3.Config.Constants;

//import solid_patterns.Person;
import d16127504_CA3.Interfaces.IStorageServices;
 


public class DbService implements IStorageServices {

	private static int LIMIT_BIG = Constants.Limits.LIMIT_BIG;
	private static int LIMIT_SMALL = Constants.Limits.LIMIT_BIG;
	
	private static final Logger LOGGER = Logger.getLogger( DbService.class.getName() );
	
	Connection c = null;
	Statement stmt = null;

	public DbService() {
		openConnection();
	}
	


	public void readAllData(DoublyLinkedList d) {
		try {
			ResultSet rs1 = stmt.executeQuery("SELECT timeStamp, name, surname, PPS, DOB, Address1, Address2, Zipcode, Phone, Priority, BriefSummaryOfCondition, PatientsConditionAndVitalSigns, SummaryOfTheTreatment FROM people");
			while (rs1.next()) {
				String timeStamp = rs1.getString("timeStamp");
				String name = rs1.getString("name");
				String surname = rs1.getString("surname");
				String PPS = rs1.getString("PPS");
				String DOB = rs1.getString("DOB");
				String Address1 = rs1.getString("Address1");
				String Address2 = rs1.getString("Address2");
				String Zipcode = rs1.getString("Zipcode");
				String Phone = rs1.getString("Phone");
				String Priority = rs1.getString("Priority");
				String BriefSummaryOfCondition = rs1.getString("BriefSummaryOfCondition");
				String PatientsConditionAndVitalSigns = rs1.getString("PatientsConditionAndVitalSigns");
				String SummaryOfTheTreatment = rs1.getString("SummaryOfTheTreatment");
				System.out.println("timeStamp: "+timeStamp);
				if ((Priority == null) || (Priority.equals(""))) Priority="0";
				System.out.println("Priority: "+Priority);
				AeRecord ae = new AeRecord( Integer.parseInt(Priority),  timeStamp,  name,  surname,  PPS,  DOB,  Phone,  Address1,  Address2,  Zipcode, BriefSummaryOfCondition);
				ae.setTimeStamp(timeStamp);
				ae.setPatientsConditionAndVitalSigns(PatientsConditionAndVitalSigns);
				ae.setSummaryOfTheTreatment(SummaryOfTheTreatment);
				d.addByPriority(ae);
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

 	
	public void saveAllData(DoublyLinkedList d) {

		  String sql = "DELETE FROM people ";
		  try {
		    stmt = c.createStatement();
		    stmt.executeUpdate(sql);
		  }
		  catch (SQLException e) {
			System.out.println(sql);
		    System.out.println(e.getMessage());
		  }


		  Node temp = d.head;
		  System.out.println("Doubly Linked List: ");
		  while (temp != null) {
			//System.out.println(temp.data.getPriority()+" " + temp.data.getName() + ","+ temp.data.getPPS());
			String finalQuery = "INSERT INTO people (timeStamp, name, surname, PPS, DOB, Address1, Address2, Zipcode, Phone, Priority, BriefSummaryOfCondition, PatientsConditionAndVitalSigns, SummaryOfTheTreatment) ";   
			finalQuery += "VALUES (DateTime('now'), '"+ ((temp.data.getName() == null) ? "" : temp.data.getName())
			+"', '"+((temp.data.getSurname() == null) ? "" : temp.data.getSurname())
			+"', '"+((temp.data.getPPS() == null) ? "" : temp.data.getPPS())
			+"', '"+((temp.data.getDOB() == null) ? "" : temp.data.getDOB())
			+"', '"+((temp.data.getAddress1() == null) ? "" : temp.data.getAddress1())
			+"', '"+((temp.data.getAddress2() == null) ? "" : temp.data.getAddress2())
			+"', '"+((temp.data.getZipcode() == null) ? "" : temp.data.getZipcode())
			+"', '"+((temp.data.getPhone() == null) ? "" : temp.data.getPhone())
			+"', '"+((temp.data.getPriority() == 0) ? "" : temp.data.getPriority())
			+"', '"+((temp.data.getBriefSummaryOfCondition() == null) ? "" : temp.data.getBriefSummaryOfCondition())
			+"', '"+((temp.data.getPatientsConditionAndVitalSigns() == null) ? "" : temp.data.getPatientsConditionAndVitalSigns())
			+"', '"+((temp.data.getSummaryOfTheTreatment() == null) ? "" : temp.data.getSummaryOfTheTreatment())
			+"')";
			
			//System.out.println("finalQuery"+finalQuery);
			temp = temp.next;
			try {
				stmt.executeUpdate(finalQuery);
			} catch (SQLException e) {
				System.out.println(finalQuery);
				LOGGER.info("Problem with inserting one row to database");
				LOGGER.info("final query:" + finalQuery);
				LOGGER.info(e.getClass().getName() + ":" + e.getMessage());
				e.printStackTrace();
			}
				
		  }

	}

 
	
	
	
	public void openConnection() {
		try {		
	    	Class.forName("org.sqlite.JDBC");
	    	c = DriverManager.getConnection("jdbc:sqlite:"+Constants.DB.AE_DEPARTAMENT_FILE);
	    	LOGGER.info( "Were connected ok!!" );
	    	//System.out.println("Were connected ok!!");
	    	createDB();
	    	
	    } catch (Exception e) {
	    	System.out.println("Problem with connection/making sqlite file!");
	    	System.err.println(e.getClass().getName() + ":"+e.getMessage());
	    	System.exit(0);
	    }

	}
	
	private void createDB() {
    	System.out.println("createDB()");
		try {
	    	stmt = c.createStatement();
	    	//" (id INTEGER PRIMARY KEY AUTOINCREMENT, "+
	    	String createTable = "CREATE TABLE people "+
	    			" (timeStamp DATETIME, "+
	    			" name CHAR("+LIMIT_BIG+"), "+
	    			" surname CHAR("+LIMIT_BIG+"), "+
	    			" PPS CHAR("+LIMIT_SMALL+"), "+
	    			" DOB CHAR("+LIMIT_SMALL+"), "+
	    			" Address1 CHAR("+LIMIT_BIG+"), "+
	    			" Address2 CHAR("+LIMIT_BIG+"), "+
	    			" Zipcode CHAR("+LIMIT_SMALL+"), "+
	    			" Phone CHAR("+LIMIT_SMALL+"), "+
	    			" Priority INTEGER, "+
	    			" BriefSummaryOfCondition CHAR("+LIMIT_BIG+"), "+
	    			" PatientsConditionAndVitalSigns CHAR("+LIMIT_BIG+"), "+
	    			" SummaryOfTheTreatment CHAR("+LIMIT_BIG+") "+
	    		")";
	    	
	    	System.out.println(createTable);
	    	stmt.executeUpdate(createTable);
	    } catch (Exception e) {
	    	LOGGER.info("Table creation failed (probably already exists)");

	    }		
		
	}
	
	

	public void closeConnection() {

		try {		
			c.close();
	    } catch (Exception e) {
	    	System.err.println(e.getClass().getName() + ":"+e.getMessage());
	    	System.exit(0);
	    }		
	}






}
