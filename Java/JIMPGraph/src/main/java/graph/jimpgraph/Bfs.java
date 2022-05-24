package graph.jimpgraph;

import java.util.LinkedList;
import java.util.Queue;

public class Bfs {

    static Queue<Integer> queue;
    static int[] vertexState;

    public static int BFS(Graph graph, int startingVertex){
        int n = graph.columns*graph.rows;
        queue = new LinkedList<Integer>;
        vertexState = new int[n];
        for(int i=0;i<n;i++){
            vertexState[n] = 0;
        }
        queue.add(startingVertex);
        int currentVertex;
        while(!queue.isEmpty()){
            currentVertex = queue.remove();
            for (int j = 0; j < graph.directions; j++)
            {
                if (vertexState[graph.vertex[currentVertex][j]] == 0
                        && graph.vertex[currentVertex][j] != -1)
                {
                    queue.add(graph.vertex[currentVertex][j]);
                    vertexState[graph.vertex[currentVertex][j]] = 1; // 1 - added to queue
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

    public static int isConst (Graph graph){
        int isGraphConst = 1;
        for (int i=0; i<graph.rows*graph.columns;i++){
            if(BFS(graph,i) == 0)
            {
                isGraphConst = 0;
                break;
            }
        }
        return isGraphConst;
    }
    public static String isConstCommunicate (Graph graph){
        if(isConst(graph) == 1) return "Graf jest spójny!";
        else return "Graf jest niespójny!";
    }
}
