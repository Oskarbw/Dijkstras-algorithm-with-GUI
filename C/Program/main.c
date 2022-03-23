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
int mode = 0;

int
readArguments (int argc, char **argv)
{
  if (strcmp (argv[1], "loadMode") == 0)
  {
      mode = 1;
    }
  if (strcmp (argv[1], "allRandMode") == 0)
    {
      mode = 2;
    }
  if (strcmp (argv[1], "randWeightMode") == 0)
    {
      mode = 3;
    }
  if (strcmp (argv[1], "conMode") == 0)
    {
      mode = 4;
    }

  /*if (mode == 1)
    {
      //Tutaj wyszukiwanie flag dla loadmode
      return 0;
    }
    To zostanie odkomentowane, kiedy napisze w środku potrzebne komendy
    Tymczasowo, dla sprawdzenia poprawności danych, poniżej dodany jest warunek
    || mode == 1*/

  if (mode == 1 || mode == 2 || mode == 3 || mode == 4)
    {
      for (int i = 2; i < argc - 1; i++)
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
  if (mode == 0)
    {
      return 1;
    }
}


int
main (int argc, char **argv)
{
  if (readArguments (argc, argv) == 0)
    {
      printf ("mode = %d\n", mode);
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
