package graph.jimpgraph;

import javafx.scene.control.CheckBox;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;

import static javafx.scene.paint.Color.BLACK;

public class Display extends HelloController{

    static int setModeCheckList(CheckBox randWeightModeCheckBox, CheckBox allRandModeCheckBox, CheckBox conModeCheckBox, int chosen){
        switch(chosen){
            case 1 -> {
                if(!randWeightModeCheckBox.isSelected()){
                    randWeightModeCheckBox.setSelected(false);
                }
                else{
                    allRandModeCheckBox.setSelected(false);
                    conModeCheckBox.setSelected(false);
                    randWeightModeCheckBox.setSelected(true);
                    return 1;
                }
            }
            case 2 -> {
                if(!allRandModeCheckBox.isSelected()){
                    allRandModeCheckBox.setSelected(false);
                }
                else{
                    conModeCheckBox.setSelected(false);
                    randWeightModeCheckBox.setSelected(false);
                    allRandModeCheckBox.setSelected(true);
                    return 2;
                }
            }
            case 3 ->{
                if(!conModeCheckBox.isSelected()){
                    conModeCheckBox.setSelected(false);
                }
                else{
                    randWeightModeCheckBox.setSelected(false);
                    allRandModeCheckBox.setSelected(false);
                    conModeCheckBox.setSelected(true);
                    return 3;
                }
            }
        }
        return 0;
    }
    static void drawGraph(AnchorPane mainPane, Graph graph){
        Rectangle background = new Rectangle();
        background.setFill(Color.valueOf("#616161"));
        background.setX(graph.graphDisplayStartPositionX);
        background.setY(graph.graphDisplayStartPositionY);
        background.setHeight(graph.graphDisplayHeight);
        background.setWidth(graph.graphDisplayWidth);
        mainPane.getChildren().add(background);
        int vertexToVertexWidth = graph.graphDisplayWidth / (graph.getColumns() + 1);
        int vertexToVertexHeight = graph.graphDisplayHeight / (graph.getRows() + 1);
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
                vertex[i+j].setStroke(BLACK);
                vertex[i+j].setStrokeWidth(vertex[i+j].getRadius()/6);

                mainPane.getChildren().add(vertex[i+j]);
            }
        }

        double arrowWidth = 0.5;
        double arrowLength = 1.9;
        double arrowDistance = 0.8;

        for(int i = 0; i<graph.getRows() * graph.getColumns(); i+=graph.getColumns()) {
            for (int j = 0; j < graph.getColumns(); j++) {
                for (int k = 0; k < graph.directions; k++) {
                    if (graph.getVertex(i + j, k) != graph.noConnection) {
                        connection[i + j] = new Line();
                        connection[i + j].setStrokeWidth(vertex[i + j].getRadius() / 8);
                        connection[i + j].setStroke(Color.BLACK);
                        switch (k) {
                            case 0 -> {
                                connection[i + j].setStartX(vertex[i + j].getCenterX() + vertex[i + j].getRadius());
                                connection[i + j].setStartY(vertex[i + j].getCenterY());
                                connection[i + j].setEndX(vertex[graph.getVertex(i + j, k)].getCenterX() - vertex[i + j].getRadius());
                                connection[i + j].setEndY(vertex[graph.getVertex(i + j, k)].getCenterY());
                            }
                            case 1 -> {
                                connection[i + j].setStartX(vertex[i + j].getCenterX());
                                connection[i + j].setStartY(vertex[i + j].getCenterY() + vertex[i + j].getRadius());
                                connection[i + j].setEndX(vertex[graph.getVertex(i + j, k)].getCenterX());
                                connection[i + j].setEndY(vertex[graph.getVertex(i + j, k)].getCenterY() - vertex[i + j].getRadius());
                            }
                            case 2 -> {
                                connection[i + j].setStartX(vertex[i + j].getCenterX() - vertex[i + j].getRadius());
                                connection[i + j].setStartY(vertex[i + j].getCenterY());
                                connection[i + j].setEndX(vertex[graph.getVertex(i + j, k)].getCenterX() + vertex[i + j].getRadius());
                                connection[i + j].setEndY(vertex[graph.getVertex(i + j, k)].getCenterY());
                            }
                            case 3 -> {
                                connection[i + j].setStartX(vertex[i + j].getCenterX());
                                connection[i + j].setStartY(vertex[i + j].getCenterY() - vertex[i + j].getRadius());
                                connection[i + j].setEndX(vertex[graph.getVertex(i + j, k)].getCenterX());
                                connection[i + j].setEndY(vertex[graph.getVertex(i + j, k)].getCenterY() + vertex[i + j].getRadius());
                            }
                        }
                        mainPane.getChildren().add(connection[i+j]);
                    }
                }
            }
        }

        for(int i = 0; i<graph.getRows() * graph.getColumns(); i+=graph.getColumns()) {
            for (int j = 0; j < graph.getColumns(); j++) {
                for(int k = 0; k < graph.directions; k++){
                    if(graph.getVertex(i+j,k) != graph.noConnection) {
                        arrow[i + j] = new Polygon();
                        switch (k) {
                            case 0 -> arrow[i + j].getPoints().setAll(
                                    vertex[graph.getVertex(i + j, k)].getCenterX() - arrowDistance * vertex[i + j].getRadius(),
                                    vertex[graph.getVertex(i + j, k)].getCenterY(),

                                    vertex[graph.getVertex(i + j, k)].getCenterX() - vertex[i + j].getRadius() * arrowLength,
                                    vertex[graph.getVertex(i + j, k)].getCenterY() - vertex[i + j].getRadius() * arrowWidth,

                                    vertex[graph.getVertex(i + j, k)].getCenterX() - vertex[i + j].getRadius() * arrowLength,
                                    vertex[graph.getVertex(i + j, k)].getCenterY() + vertex[i + j].getRadius() * arrowWidth

                            );
                            case 1 -> arrow[i + j].getPoints().setAll(
                                    vertex[graph.getVertex(i + j, k)].getCenterX(),
                                    vertex[graph.getVertex(i + j, k)].getCenterY() - arrowDistance * vertex[i + j].getRadius(),

                                    vertex[graph.getVertex(i + j, k)].getCenterX() + vertex[i + j].getRadius() * arrowWidth,
                                    vertex[graph.getVertex(i + j, k)].getCenterY() - vertex[i + j].getRadius() * arrowLength,

                                    vertex[graph.getVertex(i + j, k)].getCenterX() - vertex[i + j].getRadius() * arrowWidth,
                                    vertex[graph.getVertex(i + j, k)].getCenterY() - vertex[i + j].getRadius() * arrowLength

                            );
                            case 2 -> arrow[i + j].getPoints().setAll(
                                    vertex[graph.getVertex(i + j, k)].getCenterX() + arrowDistance * vertex[i + j].getRadius(),
                                    vertex[graph.getVertex(i + j, k)].getCenterY(),

                                    vertex[graph.getVertex(i + j, k)].getCenterX() + vertex[i + j].getRadius() * arrowLength,
                                    vertex[graph.getVertex(i + j, k)].getCenterY() - vertex[i + j].getRadius() * arrowWidth,

                                    vertex[graph.getVertex(i + j, k)].getCenterX() + vertex[i + j].getRadius() * arrowLength,
                                    vertex[graph.getVertex(i + j, k)].getCenterY() + vertex[i + j].getRadius() * arrowWidth

                            );
                            case 3 -> arrow[i + j].getPoints().setAll(
                                    vertex[graph.getVertex(i + j, k)].getCenterX(),
                                    vertex[graph.getVertex(i + j, k)].getCenterY() + arrowDistance * vertex[i + j].getRadius(),

                                    vertex[graph.getVertex(i + j, k)].getCenterX() + vertex[i + j].getRadius() * arrowWidth,
                                    vertex[graph.getVertex(i + j, k)].getCenterY() + vertex[i + j].getRadius() * arrowLength,

                                    vertex[graph.getVertex(i + j, k)].getCenterX() - vertex[i + j].getRadius() * arrowWidth,
                                    vertex[graph.getVertex(i + j, k)].getCenterY() + vertex[i + j].getRadius() * arrowLength

                            );
                        }
                        arrow[i + j].setFill((Color.valueOf(graph.getColorOfWeight(i + j, k))));
                        mainPane.getChildren().add(arrow[i+j]);
                    }
                }
            }
        }
    }
}
