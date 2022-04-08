#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <time.h>

#include "generator.h"
#include "bfs.h"
#include "plik.h"
#include "arguments.h"

int
main (int argc, char **argv)
{
  srand (time (NULL));

  int rows = 15;
  int columns = 15;
  double low = 0;
  double high = 1;
  int pairN = 5;
  int *pairs;
  int mode;
  int generatePairs = 1;
  int dec = 20;

  if (argc == 1)
    {
      printf ("Niewłaściwa liczba argumentów!\n");
      return 1;
    }
  else
    {
      mode = readMode (argc, argv);
      if (mode == 0)
	{
	  printf ("Nie podano trybu, lub został podany niepoprawny\n");
	  return 1;
	}

      if (mode == 1)
	{
	  if (readArgumentsLoadMode
	      (argc, argv, &pairN, &pairs, &generatePairs) == 0)
	    {
	      if (pairN == 0)
		{
		  printf ("Błąd związany z ilością punktów!\n");
		  return 1;
		}
	      printf ("pairN = %d\n", pairN);
	      int num = 1;
	      for (int i = 0; i < pairN * 2; i += 2)
		{
		  printf ("Para nr %d:  %d %d\n", num, pairs[i],
			  pairs[i + 1]);
		}
	      t_pair **graph = readFile (argv[2], &rows, &columns);
	      printGraph (graph, rows * columns);
	      isConst (graph, rows, columns);
	      return 0;
	    }
	}

      int result =
	readArgumentsRandMode (argc, argv, &rows, &columns, &low, &high,
			       &pairN, &pairs, &generatePairs);
      if (result == 0)
	{
	  printf ("mode = %d\n", mode);
	  printf ("rows = %d\n", rows);
	  printf ("columns = %d\n", columns);
	  printf ("dec = %d\n", dec);
	  printf ("low = %g\n", low);
	  printf ("high = %g\n", high);
	  int num = 1;
	  if (generatePairs == 1)
	    {
	      for (int i = 0; i < 10; i++)
		{
		  pairs[i] = rand () % (rows * columns);
		}
	    }
	  printf ("pairN = %d\n\n", pairN);
	  for (int i = 0; i < pairN * 2; i += 2)
	    {
	      printf ("Para nr %d:  %d %d\n", num, pairs[i], pairs[i + 1]);
	    }

	  if (mode == 2)
	    {
	      t_pair **graph =
		generateAllRandMode (rows, columns, low, high, dec);
	      printGraph (graph, (rows * columns));
	      printGraphToFile (graph, rows, columns);
	      isConst (graph, rows, columns);

	      return 0;
	    }

	  if (mode == 3)
	    {
	      t_pair **graph =
		generateRandWeightMode (rows, columns, low, high, dec);
	      printGraph (graph, (rows * columns));
        printGraphToFile (graph, rows, columns);
	      isConst (graph, rows, columns);

	      return 0;
	    }

	  if (mode == 4)
	    {
	      t_pair **graph =
		generateConMode (rows, columns, low, high, dec);
	      printGraph (graph, (rows * columns));
        printGraphToFile (graph, rows, columns);
	      isConst (graph, rows, columns);

	      return 0;
	    }
	}
    }
}
