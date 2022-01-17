package com.example.a3_381;

import javafx.scene.paint.Color;

public class XSquare extends XShape{

    public XSquare(double normLeft, double normTop, double normLength, Color color) {
        this.normLeft = normLeft;
        this.normTop = normTop;
        this.normWidth = normLength;
        this.normHeight = normLength;
        this.shapeColor = color;
        this.shapeType = InteractionModel.ShapeType.SQUARE;
    }

    public double getNormLength() {
        return normWidth;
    }

    public void setSideLength(double newNormLength) {
        this.normWidth = newNormLength;
        this.normHeight = newNormLength;
    }

    public boolean contains(double normX, double normY) {
        return normLeft < normX && normX < normLeft + normWidth && normTop < normY && normY < normTop + normHeight;
    }
    public String toString() {
        return "" + normLeft + " " + normTop + " " + normWidth;
    }
}
