package com.example.a3_381;
import javafx.scene.paint.Color;

public class XOval extends XShape {
    private double left;
    private double top;

    public XOval(double normLeft, double normTop, double normWidth, double normHeight, Color color) {
        this.left = normLeft;
        this.top = normTop;
        this.normWidth = normWidth;
        this.normHeight = normHeight;
        this.shapeColor = color;
        this.shapeType = InteractionModel.ShapeType.OVAL;
    }



    public void setNormWidth(double width) {
        this.normWidth = width;
    }


    public void setNormHeight(double height) {
        this.normHeight = height;
    }

    @Override
    public boolean contains(double normX, double normY) {
        // translate to origin
        normX -= (normWidth / 2 + normLeft);
        normY -= (normHeight / 2 + normTop);

        double XFactor = normWidth / normHeight;
        double YFactor = normHeight / normWidth;

        double convertedRadius;
        if (XFactor > YFactor) {
            normX /= XFactor;
            convertedRadius = normHeight/2;
        }
        else if (YFactor > XFactor) {
            normY /= YFactor;
            convertedRadius = normWidth/2;
        }
        else { // if the oval is a circle
            convertedRadius = normHeight/2;
        }


        return Math.sqrt(Math.pow(normX, 2) + Math.pow(normY,2)) < convertedRadius;
    }
}