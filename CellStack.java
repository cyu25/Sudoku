/* File: CellStack.java
 * Author: Claire Yu
 * Course: CS231, Professor Al Madi, Section B
 * October 4, 2022
 */
public class CellStack
{
    //node class
   private class Node
   {
        Cell cell;
        Node next;

        public Node(Cell cell)
        {
            this.cell = cell;
            this.next = null;
        }
   } 

   //variables
   private Node head;
   private int size;

   /*
    * initialize the stack's fields
    */
   public CellStack()
   {
        head = null;
        size = 0;
   }

   /*
    * push c onto the stack
    */
   public void push(Cell c)
   {
        Node newNode = new Node(c);
        newNode.next = this.head;
        this.head = newNode;
        size++;
   }

   /*
    * return the top Cell on the stack
    */
    public Cell peek()
    {
        return this.head.cell;
    }

    /*
     * remove and return the top element from the stack
     * return null if the stack is empty
     */
    public Cell pop()
    {
        //hold a temp variable
        Node temp = this.head;

        if(this.head == null)
        {
            return null;
        }
        this.head = this.head.next;
        size--;

        return temp.cell;

        
    }

    /*
     * return the number of elements in the stack
     */
    public int size()
    {
        return this.size;
    }

    /*
     * retun true if the stack is empty
     */
    public boolean empty()
    {
        if (this.head == null)
        {
            return true;
        }
        return false;
    }


}
