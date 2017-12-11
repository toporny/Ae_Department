package d16127504_CA3;

import java.awt.EventQueue;
import d16127504_CA3.Interfaces.IStorageServices;
import d16127504_CA3.Services.DbService;

public class Main {

	
	public static void main(String[] args) {

		// init DoublyLinkedList 
		DoublyLinkedList d = new DoublyLinkedList();
		//d.addFakeRecords();

		// import data from SQLite DoublyLinkedList  
		IStorageServices dbServ = new DbService();
		d.importData(dbServ);
		
		// open GUI
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AeDpartment frame = new AeDpartment(d);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
		// save all DoublyLinkedList content into SQLite database   
		Runtime.getRuntime().addShutdownHook(new Thread() {
		    @Override
		    public void run() {
		    	d.saveData(dbServ);
		    }
		});
		
	}

}
