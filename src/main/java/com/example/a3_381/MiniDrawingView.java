package com.example.a3_381;

import javafx.geometry.Insets;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;

public class MiniDrawingView extends DrawingView{
    public MiniDrawingView(int w, int h, int worldWidth, int worldHeight) {
        super(w, h, worldWidth, worldHeight);
    }

    public void setMiniController(MiniDrawingController controller) {
        myCanvas.setOnMousePressed(e -> controller.handlePressed(e.getX()/worldWidth,e.getY()/worldHeight, e));
        myCanvas.setOnMouseDragged(e -> controller.handleDragged(e.getX()/worldWidth,e.getY()/worldHeight, e));
        myCanvas.setOnMouseReleased(e -> controller.handleReleased(e.getX()/worldWidth,e.getY()/worldHeight, e));
    }

    public void drawMini() {

        // clear the canvas
        clearMiniCanvas();
        drawWindow();
        drawShapes(0, 0);
        // draw selection box
        drawSelectionBox(0,0,0);

        drawViewportOutline();

    }
    public void modelChanged() {
        drawMini();
    }
    public void imodelChanged() {
        drawMini();
    }
    public void drawWindow() {
        double normX = imodel.getViewLeft();
        double normY = imodel.getViewTop();
        gc.setFill(Color.rgb(255, 255, 255));
        gc.fillRect(normX * width, normY * width, imodel.getViewWidth() * width, imodel.getViewHeight() * height);

    }

    public void drawViewportOutline() {
        double normX = imodel.getViewLeft();
        double normY = imodel.getViewTop();
        gc.setStroke(Color.YELLOW);
        gc.strokeRect(normX * width, normY * width, imodel.getViewWidth() * width, imodel.getViewHeight() * height);
    }

    public void clearMiniCanvas() {
        gc.setFill(Color.DARKGRAY);
        gc.fillRect(0,0,100,100);
    }


}
