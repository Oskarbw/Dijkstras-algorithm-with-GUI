#ifndef generatorH
#define generatorH

#include<stdlib.h>
#include<stdio.h>
#include <time.h>
  
#define directions 4
  typedef struct tPair
{	int vertex;	double weight;} tPair;


void findNeighbours (tPair ** graph, int vertex, int rows, int cols, double minWeight, double maxWeight);void printGraph (tPair ** graph, int n);
tPair ** generateRandWeightMode (int rows, int cols, double minWeight, double maxWeight);tPair ** generateAllRandMode (int rows, int cols, double minWeight, double maxWeight);tPair ** generateConMode (int rows, int cols, double minWeight, double maxWeight);
 
#endif	
