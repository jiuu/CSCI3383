import javafx.util.Pair;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;

import java.util.*;
import java.util.Map.Entry;
public class dijkstraAlgorithm {

    //returns the neighbors for the given vertex in the given graph
    private static ArrayList<String> getNeighbors(String s, SimpleDirectedWeightedGraph<String,DefaultWeightedEdge> graph){
        String[] vSet = (graph.vertexSet()).toArray(new String[(graph.vertexSet().size())]);
        ArrayList<String> result = new ArrayList<String>();
        for (String v : vSet) {
            if (graph.containsEdge(s,v)) {
                result.add(v);
            }
        }
        return result;
    }

    //takes a map and gives us the key for the smallest value, used this because of a lack of a binary heap object, will probably hurt runtime
    private static String getMinValKey(Map<String,Integer> map) {
        Entry<String,Integer> min = null;
        for (Entry<String,Integer> entry : map.entrySet()) {
            if(min == null || min.getValue() > entry.getValue()) {
                min = entry;
            }
        }
        return min.getKey();
    }


    public static Pair<HashMap<String, Double>, HashMap<String, String>> calculatePath(SimpleDirectedWeightedGraph<String,DefaultWeightedEdge> graph, String source) { //returns ArrayList of mindist and paths
        int dist[] = new int[(graph.vertexSet()).size()]; //will hold shortest distance from source to i
        String path[] = new String[(graph.vertexSet()).size()]; //will hold shortest path from source to i
        //this block populates the array of distances to infinity and the path to -1
        for (int i = 0; i < dist.length; i++) {
        	//insert(Q,(all,inf))
        	//there is no infinity integer so as long as the weights do not exceed 100000, this should suffice
            dist[i] = Integer.MAX_VALUE - 100000; 
            //prev(all)<- null
            path[i] = null;
        }
        //insert(Q,(s,0)), but we store the distances in an array
        dist[(Integer.parseInt(source.substring(1))-1)] = 0;
        /*we use a HashMap to keep track of which nodes have been visited and the minimum distances since java
        doesn't have a PriorityQueue or Fibonacci Tree implementation*/
        //Q = makePQ()     (with a map instead of PriorityQueue)
        HashMap<String, Integer> M = new HashMap<>();
        //gives us our vertex set in an integer array
        String[] vertexSet = (graph.vertexSet()).toArray(new String[(graph.vertexSet()).size()]);
        //populates our HashMap with the distances indexed by their vertices, assumes vertices go in order starting from 1
        for (int i = 0; i < dist.length; i++) {
            M.put(vertexSet[i],dist[i]);
        }
        //for i = 1 to |V| do 
        while (!M.isEmpty()) {
            String minKey = getMinValKey(M);
            int minKeyInt = Integer.parseInt(minKey.substring(1));
            
            //(v,dist(s,v)) = extractMin(Q)
            M.remove(minKey); 
            //for each u in Adj(v) do
            for (String v : getNeighbors(minKey, graph)) {
                int vInt = Integer.parseInt(v.substring(1));
                DefaultWeightedEdge edge = graph.getEdge(minKey,v);
                int weight = (int) graph.getEdgeWeight(edge);
                //checks if path to neighboring node is shorter than what it has currently and updates if so
                //if (dist(s, v) + l(v, u) < dist(s, u)) then
                if (M.containsKey(v) && dist[vInt-1] > (dist[minKeyInt - 1] + weight)) {
                	//decreaseKey(Q, (u, dist(s, v) l(v, u)))
                    dist[vInt-1] = dist[minKeyInt-1] + weight;
                    //prev(u) = v
                    path[vInt-1] = minKey;
                    //updates value in HashMap with new weight 
                    M.put(v, dist[minKeyInt-1] + weight);
                }

            }
            //System.out.println("done");

        }
        //our resulting arraylist gives, for each vertex v, the distance then previous node
        ArrayList<String> result2 = new ArrayList<String>();
        ArrayList<Integer> result = new ArrayList<Integer>();
        HashMap<String, Double> distResults = new HashMap<>();
        HashMap<String, String> pathResults = new HashMap<>();
        for (int i = 0; i < dist.length; i++) {
            result.add(dist[i]);
            result2.add(path[i]);
            distResults.put(vertexSet[i], (double)dist[i]);
            pathResults.put(vertexSet[i], path[i]);
        }
        System.out.println(result2);
        System.out.println(result);
        Pair<HashMap<String, Double>, HashMap<String, String>> pair = new Pair<HashMap<String, Double>, HashMap<String, String>>(distResults, pathResults);

        return pair;
    }
    public static void main(String[] args) {
        SimpleDirectedWeightedGraph<String, DefaultWeightedEdge> g = new SimpleDirectedWeightedGraph<>(DefaultWeightedEdge.class);
        g.addVertex("v1");
        g.addVertex("v2");
        g.addVertex("v3");
        g.addVertex("v4");
        g.addVertex("v5");
        DefaultWeightedEdge e1 = g.addEdge("v4", "v3");
        g.setEdgeWeight(e1, 18);

        DefaultWeightedEdge e2 = g.addEdge("v5", "v2");
        g.setEdgeWeight(e2, 7);

        DefaultWeightedEdge e3 = g.addEdge("v1", "v5");
        g.setEdgeWeight(e3, 12);

        DefaultWeightedEdge e4 = g.addEdge("v2", "v4");
        g.setEdgeWeight(e4, 8);

           /* g.addVertex("v1");
        	g.addVertex("v2");
        	g.addVertex("v3");
        	g.addVertex("v4");
        	g.addVertex("v5");
            DefaultWeightedEdge e1 = g.addEdge("v1", "v2");
           	g.setEdgeWeight(e1, -5);

           	DefaultWeightedEdge e2 = g.addEdge("v2", "v3");
           	g.setEdgeWeight(e2, -7);

           	DefaultWeightedEdge e3 = g.addEdge("v3", "v4");
           	g.setEdgeWeight(e3, -1);

           	DefaultWeightedEdge e4 = g.addEdge("v4", "v1");
           	g.setEdgeWeight(e4, 20);

           	DefaultWeightedEdge e5 = g.addEdge("v1", "v5");
           	g.setEdgeWeight(e5, 1); */
        
        /* g.addVertex("v1");
        	g.addVertex("v2");
        	g.addVertex("v3");
        	g.addVertex("v4");
        	g.addVertex("v5");
         	DefaultWeightedEdge e1 = g.addEdge("v1", "v2");
        	g.setEdgeWeight(e1, -5);

        	DefaultWeightedEdge e2 = g.addEdge("v2", "v3");
        	g.setEdgeWeight(e2, 12);

        	DefaultWeightedEdge e3 = g.addEdge("v3", "v4");
        	g.setEdgeWeight(e3, 4);

        	DefaultWeightedEdge e4 = g.addEdge("v4", "v1");
        	g.setEdgeWeight(e4, -10);

        	DefaultWeightedEdge e5 = g.addEdge("v2", "v4");
        	g.setEdgeWeight(e2, 17);

        	DefaultWeightedEdge e6 = g.addEdge("v1", "v3");
        	g.setEdgeWeight(e2, 2);
         	*/
        



        System.out.println(g);
        System.out.println(calculatePath(g,"v1"));
    }

}