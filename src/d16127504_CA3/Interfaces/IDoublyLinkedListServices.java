package d16127504_CA3.Interfaces;

import d16127504_CA3.AeRecord;
import d16127504_CA3.Node;

public interface IDoublyLinkedListServices {
	
    public Node addByPriority(AeRecord data);
    public Node findNodeByPps (String Pps);
    public Node findNodeById (int ID);
    public String[][] getAllAsArrays ();
    public Node deleteNode(Node current);
    public int getSize();
    public void updateVitalSign(Node current, String patientConditions);
    public void updatePriority(Node current, int priority );
    public void updateTreatment(Node current, String treatment );
    public void importData(IStorageServices dbServ);
    public void saveData(IStorageServices dbServ);
    	
}
