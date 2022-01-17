package com.example.a3_381;

import javafx.scene.paint.Color;

public class XCircle extends XShape{

    public XCircle(double normLeft, double normTop, double normRadius, Color color) {
        this.normLeft = normLeft;
        this.normWidth = normRadius*2;
        this.normHeight = normRadius*2;
        this.normTop = normTop;
        this.shapeColor = color;
        this.shapeType = InteractionModel.ShapeType.CIRCLE;
    }

    public double getNormRadius() {
        return normWidth/2;
    }

    public void setNormRadius(double normRadius) {
        this.normWidth = normRadius*2;
        this.normHeight = normRadius*2;
    }

    public boolean contains(double normX, double normY) {
        return Math.sqrt(Math.pow((normX-(normLeft+normWidth/2)),2) + Math.pow((normY-(normTop+normHeight/2)),2)) < getNormRadius();
    }
}
