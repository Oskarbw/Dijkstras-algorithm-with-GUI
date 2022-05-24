package graph.jimpgraph;

import java.util.Random;

public class Generator {
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
}
