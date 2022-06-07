package graph.jimpgraph;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;

import java.io.FileNotFoundException;
import java.util.LinkedList;

import static javafx.scene.paint.Color.BLACK;
import static javafx.scene.paint.Color.WHITE;

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
    @FXML
    private AnchorPane mainPane;

    Graph graph = new Graph();
    final int defaultGraphSize = 15;
    final double defaultMinimum = 0;
    final double defaultMaximum = 1;

    final int graphDisplayWidth = 400;
    final int graphDisplayHeight = 376;
    final int graphDisplayStartPositionX = 14;
    final int graphDisplayStartPositionY = 15;

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
            rowsN = Integer.parseInt(rowsTextField.getText());
            if (rowsN < 2 || rowsN > 1000){
                standardOutput.appendText("Blad zwiazany z wierszami! |"+rowsN+"| to niepoprawna wartość! Ustawiono wartosc domyslna!\n");
                rowsN = 15;
                rowsTextField.setText("15");
            }
        }
        catch(NumberFormatException e){
            standardOutput.appendText("Blad zwiazany z wierszami! |"+rowsN+"| to niepoprawna wartość! Ustawiono wartosc domyslna!\n");
            rowsN = 15;
            rowsTextField.setText("15");
        }
        try{
            colsN = Integer.parseInt(columnsTextField.getText());
            if (colsN < 2 || colsN > 1000){
                standardOutput.appendText("Blad zwiazany z kolumnami! |"+colsN+"| to niepoprawna wartość! Ustawiono wartosc domyslna!\n");
                colsN = 15;
                columnsTextField.setText("15");
            }
        }
        catch(NumberFormatException e){
            standardOutput.appendText("Blad zwiazany z kolumnami! |"+colsN+"| to niepoprawna wartość! Ustawiono wartosc domyslna!\n");
            colsN = 15;
            columnsTextField.setText("15");
        }
        try{
            minN = Double.parseDouble(minWeightTextField.getText());
            if(minN < -10 || minN > 10){
                minN = 0;
                minWeightTextField.setText("0");
                standardOutput.appendText("Blad zwiazany z minimalna waga! |"+minN+"| to niepoprawna wartość! Ustawiono wartosc domyslna!\n");
            }
        }
        catch(NumberFormatException e){
            minN = 0;
            minWeightTextField.setText("0");
            standardOutput.appendText("Blad zwiazany z minimalna waga! |"+minN+"| to niepoprawna wartość! Ustawiono wartosc domyslna!\n");
        }
        try{
            maxN = Double.parseDouble(maxWeightTextField.getText());
            if(maxN - minN <= 0){
                standardOutput.appendText("Blad! Gorny zakres wag mniejszy od dolnego! Zamieniam miejscami!\n");
                double temp = maxN;
                maxN = minN;
                minN = temp;
                maxWeightTextField.setText(String.valueOf(maxN));
                minWeightTextField.setText(String.valueOf(minN));
            }
            if(maxN - minN > 100){
                standardOutput.appendText("Blad! Roznica gornego i dolnego zakresu wag jest wieksza niz 100! Ustawiono wartosci domyslne!\n");
                maxN = 1;
                minN = 0;
                maxWeightTextField.setText(String.valueOf(maxN));
                minWeightTextField.setText(String.valueOf(minN));
            }
        }
        catch(NumberFormatException e){
            maxN = 1;
            maxWeightTextField.setText("1");
            standardOutput.appendText("Blad zwiazany z maksymalna waga! |"+maxN+"| to niepoprawna wartość! Ustawiono wartosc domyslna!\n");
        }
    }

    public void drawGraph(){
        Rectangle background = new Rectangle();
        background.setFill(Color.valueOf("#616161"));
        background.setX(graphDisplayStartPositionX);
        background.setY(graphDisplayStartPositionY);
        background.setHeight(graphDisplayHeight);
        background.setWidth(graphDisplayWidth);
        mainPane.getChildren().add(background);
        int vertexToVertexWidth = graphDisplayWidth / (graph.getColumns() + 1);
        int vertexToVertexHeight = graphDisplayHeight / (graph.getRows() + 1);
        Circle[] vertex = new Circle[graph.getRows() * graph.getColumns()];
        Line[] connection = new Line[graph.getRows() * graph.getColumns() * graph.directions];
        Polygon[] arrow = new Polygon[graph.getRows() * graph.getColumns() * graph.directions * 2];
        for(int i = 0; i<graph.getRows() * graph.getColumns(); i+=graph.getColumns()){
            for(int j = 0; j<graph.getColumns(); j++) {
                vertex[i+j] = new Circle();
                vertex[i+j].setCenterX(14 + (j + 1) * vertexToVertexWidth);
                vertex[i+j].setCenterY(15 + (((i/graph.getColumns()) + 1) * vertexToVertexHeight));
                if(vertexToVertexHeight > vertexToVertexWidth)
                    vertex[i+j].setRadius((vertexToVertexWidth-1)/4);
                else
                    vertex[i+j].setRadius((vertexToVertexHeight-1)/4);
                vertex[i+j].setFill(BLACK);
                mainPane.getChildren().add(vertex[i+j]);
            }
        }

        double arrowWidth = 0.5;
        double arrowLength = 1.9;
        double arrowDistance = 0.8;

        for(int i = 0; i<graph.getRows() * graph.getColumns(); i+=graph.getColumns()) {
            for (int j = 0; j < graph.getColumns(); j++) {
                for(int k = 0; k < graph.directions; k++){
                    if(graph.getVertex(i+j,k) != graph.noConnection) {



                        connection[i + j] = new Line();
                        connection[i + j].setStrokeWidth(vertex[i + j].getRadius() / 8);
                        connection[i + j].setStroke(Color.BLACK);
                        switch(k) {
                            case 0:

                                connection[i + j].setStartX(vertex[i + j].getCenterX() + (arrowLength) * vertex[i + j].getRadius());
                                connection[i + j].setStartY(vertex[i + j].getCenterY());
                                connection[i + j].setEndX(vertex[graph.getVertex(i + j, k)].getCenterX() - (arrowLength) * vertex[i + j].getRadius());
                                connection[i + j].setEndY(vertex[graph.getVertex(i + j, k)].getCenterY());
                                break;
                            case 1:

                                connection[i + j].setStartX(vertex[i + j].getCenterX());
                                connection[i + j].setStartY(vertex[i + j].getCenterY()+ (arrowLength) * vertex[i + j].getRadius());
                                connection[i + j].setEndX(vertex[graph.getVertex(i + j, k)].getCenterX() );
                                connection[i + j].setEndY(vertex[graph.getVertex(i + j, k)].getCenterY()- (arrowLength) * vertex[i + j].getRadius());
                                break;
                            case 2:

                                connection[i + j].setStartX(vertex[i + j].getCenterX() - (arrowLength) * vertex[i + j].getRadius());
                                connection[i + j].setStartY(vertex[i + j].getCenterY());
                                connection[i + j].setEndX(vertex[graph.getVertex(i + j, k)].getCenterX() + (arrowLength) * vertex[i + j].getRadius());
                                connection[i + j].setEndY(vertex[graph.getVertex(i + j, k)].getCenterY());
                                break;
                            case 3:

                                connection[i + j].setStartX(vertex[i + j].getCenterX());
                                connection[i + j].setStartY(vertex[i + j].getCenterY()- (arrowLength) * vertex[i + j].getRadius());
                                connection[i + j].setEndX(vertex[graph.getVertex(i + j, k)].getCenterX() );
                                connection[i + j].setEndY(vertex[graph.getVertex(i + j, k)].getCenterY()+ (arrowLength) * vertex[i + j].getRadius());
                                break;
                        }




                        arrow[i + j] = new Polygon();
                        switch (k) {
                            case 0:
                                arrow[i + j].getPoints().setAll(
                                        vertex[graph.getVertex(i + j, k)].getCenterX() - arrowDistance * vertex[i + j].getRadius(),
                                        vertex[graph.getVertex(i + j, k)].getCenterY(),

                                        vertex[graph.getVertex(i + j, k)].getCenterX() - vertex[i + j].getRadius() * arrowLength,
                                        vertex[graph.getVertex(i + j, k)].getCenterY() - vertex[i + j].getRadius() * arrowWidth,

                                        vertex[graph.getVertex(i + j, k)].getCenterX() - vertex[i + j].getRadius() *arrowLength,
                                        vertex[graph.getVertex(i + j, k)].getCenterY() + vertex[i + j].getRadius() * arrowWidth

                                );
                                break;
                            case 1:
                                arrow[i + j].getPoints().setAll(
                                        vertex[graph.getVertex(i + j, k)].getCenterX() ,
                                        vertex[graph.getVertex(i + j, k)].getCenterY()- arrowDistance * vertex[i + j].getRadius(),

                                        vertex[graph.getVertex(i + j, k)].getCenterX() + vertex[i + j].getRadius() * arrowWidth,
                                        vertex[graph.getVertex(i + j, k)].getCenterY() - vertex[i + j].getRadius() * arrowLength,

                                        vertex[graph.getVertex(i + j, k)].getCenterX() - vertex[i + j].getRadius() * arrowWidth,
                                        vertex[graph.getVertex(i + j, k)].getCenterY() - vertex[i + j].getRadius() * arrowLength

                                );
                                break;
                            case 2:
                                arrow[i + j].getPoints().setAll(
                                        vertex[graph.getVertex(i + j, k)].getCenterX() + arrowDistance * vertex[i + j].getRadius(),
                                        vertex[graph.getVertex(i + j, k)].getCenterY(),

                                        vertex[graph.getVertex(i + j, k)].getCenterX() + vertex[i + j].getRadius() * arrowLength,
                                        vertex[graph.getVertex(i + j, k)].getCenterY() - vertex[i + j].getRadius() * arrowWidth,

                                        vertex[graph.getVertex(i + j, k)].getCenterX() + vertex[i + j].getRadius() * arrowLength,
                                        vertex[graph.getVertex(i + j, k)].getCenterY() + vertex[i + j].getRadius() * arrowWidth

                                );
                                break;
                            case 3:
                                arrow[i + j].getPoints().setAll(
                                        vertex[graph.getVertex(i + j, k)].getCenterX() ,
                                        vertex[graph.getVertex(i + j, k)].getCenterY()+ arrowDistance * vertex[i + j].getRadius(),

                                        vertex[graph.getVertex(i + j, k)].getCenterX() + vertex[i + j].getRadius() * arrowWidth,
                                        vertex[graph.getVertex(i + j, k)].getCenterY() + vertex[i + j].getRadius() * arrowLength,

                                        vertex[graph.getVertex(i + j, k)].getCenterX() - vertex[i + j].getRadius() * arrowWidth,
                                        vertex[graph.getVertex(i + j, k)].getCenterY() + vertex[i + j].getRadius() * arrowLength

                                );
                                break;
                        }
                        arrow[i + j].setFill((Color.valueOf(graph.getColorOfWeight(i + j, k))));

                        mainPane.getChildren().add(arrow[i+j]);
                        mainPane.getChildren().add(connection[i+j]);


                    }
                }
            }
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
            drawGraph();
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