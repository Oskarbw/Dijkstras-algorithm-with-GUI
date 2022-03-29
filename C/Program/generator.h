
#include <time.h>

typedef struct t_pair {
	int vertex;
	double weight;
} t_pair;
	



void generateRandWeightMode (int rows, int cols, double minWeight, double maxWeight, int decimalDigits);
void generateAllRandMode();
void generateConMode();
void findNeighbours();
void randomlyFindNeighbours();