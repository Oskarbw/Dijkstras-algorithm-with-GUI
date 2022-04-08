#include <stdlib.h>
#include <stdio.h>
#include <time.h>
#include "generator.h"
#include "bfs.h"
  void
findNeighbours (t_pair ** graph, int vertex, int rows, int cols,
		double minWeight, double maxWeight, int decimalDigits)
{
  int currentNumOfNeighbours = 0;
  double randMaxDouble = RAND_MAX;
  if (vertex % cols != 0)
    {				//if vertex isn't on the left edge of graph, then find his left neighbour
      graph[vertex][currentNumOfNeighbours].vertex = (vertex - 1);
      graph[vertex][currentNumOfNeighbours].weight =
	((rand () * (maxWeight - minWeight)) / randMaxDouble) + minWeight;
      
	// ^ generating weight between minWeight and maxWeight
	currentNumOfNeighbours++;
    }
   if (vertex % cols != (cols - 1))
    {				//if vertex isn't on the right edge of graph, then find his right neighbour
      graph[vertex][currentNumOfNeighbours].vertex = (vertex + 1);
      graph[vertex][currentNumOfNeighbours].weight =
	((rand () / randMaxDouble) * (maxWeight - minWeight)) + minWeight;
      
	// ^ generating weight between minWeight and maxWeight
	currentNumOfNeighbours++;
    }
   if (vertex >= cols)
    {				//if vertex isn't on the upper edge of graph, then find his upper neighbour
      graph[vertex][currentNumOfNeighbours].vertex = (vertex - cols);
      graph[vertex][currentNumOfNeighbours].weight =
	((rand () / randMaxDouble) * (maxWeight - minWeight)) + minWeight;
      
	// ^ generating weight between minWeight and maxWeight
	currentNumOfNeighbours++;
    }
   if (vertex < (rows * cols) - cols)
    {				//if vertex isn't on the lower edge of graph, then find his lower neighbour
      graph[vertex][currentNumOfNeighbours].vertex = (vertex + cols);
      graph[vertex][currentNumOfNeighbours].weight =
	((rand () / randMaxDouble) * (maxWeight - minWeight)) + minWeight;
      
	// ^ generating weight between minWeight and maxWeight
	currentNumOfNeighbours++;
    }
}

    t_pair ** generateRandWeightMode (int rows, int cols,
					   double minWeight, double maxWeight,
					   int decimalDigits)
{
   int n = rows * cols;	//number of vertexes
  t_pair **graph;
  graph = malloc (n * (sizeof (t_pair *)));
  for (int k = 0; k < n; k++)
    {
      graph[k] = malloc (4 * (sizeof (t_pair)));
    }
  
    //t_pair*** graphPnt = &graph;
       for (int i = 0; i < n; i++)
    {				//initially, changing all "vertex" variables to -1, which means "no neighbour"
      for (int j = 0; j < 4; j++)
	{
	  graph[i][j].vertex = -1;
  } }  for (int i = 0; i < n; i++)
    {				// for each vertex - find his ALL neighbours and generate weights
      findNeighbours (graph, i, rows, cols, minWeight, maxWeight,
		      decimalDigits);
    } return graph;
}

 void
printGraph (t_pair ** graph, int n)
{
  for (int i = 0; i < n; i++)
    {
      printf ("Wierzcholek %d ma dostep do wierzcholkow: ", i);
      for (int j = 0; j < 4; j++)
	{
	  if (graph[i][j].vertex == -1)
	    continue;
	   printf ("%d [%f], ", graph[i][j].vertex, graph[i][j].weight);
	}
      printf ("\n");
    }
}

 
/*
int main(){
	srand(time(NULL));
	printGraph(generateRandWeightMode(10,5,0,1,10), 50);
	
	
	return 0;
}
*/ 
  t_pair ** generateAllRandMode (int rows, int cols, double minWeight,
				  double maxWeight, int decimalDigits)
{
  t_pair ** graph =
    generateRandWeightMode (rows, cols, minWeight, maxWeight, decimalDigits);
  int n = rows * cols;
  for (int i = 0; i < n; i++)
    {
      for (int j = 0; j < 4; j++)
	{
	  if (graph[i][j].vertex == -1)
	    continue;
	  int chance = rand () % 10;
	  if (chance > 7)
	    {
	      graph[i][j].vertex = -1;
	    }
	 }
    }
   return graph;
}

   t_pair ** generateConMode (int rows, int cols, double minWeight,
				  double maxWeight, int decimalDigits)
{
  t_pair ** graph;
  int wynik = 1;
  
  do
    {
      graph =
	generateAllRandMode (rows, cols, minWeight, maxWeight, decimalDigits);
      wynik = 1;
      for (int i = 0; i < (rows * cols); i++)
	{
	  wynik = BFS (graph, (rows * cols), i);
	  if (wynik == 0)
	    break;
	}
    }
  while (wynik == 0);
    return graph;
}


