#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "arguments.h"

int readMode (int argc, char **argv)
{
    if (strcmp (argv[1], "loadMode") == 0)
        return 1;

    if (strcmp (argv[1], "allRandMode") == 0)
        return 2;

    if (strcmp (argv[1], "randWeightMode") == 0)
        return 3;

    if (strcmp (argv[1], "conMode") == 0)
        return 4;

    else
        return 0;
}


int readArgumentsLoadMode (int argc, char **argv, int *pairN, int **pairs, int *generatePairs)
{
    FILE * in = fopen (argv[2], "r");

    if (in == NULL)
    {
        printf ("Nie udało wczytać się pliku wejściowego!\n");
        return 1;
    }

    fclose (in);

    if (argc < 4)
    {
        printf ("Nie podano ilości par!\n");
        return 1;
    }

    *generatePairs = 0;
    *pairN = atoi (argv[3]);
    *pairs = malloc (sizeof (int) * (*pairN) * 2);

    for (int i = 0; i < (*pairN) * 2; i++)
    {
        if ((4 + i) < argc)
        {
            (*pairs)[i] = atoi (argv[4 + i]);
        }
        else
        {
            printf ("Błąd związany z ilością punktów!\n");
            return 1;
        }
    }
    return 0;
}


int readArgumentsRandMode (int argc, char **argv, int *rows, int *columns, double *low, double *high, int *pairN, int **pairs, int *generatePairs)
{
    for (int i = 2; i < argc - 1; i++)
    {
        if (strcmp (argv[i], "-r") == 0)
        {
            if (atoi (argv[i + 1]) < 1)
            {
                printf
                ("Nie udało się wczytać liczby wierszy, lub jest ona niepoprawna. Ustawiono wartość domyślną - 15\n");
                continue;
            }
            *rows = atoi (argv[i + 1]);
            continue;
        }

        if (strcmp (argv[i], "-c") == 0)
        {
            if (atoi (argv[i + 1]) < 1)
            {
                printf ("Nie udało się wczytać liczby kolumn, lub jest ona niepoprawna. Ustawiono wartość domyślną - 15\n");
                continue;
            }
            *columns = atoi (argv[i + 1]);
            continue;
        }

        if (strcmp (argv[i], "-low") == 0)
        {
            if (atoi (argv[i + 1]) < 1)
            {
                printf ("Nie udało się wczytać dolnego zakresu, lub jest on niepoprawny. Ustawiono wartość domyślną - 0\n");
                continue;
            }

            *low = atoi (argv[i + 1]);

            continue;

        }


        if (strcmp (argv[i], "-high") == 0)

        {
            *high = atoi (argv[i + 1]);
            continue;
        }
        if (strcmp (argv[i], "-p") == 0)
        {
            if (atoi (argv[i + 1]) < 0)
            {
                printf ("Nie udało się wczytać ilości par, lub jest ona niepoprawna. Ustawiono wartość domyślną - 5 losowych par\n");
                continue;
            }
            generatePairs = 0;
            *pairs = malloc (sizeof (int) * atoi (argv[i + 1]) * 2);
            *pairN = atoi (argv[i + 1]);

            for (int j = 0; j < (*pairN) * 2; j++)
            {
                if ((i + 2 + j) < argc)
                    (*pairs)[j] = atoi (argv[i + 2 + j]);
                else
                {
                    printf ("Błąd związany z ilością punktów!\n");
                    return 1;
                }
            }
        }
    }

    if (*low > *high)
    {
        printf ("Dolny zakres losowania wag jest większy niż górny. Ustawiono standardowe wartości losowania od 0 do 1\n");
        *low = 0;
        *high = 1;
    }
    return 0;
}
