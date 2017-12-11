package d16127504_CA3;

import d16127504_CA3.Interfaces.IDoublyLinkedListServices;
import d16127504_CA3.Interfaces.IStorageServices;

public class DoublyLinkedList implements IDoublyLinkedListServices {

	// DDL initialization
    int size = 0;
    public Node head = null;
    public Node tail = null;

    // addAtStart
    public Node addAtStart(AeRecord data){
        System.out.println("Adding Node " + data + " at the start");
        Node n = new Node(data);
        if( size==0) {
            head = n;
            tail = n;
        } else {
            n.next = head;
            head.previous = n;
            head = n;
        }
        size++;
        return n;
    }

    //addAtEnd
    public Node addAtEnd(AeRecord data){
        System.out.println("Adding Node " + data + " at the End");
        Node n = new Node(data);
        if (size == 0){
            head = n;
            tail = n;
        } else {
            tail.next = n;
            n.previous = tail;
            tail =n;
        }
        size++;
        return n;
    }

    //addAfter
    public Node addAfter(AeRecord data, Node prevNode){
        if (prevNode == null){
            System.out.println("Node after which new node to be added cannot be null");
            return null;
        } else if (prevNode == tail){ //check if it a last node
            return addAtEnd(data);
        } else {
            System.out.println("Adding node after "+ prevNode.data);
            //create a new node
            Node n = new Node(data);

            //store the next node of prevNode
            Node nextNode = prevNode.next;

            //make new node next points to prevNode
            n.next = nextNode;

            //make prevNode next points to new Node
            prevNode.next = n;

            //make nextNode previous points to new node
            nextNode.previous = n;

            //make  new Node previous points to prevNode
            n.previous = prevNode;
            size++;
            return n;
        }
    }
    
    
    // add record to DDL in right place (it keeps order of priorities)
    public Node addByPriority(AeRecord data) {
    	if (size == 0) {
    		return addAtStart(data);
        }
        Node temp = tail;
        while (temp != null) {
        	if (data.getPriority() > temp.data.getPriority()) {
        		 return addAfter(data, temp);
        	}
          temp = temp.previous;
        }
        return addAtStart(data);
    }
    

    // get element by PPS
    public Node findNodeByPps (String Pps) {
        Node temp = head;
        while (temp != null) {
          if (temp.data.getPPS().equals(Pps)) {
            return temp;
          }
          temp = temp.next;
        }
        return null;
    }
    
    // get element by ID
    // (the same as get element by positions but this time gets node)
    public Node findNodeById (int ID) {
        Node temp = head;
        int i = 1;
        while (temp != null) {
			if (ID == i) return temp;
			i++;
			temp = temp.next;
        }
        return null;
    }
    
    
    // get all elements and returns as String multiarray
    public String[][] getAllAsArrays () {
    	if (size == 0) return null;
    	String[][] tmpArray = new String[size][];
    	String row[];
        Node temp = head;
        int i = 0;
        while (temp != null) {
        	row = new String[] {""+(1+i),""+(temp.data.getPriority() == 0 ? "" : temp.data.getPriority()), temp.data.getName() + " "+temp.data.getSurname(), temp.data.getTimeStamp()};
        	tmpArray[i] = row;
        	i++;
          temp = temp.next;
        }
        return tmpArray;
    }    


    // delete Node
    public Node deleteNode(Node current) {
    
        // a node with data e doesn't exist
        if (current == null) {
            return null;
        }
        
        if (size == 0) {
        	return null;
        }
    
        // get the next and previous node
        Node previous = current.previous;
        Node next = current.next;
    
        if ((previous == null) && (next == null)) {
            size = 0;
            head = null;
            tail = null;
            return null;
        }
        
        // current node is head
        if (previous == null) {
            this.head = current.next;
            this.head.previous = null;
            size--;
            return current;
        }
    
        // current node is tail
        if (next == null) {
            this.tail = current.previous;
            this.tail.next = null;
            size--;
            return current;
        }
    
        if ((previous != null) && (next != null)) {
            Node temp = current.previous;
            temp.next = current.next;
            temp = current.next;
            temp.previous = current.previous;
        }
        size--;
        return current;            
    }


