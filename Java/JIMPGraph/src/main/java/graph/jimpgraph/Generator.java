package graph.jimpgraph;

import java.util.Random;

import static graph.jimpgraph.Bfs.BFS;

public class Generator {
    final static int probability = 7;
    void findNeighbours(Graph graph){
        for(int vertexCount=0; vertexCount < graph.columns*graph.rows; vertexCount++) {
            int currentNumOfNeighbours = 0;
            Random rand = new Random();
            if (vertexCount % graph.columns != 0) {
                graph.vertex[vertexCount][currentNumOfNeighbours] = (vertexCount - 1);
                graph.weight[vertexCount][currentNumOfNeighbours] = rand.nextDouble() * (graph.max - graph.min) + graph.min;
                currentNumOfNeighbours++;
            }
            if (vertexCount % graph.columns != (graph.columns - 1)) {
                graph.vertex[vertexCount][currentNumOfNeighbours] = (vertexCount + 1);
                graph.weight[vertexCount][currentNumOfNeighbours] = rand.nextDouble() * (graph.max - graph.min) + graph.min;
                currentNumOfNeighbours++;
            }
            if (vertexCount >= graph.columns) {
                graph.vertex[vertexCount][currentNumOfNeighbours] = (vertexCount - graph.columns);
                graph.weight[vertexCount][currentNumOfNeighbours] = rand.nextDouble() * (graph.max - graph.min) + graph.min;
                currentNumOfNeighbours++;
            }

            if(vertexCount < (graph.rows * graph.columns) - graph.columns){
                graph.vertex[vertexCount][currentNumOfNeighbours] = (vertexCount + graph.columns);
                graph.weight[vertexCount][currentNumOfNeighbours] = rand.nextDouble() * (graph.max - graph.min) + graph.min;
            }
        }
    }

    void generateRandWeightMode(Graph graph){
        findNeighbours(graph);
    }

    void generateAllRandMode(Graph graph){
        Random rand = new Random();
        generateRandWeightMode(graph);
        for(int i=0; i < (graph.rows * graph.columns); i++){
            for(int j=0; j< graph.directions; j++){
                if (graph.vertex[i][j] == graph.noConnection){
                    continue;
                }
                if(rand.nextInt(10) > probability){
                    graph.vertex[i][j] = graph.noConnection;
                }
            }
        }
    }

    void generateConMode(Graph graph){
        do{
            generateAllRandMode(graph);
        }while(BFS(graph) == 0);
    }
}
