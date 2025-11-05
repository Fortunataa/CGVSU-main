package com.cgvsu.protocurvefxapp;

import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.awt.*;
import java.util.ArrayList;
import static javafx.scene.paint.Color.BLUE;
import static javafx.scene.paint.Color.GREEN;

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
        drawSegment(graphicsContext, center.getX(), center.getY(), 100, 0, 225);
    }

    private void drawSegment(GraphicsContext graphicsContext, double x, double y, double r, double startAngle, double arcAngle) {
        double endAngle = arcAngle + startAngle;
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
                    double new_vector_M = oa_x * op_y - oa_y * op_x;
                    double new_vector_N = op_x * ob_y - op_y * ob_x;

                    if (arcAngle > 0) {
                        if (new_vector_M >= 0 && new_vector_N >= 0){
                            graphicsContext.getPixelWriter().setColor((int) i, (int) j, BLUE);
                        }
                    } else {
                        if (new_vector_M <= 0 && new_vector_N <= 0) {
                            graphicsContext.getPixelWriter().setColor((int) i, (int) j, BLUE);
                        }
                    }

                }
            }
        }
    }
}

