package graph.jimpgraph;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Circle;

import static graph.jimpgraph.HelloController.*;
import static javafx.scene.paint.Color.*;

public class ClickableCircle extends Circle {

    public ClickableCircle(){
        this.setOnMouseClicked(onMousePressedEventHandler());
    }

    public void setGreenColor(){
        this.setFill(GREEN);
    }
    public void setRedColor(){
        this.setFill(RED);
    }
    public void setBlackColor(){
        this.setFill(BLACK);
    }


    private EventHandler<MouseEvent> onMousePressedEventHandler() {
        return event ->{
            if(numberOfClick == 0 && !isGreenChosen){
                isGreenChosen = true;
                setGreenColor();
                numberOfClick = 1;
            }
            else if(numberOfClick == 1 && !isRedChosen){
                isRedChosen = true;
                setRedColor();
                numberOfClick = 2;
            }
            else{
                setBlackColor();
                numberOfClick = 0;
            }
        };
    }
}
