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

void heapifyDown(tHeapQueue* heapQueue, double* sPath){
	int tmp = 0;
	double tmpVertex = 0;
	heapQueue->cells[0] = heapQueue->cells[heapQueue->end-1];
	heapQueue->end--;
	while ((tmp*2)+1<(heapQueue->end)
		&&(sPath[heapQueue->cells[tmp]]>sPath[heapQueue->cells[(tmp*2)+1]]
		||sPath[heapQueue->cells[tmp]]>sPath[heapQueue->cells[(tmp*2)+2]])){
		if(sPath[heapQueue->cells[(tmp*2)+1]]<sPath[heapQueue->cells[(tmp*2)+2]]){
			tmpVertex = heapQueue->cells[tmp];
			heapQueue->cells[tmp] = heapQueue->cells[(tmp*2)+1];
			heapQueue->cells[(tmp*2)+1] = tmpVertex;
			tmp = (tmp*2)+1;
		}
		else if ((tmp*2)+2<(heapQueue->end)){
			tmpVertex = heapQueue->cells[tmp];
			heapQueue->cells[tmp]=heapQueue->cells[(tmp*2)+2];
			heapQueue->cells[(tmp*2)+2] = tmpVertex;
			tmp = (tmp*2)+2;
		}
	}
	
	
}
			

int popFromHeapQueue(tHeapQueue* heapQueue, double* sPath){
	int result = heapQueue->cells[0];
	heapifyDown(heapQueue, sPath);
	
	return result;
}
	
void heapifyUp(tHeapQueue* heapQueue, double* sPath){
	int tmp = heapQueue->end-1;
	double tmpVertex = 0;
	while(tmp!=0&&sPath[heapQueue->cells[(tmp-1)/2]]>sPath[heapQueue->cells[tmp]]){
		tmpVertex = heapQueue->cells[tmp];
		heapQueue->cells[tmp] = heapQueue->cells[(tmp-1)/2];
		heapQueue->cells[(tmp-1)/2] = tmpVertex;
		tmp = (tmp-1)/2;
	}
	
}

void pushToHeapQueue(tHeapQueue* heapQueue, double* sPath, double vertex){
	heapQueue->end++;
	heapQueue->cells[heapQueue->end-1] = vertex;
	heapifyUp(heapQueue, sPath);
}
	

int isHeapQueueEmpty(tHeapQueue* heapQueue){
	if (heapQueue->end>0) return 0;
	else return 1;
}

void printPath(t_pair** graph, int* ancestor, int destination, int* path){
	int tmp = destination;
	path[0] = tmp;
	int i = 1;
	double value;
	
	
	while(ancestor[tmp]!=-1){
		path[i] = ancestor[tmp];
		i++;
		tmp = ancestor[tmp];
	}
	printf("Sciezka: %d", path[i-1]);
	for (int j = i-2;j>=0;j--){
		for (int k=0;k<4;k++)
			if(graph[path[j+1]][k].vertex==path[j]){
				value = graph[path[j+1]][k].weight;
				break;
			}
		printf(" -(%g)-> %d",value, path[j]);
	}
	printf("\n\n");
}



void dijkstra (t_pair** graph, int pairN, int* pairs, int rows , int cols){
	int n = rows*cols;
	int* ancestor = malloc(sizeof(int) * n);
	double* sPath = malloc(sizeof(double) * n);
	int* wasVisited = malloc(sizeof(int) * n);
	double* results = malloc(sizeof(double) * pairN);
	tHeapQueue *heapQueue = malloc(sizeof(tHeapQueue));
	heapQueue->cells = malloc(sizeof(int) * 2* n);
	
	
	for (int i=0;i<pairN;i++){
		int start = pairs[i*2];
		int destination = pairs[(i*2)+1];
		
		for (int j=0;j<n;j++) sPath[j] = INFINITY;
		sPath[start] = 0;
		
		for (int j=0;j<n;j++) ancestor[j] = -1;
		for (int j=0;j<n;j++) wasVisited[j]=0;
		heapQueue->end = 0;
		
		pushToHeapQueue(heapQueue, sPath, start);
		
		int currentVertex;
		for(int k=0;k<n;k++){
			
			if(heapQueue->end>0) 
				currentVertex = popFromHeapQueue(heapQueue, sPath);
			else 
				break;
			
			for (int j=0;j<4;j++){
				if(graph[currentVertex][j].vertex!=-1 &&
				(graph[currentVertex][j].weight +sPath[currentVertex]<sPath[graph[currentVertex][j].vertex])){
					sPath[graph[currentVertex][j].vertex] = graph[currentVertex][j].weight +sPath[currentVertex];
					ancestor[graph[currentVertex][j].vertex] = currentVertex;
					if(wasVisited[graph[currentVertex][j].vertex]==0){
						pushToHeapQueue(heapQueue, sPath, graph[currentVertex][j].vertex);
						wasVisited[graph[currentVertex][j].vertex]=1; 
					}
				}
			}
			
			
		}
		printf("Odleglosc %d do %d: %g\n",start,destination, sPath[destination]);
		
		int* path = malloc(sizeof(int) * n);
		if(sPath[destination]!=INFINITY)
			printPath(graph, ancestor, destination, path);
		else
			printf("Sciezka nie istnieje!\n");
	}
}
		
		
		
		
		