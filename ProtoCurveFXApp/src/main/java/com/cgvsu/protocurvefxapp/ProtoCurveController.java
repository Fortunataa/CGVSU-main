package com.cgvsu.protocurvefxapp;

import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.util.ArrayList;

public class ProtoCurveController {

    @FXML
    AnchorPane anchorPane;
    @FXML
    private Canvas canvas;

    ArrayList<Point2D> points = new ArrayList<Point2D>();

    @FXML
    private void initialize() {
        anchorPane.prefWidthProperty().addListener((ov, oldValue, newValue) -> canvas.setWidth(newValue.doubleValue()));
        anchorPane.prefHeightProperty().addListener((ov, oldValue, newValue) -> canvas.setHeight(newValue.doubleValue()));

        canvas.setOnMouseClicked(event -> {
            switch (event.getButton()) {
                case PRIMARY -> handlePrimaryClick(canvas.getGraphicsContext2D(), event);
            }
        });
    }

    private void handlePrimaryClick(GraphicsContext graphicsContext, MouseEvent event) {
        final Point2D clickPoint = new Point2D(event.getX(), event.getY());
        points.add(clickPoint);
        Point2D center = clickPoint;
        drawSegment(graphicsContext, (int)center.getX(), (int)center.getY(), 100, 135, 45);
    }

    private void drawSegment(GraphicsContext graphicsContext, int x, int y, double r, double startAngle, double arcAngle) {
        startAngle = startAngle % 360;
        if (arcAngle > 360) {
            arcAngle = 360;
        }

        if (arcAngle < -360) {
            arcAngle = -360;
        }

        double direction;
        if (arcAngle >= 0){
            direction = 1;
        } else {
            direction = -1;
        }

        double endAngle = arcAngle + startAngle;

        double beginX = x + r * Math.cos(Math.toRadians(startAngle));
        double beginY = y - r * Math.sin(Math.toRadians(startAngle));
        graphicsContext.strokeLine(x, y, beginX, beginY);

        double endX = x + r * Math.cos(Math.toRadians(endAngle));
        double endY = y - r * Math.sin(Math.toRadians(endAngle));
        graphicsContext.strokeLine(x, y, endX, endY);

        int segment_length = (int) Math.abs(arcAngle);
        double prevX = beginX, prevY = beginY;
        for (int i = 1; i < segment_length; i++) {
            double angle = startAngle + i * direction;
            double rad = Math.toRadians(angle);
            double currX = x + r * Math.cos(rad);
            double currY = y - r * Math.sin(rad);

            graphicsContext.strokeLine(prevX, prevY, currX, currY);
            prevX = currX;
            prevY = currY;
        }
        graphicsContext.strokeLine(prevX, prevY, endX, endY);
    }
}