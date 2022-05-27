package graph.jimpgraph;

import java.util.Random;

public class Generator {
    final static int probability = 7;
    void findNeighbours(Graph graph){
        for(int vertexCount=0; vertexCount < graph.getColumns()*graph.getRows(); vertexCount++) {
            int currentNumOfNeighbours = 0;
            Random rand = new Random();
            if (vertexCount % graph.getColumns() != 0) {
                graph.vertex[vertexCount][currentNumOfNeighbours] = (vertexCount - 1);
                graph.weight[vertexCount][currentNumOfNeighbours] = rand.nextDouble() * (graph.getMaxWeight() - graph.getMinWeight()) + graph.getMinWeight();
                currentNumOfNeighbours++;
            }
            if (vertexCount % graph.getColumns() != (graph.getColumns() - 1)) {
                graph.vertex[vertexCount][currentNumOfNeighbours] = (vertexCount + 1);
                graph.weight[vertexCount][currentNumOfNeighbours] = rand.nextDouble() * (graph.getMaxWeight() - graph.getMinWeight()) + graph.getMinWeight();
                currentNumOfNeighbours++;
            }
            if (vertexCount >= graph.getColumns()) {
                graph.vertex[vertexCount][currentNumOfNeighbours] = (vertexCount - graph.getColumns());
                graph.weight[vertexCount][currentNumOfNeighbours] = rand.nextDouble() * (graph.getMaxWeight() - graph.getMinWeight()) + graph.getMinWeight();
                currentNumOfNeighbours++;
            }

            if(vertexCount < (graph.getRows() * graph.getColumns()) - graph.getColumns()){
                graph.vertex[vertexCount][currentNumOfNeighbours] = (vertexCount + graph.getColumns());
                graph.weight[vertexCount][currentNumOfNeighbours] = rand.nextDouble() * (graph.getMaxWeight() - graph.getMinWeight()) + graph.getMinWeight();
                currentNumOfNeighbours++;
            }
        }
    }

    void generateRandWeightMode(Graph graph){
        findNeighbours(graph);
    }

    void generateAllRandMode(Graph graph){
        Random rand = new Random();
        generateRandWeightMode(graph);
        for(int i=0; i < (graph.getRows() * graph.getColumns()); i++){
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
        }while(Bfs.BFS(graph,0) == 0);
    }
}
