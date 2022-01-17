package com.example.a3_381;

import javafx.scene.control.Button;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class ShapeToolbar extends StackPane {
    Button rectangleButton, squareButton, circleButton, ovalButton, lineButton;
    Rectangle rectangleIcon;
    Rectangle squareIcon;
    Circle circleIcon;
    Ellipse ovalIcon;
    Line lineIcon;

    HashMap<InteractionModel.ShapeType, Button> shapesToButtons;
    private InteractionModel imodel;

    public ShapeToolbar() {
        rectangleIcon = new Rectangle(30,20, Color.BLACK);
        squareIcon = new Rectangle(30,30,Color.BLACK);
        circleIcon = new Circle(15,Color.BLACK);
        ovalIcon = new Ellipse(15,10);
        ovalIcon.setFill(Color.BLACK);
        lineIcon = new Line(0,0,30,30);

        rectangleButton = new Button("Rectangle", rectangleIcon);
        squareButton = new Button ("Square", squareIcon);
        circleButton = new Button("Circle", circleIcon);
        ovalButton = new Button("Oval", ovalIcon);
        lineButton = new Button("Line", lineIcon);

        shapesToButtons = new HashMap<>(Map.of(
                InteractionModel.ShapeType.RECTANGLE, rectangleButton,
                InteractionModel.ShapeType.SQUARE, squareButton,
                InteractionModel.ShapeType.CIRCLE, circleButton,
                InteractionModel.ShapeType.OVAL, ovalButton,
                InteractionModel.ShapeType.LINE, lineButton
        ));

        ArrayList<Button> allButtons = new ArrayList<>(Arrays.asList(rectangleButton,squareButton,circleButton,ovalButton,lineButton));

        VBox root = new VBox();

        allButtons.forEach(e -> {
            e.setPrefWidth(100);
            e.setMaxHeight(Double.MAX_VALUE);
            VBox.setVgrow(e, Priority.ALWAYS);
            root.getChildren().add(e);
        });

        this.getChildren().add(root);
    }

    public void imodelChanged() {
        Button selectedButton = shapesToButtons.get(imodel.getCurrentShape());
        ArrayList<Button> allButtons = new ArrayList<>(Arrays.asList(rectangleButton,squareButton,circleButton,ovalButton,lineButton));

        allButtons.forEach(e-> {
            Shape curIcon = (Shape) e.getGraphic();
            curIcon.setStroke(Color.BLACK);
            curIcon.setFill(Color.BLACK);
        });
        Shape selIcon = (Shape) selectedButton.getGraphic();
        selIcon.setFill(imodel.getCurrentColor());
        if (selIcon instanceof Line) {
            selIcon.setStroke(imodel.getCurrentColor());
        }
    }

    public void addInteractionModel(InteractionModel imodel) {
        this.imodel = imodel;
    }
    public void addController(DrawingController controller) {
        rectangleButton.setOnAction(e->controller.handleRectangleButton());
        squareButton.setOnAction(e->controller.handleSquareButton());
        circleButton.setOnAction(e->controller.handleCircleButton());
        ovalButton.setOnAction(e->controller.handleOvalButton());
        lineButton.setOnAction(e->controller.handleLineButton());
    }
}
