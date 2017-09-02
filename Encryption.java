import java.util.Scanner;
import java.io.IOException;
import java.io.File;

// Program that encrypts a message using an algorithm that involves a deck of 28 cards
// and generating keystream values corresponding to each character in the message
// Uses a doubly-linked circular list to keep track of the "cards", which are represented by numbers 1-28
// Jokers are represented by cards 27 and 28
public class Deck {
   
    // Defines a node class which will be used in double linked list
   private class Node {
      public int data;
      public Node next;
      public Node previous;
       
       // @ param the data contained in the node, the node after it, and the node before it
      public Node(int data, Node next, Node previous) {
         this.data = data;
         this.next = next;
         this.previous = previous;
      }
   }
   
   // Instance Variables 
   private String file;
   private Node head;
   private Node tail;

   // Constructor that defines a doubly circular linked list
   // @param the name of a file containing the "cards" (ie list of 28 numbers)
   public Deck(String filename) {
      head = null; // initializes what head points to
      Node current = head; // placeholder that points to new nodes as they're added to the list
      
      try {
         Scanner input = new Scanner(new File(filename)); // reads file containing "deck" numbers
         
         while (input.hasNextInt()) {
            int symbol = input.nextInt();
            
            // adds the first node containing the first number from the file
            if (current == null) {
               Node added = new Node(symbol, null, null);
               head = added; // head pointer is updated to point to the first thing
               current = head; // current placeholder updated
               tail = current; // the tail is updated to reflect the last node added
            } 
            
            else {
               Node added = new Node(symbol, null, current); 
               current.next = added;
               current = current.next;
               tail = current;
            }
            
            tail = current; // tail is set to reflect the very last item added
            tail.next = head; // list loops around back to head
            head.previous = tail; // head loops around back to tail
         }
      } 
      
      catch (IOException e) {
         System.out.println("File DNE");
      }
   }
   
   // @param an integer greater than 1
   // brings out n numbers from the list
   public void print(int n) {
      if (n<0){
         throw new IndexOutOfBoundsException();
      }
       
      else {
         Node tracker = head;
         int numLoops = 0; // keeps track of how many times we've looped through list
         String list = ""; // displays the list by iterating through while loop
         
         while (numLoops<n) {
            list = list + tracker.data + " "; // retrieves data at node and adds it to list
            numLoops += 1; 
            tracker = tracker.next; // updates tracker to point to next item in list
         }
         System.out.println(list);
      }   
   }
   
   // @param an integer greater than 1
   // prints out n integers from the list, starting at the end of the list
   public void printBackwards(int n) {
      if (n<0) {
         throw new IndexOutOfBoundsException();
      }
      else {
         Node tracker = tail;
         int numLoops = 0;
         String list = "";
         while (numLoops<n) {
            list = list + tracker.data + " ";
            numLoops += 1;
            tracker = tracker.previous;
         }
         System.out.println(list);      
      }
   }

    // Step 1: swap 27 one place to the right
    // Step 2: swap 28 two places to the right
    // Step 3: do triple cut-- move everything before first joker to the end,
    //   move everything after the second joker to the beginning
    // Step 4: take value of last number, count down from beginning of list that many times,
    //   move that group of cards to end of the list, then move the previous tail back to end
    // Step 5: take value of 1st num, count down that many times +1, return data at that node
    // @return the next key value to use in encryption
   public int nextKeyValue() {
   // Step 1
      Node tracker = head;
      while (tracker.data != 27) { 
         tracker = tracker.next;
      }
      Node nextTracker = tracker.next; // Node after the node containing 27
      int tempData = tracker.data; // Store 27 in memory
      tracker.data = nextTracker.data; // Replace 27 with num after it
      nextTracker.data = tempData; // Replace num after 27 with 27   
    // Step 2
      tracker = head; // reset tracker
      for (int i=0; i < 2; i++){ // Will do 2 swaps
         while (tracker.data != 28){ // Step 2
            tracker = tracker.next;
         }
         
         nextTracker = tracker.next; // Node after the node containing 28
         tempData = tracker.data; // Store 28 in memory
         tracker.data = nextTracker.data; // Replace 28 with num after it
         nextTracker.data = tempData; // Replace num after 28 with 28     
      }
   // Step 3
      tracker = head; // reset tracker
      while (tracker.data != 28 && tracker.data != 27) {
         tracker = tracker.next;
      }
      Node tempHead = head; // temporarily store the original head 
      Node tempMem = tracker; // store 1st joker Node in memory
      tracker = tail; // pointer starting at the tail
      while (tracker.data != 27 && tracker.data != 28) { // loop backwards from the tail until you hit first joker
         tracker = tracker.previous;
      }
      if (head.data == 27 || head.data == 28) { // take care of case when the first item is joker
         head = tracker.next;
         tail = tracker;
      }
      else if (tail.data == 27 || tail.data == 28) { // take care of case when last item is joker
         head = tempMem;
         tail = tempMem.previous;
      }
      else if (head.data == 28 || head.data == 27 || head.data == 28 || head.data == 27){ // take care of case when first or last item is joker
         head = head;
         tail = tail;
      }
      else{
         head = tracker.next; // update head to point to the first number after 2nd joker
         tracker.next = tempHead; // point the node containing 27 to the original head
         tail.next = tempMem; // point the original tail to 28
         tail = tempMem.previous; // update tail
         tail.next = head; 
      }
    // Step 4
      Node insertHere = tail.previous; // node right before last number
      tracker = head;
      if (tail.data == 28){ // Scase when last num is 28, only go up to 27
         for (int i=0; i< tail.data-1; i++){
            tracker = tracker.next;
         }
      }
      for (int i=0; i<tail.data; i++){ // find the node in the index of the value of last num
         tracker = tracker.next;
      }
      if (tail.data != 28){
         Node before = tracker.previous; // node right before the tracker
         insertHere.next = head; // point num before last to first num in list
         before.next = tail; 
         tail = before.next; // update tail
         head = tracker; // update head
      }
    // Step 5
      tracker = head; // reset tracker
      for (int i=0; i < head.data; i++){ // find the node in the index after the value of the head
         tracker = tracker.next;
      }
      return tracker.data;  
   }
}
     
  
   
