package graph.jimpgraph;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.CheckBox;

import java.io.FileNotFoundException;
import java.util.LinkedList;

public class HelloController {
    @FXML
    private TextField getPathToGraphTextField;
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

    final int defaultGraphSize = 15;
    final double defaultMinimum = 0;
    final double defaultMaximum = 1;

    boolean isGraphGenerated = false;

    int startDijkstra = 0, endDijkstra = 1;
    int mode = 0;
    int rowsN = defaultGraphSize, colsN = defaultGraphSize;
    double minN = defaultMinimum, maxN = defaultMaximum;

    public void setRandWeightMode(){
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
    public void setAllRandMode(){
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
    public void setConMode(){
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

    void submitDijkstraPoints(){
        try {
            startDijkstra = Integer.parseInt(startDijkstraTextField.getText());
            if (startDijkstra < 0 || startDijkstra > graph.getRows() * graph.getColumns()){
                throw new NumberFormatException();
            }
        } catch (NumberFormatException e) {
            startDijkstra = 0;
            startDijkstraTextField.setText("0");
            standardOutput.appendText("Blad zwiazany z wierzcholkiem startowym! Ustawiono wartosc domyslna\n");
        }
        try {
            endDijkstra = Integer.parseInt(endDijkstraTextField.getText());
            if (endDijkstra < 0 || (endDijkstra > graph.getRows() * graph.getColumns() && endDijkstra != 1)) {
                throw new NumberFormatException();
            }
        } catch (NumberFormatException e) {
            endDijkstra = 1;
            endDijkstraTextField.setText("1");
            standardOutput.appendText("Blad zwiazany z wierzcholkiem koncowym! Ustawiono wartosc domyslna\n");
        }
    }
    public void submitGraphSpecs(){
        standardOutput.setText("");
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
        try{
            minN = Double.parseDouble(minWeightTextField.getText());
        }
        catch(NumberFormatException e){
            colsN = 0;
            minWeightTextField.setText("0");
            standardOutput.appendText("Blad zwiazany z minimalna waga! Ustawiono wartosc domyslna!\n");
        }
        try{
            maxN = Double.parseDouble(maxWeightTextField.getText());
        }
        catch(NumberFormatException e){
            maxN = 1;
            maxWeightTextField.setText("1");
            standardOutput.appendText("Blad zwiazany z maksymalna waga! Ustawiono wartosc domyslna!\n");
        }
    }

    public void generateGraph(){
        if(mode != 0) {
            submitGraphSpecs();
            isGraphGenerated = true;
            graph.initializeGraph(rowsN,colsN,minN,maxN,mode);
            standardOutput.appendText("Generowanie grafu!\nWlasciwosci:\n");
            switch (graph.getMode()) {
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
            standardOutput.appendText("Wiersze: "+graph.getRows()+"\n");
            standardOutput.appendText("Kolumny: "+graph.getColumns()+"\n");
            standardOutput.appendText("Dolny zakres: "+graph.getMinWeight()+"\n");
            standardOutput.appendText("Górny zakres: "+graph.getMaxWeight()+"\n");
        }
        else{
            standardOutput.appendText("Wybierz tryb!\n");
        }
    }

    public void searchGraph(){
        submitDijkstraPoints();
        LinkedList<Integer> path;
        if(!isGraphGenerated){
            standardOutput.appendText("Nie wygenerowano grafu!\n");
        }
        else{
            if(dijkstra.dijkstra(graph, startDijkstra, endDijkstra)!=null) {
                path = dijkstra.dijkstra(graph, startDijkstra, endDijkstra);
                standardOutput.appendText("Znaleziona sciezka " + path.toString() + "\n");

                int tmp2 = path.removeFirst();
                double tmpLength;
                StringBuilder withWeightsCommunicate;
                withWeightsCommunicate = new StringBuilder(String.valueOf(tmp2));
                while (!path.isEmpty()) {

                    tmpLength = Dijkstra.getPathLength(path.peekFirst()) - Dijkstra.getPathLength(tmp2);
                    tmp2 = path.removeFirst();
                    withWeightsCommunicate.append("-(").append(String.format("%.03f", tmpLength)).append(")->").append(tmp2);
                }
                standardOutput.appendText("Z wagami: " + withWeightsCommunicate + "\n");
            }
            else
                standardOutput.appendText("Sciezka nie istnieje!" + "\n");
        }
    }

    public void readGraph(){
        getPathToGraphTextField.setVisible(true);
        readFileTextField.setVisible(true);
        readFileTextField.setEditable(true);
    }

    public void submitPath(){
        String path = readFileTextField.getText();
        readFileTextField.setVisible(false);
        readFileTextField.setEditable(false);
        getPathToGraphTextField.setVisible(false);
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

    public void runBfs(){
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