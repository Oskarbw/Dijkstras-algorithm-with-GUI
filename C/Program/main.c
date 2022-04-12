#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <time.h>

#include "generator.h"
#include "bfs.h"
#include "plik.h"
#include "arguments.h"
#include "dijkstra.h"

int main(int argc, char** argv)
{
    srand(time(NULL));

    int rows = 15;
    int cols = 15;
    double low = 0;
    double high = 1;
    int pairN = 5;
    int* pairs;
    int mode;
    int generatePairs = 1;
    int doSave = 0;

    if (argc == 1)
    {
        printf("Niewłaściwa liczba argumentów!\n");
        return 1;
    }

    else
    {
        mode = readMode(argc, argv);
        if (mode == 0)
        {
            printf("Nie podano trybu, lub został podany niepoprawny\n");
            return 1;
        }

        if (mode == 1)
        {
            if (readArgumentsLoadMode(argc, argv, &pairN, &pairs, &generatePairs) == 0)
            {
                if (pairN == 0)
                {
                    printf("Błąd związany z ilością punktów!\n");
                    return 1;
                }
                printf("Ilość par: %d\n", pairN);
                int num = 1;
                for (int i = 0; i < pairN * 2; i += 2)
                {
                    printf("Para nr %d:  %d %d\n", num, pairs[i], pairs[i + 1]);
                    num++;
                }
                printf("\nGraf:\n\n");
                tPair** graph = readFile(argv[2], &rows, &cols);
                printGraph(graph, rows * cols);
                isConst(graph, rows, cols);
                dijkstra(graph, pairN, pairs, rows, cols);
                return 0;
            }
            else
                return 1;
        }

        int result = readArgumentsRandMode(
            argc, argv, &rows, &cols, &low, &high, &pairN, &pairs, &generatePairs, &doSave);
        if (result == 0)
        {
            printf("Parametry opisujące graf:\n");
            printf("Ilość wierszy: %d\n",rows);
            printf("Ilośc kolumn: %d\n",cols);
            printf("Dolny zakres wag: %g\n",low);
            printf("Górny zakres wag: %g\n",high);
            int num = 1;
            if (generatePairs == 1)
            {
                printf("\nLosowo wygenerowano 5 par:\n");
                for (int i = 0; i < 10; i++)
                {
                    pairs[i] = rand() % (rows * cols);
                }
            }

            else
            {
                printf("\nIlość par punktów: %d",pairN);
            }

            for (int i = 0; i < pairN * 2; i += 2)
            {
                printf("Para nr %d:  %d %d\n", num, pairs[i], pairs[i + 1]);
                num++;
            }
            
            printf("\nGraf:\n\n");

            if (mode == 2)
            {
                tPair** graph = generateAllRandMode(rows, cols, low, high);
                printGraph(graph, (rows * cols));
                if (doSave == 1)
                    printGraphToFile(graph, rows, cols);
                isConst(graph, rows, cols);
                dijkstra(graph, pairN, pairs, rows, cols);

                return 0;
            }

            if (mode == 3)
            {
                tPair** graph = generateRandWeightMode(rows, cols, low, high);
                printGraph(graph, (rows * cols));
                if (doSave == 1)
                    printGraphToFile(graph, rows, cols);
                isConst(graph, rows, cols);
                dijkstra(graph, pairN, pairs, rows, cols);

                return 0;
            }

            if (mode == 4)
            {
                tPair** graph = generateConMode(rows, cols, low, high);
                printGraph(graph, (rows * cols));
                if (doSave == 1)
                    printGraphToFile(graph, rows, cols);
                isConst(graph, rows, cols);
                dijkstra(graph, pairN, pairs, rows, cols);

                return 0;
            }
        }
    }
}
