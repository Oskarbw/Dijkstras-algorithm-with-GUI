#include <stdlib.h>
#include <stdio.h>
#include <math.h>
#include "generator.h"
#include "bfs.h"
#include "file.h"
#include "dijkstra.h"


void heapifyDown(tHeapQueue* heapQueue, double* pathLength)
{
    int tmp = 0;
    double tmpVertex = 0;
    heapQueue->cells[0] = heapQueue->cells[heapQueue->end-1];
    heapQueue->end--;
    while ((tmp*2)+1<(heapQueue->end)&&(pathLength[heapQueue->cells[tmp]]>pathLength[heapQueue->cells[(tmp*2)+1]]||pathLength[heapQueue->cells[tmp]]>pathLength[heapQueue->cells[(tmp*2)+2]]))
    {
        if(pathLength[heapQueue->cells[(tmp*2)+1]]<pathLength[heapQueue->cells[(tmp*2)+2]])
        {
            tmpVertex = heapQueue->cells[tmp];
            heapQueue->cells[tmp] = heapQueue->cells[(tmp*2)+1];
            heapQueue->cells[(tmp*2)+1] = tmpVertex;
            tmp = (tmp*2)+1;
        }
        else if ((tmp*2)+2<(heapQueue->end))
        {
            tmpVertex = heapQueue->cells[tmp];
            heapQueue->cells[tmp]=heapQueue->cells[(tmp*2)+2];
            heapQueue->cells[(tmp*2)+2] = tmpVertex;
            tmp = (tmp*2)+2;
        }
    }
}

int popFromHeapQueue(tHeapQueue* heapQueue, double* pathLength)
{
    int result = heapQueue->cells[0];
    heapifyDown(heapQueue, pathLength);
    return result;
}

void heapifyUp(tHeapQueue* heapQueue, double* pathLength)
{
    int tmp = heapQueue->end-1;
    double tmpVertex = 0;
    while(tmp!=0&&pathLength[heapQueue->cells[(tmp-1)/2]]>pathLength[heapQueue->cells[tmp]])
    {
        tmpVertex = heapQueue->cells[tmp];
        heapQueue->cells[tmp] = heapQueue->cells[(tmp-1)/2];
        heapQueue->cells[(tmp-1)/2] = tmpVertex;
        tmp = (tmp-1)/2;
    }
}

void pushToHeapQueue(tHeapQueue* heapQueue, double* pathLength, double vertex)
{
    heapQueue->end++;
    heapQueue->cells[heapQueue->end-1] = vertex;
    heapifyUp(heapQueue, pathLength);
}


int isHeapQueueEmpty(tHeapQueue* heapQueue)
{
    if (heapQueue->end>0) return 0;
    else return 1;
}

void printPath(tPair** graph, int* ancestor, int destination, int* path)
{
    int tmp = destination;
    path[0] = tmp;
    int i = 1;
    double value;

    while(ancestor[tmp]!=-1)
    {
        path[i] = ancestor[tmp];
        i++;
        tmp = ancestor[tmp];
    }
    printf("Sciezka: %d", path[i-1]);
    for (int j = i-2; j>=0; j--)
    {
        for (int k=0; k<directions; k++)
            if(graph[path[j+1]][k].vertex==path[j])
            {
                value = graph[path[j+1]][k].weight;
                break;
            }
        printf(" -(%g)-> %d",value, path[j]);
    }
    printf("\n\n");
}

void printPathNoWeights(tPair** graph, int* ancestor, int destination, int* path)
{
    int tmp = destination;
    path[0] = tmp;
    int i = 1;

    while(ancestor[tmp]!=-1)
    {
        path[i] = ancestor[tmp];
        i++;
        tmp = ancestor[tmp];
    }
    printf("Sciezka: %d", path[i-1]);
    for (int j = i-2; j>=0; j--)
    {
        printf(" -> %d", path[j]);
    }
    printf("\n\n");
}


void dijkstra (tPair** graph, int pairN, int* pairs, int rows, int cols, int doPrintWeights)
{
    int n = rows*cols;
    int* ancestor = malloc(sizeof(int) * n);
    int* path = malloc(sizeof(int) * n);
    double* pathLength = malloc(sizeof(double) * n);
    int* wasVisited = malloc(sizeof(int) * n);
    double* results = malloc(sizeof(double) * pairN);
    tHeapQueue *heapQueue = malloc(sizeof(tHeapQueue));
    heapQueue->cells = malloc(sizeof(int) * 2* n);

    for (int i=0; i<pairN; i++)
    {
        int start = pairs[i*2];
        int destination = pairs[(i*2)+1];
        for (int j=0; j<n; j++)
            pathLength[j] = INFINITY;
        pathLength[start] = 0;
        for (int j=0; j<n; j++)
            ancestor[j] = -1;
        for (int j=0; j<n; j++)
            wasVisited[j]=0;
        heapQueue->end = 0;
        pushToHeapQueue(heapQueue, pathLength, start);
        int currentVertex;

        for(int k=0; k<n; k++)
        {
            if(heapQueue->end>0)
                currentVertex = popFromHeapQueue(heapQueue, pathLength);
            else
                break;

            for (int j=0; j<directions; j++)
            {
                if(graph[currentVertex][j].vertex!=-1 &&
                        (graph[currentVertex][j].weight +pathLength[currentVertex]<pathLength[graph[currentVertex][j].vertex]))
                {
                    pathLength[graph[currentVertex][j].vertex] = graph[currentVertex][j].weight +pathLength[currentVertex];
                    ancestor[graph[currentVertex][j].vertex] = currentVertex;
                    if(wasVisited[graph[currentVertex][j].vertex]==0)
                    {
                        pushToHeapQueue(heapQueue, pathLength, graph[currentVertex][j].vertex);
                        wasVisited[graph[currentVertex][j].vertex]=1;
                    }
                }
            }


        }
        printf("Odleglosc %d do %d: %g\n",start,destination, pathLength[destination]);
        if(pathLength[destination]!=INFINITY)
            if(doPrintWeights)
				printPath(graph, ancestor, destination, path);
			else
				printPathNoWeights(graph, ancestor, destination, path);
        else
            printf("Sciezka nie istnieje!\n\n");
    }
    free(ancestor);
    free(path);
    free(pathLength);
    free(wasVisited);
    free(results);
    free(heapQueue->cells);
    free(heapQueue);
}
