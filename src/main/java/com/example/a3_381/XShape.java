package com.example.a3_381;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public abstract class XShape {
    Color shapeColor;
    InteractionModel.ShapeType shapeType;
    double normTop, normLeft, normWidth, normHeight;
    int z;

    public int getZ() {
        return z;
    }

    public void setZ(int z) {
        this.z = z;
    }

    public abstract boolean contains(double normX, double normY);

    public Color getShapeColor() {
        return shapeColor;
    }

    public double getNormTop() {
        return normTop;
    }

    public double getNormLeft() {
        return normLeft;
    }

    public void setLocation(double newNormLeft, double newNormTop) {
        this.normLeft = newNormLeft;
        this.normTop = newNormTop;
    }

    public double getNormWidth() {
        return normWidth;
    }

    public double getNormHeight() {
        return normHeight;
    }

    public InteractionModel.ShapeType getShapeType() {
        return shapeType;
    }
}
