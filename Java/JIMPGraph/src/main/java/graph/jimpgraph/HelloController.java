package graph.jimpgraph;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.CheckBox;

public class HelloController {
    @FXML
    private CheckBox allRandModeCheckBox;
    @FXML
    private CheckBox conModeCheckBox;
    @FXML
    private CheckBox randWeightModeCheckBox;
    @FXML
    private Button generateButton;
    @FXML
    private Button searchButton;
    @FXML
    private Button loadButton;
    @FXML
    private Button saveButton;
    @FXML
    private Button bfsButton;
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

    int startDijkstra = 0, endDijkstra = 1;
    int mode = 0;
    int rowsN = 15, colsN = 15, rowsGraph = 0, colsGraph = 0;
    double minN = 0, maxN = 1, minGraph = 0, maxGraph = 1;

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

    public void sumbitStartDijkstra(ActionEvent event){
        try{
            startDijkstra = Integer.parseInt(startDijkstraTextField.getText());
        }
        catch(NumberFormatException e){
            startDijkstra = 0;
            startDijkstraTextField.setText("0");
            System.out.println("Blad zwiazany z wierzcholkiem startowym! Ustawiono wartosc domyslna");
        }
    }

    public void sumbitEndDijkstra(ActionEvent event){
        try{
            endDijkstra = Integer.parseInt(startDijkstraTextField.getText());
        }
        catch(NumberFormatException e){
            endDijkstra = 1;
            endDijkstraTextField.setText("0");
            System.out.println("Blad zwiazany z wierzcholkiem startowym! Ustawiono wartosc domyslna");
        }
    }
    public void sumbitRows(ActionEvent event){
        try{
            rowsN = Integer.parseInt(rowsTextField.getText());
        }
        catch(NumberFormatException e){
            rowsN = 15;
            rowsTextField.setText("15");
            System.out.println("Blad zwiazany z wierszami! Ustawiono wartosc domyslna!");
        }
    }

    public void sumbitColumns(ActionEvent event){
        try{
            colsN = Integer.parseInt(columnsTextField.getText());
        }
        catch(NumberFormatException e){
            colsN = 15;
            columnsTextField.setText("15");
            System.out.println("Blad zwiazany z kolumnami! Ustawiono wartosc domyslna!");
        }
    }

    public void sumbitMin(ActionEvent event){
        try{
            minN = Double.parseDouble(minWeightTextField.getText());
        }
        catch(NumberFormatException e){
            colsN = 0;
            minWeightTextField.setText("0");
            System.out.println("Blad zwiazany z minimalna waga! Ustawiono wartosc domyslna!");
        }
    }
    public void sumbitMax(ActionEvent event){
        try{
            maxN = Double.parseDouble(maxWeightTextField.getText());
        }
        catch(NumberFormatException e){
            maxN = 1;
            maxWeightTextField.setText("1");
            System.out.println("Blad zwiazany z maksymalna waga! Ustawiono wartosc domyslna!");
        }
    }
    public void generateGraph(ActionEvent event){
        if(mode != 0) {
            rowsGraph = rowsN;
            colsGraph = colsN;
            minGraph = minN;
            maxGraph = maxN;
            switch (mode) {
                case 1 -> System.out.println("Tryb randWeightMode");
                case 2 -> System.out.println("Tryb allRandMode");
                case 3 -> System.out.println("Tryb conMode");
                default -> System.out.println("Błąd trybu!");
            }
            System.out.println(rowsGraph);
            System.out.println(colsGraph);
            System.out.println(minGraph);
            System.out.println(maxGraph);
        }
        else{
            System.out.println("Wybierz tryb!");
        }
    }

}