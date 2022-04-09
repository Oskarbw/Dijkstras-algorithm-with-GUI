#include <stdlib.h>
#include <stdio.h>
#include "generator.h"
#include "bfs.h"
#include "plik.h"

#define INFINITY 99999

typedef struct tHeapQueue{
	int* cells;
	int end;
} tHeapQueue;


//int popFromHeapQueue(int* priorityQueue);

//void addToHeapQueue(int* priorityQueue, double value);

//int isHeapQueueEmpty(int* priorityQueue);


void dijkstra (t_pair** graph, int pairN, int* pairs, int rows , int cols){
	int n = rows*cols;
	int* ancestor = malloc (sizeof(int) * n);
	double* shortestPath = malloc (sizeof(double) * n);
	int* wasVisited = malloc(sizeof(int) * n);
	double* results = malloc(sizeof(double) * pairN);
	
	for (int i=0;i<pairN;i++){
		int start = pairs[i*2];
		int destination = pairs[(i*2)+1];
		
		for (int j=0;j<n;j++) shortestPath[j] = INFINITY;
		shortestPath[start] = 0;
		
		for (int j=0;j<n;j++) ancestor[j] = -1;
		for (int j=0;j<n;j++) wasVisited[j]=0;
		
		
		double minimum = 0;
		int currentVertex = start; 
		for(int k=0;k<n;k++){
			minimum = INFINITY;
			for (int j=0;j<n;j++){
				if (shortestPath[j]<minimum&&wasVisited[j]==0){
					minimum = shortestPath[j];
					currentVertex = j;
				}
			}
			//printf("currentvertex: %d %g\n", currentVertex, minimum);
			if(minimum == INFINITY) break;
			
			for (int j=0;j<4;j++){
				if(graph[currentVertex][j].vertex!=-1 &&
				(graph[currentVertex][j].weight +shortestPath[currentVertex]<shortestPath[graph[currentVertex][j].vertex])){
					shortestPath[graph[currentVertex][j].vertex] = graph[currentVertex][j].weight +shortestPath[currentVertex];
					ancestor[graph[currentVertex][j].vertex] = currentVertex;
				}
			}
			wasVisited[currentVertex]=1; 
			
			
			
			
			
			
		
		}
		printf("Odleglosc %d do %d: %g\n",start,destination, shortestPath[destination]);
	}
}
		
		
		
		
		