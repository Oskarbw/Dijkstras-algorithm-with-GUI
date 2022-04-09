#include <stdlib.h>
#include <stdio.h>
#include "generator.h"
#include "bfs.h"
#include "plik.h"

#define INFINITY 99999
0 1 2 3 4 5 6 7 (8)  
typedef struct tHeapQueue{
	double* cells;
	int end;
} tHeapQueue;

void heapifyDown(tHeapQueue* heapQueue){
	int tmp = 0;
	double tmpValue = 0;
	heapQueue->cells[0] = heapQueue->cells[heapQueue->(end-1)];
	heapQueue->end--;
	while ((tmp*2)+1<(heapQueue->end)&&(heapQueue->cells[tmp]>heapQueue->cells[(tmp*2)+1]||heapQueue->cells[tmp]>heapQueue->cells[(tmp*2)+2])){
		if(heapQueue->cells[(tmp*2)+1]<heapQueue->cells[(tmp*2)+2]){
			tmpValue = heapQueue->cells[tmp];
			heapQueue->cells[tmp] = heapQueue->cells[(tmp*2)+1];
			heapQueue->cells[(tmp*2)+1] = tmpValue;
			tmp = (tmp*2)+1;
		}
		else if ((tmp*2)+2<(heapQueue->end)){
			tmpValue = heapQueue->cells[tmp];
			heapQueue->cells[tmp]=heapQueue->cells[(tmp*2)+2];
			heapQueue->cells[(tmp*2)+2] = tmpValue;
			tmp = (tmp*2)+2;
		}
	}
	
}
			

int popFromHeapQueue(tHeapQueue* heapQueue){
	int result = heapQueue->cells[0];
	heapifyDown(tHeapQueue* heapQueue);
	
	return result;
}
	
void heapifyUp(tHeapQueue* heapQueue){
	int tmp = heapQueue->end-1;
	double tmpValue = 0;
	while(tmp!=0&&heapQueue->cells[(tmp/2)]>heapQueue->cells[tmp]){
		tmpValue = heapQueue->cells[tmp];
		heapQueue->cells[tmp] = heapQueue->cells[tmp/2];
		heapQueue->cells[tmp/2] = tmpValue;
		tmp = tmp/2;
	}
}

void pushToHeapQueue(tHeapQueue* heapQueue, double value){
	heapQueue->end++;
	heapQueue->cells[end-1] = value;
	heapifyUp(heapQueue);
}
	

int isHeapQueueEmpty(tHeapQueue* heapQueue){
	if (heapQueue->end>0) return 0;
	else return 1;
}


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
		
		
		
		
		