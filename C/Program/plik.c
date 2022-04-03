#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "generator.h"
#include "plik.h"

t_pair** readFile(char* path, int *rows, int *cols){
	t_pair** graph;
	FILE* in = fopen(path,"r");
	fscanf(in, "%d %d", rows, cols);
	graph = malloc((*rows)*(*cols) * (sizeof(t_pair*)));
	for(int i=0; i<(*rows)*(*cols); i++){
		graph[i] = malloc(sizeof(t_pair) * 4);
	}
	for(int i=0; i < (*rows)*(*cols); i++){
		for(int j=0; j<4; j++){
			graph[i][j].vertex = -1;
		}
	}
	int currentNode = 0;
	for(int i=0; i<(*rows)*(*cols)+1; i++){
		int currentConnect = 0;
		char line[250];
		fgets(line,250,in);
		char corrector[] = "	 :\n";
		char *safe = malloc(sizeof(char)*50);
		safe = strtok (line, corrector);
		if(i != 0){
			while (safe != NULL)
			{
				graph[currentNode][currentConnect].vertex = atoi(safe);
				safe = strtok (NULL, corrector);
				graph[currentNode][currentConnect].weight = atof(safe);
				safe = strtok (NULL, corrector);
				currentConnect++;
			}
		currentNode++;
		}
	}
	return graph;
}
