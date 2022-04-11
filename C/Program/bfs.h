#ifndef bfsH
#define bfsH

#include<stdlib.h>
#include<stdio.h>
  typedef struct tQueue
{	int *cells;   	int start;  	int end;} tQueue;
 int BFS (tPair ** graph, int n, int startingVertex);
int popFromQueue (tQueue * queue);
void addToQueue (tQueue * queue, int vertex);
int isConst (tPair ** graph, int rows, int columns);
  
#endif
