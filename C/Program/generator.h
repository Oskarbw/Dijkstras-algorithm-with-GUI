#ifndef GENERATOR_H
#define GENERATOR_H

#include<stdlib.h>
#include<stdio.h>
#include <time.h>

typedef struct t_pair {
	int vertex;
	double weight;
} t_pair;


void findNeighbours(t_pair** graph, int vertex, int rows, int cols, double minWeight, double maxWeight, int decimalDigits);
void printGraph(t_pair** graph, int n);
t_pair** generateRandWeightMode (int rows, int cols, double minWeight, double maxWeight, int decimalDigits);

#endif
