package graph.jimpgraph;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.AnchorPane;
import java.io.FileNotFoundException;
import java.util.LinkedList;

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
    Graph graph = new Graph();
    boolean isGraphGenerated = false;

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
        standardOutput.setText("");
        try{
            graph.setRowsN(Integer.parseInt(rowsTextField.getText()));
            if (graph.getRowsN() < 2 || graph.getRowsN() > 1000){
                standardOutput.appendText("Blad zwiazany z wierszami! |"+graph.getRowsN()+"| to niepoprawna wartość! Ustawiono wartosc domyslna!\n");
                graph.setRowsN(15);
                rowsTextField.setText("15");
            }
        }
        catch(NumberFormatException e){
            standardOutput.appendText("Blad zwiazany z wierszami! |"+graph.getRowsN()+"| to niepoprawna wartość! Ustawiono wartosc domyslna!\n");
            graph.setRowsN(15);
            rowsTextField.setText("15");
        }
        try{
            graph.setColumnsN(Integer.parseInt(columnsTextField.getText()));
            if (graph.getColumnsN() < 2 || graph.getColumnsN() > 1000){
                standardOutput.appendText("Blad zwiazany z kolumnami! |"+graph.getColumnsN()+"| to niepoprawna wartość! Ustawiono wartosc domyslna!\n");
                graph.setColumnsN(15);
                columnsTextField.setText("15");
            }
        }
        catch(NumberFormatException e){
            standardOutput.appendText("Blad zwiazany z kolumnami! |"+graph.getColumnsN()+"| to niepoprawna wartość! Ustawiono wartosc domyslna!\n");
            graph.setColumnsN(15);
            columnsTextField.setText("15");
        }
        try{
            graph.setMinWeightN(Double.parseDouble(minWeightTextField.getText()));
            if(graph.getMinWeightN() < -10 || graph.getMinWeightN() > 10){
                graph.setMinWeightN(0);
                minWeightTextField.setText("0");
                standardOutput.appendText("Blad zwiazany z minimalna waga! |"+graph.getMinWeightN()+"| to niepoprawna wartość! Ustawiono wartosc domyslna!\n");
            }
        }
        catch(NumberFormatException e){
            graph.setMinWeightN(0);
            minWeightTextField.setText("0");
            standardOutput.appendText("Blad zwiazany z minimalna waga! |"+graph.getMinWeightN()+"| to niepoprawna wartość! Ustawiono wartosc domyslna!\n");
        }
        try{
            graph.setMaxWeightN(Double.parseDouble(maxWeightTextField.getText()));
            if(graph.getMaxWeightN() - graph.getMinWeightN() <= 0){
                standardOutput.appendText("Blad! Gorny zakres wag mniejszy od dolnego! Zamieniam miejscami!\n");
                double temp = graph.getMaxWeightN();
                graph.setMaxWeightN(graph.getMinWeightN());
                graph.setMinWeightN(temp);
                maxWeightTextField.setText(String.valueOf(graph.getMaxWeightN()));
                minWeightTextField.setText(String.valueOf(graph.getMinWeightN()));
            }
            if(graph.getMaxWeightN() - graph.getMinWeightN() > 100){
                standardOutput.appendText("Blad! Roznica gornego i dolnego zakresu wag jest wieksza niz 100! Ustawiono wartosci domyslne!\n");
                graph.setMaxWeightN(1);
                graph.setMinWeightN(0);
                maxWeightTextField.setText(String.valueOf(graph.getMaxWeightN()));
                minWeightTextField.setText(String.valueOf(graph.getMinWeightN()));
            }
        }
        catch(NumberFormatException e){
            graph.setMaxWeightN(1);
            maxWeightTextField.setText("1");
            standardOutput.appendText("Blad zwiazany z maksymalna waga! |"+graph.getMaxWeightN()+"| to niepoprawna wartość! Ustawiono wartosc domyslna!\n");
        }
    }

    public void generateGraph(){
        if(mode != 0) {
            submitGraphSpecs();
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
            Display.drawGraph(mainPane, graph);
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
            if(Dijkstra.dijkstra(graph, startDijkstra, endDijkstra)!=null) {
                path = Dijkstra.dijkstra(graph, startDijkstra, endDijkstra);
                assert path != null;
                standardOutput.appendText("Znaleziona sciezka " + path + "\n");
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