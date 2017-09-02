import java.util.*;
import java.io.IOException;
import java.io.File;

// a class that uses a word list to compute solutions to changelings
// a changeling is a pair of words with the same number of letters
public class Changelings
{
   // this findNeighbors method finds all the neighbors of the String word
   // and store them in a hashset from an arrayList
   // @param: word is compared to strings in array; copy stores all words in file
   // @return: set of adjacent strings
   public static Set<String> findNeighbors(String word, ArrayList<String> copy)
   {
      Set<String> connections = new HashSet<>();
      for (String s2: copy) {
         if (!s2.equals(word))
         {
            if (word.substring(0, 1).compareTo(s2.substring(0,1)) != 0)
            {
               if (word.substring(1, word.length()).compareTo(s2.substring(1, s2.length())) == 0) {
                  connections.add(s2);
               }
            }
            else if (word.substring(1,2).compareTo(s2.substring(1,2)) != 0)
            {
               if (word.substring(2, word.length()).compareTo(s2.substring(2, s2.length())) == 0) {
                  connections.add(s2);
               }
            }
            else{
               connections.add(s2);
            }
         }
      }
      return connections;
   }
   
   public static void main(String[] args) throws IOException
   {
      if (args.length != 3)
      {
         System.out.println("Incorrect command-line argument. Exit program.");
      }
      else
      {
         // create a graph that contains all the words in the file
         Map<String, Set<String>> graph = new HashMap<>();
         Scanner input = new Scanner(new File(args[0]));
         ArrayList<String> array = new ArrayList<String>();
         while (input.hasNext())
         {
            array.add(input.next());
         }
         for (String s : array)
         {
            Set<String> connections = findNeighbors(s, array);
            graph.put(s, connections);
         }
         
         // find the shortest path between args[2] and args[1]
         // Since we will print the path backward,
         // we make args[2] the start node and args[1] the end node
         Map<String, String> prevNodes = new HashMap<>();
         ArrayList<String> visited = new ArrayList<String>();
         visited.add(args[2]);
         boolean done = false;
         Queue<String> q = new ArrayDeque<String>();
         q.offer(args[2]);
         while (!q.isEmpty() && !done)
         {
            String currentNode = q.remove();
            Set<String> set = graph.get(currentNode);
            if (set != null)
            {
               for (String s : set)
               {
                  if (!visited.contains(s))
                  {
                     visited.add(s);
                     prevNodes.put(s, currentNode);
                     q.offer(s);
                  
                     if (s.equals(args[1]))
                     {
                        done = true;
                     }
                  }
               }
            }
         }
         // print the path
         String currentNode = args[1];
         if (prevNodes.get(currentNode) == null)
         {
            System.out.println("No solution...");
         }
         else
         {
            while (currentNode != null)
            {
               System.out.println(currentNode);
               currentNode = prevNodes.get(currentNode);
            }
         }
      }
   }
}