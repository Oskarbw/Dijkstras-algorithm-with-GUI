package graph.jimpgraph;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.CheckBox;

import java.io.FileNotFoundException;
import java.util.Iterator;
import java.util.LinkedList;

public class HelloController {
    @FXML
    private TextArea standardOutput;
    @FXML
    private CheckBox allRandModeCheckBox;
    @FXML
    private CheckBox conModeCheckBox;
    @FXML
    private CheckBox randWeightModeCheckBox;
    @FXML
    private TextField rowsTextField;
    @FXML
    private TextField columnsTextField;
    @FXML
    private TextField minWeightTextField;
    @FXML
    private TextField maxWeightTextField;
    @FXML
    private TextField startDijkstraTextField;
    @FXML
    private TextField endDijkstraTextField;
    @FXML
    private TextField readFileTextField;

    Graph graph = new Graph();

    Generator generate = new Generator();

    FileManager fm = new FileManager();

    Dijkstra dijkstra = new Dijkstra();

    Bfs bfs = new Bfs();

    final int defaultGraphSize = 15;
    final double defaultMinimum = 0;
    final double defaultMaximum = 1;

    boolean isGraphGenerated = false;

    int startDijkstra = 0, endDijkstra = 1;
    int mode = 0;
    int rowsN = defaultGraphSize, colsN = defaultGraphSize;
    double minN = defaultMinimum, maxN = defaultMaximum;

    public void setRandWeightMode(ActionEvent event){
        if(!randWeightModeCheckBox.isSelected()){
            randWeightModeCheckBox.setSelected(false);
        }
        else{
            allRandModeCheckBox.setSelected(false);
            conModeCheckBox.setSelected(false);
            randWeightModeCheckBox.setSelected(true);
            mode = 1;
        }

    }
    public void setAllRandMode(ActionEvent event){
        if(!allRandModeCheckBox.isSelected()){
            allRandModeCheckBox.setSelected(false);
        }
        else{
            conModeCheckBox.setSelected(false);
            randWeightModeCheckBox.setSelected(false);
            allRandModeCheckBox.setSelected(true);
            mode = 2;
        }
    }
    public void setConMode(ActionEvent event){
        if(!conModeCheckBox.isSelected()){
            conModeCheckBox.setSelected(false);
        }
        else{
            randWeightModeCheckBox.setSelected(false);
            allRandModeCheckBox.setSelected(false);
            conModeCheckBox.setSelected(true);
            mode = 3;
        }
    }



    public void submitStartDijkstra(ActionEvent event){
        if(isGraphGenerated) {
            try {
                startDijkstra = Integer.parseInt(startDijkstraTextField.getText());
                if (startDijkstra < 0 || startDijkstra > graph.rows * graph.columns){
                    throw new NumberFormatException();
                }
            } catch (NumberFormatException e) {
                startDijkstra = 0;
                startDijkstraTextField.setText("0");
                standardOutput.appendText("Blad zwiazany z wierzcholkiem startowym! Ustawiono wartosc domyslna\n");
            }
        }
        else{
            standardOutput.appendText("Nie wygenerowano grafu!\n");
        }
    }

    public void submitEndDijkstra(ActionEvent event){
        if(isGraphGenerated) {
            try {
                endDijkstra = Integer.parseInt(endDijkstraTextField.getText());
                if (endDijkstra < 0 || endDijkstra > graph.rows * graph.columns) {
                    throw new NumberFormatException();
                }
            } catch (NumberFormatException e) {
                endDijkstra = 1;
                endDijkstraTextField.setText("1");
                standardOutput.appendText("Blad zwiazany z wierzcholkiem koncowym! Ustawiono wartosc domyslna\n");
            }
        }
        else{
            standardOutput.appendText("Nie wygenerowano grafu!\n");
        }
    }
    public void submitRows(ActionEvent event){
        try{
            rowsN = Integer.parseInt(rowsTextField.getText());
            if (rowsN < 2){
                throw new NumberFormatException();
            }
        }
        catch(NumberFormatException e){
            rowsN = 15;
            rowsTextField.setText("15");
            standardOutput.appendText("Blad zwiazany z wierszami! Ustawiono wartosc domyslna!\n");
        }
    }

