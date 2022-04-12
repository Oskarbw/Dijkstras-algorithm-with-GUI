#include <stdio.h>
#include <string.h>
#include "generator.h"
#include "bfs.h"
#include "file.h"
#include "dijkstra.h"

int testRandWeightModeGenerator()
{
    tPair** graph = NULL;
    graph = generateRandWeightMode(5,5,0,1);
    if(graph != NULL)
    {
        printf("Test generateRandWeightMode udany!\n");
        return 0;
    }
    else
    {
        printf("Test generateRandWeightMode nieudany!\n");
        return 1;
    }
}

int testAllRandModeGenerator()
{
    tPair** graph = NULL;
    graph = generateAllRandMode(5,5,0,1);
    if(graph != NULL)
    {
        printf("Test generateAllRandMode udany!\n");
        return 0;
    }
    else
    {
        printf("Test generateAllRandMode nieudany!\n");
        return 1;
    }
}

int testConModeGenerator()
{
    tPair** graph = NULL;
    graph = generateConMode(5,5,0,1);
    if(graph != NULL)
    {
        printf("Test generateConMode udany!\n");
        return 0;
    }
    else
    {
        printf("Test generateConMode nieudany!\n");
        return 1;
    }
}

int testRead()
{
    FILE *in = fopen("isodData","r");
    if(in == NULL)
    {
        printf("Nie udało się znaleźć pliku z zawartością testową!\n");
        fclose(in);
        return 1;
    }
    fclose(in);

    int rows = -1;
    int cols = -1;
    tPair** graph = readFile("isodData",&rows,&cols);

    if(rows != -1 && cols != -1)
    {
        printf("Test wczytywania udany!");
        return 0;
    }
}

int testBFS()
{
    int rows = -1;
    int cols = -1;
    tPair** graph = readFile("isodData",&rows,&cols);

    if(isConst(graph,rows,cols) == 0)
    {
        printf("Test BFSa udany!\n\n");
        return 0;
    }
    else
    {
        printf("Test BFSa nieudany!\n\n");
        return 1;
    }
}

int testDijkstra()
{
    int rows = -1;
    int cols = -1;
    tPair** graph = readFile("isodData",&rows,&cols);

    int pairs[2];
    pairs[0] = 1;
    pairs[1] = 13;

    printf("Oczekiwany wynik Dijkstry:\n");
    printf("Odleglosc 1 do 13: 1.44366\n");
    printf("Sciezka: 1 -(0.263775)-> 5 -(0.315096)-> 9 -(0.864784)-> 13\n\n");
    printf("Wynik Dijkstry z programu:\n");
    dijkstra(graph, 1, pairs, rows, cols, 1);

    return 0;
}
int allTests()
{
    if(testRead() == 0)
    {
        testBFS();
        testDijkstra();
    }    
    testRandWeightModeGenerator();
    testAllRandModeGenerator();
    testConModeGenerator();

    return 0;
}