    //get Size
    public int getSize() {
        return this.size;
    }
 
    // update vital signs in Node
    public void updateVitalSign(Node current, String patientConditions) {
    	System.out.println("getPatientsConditionAndVitalSigns: " + current.data.getPatientsConditionAndVitalSigns() );
    	current.data.setPatientsConditionAndVitalSigns(patientConditions);
    }
    
    // update priority in Node
    public void updatePriority(Node current, int priority ) {
    	current.data.setPriority( priority);
    	Node toRemove = current; 
    	addByPriority(current.data);
    	deleteNode(toRemove);
    }
    
    // update treatment in Node
    public void updateTreatment(Node current, String treatment ) {
    	current.data.setSummaryOfTheTreatment(treatment);
    }
    

    //import data from IStorageServices 
    public void importData(IStorageServices dbServ) {
    	dbServ.readAllData(this);
    }

    //save data from IStorageServices 
    public void saveData(IStorageServices dbServ) {
    	dbServ.saveAllData(this);
    }
    

    // helper for debug purposes
    public void addFakeRecords() {
    	AeRecord d;
        d = new AeRecord (1, "2015-04-03 12:12","Name_01","Surname_01","12324311","1099-02-01","353861055754","Address_A_1","Address_B_1","Dublin 11","Brief summmary of patient nr 1");        addByPriority(d);
        d = new AeRecord (2, "2015-04-04 12:13","Name_02","Surname_02","12324312","1099-02-02","353861055755","Address_A_2","Address_B_2","Dublin 18","Brief summmary of patient nr 2");        addByPriority(d);
        d = new AeRecord (3, "2015-04-05 12:14","Name_03","Surname_03","12324313","1899-12-03","353861055754","Address_A_3","Address_B_3","Dublin 25","Brief summmary of patient nr 3");        addByPriority(d);
        d = new AeRecord (4, "2015-04-06 12:15","Name_04","Surname_04","12324314","1899-12-04","353861055756","Address_A_4","Address_B_4","Dublin 32","Brief summmary of patient nr 4");        addByPriority(d);
        d = new AeRecord (5, "2015-04-07 12:16","Name_05","Surname_05","12324315","1899-12-05","353861055754","Address_A_5","Address_B_5","Dublin 39","Brief summmary of patient nr 5");        addByPriority(d);
        d = new AeRecord (6, "2015-04-08 12:17","Name_06","Surname_06","12324316","1899-12-06","353861055757","Address_A_6","Address_B_6","Dublin 46","Brief summmary of patient nr 6");        addByPriority(d);
        d = new AeRecord (8, "2015-04-09 12:18","Name_07","Surname_07","12324317","1899-12-07","353861055754","Address_A_7","Address_B_7","Dublin 53","Brief summmary of patient nr 7");        addByPriority(d);
        d = new AeRecord (9, "2015-04-10 12:19","Name_08","Surname_08","12324318","1899-12-08","353861055758","Address_A_8","Address_B_8","Dublin 60","Brief summmary of patient nr 8");        addByPriority(d);
        d = new AeRecord (8, "2015-04-11 12:20","Name_09","Surname_09","12324319","1899-12-09","353861055754","Address_A_9","Address_B_9","Dublin 67","Brief summmary of patient nr 9");        addByPriority(d);
        d = new AeRecord (7, "2015-04-12 12:21","Name_10","Surname_10","12324320","1899-12-10","353861055759","Address_A_10","Address_B_10","Dublin 74","Brief summmary of patient nr 10");     addByPriority(d);
        d = new AeRecord (6, "2015-04-13 12:22","Name_11","Surname_11","12324321","1899-12-11","353861055754","Address_A_11","Address_B_11","Dublin 81","Brief summmary of patient nr 11");     addByPriority(d);
        d = new AeRecord (5, "2015-04-14 12:23","Name_12","Surname_12","12324322","1899-12-12","353861055760","Address_A_12","Address_B_12","Dublin 88","Brief summmary of patient nr 12");     addByPriority(d);
        d = new AeRecord (4, "2015-04-15 12:24","Name_13","Surname_13","12324323","1899-12-13","353861055754","Address_A_13","Address_B_13","Dublin 95","Brief summmary of patient nr 13");     addByPriority(d);
        d = new AeRecord (3, "2015-04-16 12:25","Name_14","Surname_14","12324324","1899-12-14","353861055761","Address_A_14","Address_B_14","Dublin 102","Brief summmary of patient nr 14");        addByPriority(d);
        d = new AeRecord (3, "2015-04-17 12:26","Name_15","Surname_15","12324325","1899-12-15","353861055754","Address_A_15","Address_B_15","Dublin 109","Brief summmary of patient nr 15");        addByPriority(d);
        d = new AeRecord (2, "2015-04-18 12:27","Name_16","Surname_16","12324326","1899-12-16","353861055762","Address_A_16","Address_B_16","Dublin 116","Brief summmary of patient nr 16");addByPriority(d);
        d = new AeRecord (1, "2015-04-19 12:28","Name_17","Surname_17","12324327","1899-12-17","353861055754","Address_A_17","Address_B_17","Dublin 123","Brief summmary of patient nr 17");addByPriority(d);
        d = new AeRecord (2, "2015-04-20 12:29","Name_18","Surname_18","12324328","1899-12-18","353861055763","Address_A_18","Address_B_18","Dublin 130","Brief summmary of patient nr 18");addByPriority(d);
        d = new AeRecord (3, "2015-04-21 12:30","Name_19","Surname_19","12324329","1899-12-19","353861055754","Address_A_19","Address_B_19","Dublin 137","Brief summmary of patient nr 19");addByPriority(d);
        d = new AeRecord (4, "2015-04-22 12:31","Name_20","Surname_20","12324330","1899-12-20","353861055764","Address_A_20","Address_B_20","Dublin 144","Brief summmary of patient nr 20");addByPriority(d);
        d = new AeRecord (5, "2015-04-23 12:32","Name_21","Surname_21","12324331","1899-12-21","353861055754","Address_A_21","Address_B_21","Dublin 151","Brief summmary of patient nr 21");addByPriority(d);
        d = new AeRecord (6, "2015-04-24 12:33","Name_22","Surname_22","12324332","1899-12-22","353861055765","Address_A_22","Address_B_22","Dublin 158","Brief summmary of patient nr 22");addByPriority(d);
        d = new AeRecord (7, "2015-04-25 12:34","Name_23","Surname_23","12324333","1899-12-23","353861055754","Address_A_23","Address_B_23","Dublin 165","Brief summmary of patient nr 23");addByPriority(d);
        d = new AeRecord (8, "2015-04-26 12:35","Name_24","Surname_24","12324334","1899-12-24","353861055766","Address_A_24","Address_B_24","Dublin 172","Brief summmary of patient nr 24");addByPriority(d);
        d = new AeRecord (9, "2015-04-27 12:36","Name_25","Surname_25","12324335","1899-12-25","353861055754","Address_A_25","Address_B_25","Dublin 179","Brief summmary of patient nr 25");addByPriority(d);
        d = new AeRecord (0, "2015-04-28 12:37","Name_26","Surname_26","12324336","1899-12-26","353861055767","Address_A_26","Address_B_26","Dublin 186","Brief summmary of patient nr 26");addByPriority(d);
        d = new AeRecord (9, "2015-04-29 12:38","Name_27","Surname_27","12324337","1899-12-27","353861055754","Address_A_27","Address_B_27","Dublin 193","Brief summmary of patient nr 27");addByPriority(d);
        d = new AeRecord (9, "2015-04-30 12:39","Name_28","Surname_28","12324338","1899-12-28","353861055768","Address_A_28","Address_B_28","Dublin 200","Brief summmary of patient nr 28");addByPriority(d);
        d = new AeRecord (9, "2015-05-01 12:40","Name_29","Surname_29","12324339","1899-12-29","353861055754","Address_A_29","Address_B_29","Dublin 207","Brief summmary of patient nr 29");addByPriority(d);
        d = new AeRecord (8, "2015-05-02 12:41","Name_30","Surname_30","12324340","1899-12-30","353861055769","Address_A_30","Address_B_30","Dublin 214","Brief summmary of patient nr 30");addByPriority(d);
        d = new AeRecord (7, "2015-05-03 12:42","Name_31","Surname_31","12324341","1899-12-01","353861055754","Address_A_31","Address_B_31","Dublin 221","Brief summmary of patient nr 31");addByPriority(d);
        d = new AeRecord (6, "2015-05-04 12:43","Name_32","Surname_32","12324342","1899-12-02","353861055770","Address_A_32","Address_B_32","Dublin 228","Brief summmary of patient nr 32");addByPriority(d);
        d = new AeRecord (0, "2015-05-05 12:44","Name_33","Surname_33","12324343","1899-12-03","353861055754","Address_A_33","Address_B_33","Dublin 235","Brief summmary of patient nr 33");addByPriority(d);
        d = new AeRecord (8, "2015-05-06 12:45","Name_34","Surname_34","12324344","1899-12-04","353861055771","Address_A_34","Address_B_34","Dublin 242","Brief summmary of patient nr 34");addByPriority(d);
        d = new AeRecord (1, "2015-05-07 12:46","Name_35","Surname_35","12324345","1899-12-05","353861055754","Address_A_35","Address_B_35","Dublin 249","Brief summmary of patient nr 35");addByPriority(d);
        d = new AeRecord (4, "2015-05-08 12:47","Name_36","Surname_36","12324346","1899-12-06","353861055772","Address_A_36","Address_B_36","Dublin 256","Brief summmary of patient nr 36");addByPriority(d);
        d = new AeRecord (3, "2015-05-09 12:48","Name_37","Surname_37","12324347","1899-12-07","353861055754","Address_A_37","Address_B_37","Dublin 263","Brief summmary of patient nr 37");addByPriority(d);
        d = new AeRecord (2, "2015-05-10 12:49","Name_38","Surname_38","12324348","1899-12-08","353861055773","Address_A_38","Address_B_38","Dublin 270","Brief summmary of patient nr 38");addByPriority(d);
        d = new AeRecord (1, "2015-05-11 12:50","Name_39","Surname_39","12324349","1899-12-09","353861055754","Address_A_39","Address_B_39","Dublin 277","Brief summmary of patient nr 39");addByPriority(d);
        d = new AeRecord (2, "2015-05-12 12:51","Name_40","Surname_40","12324350","1899-12-10","353861055774","Address_A_40","Address_B_40","Dublin 284","Brief summmary of patient nr 40");addByPriority(d);
        d = new AeRecord (3, "2015-05-13 12:52","Name_41","Surname_41","12324351","1899-12-11","353861055754","Address_A_41","Address_B_41","Dublin 291","Brief summmary of patient nr 41");addByPriority(d);
        d = new AeRecord (4, "2015-05-14 12:53","Name_42","Surname_42","12324352","1899-12-12","353861055775","Address_A_42","Address_B_42","Dublin 298","Brief summmary of patient nr 42");addByPriority(d);
        d = new AeRecord (5, "2015-05-15 12:54","Name_43","Surname_43","12324353","1899-12-13","353861055754","Address_A_43","Address_B_43","Dublin 305","Brief summmary of patient nr 43");addByPriority(d);
        d = new AeRecord (6, "2015-05-16 12:55","Name_44","Surname_44","12324354","1899-12-14","353861055776","Address_A_44","Address_B_44","Dublin 312","Brief summmary of patient nr 44");addByPriority(d);
        d = new AeRecord (7, "2015-05-17 12:56","Name_45","Surname_45","12324355","1899-12-15","353861055754","Address_A_45","Address_B_45","Dublin 319","Brief summmary of patient nr 45");addByPriority(d);
        d = new AeRecord (8, "2015-05-18 12:57","Name_46","Surname_46","12324356","1899-12-16","353861055777","Address_A_46","Address_B_46","Dublin 326","Brief summmary of patient nr 46");addByPriority(d);
        d = new AeRecord (9, "2015-05-19 12:58","Name_47","Surname_47","12324357","1899-12-17","353861055754","Address_A_47","Address_B_47","Dublin 333","Brief summmary of patient nr 47");addByPriority(d);
        d = new AeRecord (0, "2015-05-20 12:59","Name_48","Surname_48","12324358","1899-12-18","353861055778","Address_A_48","Address_B_48","Dublin 340","Brief summmary of patient nr 48");addByPriority(d);
        d = new AeRecord (8, "2015-05-21 13:00","Name_49","Surname_49","12324359","1899-12-19","353861055754","Address_A_49","Address_B_49","Dublin 347","Brief summmary of patient nr 49");addByPriority(d);
        d = new AeRecord (8, "2015-05-22 13:01","Name_50","Surname_50","12324360","1899-12-20","353861055779","Address_A_50","Address_B_50","Dublin 354","Brief summmary of patient nr 50");addByPriority(d);
        d = new AeRecord (1, "2015-05-23 13:02","Name_51","Surname_51","12324361","1899-12-21","353861055754","Address_A_51","Address_B_51","Dublin 361","Brief summmary of patient nr 51");addByPriority(d);
        d = new AeRecord (3, "2015-05-24 13:03","Name_52","Surname_52","12324362","1899-12-22","353861055780","Address_A_52","Address_B_52","Dublin 368","Brief summmary of patient nr 52");addByPriority(d);
        d = new AeRecord (4, "2015-05-25 13:04","Name_53","Surname_53","12324363","1899-12-23","353861055754","Address_A_53","Address_B_53","Dublin 375","Brief summmary of patient nr 53");addByPriority(d);
        d = new AeRecord (5, "2015-05-26 13:05","Name_54","Surname_54","12324364","1899-12-24","353861055781","Address_A_54","Address_B_54","Dublin 382","Brief summmary of patient nr 54");addByPriority(d);
        d = new AeRecord (6, "2015-05-27 13:06","Name_55","Surname_55","12324365","1899-12-25","353861055754","Address_A_55","Address_B_55","Dublin 389","Brief summmary of patient nr 55");addByPriority(d);
        d = new AeRecord (7, "2015-05-28 13:07","Name_56","Surname_56","12324366","1899-12-26","353861055782","Address_A_56","Address_B_56","Dublin 396","Brief summmary of patient nr 56");addByPriority(d);
        d = new AeRecord (8, "2015-05-29 13:08","Name_57","Surname_57","12324367","1899-12-27","353861055754","Address_A_57","Address_B_57","Dublin 403","Brief summmary of patient nr 57");addByPriority(d);
        d = new AeRecord (9, "2015-05-30 13:09","Name_58","Surname_58","12324368","1899-12-28","353861055783","Address_A_58","Address_B_58","Dublin 410","Brief summmary of patient nr 58");addByPriority(d);
        d = new AeRecord (0, "2015-05-31 13:10","Name_59","Surname_59","12324369","1899-12-29","353861055754","Address_A_59","Address_B_59","Dublin 417","Brief summmary of patient nr 59");addByPriority(d);
        d = new AeRecord (9, "2015-06-01 13:11","Name_60","Surname_60","12324370","1899-12-01","353861055784","Address_A_60","Address_B_60","Dublin 424","Brief summmary of patient nr 60");addByPriority(d);
        d = new AeRecord (8, "2015-06-02 13:12","Name_61","Surname_61","12324371","1899-12-02","353861055754","Address_A_61","Address_B_61","Dublin 431","Brief summmary of patient nr 61");addByPriority(d);
        d = new AeRecord (7, "2015-06-03 13:13","Name_62","Surname_62","12324372","1899-12-03","353861055785","Address_A_62","Address_B_62","Dublin 438","Brief summmary of patient nr 62");addByPriority(d);
        d = new AeRecord (6, "2015-06-04 13:14","Name_63","Surname_63","12324373","1899-12-04","353861055754","Address_A_63","Address_B_63","Dublin 445","Brief summmary of patient nr 63");addByPriority(d);
        d = new AeRecord (5, "2015-06-05 13:15","Name_64","Surname_64","12324374","1899-12-05","353861055786","Address_A_64","Address_B_64","Dublin 452","Brief summmary of patient nr 64");addByPriority(d);
        d = new AeRecord (4, "2015-06-06 13:16","Name_65","Surname_65","12324375","1899-12-06","353861055754","Address_A_65","Address_B_65","Dublin 459","Brief summmary of patient nr 65");addByPriority(d);
        d = new AeRecord (3, "2015-06-07 13:17","Name_66","Surname_66","12324376","1899-12-07","353861055787","Address_A_66","Address_B_66","Dublin 466","Brief summmary of patient nr 66");addByPriority(d);
        d = new AeRecord (4, "2015-06-08 13:18","Name_67","Surname_67","12324377","1899-12-08","353861055754","Address_A_67","Address_B_67","Dublin 473","Brief summmary of patient nr 67");addByPriority(d);
        d = new AeRecord (5, "2015-06-09 13:19","Name_68","Surname_68","12324378","1899-12-09","353861055788","Address_A_68","Address_B_68","Dublin 480","Brief summmary of patient nr 68");addByPriority(d);
        d = new AeRecord (2, "2015-06-10 13:20","Name_69","Surname_69","12324379","1899-12-10","353861055754","Address_A_69","Address_B_69","Dublin 487","Brief summmary of patient nr 69");addByPriority(d);
        d = new AeRecord (4, "2015-06-11 13:21","Name_70","Surname_70","12324380","1899-12-11","353861055789","Address_A_70","Address_B_70","Dublin 494","Brief summmary of patient nr 70");addByPriority(d);
        d = new AeRecord (5, "2015-06-12 13:22","Name_71","Surname_71","12324381","1899-12-12","353861055754","Address_A_71","Address_B_71","Dublin 501","Brief summmary of patient nr 71");addByPriority(d);
        d = new AeRecord (6, "2015-06-13 13:23","Name_72","Surname_72","12324382","1899-12-13","353861055790","Address_A_72","Address_B_72","Dublin 508","Brief summmary of patient nr 72");addByPriority(d);
        d = new AeRecord (7, "2015-06-14 13:24","Name_73","Surname_73","12324383","1899-12-14","353861055754","Address_A_73","Address_B_73","Dublin 515","Brief summmary of patient nr 73");addByPriority(d);
        d = new AeRecord (8, "2015-06-15 13:25","Name_74","Surname_74","12324384","1899-12-15","353861055791","Address_A_74","Address_B_74","Dublin 522","Brief summmary of patient nr 74");addByPriority(d);
        d = new AeRecord (9, "2015-06-16 13:26","Name_75","Surname_75","12324385","1899-12-16","353861055754","Address_A_75","Address_B_75","Dublin 529","Brief summmary of patient nr 75");addByPriority(d);

    }
    
    // helper for debug purposes
    public void print() {
        Node temp = head;
        System.out.println("Doubly Linked List: ");
        while (temp != null) {
            System.out.println(temp.data.getPriority()+" " + temp.data.getName() + ","+ temp.data.getPPS());
            temp = temp.next;
        }
        System.out.println();
    }
}


