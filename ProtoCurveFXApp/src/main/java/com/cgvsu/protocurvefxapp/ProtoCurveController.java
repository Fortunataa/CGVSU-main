package com.cgvsu.protocurvefxapp;

import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.awt.*;
import java.util.ArrayList;
import javafx.scene.paint.Color;


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
        Color color = Color.rgb(216, 191, 216);
        drawSegment(graphicsContext, center.getX(), center.getY(), 100, 100, 45, color);
    }

    private void drawSegment(GraphicsContext graphicsContext, double x, double y, double r, double startAngle, double arcAngle, Color color) {
        double endAngle = arcAngle + startAngle;
        boolean isFullCircle = arcAngle >= 360 || startAngle == endAngle;

        double oa_x = r * Math.cos(Math.toRadians(startAngle));
        double oa_y = r * Math.sin(Math.toRadians(startAngle));
        double ob_x = r * Math.cos(Math.toRadians(endAngle));
        double ob_y = r * Math.sin(Math.toRadians(endAngle));

        double left = x - r;
        double top = y - r;
        double box = 2 * r;

        for (double i = left; i < left + box; i++) {
            for (double j = top; j < top + box; j++) {
                double op_x = i - x;
                double op_y = j - y;

                if (Math.pow((op_x), 2) + Math.pow((op_y), 2) <= Math.pow(r, 2)) {
                    if (isFullCircle || isPointInSegment(op_x, op_y, oa_x, oa_y, ob_x, ob_y)) {
                        graphicsContext.getPixelWriter().setColor((int) i, (int) j, color);
                    }
                }
            }
        }
    }

    private boolean isPointInSegment(double pointX, double pointY, double oa_x, double oa_y, double ob_x, double ob_y) {
        double vector_M = oa_x * pointY - oa_y * pointX;
        double vector_N = pointX * ob_y - pointY * ob_x;
        double productOA_OB = oa_x * ob_y - oa_y * ob_x;
        if (productOA_OB > 0) {
            return vector_M >= 0 && vector_N >= 0;
        } else if (productOA_OB < 0) {
            return vector_M >= 0 || vector_N >= 0;
        } else {
            return false;
        }
    }
}

