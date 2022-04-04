#ifndef ARGUMENTS_H
#define ARGUMENTS_H

int readMode(int argc, char** argv);
int readArgumentsLoadMode(int argc, char** argv, int *pairN, int **pairs, int *generatePairs);
int readArgumentsRandMode(int argc, char** argv, int* rows, int* columns, double *low, double *high, int* pairN, int** pairs, int* generatePairs);

#endif