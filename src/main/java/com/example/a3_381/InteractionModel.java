package com.example.a3_381;

import javafx.scene.paint.Color;

import java.util.ArrayList;

public class InteractionModel {
    private ArrayList<InteractionModelSubscriber> subs;
    private XShape selection;
    private double viewLeft, viewTop, viewWidth, viewHeight;


    public double getViewLeft() {
        return viewLeft;
    }

    public void setViewWidth(double viewWidth) {
        this.viewWidth = viewWidth;
        notifySubscribers();
    }

    public void setViewHeight(double viewHeight) {
        this.viewHeight = viewHeight;
        notifySubscribers();
    }

    public double getViewWidth() {
        return viewWidth;
    }

    public double getViewHeight() {
        return viewHeight;
    }

    public void setViewDimensions(double w, double h) {
        this.viewWidth = w;
        this.viewHeight = h;
    }
    public double getViewTop() {
        return viewTop;
    }

    public void dragCanvas(double dWorldX, double dWorldY) {
        viewLeft = viewLeft - dWorldX;
        viewTop = viewTop - dWorldY;
        if (viewLeft < 0) viewLeft = 0;
        if (viewTop < 0) viewTop = 0;
        if (viewLeft > 1.0 - viewWidth) viewLeft = 1.0 - viewWidth;
        if (viewTop > 1.0 - viewHeight) viewTop = 1.0 - viewHeight;
        notifySubscribers();
    }

    public boolean onViewport(double normX, double normY) {
        return viewLeft < normX && normX < viewLeft + viewWidth && viewTop < normY && normY < viewTop + viewHeight;
    }

    public enum ShapeType {
        RECTANGLE, SQUARE, CIRCLE, OVAL, LINE
    }

    public enum ColorState{
        RED, BLUE, YELLOW, GREEN, TUSCANSUNSET
    }

    public XShape getSelection() {
        return selection;
    }

    public void setSelection(XShape selection) {
        this.selection = selection;
        notifySubscribers();
    }

    public InteractionModel() {
        subs = new ArrayList<>();
        currentShape = ShapeType.RECTANGLE;
        currentColor = ColourToolbar.RED;
        viewLeft = 0;
        viewTop = 0;
        viewWidth = 0.25;
        viewHeight = 0.25;
    }

    public ShapeType getCurrentShape() {
        return currentShape;
    }

    public void setCurrentShape(ShapeType currentShape) {
        this.currentShape = currentShape;
        notifySubscribers();
    }

    public Color getCurrentColor() {
        return currentColor;
    }

    public void notifySubscribers() {
        subs.forEach(InteractionModelSubscriber::imodelChanged);

    }

    public void addSubscriber(InteractionModelSubscriber newSub) {
        subs.add(newSub);
    }
    private ShapeType currentShape;
    private Color currentColor;

    public void setCurrentColor(Color currentColor) {
        this.currentColor = currentColor;
        notifySubscribers();
    }

    public boolean onHandle(double normX, double normY) {
        if (selection != null) {
            double x, y;
            if (selection.shapeType == ShapeType.LINE) {
                x = ((XLine) selection).getX2();
                y = ((XLine) selection).getY2();
            } else {
                x = selection.getNormLeft() + selection.getNormWidth();
                y = selection.getNormTop() + selection.getNormHeight();
            }
            return Math.sqrt(Math.pow((normX-x),2) + Math.pow((normY-y),2)) < (4.0/500);
        }
        else {
            return false;
        }
    }
}
