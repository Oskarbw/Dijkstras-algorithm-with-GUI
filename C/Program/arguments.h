#ifndef argumentsH
#define argumentsH

int readMode (int argc, char **argv);
int readArgumentsLoadMode (int argc, char **argv, int *pairN, int **pairs, int *generatePairs);
int readArgumentsRandMode (int argc, char **argv, int *rows, int *cols, double *low, double *high, int *pairN, int **pairs, int *generatePairs, int *doSave, int* doPrintWeights, int *showGraph);

 
#endif
