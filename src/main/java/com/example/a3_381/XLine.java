package com.example.a3_381;

import javafx.scene.paint.Color;

public class XLine extends XShape{
    private double x2;
    private double y2;

    public XLine(double normLeft, double normTop, double x2, double y2, Color color) {
        this.normLeft = normLeft;
        this.normTop = normTop;
        this.x2 = x2;
        this.y2 = y2;
        this.normWidth = Math.abs(normLeft-x2);
        this.normHeight = Math.abs(normTop-y2);
        this.shapeType = InteractionModel.ShapeType.LINE;
        this.shapeColor = color;
    }

    public void setEndpoints(double x2, double y2) {
        this.x2 = x2;
        this.y2 = y2;
        this.normWidth = Math.abs(normLeft-x2);
        this.normHeight = Math.abs(normTop-y2);
    }

    public double getX2() {
        return x2;
    }

    public double getY2() {
        return y2;
    }

    @Override
    public boolean contains(double normX, double normY) {
        double lineLength = Math.sqrt(normWidth * normWidth + normHeight * normHeight);

        double A = (normTop - y2) / lineLength;
        double B = (x2 - normLeft) / lineLength;
        double C = -1 * ((normTop - y2) * normLeft + (x2 - normLeft) * normTop) / lineLength;


        double tolerance = 0.01;
        double upperBound = normTop > y2 ? normTop + tolerance : y2 + tolerance;
        double lowerBound = normTop > y2 ? y2 - tolerance : normTop - tolerance;
        return Math.abs(A * normX + B * normY + C) < tolerance && normY > lowerBound && normY < upperBound;
    }
}
