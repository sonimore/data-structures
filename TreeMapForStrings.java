import java.util.*;
import java.util.ArrayList;
// Maps movie titles to plots using a binary search tree
class TreeMapForStrings
{
   // Object that contains key and value pair
   private class Entry {
      private String key;
      private String value;
      
      public Entry(String key, String value) {
         this.key = key;
         this.value = value;
      }
      
      public String entryValue()
      {
         return this.value;
      }
      
      public String entryKey()
      {
         return this.key;
      }
   }
   
   // node class containing Entry objects (with key-value pairs)
   private static class Node<Entry> {
      public Entry data;
      public Node<Entry> left;
      public Node<Entry> right;
    
      public Node(Entry data, Node<Entry> left, Node<Entry> right) {
         this.data = data;
         this.left = left;
         this.right = right;
      }
   }   
    
   private Node<Entry> root; // global variable

   
   public TreeMapForStrings(){
      root = null;
   }
   
   // method that recursively calls add on data and subroot
   public void add(Entry data) {
      root = add(data, root);
   }
   
   // helper method that recursively adds nodes containing Entry objects
   public Node<Entry> add(Entry data, Node<Entry> subroot) {
      if (subroot == null) {
         subroot = new Node<Entry>(data,null,null);
      } 
      else if (data.entryKey().compareTo(subroot.data.entryKey()) < 0) {
         subroot.left = add(data, subroot.left);
      }  
      else if (data.entryKey().compareTo(subroot.data.entryKey()) > 0) {
         subroot.right = add(data, subroot.right);
      } 
      else {
         subroot.data = data;
      }
   
      return subroot;
   }      
   
   // add a key with a value to the tree
   public void put(String key, String value)
   {
      Entry anEntry = new Entry(key, value);
      add(anEntry);
   }
   
   // recursively calls helper method to retrieve value associated with key
   public String getValue(String key){
      return getValue(key, root);
   }
      
    // returns the value associated with a particular key
   public String getValue(String key, Node<Entry> subroot)
   {
      // empty tree
      if (root == null) { 
         return "No Entries";
      }
      // keep searching...
      else if (key.compareTo(subroot.data.entryKey()) < 0) {
         return getValue(key, subroot.left);
      }
      // keep searching...
      else if (key.compareTo(subroot.data.entryKey()) > 0){
         return getValue(key, subroot.right);
      }
      // once you find the key, return the value associated with it
      else if (key.compareTo(subroot.data.entryKey()) == 0){
         return subroot.data.entryValue();
      }
      // couldn't find the key
      else{
         return "Not in tree.";
      }
   }
   
   // @ param prefix to search for within keys in BST
   // calls recursive method that returns array containing keys with prefix
   ArrayList keys = new ArrayList();
   public ArrayList<String> getKeysForPrefix (String prefix){
      return getKeysForPrefix(prefix, root);
   }
   
   // @ param prefix to search for and a Node to search within
   // @ return array with keys that contain prefix
   public ArrayList<String> getKeysForPrefix(String prefix, Node<Entry> subroot) {
   
      if (subroot == null) {
         return keys;
      }
   
      if (subroot.data.entryKey().startsWith(prefix)) {
         keys.add(subroot.data.entryKey());
      }
      
      getKeysForPrefix(prefix, subroot.right); 
      getKeysForPrefix(prefix, subroot.left);
   
      return keys;
   }
   // test code
   public static void main(String[] args){
      TreeMapForStrings tree = new TreeMapForStrings();
      tree.put("abc", "a123");
      tree.put("b", "b1");
      tree.put("a", "a1");
      tree.put("a", "a2");
      tree.put("c", "c1");
      tree.put("abc", "a123");
   
      System.out.println(tree.getValue("b"));
      System.out.println(tree.getValue("a"));
      System.out.println(tree.getValue("c"));
      System.out.println(tree.getValue("abc"));
      tree.getKeysForPrefix("a");
      tree.getKeysForPrefix("b");
      tree.getKeysForPrefix("c");
   }
}