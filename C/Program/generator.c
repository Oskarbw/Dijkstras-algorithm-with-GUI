#include <stdlib.h>
#include <stdio.h>
#include <time.h>
#include "generator.h"
#include "bfs.h"

#define probability 7

void findNeighbours(
    tPair** graph, int vertex, int rows, int cols, double minWeight, double maxWeight)
{
    int currentNumOfNeighbours = 0;
    double randMaxDouble = RAND_MAX;

    if (vertex % cols != 0)
    {
        graph[vertex][currentNumOfNeighbours].vertex = (vertex - 1);
        graph[vertex][currentNumOfNeighbours].weight
            = ((rand() * (maxWeight - minWeight)) / randMaxDouble) + minWeight;
        currentNumOfNeighbours++;
    }

    if (vertex % cols != (cols - 1))
    {
        graph[vertex][currentNumOfNeighbours].vertex = (vertex + 1);
        graph[vertex][currentNumOfNeighbours].weight
            = ((rand() / randMaxDouble) * (maxWeight - minWeight)) + minWeight;
        currentNumOfNeighbours++;
    }

    if (vertex >= cols)
    {
        graph[vertex][currentNumOfNeighbours].vertex = (vertex - cols);
        graph[vertex][currentNumOfNeighbours].weight
            = ((rand() / randMaxDouble) * (maxWeight - minWeight)) + minWeight;
        currentNumOfNeighbours++;
    }

    if (vertex < (rows * cols) - cols)
    {
        graph[vertex][currentNumOfNeighbours].vertex = (vertex + cols);
        graph[vertex][currentNumOfNeighbours].weight
            = ((rand() / randMaxDouble) * (maxWeight - minWeight)) + minWeight;
        currentNumOfNeighbours++;
    }
}

tPair** generateRandWeightMode(int rows, int cols, double minWeight, double maxWeight)
{
    int n = rows * cols;
    tPair** graph;
    graph = malloc(n * (sizeof(tPair*)));

    for (int k = 0; k < n; k++)
    {
        graph[k] = malloc(directions * (sizeof(tPair)));
    }

    for (int i = 0; i < n; i++)
    {
        for (int j = 0; j < directions; j++)
        {
            graph[i][j].vertex = -1;
        }
    }

    for (int i = 0; i < n; i++)
    {
        findNeighbours(graph, i, rows, cols, minWeight, maxWeight);
    }
    return graph;
}


void printGraph(tPair** graph, int n)
{
    for (int i = 0; i < n; i++)
    {
        printf("Wierzcholek %d ma dostep do wierzcholkow: ", i);
        for (int j = 0; j < directions; j++)
        {
            if (graph[i][j].vertex == -1)
                continue;
            printf("%d [%f], ", graph[i][j].vertex, graph[i][j].weight);
        }
        printf("\n");
    }
}

tPair** generateAllRandMode(int rows, int cols, double minWeight, double maxWeight)
{
    tPair** graph = generateRandWeightMode(rows, cols, minWeight, maxWeight);
    int n = rows * cols;
    for (int i = 0; i < n; i++)
    {
        for (int j = 0; j < directions; j++)
        {
            if (graph[i][j].vertex == -1)
                continue;

            int throw = rand() % 10;
            if (throw > probability)
            {
                graph[i][j].vertex = -1;
            }
        }
    }
    return graph;
}


tPair** generateConMode(int rows, int cols, double minWeight, double maxWeight)
{
    tPair** graph;
    int wynik = 1;
    do
    {
        graph = generateAllRandMode(rows, cols, minWeight, maxWeight);
        wynik = 1;
        for (int i = 0; i < (rows * cols); i++)
        {
            wynik = BFS(graph, (rows * cols), i);
            if (wynik == 0)
                break;
        }
    } while (wynik == 0);


    return graph;
}
