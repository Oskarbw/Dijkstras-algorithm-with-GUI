#include <stdlib.h>
#include <time.h>

void findNeighbours(t_pair *graph[][], int vertex, int rows, int cols, double minWeight, double maxWeight, int decimalDigits){
	int currentNumOfNeighbours = 0;
	
	if(vertex%cols!=0){ //if vertex isn't on the left edge of graph, then find his left neighbour
		graph[vertex][currentNumOfNeighbours]->vertex = (vertex-1);
		double randomValue = ((srand()/RAND_MAX)*(maxWeight-minWeight))+minWeight;
		// ^ generating number between minWeight and maxWeight
		for (int i=0;i<(15-decimalDigits);i++){ //rounding double value to desired number of digits
		randomValue= round(randomValue * 10) / 10;
		}
			
		currentNumOfNeighbours++;
	}
	
	if(vertex%cols!=(cols-1)){ //if vertex isn't on the right edge of graph, then find his right neighbour
		graph[vertex][currentNumOfNeighbours]->vertex = (vertex+1);
		graph[vertex][currentNumOfNeighbours]->weight = ((srand()/RAND_MAX)*(maxWeight-minWeight))+minWeight;
		// ^ generating number between minWeight and maxWeight
		currentNumOfNeighbours++;
	}
	
	if(vertex>=cols){ //if vertex isn't on the upper edge of graph, then find his upper neighbour
		graph[vertex][currentNumOfNeighbours]->vertex = (vertex+1);
		graph[vertex][currentNumOfNeighbours]->weight = ((srand()/RAND_MAX)*(maxWeight-minWeight))+minWeight;
		// ^ generating number between minWeight and maxWeight
		currentNumOfNeighbours++;
	}
	
	if(vertex<(rows*cols)-cols){ //if vertex isn't on the lower edge of graph, then find his lower neighbour
		graph[vertex][currentNumOfNeighbours]->vertex = (vertex+1);
		graph[vertex][currentNumOfNeighbours]->weight = ((srand()/RAND_MAX)*(maxWeight-minWeight))+minWeight;
		// ^ generating number between minWeight and maxWeight
		currentNumOfNeighbours++;
	}
}
void generateRandWeightMode (int rows, int cols, double minWeight, double maxWeight, int decimalDigits){
	srand(time(NULL));
	int n = rows *cols; //number of vertexes
	t_pair *graph[n][4]; //creating structure to contain graph; second dimension is 4 because that's the max number of vertex's neighbours
	
	for (int i=0;i<n;i++){ //initially, changing all "vertex" variables to -1, which means "no neighbour"
		for (int j=0; j<4;j++){
			graph[i][j]->vertex = -1;
		}
	}
	
	for (int i=0;i<n;i++){ // for each vertex - find his ALL neighbours and generate weights
		findNeighbours(graph, i, rows, cols);
	}
	
	
	
	
	
	
















void generateAllRandMode();
void generateConMode();

void randomlyFindNeighbours();