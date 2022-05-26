package graph.jimpgraph;

import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Comparator;

class TheComparator implements Comparator<Integer> {
    public int compare(Integer a, Integer b)
    {
        if (Dijkstra.pathLength[a]>Dijkstra.pathLength[b]){
            return 1;
        }
        else if (Dijkstra.pathLength[a]<Dijkstra.pathLength[b]){
            return -1;
        }
        else if (Dijkstra.pathLength[a]==Dijkstra.pathLength[b]){
            return 0;
        }
        else return -99;
    }
}


public class Dijkstra {
    final static double INFINITY = 1000000;
    static PriorityQueue<Integer> pQueue;
    static double[] pathLength;


    public LinkedList<Integer> dijkstra (Graph graph, int start, int destination) {

        LinkedList<Integer> path;
        int n = graph.getRows()*graph.getColumns();
        path = new LinkedList<Integer>();
        pQueue = new PriorityQueue<Integer>(new TheComparator());
        int[] ancestor = new int[n];
        int[] wasVisited = new int[n];
        pathLength  = new double[n];
        int currentVertex;
        for(int i=0;i<n;i++){
            ancestor[i]= -1;
            wasVisited[i] = 0;
            pathLength[i] =INFINITY;
        }
        pathLength[start] = 0;
        pQueue.add(start);

        for (int i=0; i < n ; i++){

            if (!pQueue.isEmpty()){
                currentVertex = pQueue.remove();

            }
            else
                break;

            for (int j=0; j<graph.directions; j++){

                if(graph.vertex[currentVertex][j]!=-1 &&
                        (graph.weight[currentVertex][j] +pathLength[currentVertex]<pathLength[graph.vertex[currentVertex][j]]))
                {
                    pathLength[graph.vertex[currentVertex][j]] = graph.weight[currentVertex][j] +pathLength[currentVertex];
                    ancestor[graph.vertex[currentVertex][j]] = currentVertex;
                    if(wasVisited[graph.vertex[currentVertex][j]]==0)
                    {
                        pQueue.add(graph.vertex[currentVertex][j]);
                        wasVisited[graph.vertex[currentVertex][j]]=1;
                    }
                }
            }
        }
        if(pathLength[destination]==INFINITY)
        {
            path.clear();
            return null;
        }


        int tmp = destination;
        path.add(destination);
        while(ancestor[tmp]!=-1)
        {

            path.addFirst(ancestor[tmp]);
            tmp = ancestor[tmp];
        }
        return path;

    }


}
