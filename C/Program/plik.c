#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "generator.h"
#include "plik.h"
 void
printGraphToFile (t_pair ** graph, int rows, int cols)
{
  int n = rows * cols;
  char *path = "graph";
  char *date = malloc (sizeof (char) * 25);
   time_t t = time (NULL);
  struct tm tm = *localtime (&t);
  sprintf (date, "graph_%d-%02d-%02d_%02d:%02d:%02d", tm.tm_year + 1900,
	    tm.tm_mon + 1, tm.tm_mday, tm.tm_hour, tm.tm_min, tm.tm_sec);
  printf ("TEST: %s\n", date);
   FILE * out = fopen (date, "w");
   fprintf (out, "%d %d\n", rows, cols);
  for (int i = 0; i < n; i++)
    {
      fprintf (out, "	 ");
      for (int j = 0; j < 4; j++)
	{
	  if (graph[i][j].vertex == -1)
	    continue;
	  fprintf (out, "%d :%f  ", graph[i][j].vertex, graph[i][j].weight);
	}
      fprintf (out, "\n");
    }
}

 t_pair ** readFile (char *path, int *rows, int *cols)
{
  t_pair ** graph;
  FILE * in = fopen (path, "r");
  fscanf (in, "%d %d", rows, cols);
  graph = malloc ((*rows) * (*cols) * (sizeof (t_pair *)));
  for (int i = 0; i < (*rows) * (*cols); i++)
    {
      graph[i] = malloc (sizeof (t_pair) * 4);
    }
  for (int i = 0; i < (*rows) * (*cols); i++)
    {
      for (int j = 0; j < 4; j++)
	{
	  graph[i][j].vertex = -1;
    } } int currentNode = 0;
  for (int i = 0; i < (*rows) * (*cols) + 1; i++)
    {
      int currentConnect = 0;
      char line[250];
      fgets (line, 250, in);
      char corrector[] = "	 :\n";
      char *safe = malloc (sizeof (char) * 50);
      safe = strtok (line, corrector);
      if (i != 0)
	{
	  while (safe != NULL)
	    
	    {
	      graph[currentNode][currentConnect].vertex = atoi (safe);
	      safe = strtok (NULL, corrector);
	      graph[currentNode][currentConnect].weight = atof (safe);
	      safe = strtok (NULL, corrector);
	      currentConnect++;
	    }
	  currentNode++;
	}
    }
  return graph;
}


