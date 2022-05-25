package graph.jimpgraph;

import java.util.LinkedList;
import java.util.Queue;

public class Bfs {

    static LinkedList<Integer> queue;
    static int[] vertexState;

    public static int BFS(Graph graph, int startingVertex){
        int n = graph.columns*graph.rows;
        queue = new LinkedList<Integer>();
        vertexState = new int[n];
        for(int i=0;i<n;i++){
            vertexState[i] = 0;
        }
        queue.add(startingVertex);
        int currentVertex;
        while(!queue.isEmpty()){
            currentVertex = queue.removeFirst();
            for (int j = 0; j < graph.directions; j++)
            {
                if (graph.vertex[currentVertex][j] != -1) {
                    if (vertexState[graph.vertex[currentVertex][j]] == 0) {
                        queue.add(graph.vertex[currentVertex][j]);
                        vertexState[graph.vertex[currentVertex][j]] = 1; // 1 - added to queue
                    }
                }
            }
            vertexState[currentVertex] = 2; // visited


        }
        int haveAllVertexesBeenVisited = 1;
        for (int i = 0; i < n; i++)
        {
            if (vertexState[i] != 2)
            {
                haveAllVertexesBeenVisited = 0;
                break;
            }
        }
        return haveAllVertexesBeenVisited;

    }


    public static String isConstCommunicate (Graph graph){
        if(BFS(graph,0) == 1) return "Graf jest spójny!";
        else return "Graf jest niespójny!";
    }
}
