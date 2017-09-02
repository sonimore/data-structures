import java.util.Scanner;
import java.io.File;
import java.io.IOException;
import java.util.*;
public class TreeSearchEngine{
   public static void main(String[] args) throws IOException {
      String filename = args[2];
      System.out.println("filename: " + filename);
      long firstTime = System.nanoTime(); 
      System.out.println(firstTime);
      TreeMapForStrings tree = new TreeMapForStrings();
      Scanner input = new Scanner(new File(filename));
      String key = "";
      String value = "";
      boolean tracker = false;
      long secTime = 0;;
      while (input.hasNextLine()){
         
         String string = input.nextLine();
         secTime = System.nanoTime(); 
      
        
        // when you find movie title, set it to the key without MV:
         if (string.startsWith("MV:")){
            string = string.substring(4, string.length());
            key = string;
         }
         
        // when you find plot, set a tracker to true so that you can concatenate correct strings for value
         if (string.startsWith("PL:") && tracker == false){
            tracker = true;
         }
         
         // concatenate strings beginning with plot for value
         if (tracker == true && string.startsWith("PL:")){
            value += string.substring(3, string.length());
         }  
           
         // makes sure that you stop concatenating when PL: stops and puts the key, value pair into hash table
         if (tracker == true && !string.startsWith("PL:")){
            tracker = false;
            tree.put(key,value);
            value = "";   
         }
         
      }
      System.out.println("Reading file: ");
      System.out.println(secTime - firstTime);
     
      // if the last line starts with PL:, still add it to hash table
      if (tracker == true) {
         tree.put(key, value);
      }
      long thirdTime = System.nanoTime();
      System.out.println(thirdTime - secTime);
           
      String n = "";
      // search for a movie and return its plot
      while (n != "####") {              
         Scanner userInp = new Scanner(System.in);
         System.out.println("Enter a movie to read its plot summary: ");
         n = userInp.nextLine();
         ArrayList<String> keys = tree.getKeysForPrefix(n); // create array with keys containing prefix
         for (int i=0; i < keys.size(); i++){  // search for keys in array and values
            System.out.println(keys.size());
            String tempKey = keys.get(i);
            System.out.println(tempKey);
            System.out.println(tree.getValue(tempKey));
         }
      }        
   }

}