    public void submitColumns(ActionEvent event){
        try{
            colsN = Integer.parseInt(columnsTextField.getText());
            if (colsN < 2){
                throw new NumberFormatException();
            }
        }
        catch(NumberFormatException e){
            colsN = 15;
            columnsTextField.setText("15");
            standardOutput.appendText("Blad zwiazany z kolumnami! Ustawiono wartosc domyslna!\n");
        }
    }

    public void submitMin(ActionEvent event){
        try{
            minN = Double.parseDouble(minWeightTextField.getText());
        }
        catch(NumberFormatException e){
            colsN = 0;
            minWeightTextField.setText("0");
            standardOutput.appendText("Blad zwiazany z minimalna waga! Ustawiono wartosc domyslna!\n");
        }
    }
    public void submitMax(ActionEvent event){
        try{
            maxN = Double.parseDouble(maxWeightTextField.getText());
        }
        catch(NumberFormatException e){
            maxN = 1;
            maxWeightTextField.setText("1");
            standardOutput.appendText("Blad zwiazany z maksymalna waga! Ustawiono wartosc domyslna!\n");
        }
    }
    public void generateGraph(ActionEvent event){
        if(mode != 0) {
            isGraphGenerated = true;
            graph.initializeGraph(rowsN,colsN,minN,maxN,mode);
            standardOutput.setText("Generowanie grafu!\nWlasciwosci:\n");
            switch (mode) {
                case 1 -> {
                    standardOutput.appendText("\nTryb kartka w kratke\n");
                    generate.generateRandWeightMode(graph);
                }
                case 2 -> {
                    standardOutput.appendText("\nTryb losowy\n");
                    generate.generateAllRandMode(graph);
                }
                case 3 -> {
                    standardOutput.appendText("\nTryb spojnosci\n");
                    generate.generateConMode(graph);
                }
                default -> standardOutput.appendText("\nBlad trybu\n");
            }
            standardOutput.appendText("Wiersze: "+graph.rows+"\n");
            standardOutput.appendText("Kolumny: "+graph.columns+"\n");
            standardOutput.appendText("Dolny zakres: "+graph.min+"\n");
            standardOutput.appendText("Górny zakres: "+graph.max+"\n");
        }
        else{
            standardOutput.appendText("Wybierz tryb!\n");
        }
    }

    public void searchGraph(ActionEvent event){


        LinkedList<Integer> path;
        if(isGraphGenerated){
            path = dijkstra.dijkstra(graph, startDijkstra, endDijkstra);
            standardOutput.appendText("Znaleziona sciezka " + path.toString()+"\n");

            int tmp2 = path.removeFirst();
            double tmpLength;
            String withWeightsCommunicate = new String();
            withWeightsCommunicate = String.valueOf(tmp2);
            while(!path.isEmpty()){

                tmpLength = dijkstra.pathLength[path.peekFirst()] - dijkstra.pathLength[tmp2];
                tmp2 = path.removeFirst();
                withWeightsCommunicate = withWeightsCommunicate + "-(" + String.format("%.03f", tmpLength) + ")->"+ tmp2;
            }
            standardOutput.appendText("Z wagami: "+ withWeightsCommunicate+"\n");
        }
        else{
            standardOutput.appendText("Nie wygenerowano grafu!\n");
        }
    }

    public void readGraph(ActionEvent event){
        readFileTextField.setVisible(true);
        readFileTextField.setEditable(true);
    }

    public void submitPath(ActionEvent event){
        String path = readFileTextField.getText();
        readFileTextField.setVisible(false);
        readFileTextField.setEditable(false);
        try{
            if(fm.readFile(path,graph) == 0){
                standardOutput.appendText("Udalo sie wczytac graf!\n");
                isGraphGenerated = true;
            }
            else{
                standardOutput.appendText("Nie udalo sie wczytac grafu!\n");
            }
        }catch(FileNotFoundException e){
            standardOutput.appendText("Nie udalo sie wczytac grafu!\n");
        }
    }

    public void runBfs(ActionEvent event){
        if(isGraphGenerated) {
            if (Bfs.BFS(graph, 0) == 1)
                standardOutput.appendText("Graf jest spójny!\n");
            else
                standardOutput.appendText("Graf jest niespójny\n");
        }
        else
            standardOutput.appendText("Nie wygenerowano grafu!\n");
    }

}