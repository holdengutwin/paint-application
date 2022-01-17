package com.example.a3_381;

import javafx.geometry.Pos;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;

import java.util.Stack;

public class MainUI extends BorderPane implements DrawingModelSubscriber,InteractionModelSubscriber {
    ShapeToolbar shapes;
    ColourToolbar colours;
    DrawingView canvas;
    MiniDrawingView minicanvas;

    public MainUI() {
        shapes = new ShapeToolbar();
        colours = new ColourToolbar();
        canvas = new DrawingView(500,500, 2000, 2000);
        minicanvas = new MiniDrawingView(100,100, 100, 100);

        canvas.setWindowWidthProperty(this.widthProperty());
        canvas.setWindowHeightProperty(this.heightProperty());

        StackPane canvases = new StackPane();
        StackPane.setAlignment(minicanvas,Pos.TOP_LEFT);
        StackPane.setAlignment(canvas,Pos.TOP_LEFT);
        canvases.getChildren().addAll(canvas,minicanvas);
        this.setLeft(shapes);
        this.setRight(colours);
        this.setCenter(canvases);
    }

    public void setController(DrawingController controller) {
        canvas.setController(controller);
        minicanvas.setController(controller);
        colours.addController(controller);
        shapes.addController(controller);

        this.setOnKeyPressed(controller::handleKey);
    }

    public void setMiniController(MiniDrawingController miniController) {
        minicanvas.setMiniController(miniController);

    }



    public void setModel(DrawingModel model) {
        canvas.setModel(model);
        minicanvas.setModel(model);
    }

    public void modelChanged() {
        canvas.modelChanged();
        minicanvas.modelChanged();
    }

    public void imodelChanged() {
        shapes.imodelChanged();
        colours.imodelChanged();
        canvas.imodelChanged();
        minicanvas.imodelChanged();
    }

    public void setInteractionModel(InteractionModel imodel) {
        colours.setInteractionModel(imodel);
        shapes.addInteractionModel(imodel);
        canvas.addInteractionModel(imodel);
        minicanvas.addInteractionModel(imodel);
    }
}
