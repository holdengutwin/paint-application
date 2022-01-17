package com.example.a3_381;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Line;

import static com.example.a3_381.InteractionModel.ShapeType.*;

public class DrawingController {
    DrawingModel model;
    InteractionModel imodel;

    double prevX, prevY;
    public DrawingController() {
        this.currentState = State.READY;
    }
    public void addModel(DrawingModel model) {
        this.model = model;
    }

    public void addInteractionModel(InteractionModel imodel) {
        this.imodel = imodel;
    }

    public void handleRedButton() {
        imodel.setCurrentColor(ColourToolbar.RED);
    }
    public void handleBlueButton() {
        imodel.setCurrentColor(ColourToolbar.BLUE);
    }
    public void handleYellowButton() {
        imodel.setCurrentColor(ColourToolbar.YELLOW);
    }
    public void handleGreenButton() {
        imodel.setCurrentColor(ColourToolbar.GREEN);
    }
    public void handleTuscanSunsetButton() {
        imodel.setCurrentColor(ColourToolbar.TUSCANSUNSET);
    }

    public void handleRectangleButton() {
        imodel.setCurrentShape(InteractionModel.ShapeType.RECTANGLE);
    }

    public void handleSquareButton() {
        imodel.setCurrentShape(InteractionModel.ShapeType.SQUARE);
    }

    public void handleCircleButton() {
        imodel.setCurrentShape(CIRCLE);
    }

    public void handleOvalButton() {
        imodel.setCurrentShape(OVAL);
    }

    public void handleLineButton() {
        imodel.setCurrentShape(LINE);
    }

    public void handleKey(KeyEvent e) {
        if (e.getCode() == KeyCode.DELETE && imodel.getSelection() != null) {
            model.deleteShape(imodel.getSelection());
            imodel.setSelection(null);
        }
    }


    private enum State {
        READY, RESIZING, MOVING, PREPARE_CREATE
    }

    double anchorX;
    double anchorY;

    private State currentState;

    public void handlePressed(double normX, double normY, MouseEvent e) {
        prevX = normX;
        prevY = normY;
        normX += imodel.getViewLeft();
        normY += imodel.getViewTop();
        switch (currentState) {
            case READY -> {
                if (e.getButton() == MouseButton.SECONDARY) {
                    // do nothing, this is only to prevent right click from selecting or creating a new shape
                }
                else if (imodel.onHandle(normX,normY)) {
                    anchorX = imodel.getSelection().getNormLeft();
                    anchorY = imodel.getSelection().getNormTop();
                    currentState = State.RESIZING;
                }
                else if (model.checkHit(normX,normY)) { // context: on shape
                    XShape curShape = model.whichShape(normX,normY);
                    imodel.setSelection(curShape);
                    model.bringToFront(curShape);

                    currentState = State.MOVING;

                }
                else { // context: on bg
                    // side effects:
                    // - create new shape
                    anchorX = normX;
                    anchorY = normY;
                    currentState = State.PREPARE_CREATE;
                    imodel.setSelection(null);
                }
            }
        }
    }

    public void handleDragged(double normX, double normY, MouseEvent e) {
        double dX = normX - prevX;
        double dY = normY - prevY;
        prevX = normX;
        prevY = normY;
        normX += imodel.getViewLeft();
        normY += imodel.getViewTop();

        if (e.getButton() == MouseButton.SECONDARY) {
            imodel.dragCanvas(dX, dY);
        }
        else {
            switch (currentState) {
                case PREPARE_CREATE -> {
                    switch (imodel.getCurrentShape()) {
                        case RECTANGLE -> {
                            XRectangle newRect = new XRectangle(anchorX, anchorY, 0, 0, imodel.getCurrentColor());
                            model.addRectangle(newRect);
                            imodel.setSelection(newRect);
                        }
                        case SQUARE -> {
                            XSquare newSquare = new XSquare(anchorX, anchorY, 0, imodel.getCurrentColor());
                            model.addSquare(newSquare);
                            imodel.setSelection(newSquare);
                        }
                        case CIRCLE -> {
                            XCircle newCircle = new XCircle(anchorX, anchorY, 0, imodel.getCurrentColor());
                            model.addCircle(newCircle);
                            imodel.setSelection(newCircle);
                        }
                        case OVAL -> {
                            XOval newOval = new XOval(anchorX, anchorY, 0, 0, imodel.getCurrentColor());
                            model.addOval(newOval);
                            imodel.setSelection(newOval);
                        }
                        case LINE -> {
                            XLine newLine = new XLine(anchorX, anchorY, normX, normY, imodel.getCurrentColor());
                            model.addLine(newLine);
                            imodel.setSelection(newLine);
                        }
                    }
                    this.currentState = State.RESIZING;
                }
                case MOVING -> {
                    model.moveShape(imodel.getSelection(), dX, dY);
                }
                case RESIZING -> {
                    switch (imodel.getSelection().getShapeType()) {
                        case RECTANGLE -> {
                            XRectangle currentRect = (XRectangle) imodel.getSelection();
                            double newLeft = Math.min(normX, anchorX);
                            double newTop = Math.min(normY, anchorY);
                            double newWidth = Math.abs(normX - anchorX);
                            double newHeight = Math.abs(normY - anchorY);
                            model.updateRectangle(currentRect, newLeft, newTop, newWidth, newHeight);
                        }

                        case SQUARE -> {
                            XSquare currentSquare = (XSquare) imodel.getSelection();
                            double newLength = Math.min( // take the minimum of the x and y lengths
                                    Math.abs(normX - anchorX), Math.abs(normY - anchorY)
                            );
                            double newLeft = Math.min(Math.max(anchorX - newLength, normX), anchorX);
                            double newTop = Math.min(Math.max(anchorY - newLength, normY), anchorY);

                            model.updateSquare(currentSquare, newLeft, newTop, newLength);
                        }
                        case CIRCLE -> {
                            XCircle currentCircle = (XCircle) imodel.getSelection();
                            double newRadius = Math.min( // take the minimum of the x and y lengths
                                    Math.abs(normX - anchorX), Math.abs(normY - anchorY)
                            ) / 2;

                            double newLeft = Math.min(Math.max(anchorX - newRadius * 2, normX), anchorX);
                            double newTop = Math.min(Math.max(anchorY - newRadius * 2, normY), anchorY);

                            model.updateCircle(currentCircle, newLeft, newTop, newRadius);
                        }

                        case OVAL -> {
                            XOval currentOval = (XOval) imodel.getSelection();
                            double newLeft = Math.min(normX, anchorX);
                            double newTop = Math.min(normY, anchorY);
                            double newWidth = Math.abs(normX - anchorX);
                            double newHeight = Math.abs(normY - anchorY);
                            model.updateOval(currentOval, newLeft, newTop, newWidth, newHeight);
                        }

                        case LINE -> {
                            XLine currentLine = (XLine) imodel.getSelection();
                            model.updateLine(currentLine, normX, normY);
                        }

                    }
                }
            }
        }
    }

    public void handleMoved(double normX, double normY, MouseEvent e) {
    }

    public void handleReleased(double normX, double normY, MouseEvent e) {
        this.currentState = State.READY;
    }
}
