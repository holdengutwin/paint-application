package com.example.a3_381;

import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.Comparator;

public class DrawingModel {
    private ArrayList<DrawingModelSubscriber> subs;
    private ArrayList<XShape> shapes;

    private int nextZ;

    public DrawingModel() {
        subs = new ArrayList<>();
        shapes = new ArrayList<>();
        nextZ=0;
    }

    public void addSubscriber(DrawingModelSubscriber newSub) {
        subs.add(newSub);
    }

    public void notifySubscribers() {
        subs.forEach(DrawingModelSubscriber::modelChanged);
    }

    public ArrayList<XShape> getShapes() {
        return shapes;
    }

    // adds it to the list, and returns the created rectangle
    public void addRectangle(XRectangle newRectangle) {
        shapes.add(newRectangle);

        newRectangle.setZ(nextZ);
        nextZ++;
        notifySubscribers();
    }

    public XShape whichShape(double normX, double normY) {
        shapes.sort(Comparator.comparingInt(XShape::getZ));
        XShape found = null;
        for (XShape shape : shapes) {
            if (shape.contains(normX, normY)){
                found = shape;
            }
        }
        return found;
    }

    public boolean checkHit(double normX, double normY) {
        return shapes.stream().anyMatch(s->s.contains(normX,normY));
    }
    public void updateRectangle(XRectangle rect, double newLeft, double newTop, double newWidth, double newHeight) {
        rect.setLocation(newLeft, newTop);
        rect.setNormWidth(newWidth);
        rect.setNormHeight(newHeight);
        notifySubscribers();
    }

    public void bringToFront(XShape shape) {
        shape.setZ(nextZ);
        nextZ++;
        notifySubscribers();
    }
    public void addSquare(XSquare newSquare) {
        shapes.add(newSquare);

        newSquare.setZ(nextZ);
        nextZ++;
        notifySubscribers();
    }

    public void updateSquare(XSquare square, double x, double y, double l) {
        square.setLocation(x,y);
        square.setSideLength(l);
        notifySubscribers();
    }

    public void addCircle(XCircle newCircle) {
        shapes.add(newCircle);

        newCircle.setZ(nextZ);
        nextZ++;
        notifySubscribers();
    }
    public void updateCircle(XCircle circle, double newLeft, double newTop, double newRadius) {
        circle.setLocation(newLeft, newTop);
        circle.setNormRadius(newRadius);
        notifySubscribers();
    }

    public void addOval(XOval newOval) {
        shapes.add(newOval);

        newOval.setZ(nextZ);
        nextZ++;
        notifySubscribers();
    }
    public void updateOval(XOval oval, double newLeft, double newTop, double newWidth, double newHeight) {
        oval.setLocation(newLeft, newTop);
        oval.setNormWidth(newWidth);
        oval.setNormHeight(newHeight);
        notifySubscribers();
    }

    public void addLine(XLine newLine) {
        shapes.add(newLine);

        newLine.setZ(nextZ);
        nextZ++;
        notifySubscribers();
    }

    public void updateLine(XLine line, double newX2, double newY2) {
        line.setEndpoints(newX2,newY2);
        notifySubscribers();
    }

    public void sortShapesByZ() {
        shapes.sort(Comparator.comparingInt(XShape::getZ));
    }

    public void deleteShape(XShape selection) {
        shapes.remove(selection);
        notifySubscribers();
    }

    public void moveShape(XShape shape, double dx, double dy) {
        shape.setLocation(shape.getNormLeft() + dx, shape.getNormTop() + dy);
        if (shape.getShapeType() == InteractionModel.ShapeType.LINE) {
            ((XLine)shape).setEndpoints(((XLine) shape).getX2() + dx, ((XLine) shape).getY2() + dy);
        }
        notifySubscribers();
    }
}
