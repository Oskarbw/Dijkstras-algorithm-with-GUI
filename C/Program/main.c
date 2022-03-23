#include <stdio.h>
#include <stdlib.h>
#include <string.h>
int rows = 10;
int columns = 10;
double low = 0;
double high = 1;
int dec = 10;
int pairN = 5;
int *pairs;

int
readArguments (int argc, char **argv)
{
  for (int i = 1; i < argc - 1; i++)
    {
      if (strcmp (argv[i], "-r") == 0)
	{
	  if (atoi (argv[i + 1]) < 1)
	    continue;
	  rows = atoi (argv[i + 1]);
	  continue;
	}
      if (strcmp (argv[i], "-c") == 0)
	{
	  if (atoi (argv[i + 1]) < 1)
	    continue;
	  columns = atoi (argv[i + 1]);
	  continue;
	}
      if (strcmp (argv[i], "-low") == 0)
	{
	  if (atof (argv[i + 1]) < 0)
	    continue;
	  low = atof (argv[i + 1]);
	  continue;
	}
      if (strcmp (argv[i], "-high") == 0)
	{
	  high = atof (argv[i + 1]);
	  continue;
	}
      if (strcmp (argv[i], "-dec") == 0)
	{
	  if (atoi (argv[i + 1]) < 0 || atoi (argv[i + 1]) > 20)
	    continue;
	  dec = atoi (argv[i + 1]);
	  continue;
	}
      if (strcmp (argv[i], "-p") == 0)
	{
	  if (atoi (argv[i + 1]) < 0)
	    continue;
	  *pairs = malloc (sizeof (int) * atoi (argv[i + 1]) * 2);
	  pairN = atoi (argv[i + 1]);
	  for (int j = 0; j < (pairN * 2); j++)
	    {
	      pairs[j] = atoi (argv[i + 2 + j]);
	    }
	  continue;
	}
    }
  if (low > high)
    {
      low = 0;
      high = 1;
    }
  return 0;
}


int
main (int argc, char **argv)
{
  if (readArguments (argc, argv) == 0)
    {
      printf ("rows = %d\n", rows);
      printf ("columns = %d\n", columns);
      printf ("dec = %d\n", dec);
      printf ("low = %g\n", low);
      printf ("high = %g\n", high);
      printf ("pairN = %d\n", pairN);
      return 0;
    }
  else
    {
      printf ("Błąd argumentu!\n");
      return 1;
    }
}
