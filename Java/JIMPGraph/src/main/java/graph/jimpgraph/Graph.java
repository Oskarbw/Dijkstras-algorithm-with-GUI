package graph.jimpgraph;

public class Graph {

    final int noConnection = -1;
    final int directions = 4;
    private int rows;
    private int columns;
    private int mode;
    private double min;
    private double max;


    public int[][] vertex;
    public double[][] weight;

   int getRows(){
        return rows;
   }
   int getColumns(){
       return columns;
   }
   int getMode(){
       return mode;
   }
   double getMinWeight(){
       return min;
   }
   double getMaxWeight(){
       return max;
   }

    void initializeGraph(int r, int c, double minG, double maxG, int m){
        rows = r;
        columns = c;
        min = minG;
        max = maxG;
        mode = m;
        vertex = new int[rows*columns][directions];
        weight = new double[rows*columns][directions];
        for(int i=0; i<rows*columns; i++){
            for(int j=0; j<directions; j++){
                vertex[i][j] = noConnection;
            }
        }
    }
}
