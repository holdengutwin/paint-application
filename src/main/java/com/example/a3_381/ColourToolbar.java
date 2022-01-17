package com.example.a3_381;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class ColourToolbar extends StackPane {
    Button redButton, blueButton, yellowButton, greenButton, tuscanSunsetButton;
    ArrayList<Button> allButtons;
    InteractionModel imodel;
    static Color RED = Color.web("#ff0000");
    static Color BLUE = Color.web("0062cc");
    static Color YELLOW = Color.web("ffe800");
    static Color GREEN = Color.web("#378805");
    static Color TUSCANSUNSET = Color.web("#E77F3A");

    HashMap<Color, Button> colorsToButtons;
    HashMap<InteractionModel.ShapeType, Button> shapesToButtons;
    public ColourToolbar() {
        redButton = new Button("Red");
        redButton.setStyle("-fx-background-color: #ff0000;");

        blueButton = new Button("Blue");
        blueButton.setStyle("-fx-background-color: #0062cc");

        yellowButton = new Button("Yellow");
        yellowButton.setStyle("-fx-background-color: #ffe800");

        greenButton = new Button("Green");
        greenButton.setStyle("-fx-background-color: #378805");

        tuscanSunsetButton = new Button("Tuscan Sunset");
        tuscanSunsetButton.setStyle("-fx-background-color: #E77F3A");

        colorsToButtons = new HashMap<>(Map.of(
                RED,redButton,
                BLUE,blueButton,
                GREEN,greenButton,
                YELLOW,yellowButton,
                TUSCANSUNSET,tuscanSunsetButton
        ));



        allButtons = new ArrayList<>(Arrays.asList(
                redButton,
                blueButton,
                yellowButton,
                greenButton,
                tuscanSunsetButton
        ));

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
        allButtons.forEach(e->e.setPadding(new Insets(10)));
        Button currentSelected = colorsToButtons.get(imodel.getCurrentColor());
        currentSelected.setPadding(new Insets(0));
    }

    public void addController(DrawingController controller) {
        redButton.setOnAction(e->controller.handleRedButton());
        blueButton.setOnAction(e->controller.handleBlueButton());
        yellowButton.setOnAction(e->controller.handleYellowButton());
        greenButton.setOnAction(e->controller.handleGreenButton());
        tuscanSunsetButton.setOnAction(e->controller.handleTuscanSunsetButton());
    }

    public void setInteractionModel(InteractionModel imodel) {
        this.imodel = imodel;
    }

}
