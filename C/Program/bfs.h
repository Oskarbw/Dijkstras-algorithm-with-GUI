#ifndef BFS_H
#define BFS_H

#include<stdlib.h>
#include<stdio.h>

typedef struct t_queue{
	int* cells;
	int start;
	int end;
	
}t_queue;

int BFS(t_pair** graph, int n,int startingVertex);
int popFromQueue(t_queue* queue);
void addToQueue(t_queue* queue, int vertex);



#endif