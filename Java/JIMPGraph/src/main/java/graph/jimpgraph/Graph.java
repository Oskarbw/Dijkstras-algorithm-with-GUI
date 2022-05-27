package graph.jimpgraph;

public class Graph {

    final int noConnection = -1;
    final int directions = 4;
    private int rows;
    private int columns;
    private int mode;
    private double min;
    private double max;


    private int[][] vertex;
    private double[][] weight;

    int getVertex(int a, int b){
        return vertex[a][b];
    }
    double getWeight(int a, int b) {
        return weight[a][b];
    }

    void setVertex(int a, int b, int c){
        vertex[a][b] = c;
    }

    void setWeight(int a, int b, double c){
        weight[a][b] = c;
    }

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
