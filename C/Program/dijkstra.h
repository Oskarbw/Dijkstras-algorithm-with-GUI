#ifndef dijkstraH
#define dijkstraH

typedef struct tHeapQueue{
	int* cells;
	int end;
} tHeapQueue;

void heapifyDown(tHeapQueue* heapQueue, double* pathLength);
int popFromHeapQueue(tHeapQueue* heapQueue, double* pathLength);
void heapifyUp(tHeapQueue* heapQueue, double* pathLength);
void pushToHeapQueue(tHeapQueue* heapQueue, double* pathLength, double vertex);
int isHeapQueueEmpty(tHeapQueue* heapQueue);
void printPath(tPair** graph, int* ancestor, int destination, int* path);
 
void dijkstra (tPair** graph, int pairN, int* pairs, int rows , int cols, int doPrintWeights); 
 
#endif