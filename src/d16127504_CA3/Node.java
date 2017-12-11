package d16127504_CA3;

public class Node  {
   public AeRecord data;
   public Node next;
   public Node previous;
   
   public Node(AeRecord data){
      this.data = data;
      next = null;
      previous = null;
   }
   
}