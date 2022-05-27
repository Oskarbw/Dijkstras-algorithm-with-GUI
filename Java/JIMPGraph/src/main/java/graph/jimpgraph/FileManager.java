package graph.jimpgraph;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class FileManager {
    public int readFile(String path, Graph graph) throws FileNotFoundException{
        File file = new File(path);
        Scanner sc = new Scanner(file);
        String parameters = sc.nextLine();
        String[] parsedParameters = parameters.split(" ");
        try {
            graph.initializeGraph(Integer.parseInt(parsedParameters[0]), Integer.parseInt(parsedParameters[1]), 0, 1, 0);
            int numberOfLine = 0;
            while (sc.hasNextLine()) {
                int numberOfConnections = 0;
                String line = sc.nextLine();
                String[] parsedLine = line.split(" ");
                for (int i = 0; i < parsedLine.length; i += 3) {
                    graph.setVertex(numberOfLine, numberOfConnections, Integer.parseInt(parsedLine[i + 1]));
                    parsedLine[i + 2] = parsedLine[i + 2].replace(':', '0');
                    graph.setWeight(numberOfLine, numberOfConnections, Double.parseDouble(parsedLine[i + 2]));
                    numberOfConnections += 1;
                }
                numberOfLine += 1;
            }
            return 0;
        }
        catch(NumberFormatException e){
            return 1;
        }
    }
}
