package graph.jimpgraph;

public class Graph {

    final int noConnection = -1;
    final int directions = 4;
    public int rows;
    public int columns;
    public int mode;
    public double min;
    public double max;

    public int[][] vertex;
    public double[][] weight;

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
