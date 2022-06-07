package graph.jimpgraph;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;

import java.io.FileNotFoundException;

public class HelloController {
    @FXML
    TextField getPathToGraphTextField;
    @FXML
    TextArea standardOutput;
    @FXML
    CheckBox allRandModeCheckBox;
    @FXML
    CheckBox conModeCheckBox;
    @FXML
    CheckBox randWeightModeCheckBox;
    @FXML
    CheckBox colorWeightCheckBox;
    @FXML
    CheckBox onlyPathCheckBox;
    @FXML
    TextField rowsTextField;
    @FXML
    TextField columnsTextField;
    @FXML
    TextField minWeightTextField;
    @FXML
    TextField maxWeightTextField;
    @FXML
    TextField startDijkstraTextField;
    @FXML
    TextField endDijkstraTextField;
    @FXML
    TextField readFileTextField;
    @FXML
    AnchorPane mainPane;

    public static int numberOfClick = 0;
    public static boolean isGreenChosen = false;
    public static boolean isRedChosen = false;

    ClickableCircle[] clickableCircle;
    Graph graph = new Graph();
    boolean isGraphGenerated = false;
    boolean isDijkstraUsed = false;
    final int justDrawGraph = -1;
    int startDijkstra = 0, endDijkstra = 1;
    int mode = 0;

    public void setRandWeightMode(){
        mode = Display.setModeCheckList(randWeightModeCheckBox, allRandModeCheckBox, conModeCheckBox, 1);
    }
    public void setAllRandMode(){
        mode = Display.setModeCheckList(randWeightModeCheckBox, allRandModeCheckBox, conModeCheckBox, 2);
    }
    public void setConMode(){
        mode = Display.setModeCheckList(randWeightModeCheckBox, allRandModeCheckBox, conModeCheckBox, 3);
    }

    void submitDijkstraPoints(){
        try {
            startDijkstra = Integer.parseInt(startDijkstraTextField.getText());
            if (startDijkstra < 0 || startDijkstra > graph.getRows() * graph.getColumns()){
                standardOutput.appendText("Blad zwiazany z wierzcholkiem startowym! |"+startDijkstra+"| To niepoprawna wartość! Ustawiono wartosc domyslna\n");
                startDijkstra = 0;
                startDijkstraTextField.setText("0");
            }
        } catch (NumberFormatException e) {
            standardOutput.appendText("Blad zwiazany z wierzcholkiem startowym! |"+startDijkstra+"| To niepoprawna wartość! Ustawiono wartosc domyslna\n");
            startDijkstra = 0;
            startDijkstraTextField.setText("0");
        }
        try {
            endDijkstra = Integer.parseInt(endDijkstraTextField.getText());
            if (endDijkstra < 0 || (endDijkstra > graph.getRows() * graph.getColumns() && endDijkstra != 1)) {
                standardOutput.appendText("Blad zwiazany z wierzcholkiem koncowym! |" +endDijkstra+"| to niepoprawna wartość! Ustawiono wartosc domyslna\n");
                endDijkstra = 1;
                endDijkstraTextField.setText("1");
            }
        } catch (NumberFormatException e) {
            standardOutput.appendText("Blad zwiazany z wierzcholkiem koncowym! |" +endDijkstra+"| to niepoprawna wartość! Ustawiono wartosc domyslna\n");
            endDijkstra = 1;
            endDijkstraTextField.setText("1");
        }
    }
    public void submitGraphSpecs(){
        Display.initializeGraphSpecs(standardOutput, graph,
                rowsTextField, columnsTextField,
                minWeightTextField, maxWeightTextField);
    }

    public void onActivateColorCheckBox(){
        if(isGraphGenerated) {
            if(!isDijkstraUsed)
                clickableCircle = Display.drawGraph(mainPane, graph, justDrawGraph, justDrawGraph,
                    standardOutput, colorWeightCheckBox, onlyPathCheckBox);
            else
                clickableCircle = Display.drawGraph(mainPane, graph, startDijkstra, endDijkstra,
                        standardOutput, colorWeightCheckBox, onlyPathCheckBox);
        }
    }

    public void generateGraph(){
        if(mode != 0) {
            submitGraphSpecs();
            isDijkstraUsed = false;
            isGraphGenerated = true;
            graph.initializeGraph(graph.getRowsN(), graph.getColumnsN(), graph.getMinWeightN(), graph.getMaxWeightN(), mode);
            standardOutput.appendText("Generowanie grafu!\nWlasciwosci:\n");
            switch (graph.getMode()) {
                case 1 -> {
                    standardOutput.appendText("\nTryb kartka w kratke\n");
                    Generator.generateRandWeightMode(graph);
                }
                case 2 -> {
                    standardOutput.appendText("\nTryb losowy\n");
                    Generator.generateAllRandMode(graph);
                }
                case 3 -> {
                    standardOutput.appendText("\nTryb spojnosci\n");
                    Generator.generateConMode(graph);
                }
                default -> standardOutput.appendText("\nBlad trybu\n");
            }
            clickableCircle = Display.drawGraph(mainPane, graph, justDrawGraph, justDrawGraph,
                    standardOutput,colorWeightCheckBox,onlyPathCheckBox);
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
        if(isGraphGenerated) {
            submitDijkstraPoints();
            isDijkstraUsed = true;
            clickableCircle = Display.drawGraph(mainPane, graph, startDijkstra, endDijkstra,
                    standardOutput, colorWeightCheckBox, onlyPathCheckBox);
        }
        else
            standardOutput.appendText("Nie wygenerowano grafu!\n");
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
            if(FileManager.readFile(path,graph) == 0){
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