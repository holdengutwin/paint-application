package com.example.a3_381;


import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.geometry.Insets;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.util.Comparator;

public class DrawingView extends Pane implements DrawingModelSubscriber, InteractionModelSubscriber{
    Canvas myCanvas;
    GraphicsContext gc;
    double width, height;
    double worldWidth, worldHeight;

    DrawingModel drawingModel;
    InteractionModel imodel;

    public DrawingView(int w, int h, int worldWidth, int worldHeight) {
        width = w;
        height = h;
        this.worldWidth = worldWidth;
        this.worldHeight = worldHeight;
        myCanvas = new Canvas(width, height);
        this.setMaxSize(w,h);
        gc = myCanvas.getGraphicsContext2D();
        getChildren().add(myCanvas);
        this.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
    }

    public void modelChanged() {
        drawMain();
    }

    public void setController(DrawingController controller) {

        myCanvas.setOnMousePressed(e -> controller.handlePressed(e.getX()/worldWidth,e.getY()/worldHeight, e));
        myCanvas.setOnMouseDragged(e -> controller.handleDragged(e.getX()/worldWidth,e.getY()/worldHeight, e));
        myCanvas.setOnMouseMoved(e -> controller.handleMoved(e.getX()/worldWidth + imodel.getViewLeft(),e.getY()/worldHeight + imodel.getViewTop(), e));
        myCanvas.setOnMouseReleased(e -> controller.handleReleased(e.getX()/worldWidth + imodel.getViewLeft(),e.getY()/worldHeight + imodel.getViewTop(), e));
        myCanvas.addEventFilter(KeyEvent.KEY_PRESSED, controller::handleKey);
    }

    public void setModel(DrawingModel model) {
        this.drawingModel = model;
    }

    @Override
    public void imodelChanged() {
        drawMain();
    }

    public void addInteractionModel(InteractionModel imodel) {
        this.imodel = imodel;
    }

    public void drawMain() {

        // clear the canvas
        clearCanvas();

        drawShapes(imodel.getViewLeft(), imodel.getViewTop());
        // draw selection box
        drawSelectionBox(imodel.getViewLeft(), imodel.getViewTop(),5);

        // draw handle
        drawHandle(imodel.getViewLeft(), imodel.getViewTop());
    }

    public void clearCanvas() {
        gc.setFill(Color.WHITE);
        gc.fillRect(0, 0, myCanvas.getWidth(), myCanvas.getHeight());
    }
    public void drawShapes(double viewLeft, double viewTop) {
        drawingModel.sortShapesByZ();
        gc.setStroke(Color.BLACK);
        for (XShape shape : drawingModel.getShapes()) {
            double drawX = shape.getNormLeft() * worldWidth - viewLeft * worldWidth;
            double drawY = shape.getNormTop() * worldHeight - viewTop * worldHeight;
            double drawWidth = shape.getNormWidth() * worldWidth;
            double drawHeight = shape.getNormHeight() * worldHeight;
            gc.setFill(shape.getShapeColor());
            switch(shape.getShapeType()) {

                case SQUARE,RECTANGLE -> {
                    gc.fillRect(drawX, drawY, drawWidth, drawHeight);
                    gc.strokeRect(drawX, drawY, drawWidth, drawHeight);
                }

                case CIRCLE, OVAL -> {
                    gc.fillOval(drawX, drawY, drawWidth, drawHeight);
                    gc.strokeOval(drawX, drawY, drawWidth, drawHeight);
                }

                case LINE -> {
                    XLine xl = (XLine) shape;
                    gc.setStroke(xl.getShapeColor());
                    gc.setLineWidth(5);
                    gc.strokeLine(drawX, drawY, xl.getX2() * worldWidth - viewLeft * worldWidth, xl.getY2() * worldHeight - viewTop * worldHeight);

                    // restore
                    gc.setStroke(Color.BLACK);
                    gc.setLineWidth(1);
                }

            }

        }
    }
    public void drawSelectionBox(double viewLeft, double viewTop, int dashes) {
        // draw selection box
        XShape sel = imodel.getSelection();

        if (sel != null) {
            gc.setStroke(Color.ORANGE);
            gc.setLineWidth(3);
            gc.setLineDashes(dashes);
            double drawX = sel.getNormLeft() * worldWidth- viewLeft * worldWidth;
            double drawY = sel.getNormTop() * worldHeight - viewTop * worldHeight;

            if (sel.getShapeType() == InteractionModel.ShapeType.LINE) {
                gc.strokeLine(drawX,drawY, ((XLine)sel).getX2() * worldWidth - viewLeft * worldWidth, ((XLine)sel).getY2() * worldHeight - viewTop * worldHeight);
            }
            else {
                double drawWidth = sel.getNormWidth() * worldWidth;
                double drawHeight = sel.getNormHeight() * worldHeight;
                gc.strokeRect(drawX,drawY,drawWidth, drawHeight);
            }
            gc.setLineDashes(0);
            gc.setLineWidth(1);
            gc.setStroke(Color.BLACK);
        }
    }

    public void drawHandle(double viewLeft, double viewTop) {
        XShape sel = imodel.getSelection();

        if (sel != null) {
            double x,y;
            if (sel.getShapeType() == InteractionModel.ShapeType.LINE) {
                x = ((XLine)sel).getX2();
                y = ((XLine)sel).getY2();
            }
            else {
                x = sel.getNormLeft() + sel.getNormWidth();
                y = sel.getNormTop() + sel.getNormHeight();
            }
            gc.setFill(Color.YELLOW);
            gc.fillOval(x * worldWidth - viewLeft * worldWidth - 4,y * worldHeight - viewTop* worldHeight- 4,8,8);
            gc.strokeOval(x * worldWidth- viewLeft * worldWidth - 4,y * worldHeight - viewTop* worldHeight - 4,8,8);
        }
    }

    public void setWindowWidthProperty(ReadOnlyDoubleProperty widthProperty) {
        widthProperty.addListener((observable, oldVal, newVal) -> {
            this.width = newVal.doubleValue() - 200;
            myCanvas.setWidth(this.width);
            imodel.setViewWidth(this.width/worldWidth);
            drawMain();
        });
    }

    public void setWindowHeightProperty(ReadOnlyDoubleProperty heightProperty) {
        heightProperty.addListener((observable, oldVal, newVal) -> {
            this.height = newVal.doubleValue();
            myCanvas.setHeight(this.height);
            imodel.setViewHeight(this.height/worldHeight);
            drawMain();
        });
    }
}

