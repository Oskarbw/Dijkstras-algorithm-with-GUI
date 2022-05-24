package graph.jimpgraph;

public class Graph {

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
    }
}
