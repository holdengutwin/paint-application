package com.example.a3_381;
import javafx.scene.paint.Color;

public class XRectangle extends XShape {
    private double left;
    private double top;

    public XRectangle(double normLeft, double normTop, double normWidth, double normHeight, Color color) {
        this.left = normLeft;
        this.top = normTop;
        this.normWidth = normWidth;
        this.normHeight = normHeight;
        this.shapeColor = color;
        this.shapeType = InteractionModel.ShapeType.RECTANGLE;
    }

    public void setNormWidth(double width) {
        this.normWidth = width;
    }


    public void setNormHeight(double height) {
        this.normHeight = height;
    }

    @Override
    public boolean contains(double normX, double normY) {
        return normLeft < normX && normX < normLeft + normWidth && normTop < normY && normY < normTop + normHeight;
    }
}