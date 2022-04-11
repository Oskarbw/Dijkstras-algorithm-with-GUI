#include <stdlib.h>
#include <stdio.h>
#include "generator.h"
#include "bfs.h"

void addToQueue (t_queue * queue, int vertex)
{
    queue->cells[queue->end] = vertex;
    queue->end++;
}

int popFromQueue (t_queue * queue)
{
    if (queue->start < queue->end)
    {
        int value = queue->cells[queue->start];
        queue->start++;
        return value;
    }
    else
    {
        return -2;
    }
}


int BFS (t_pair ** graph, int n, int startingVertex)
{
    int *vertexState = malloc (n * (sizeof (int)));	//creating array that will keep information whether vertex has been visited
    for (int i = 0; i < n; i++)
    {
        //initially all vertexes are unvisitied (vertexState=0)
        vertexState[i] = 0;
    }
    t_queue * queue = malloc (sizeof (t_queue));
    queue->cells = malloc ((n + 3) * (sizeof (int)));
    queue->start = 0;
    queue->end = 0;

    addToQueue (queue, startingVertex);
    int currentVertex;
    while ((currentVertex = popFromQueue (queue)) != -2)
    {
        //while queue isn't empty
        for (int j = 0; j < 4; j++)
        {
            if (vertexState[graph[currentVertex][j].vertex] == 0
                && graph[currentVertex][j].vertex != -1)
            {
                addToQueue (queue, graph[currentVertex][j].vertex);
                vertexState[graph[currentVertex][j].vertex] = 1;	//1 - added to queue
            }
        }
        vertexState[currentVertex] = 2;	//visited
    }

    int haveAllVertexesBeenVisited = 1;	//1 - they have
    for (int i = 0; i < n; i++)
    {
        if (vertexState[i] != 2)
        {
            haveAllVertexesBeenVisited = 0;	//0 - they haven't
            break;
        }
    }
    return haveAllVertexesBeenVisited;
}


int isConst (t_pair ** graph, int rows, int columns)
{
    int wynik = 1;

    for (int i = 0; i < (rows * columns); i++)
    {
        wynik = BFS (graph, (rows * columns), i);
        if (wynik == 0) break;
    }

    if (wynik == 1)
    {
        printf ("\n\n Wynik dzialania BFSa: spojny\n");
        return 0;
    }

    else
    {
        printf ("\n\n Wynik dzialania BFSa: niespojny\n");
        return 1;
    }
}
