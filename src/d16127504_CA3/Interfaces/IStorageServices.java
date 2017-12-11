package d16127504_CA3.Interfaces;

import d16127504_CA3.DoublyLinkedList;

public interface IStorageServices {

	public void openConnection();
	public void closeConnection();
	public void readAllData(DoublyLinkedList d);
	public void saveAllData(DoublyLinkedList d);
	
}
