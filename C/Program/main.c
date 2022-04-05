#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <time.h>
#include "generator.h"
#include "bfs.h"
#include "plik.h"


int rows = 15;
int columns = 15;
double low = 0;
double high = 1;
int dec = 10;
int pairN = 5;
int *pairs;
int mode = 0;
FILE *in;
int generatePairs = 1;

void dijkstra (t_pair** graph, int pairN, int* pairs, int rows , int cols);

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

  if (mode == 1)
    {
      in = fopen (argv[2], "r");
      if (in == NULL)
	{
	  printf ("Nie udało wczytać się pliku wejściowego!\n");
	  return 1;
	}
      fclose(in);
      if(argc < 4){
		  printf("Nie podano ilości par!\n");
		  return 1;
	  }
	  generatePairs = 0;
	  pairN = atoi (argv[3]);
      pairs = malloc (sizeof (int) * pairN * 2);
      for (int i = 0; i < (pairN * 2); i++)
	{
	  if ((4 + i) < argc)
	    pairs[i] = atoi (argv[4 + i]);
	  else
	    {
	      printf ("Błąd związany z ilością punktów!\n");
	      return 1;
	    }
	}
    }

  if (mode == 2 || mode == 3 || mode == 4)
    {
      for (int i = 2; i < argc - 1; i++)
	{
	  //Szukanie flagi -r
	  if (strcmp (argv[i], "-r") == 0)
	    {
	      if (atoi (argv[i + 1]) < 1)
		{
		  printf
		    ("Nie udało się wczytać liczby wierszy,lub jest ona niepoprawna. Ustawiono wartość domyślną - 10\n");
		  continue;
		}
	      rows = atoi (argv[i + 1]);
	      continue;
	    }

	  //Szukanie flagi -c
	  if (strcmp (argv[i], "-c") == 0)
	    {
	      if (atoi (argv[i + 1]) < 1)
		{
		  printf
		    ("Nie udało się wczytać liczby kolumn, lub jest ona niepoprawna. Ustawiono wartość domyślną - 10\n");
		  continue;
		}
	      columns = atoi (argv[i + 1]);
	      continue;
	    }

	  //Szukanie flagi -low
	  if (strcmp (argv[i], "-low") == 0)
	    {
	      if (atof (argv[i + 1]) < 0)
		{
		  printf
		    ("Nie udało się wczytać dolnego zakresu losowania wag, lub jest on niepoprawny. Ustawiono wartość domyślną - 0\n");
		  continue;
		}
	      low = atof (argv[i + 1]);
	      continue;
	    }

	  //Szukanie flagi -high
	  if (strcmp (argv[i], "-high") == 0)
	    {
	      high = atof (argv[i + 1]);
	      continue;
	    }

	  //Szukanie flagi -dec
	  if (strcmp (argv[i], "-dec") == 0)
	    {
	      if (atoi (argv[i + 1]) < 0 || atoi (argv[i + 1]) > 20)
		{
		  printf
		    ("Nie udało się wczytać ilości liczb po przecinku dla wag lub jest ona niepoprawna. Ustawiono wartość domyślną - 10\n");
		  continue;
		}
	      dec = atoi (argv[i + 1]);
	      continue;
	    }

	  //Szukanie flagi -p
	  if (strcmp (argv[i], "-p") == 0)
	    {
	      if (atoi (argv[i + 1]) < 0)
		{
		  printf
		    ("Nie udało się wczytać ilości par, lub jest ona niepoprawna. Ustawiono wartość domyślną - 5 losowych par\n");
		  continue;
		}
	      generatePairs = 0;
	      pairs = malloc (sizeof (int) * atoi (argv[i + 1]) * 2);
	      pairN = atoi (argv[i + 1]);
	      for (int j = 0; j < (pairN * 2); j++)
		{
		  if ((i + 2 + j) < argc)
		    pairs[j] = atoi (argv[i + 2 + j]);
		  else
		    {
		      pairN = 0;
		      break;
		    }
		}
	      continue;
	    }
	}


      if (low > high)
	{
	  printf
	    ("Dolny zakres losowania wag jest większy niż górny. Ustawiono standardowe wartości losowania od 0 do 1\n");
	  low = 0;
	  high = 1;
	}
    }
  if (mode == 0)
    {
      printf ("Błąd argumentu! Nie podano właściwego trybu!\n");
      return 1;
    }
  if (pairN == 0)
    {
      printf ("Błąd podczas podawania par!\n");
      return 1;
    }
  return 0;

}


int
main (int argc, char **argv)
{
	srand(time(NULL));
  if(argc == 1){
	  printf("Brak argumentów!\n");
	  return 1;
  }
  if (readArguments (argc, argv) == 0)
    {
		if(mode != 1){
			printf ("mode = %d\n", mode);
			printf ("rows = %d\n", rows);
			printf ("columns = %d\n", columns);
			printf ("dec = %d\n", dec);
			printf ("low = %g\n", low);
			printf ("high = %g\n", high);
			printf ("pairN = %d\n\n", pairN);
		}
      int iterator = 1;
      for (int i = 0; i < (pairN * 2); i += 2)
	{
	  if(generatePairs == 0)
		  printf ("Para nr %d: %d %d\n", iterator, pairs[i], pairs[i + 1]);
	  iterator++;
	}
      //return 0;
    }
  else
    {
      return 1;
    }

	if(mode==1){ 
		printf("\n");
		t_pair** graph = readFile(argv[2],&rows,&columns);
		printf("rows = %d\n columns = %d\n",rows,columns);
		printGraph(graph, (rows*columns));
		int wynik = 1;
		for(int i=0; i < rows*columns; i++){
			wynik = BFS(graph,(rows*columns),i);
			if(wynik == 0) break;
		}
		if(wynik==1)
			printf("\n\n WYnik dzialania BFSa: spojny\n");
		else
			printf("\b\b Wynik dzialania BFSa: niespojny\n");
		return 0;
	}

	if(mode==3){ //If mode is set to randWeightMode, then generate graph 
		
		t_pair** graph = generateRandWeightMode(rows,columns,low,high,dec);
		printGraph(graph, (rows*columns));
		dijkstra(graph, pairN, pairs, rows, columns);
		
	}
	
	if(mode==2){ //If mode is set to allRandMode, then generate graph 
		
		t_pair** graph = generateAllRandMode(rows,columns,low,high,dec);
		printGraph(graph, (rows*columns));
		int wynik = 1;
		for(int i=0;i<(rows*columns);i++){
			wynik = BFS(graph,(rows*columns),i);
			if (wynik==0) break;
		}
			
		if(wynik==1){	
			printf("\n\n Wynik dzialania BFSa: spojny\n");
		} else{
			printf("\n\n Wynik dzialania BFSa: niespojny\n");
		}
		dijkstra(graph, pairN, pairs, rows, columns);
	}
		
	if(mode==4){
		t_pair** graph = generateConMode(rows,columns,low,high,dec);
		printGraph(graph, (rows*columns));
		int wynik = 1;
		for(int i=0;i<(rows*columns);i++){
			wynik = BFS(graph,(rows*columns),i);
			if (wynik==0) break;
		}
		if(wynik==1){	
			printf("\n\n Wynik dzialania BFSa: spojny\n");
		} else{
			printf("\n\n Wynik dzialania BFSa: niespojny\n");
		
		}	
		dijkstra(graph, pairN, pairs, rows, columns);
	}
	
}
