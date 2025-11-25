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

import static com.cgvsu.protocurvefxapp.DrawCircleSegment.drawSegment;


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
        /*
        final Point2D clickPoint = new Point2D(event.getX(), event.getY());
        points.add(clickPoint);
        Point2D center = clickPoint;
        Color.rgb(216, 191, 216)
        Color color_center = Color.RED;
        Color color_end = Color.BLUE;
         */
        //drawSegment(graphicsContext, center.getX(), center.getY(), 100, 0, 360, color_center, color_end);
        drawSegment(graphicsContext, 100, 100, 80, 0, 90, Color.RED, Color.BLUE);
        drawSegment(graphicsContext, 300, 100, 80, 90, 180, Color.GREEN, Color.YELLOW);
        drawSegment(graphicsContext, 100, 300, 80, 180, 200, Color.ORANGE, Color.PURPLE);
        drawSegment(graphicsContext, 500, 200, 100, 0, 360, Color.PINK, Color.DARKBLUE);
        drawSegment(graphicsContext, 300, 300, 80, -45, 270, Color.CYAN, Color.MAGENTA);
        drawSegment(graphicsContext, 600, 400, 120, 180, 45, Color.CYAN, Color.CYAN);
        /*drawSegment(graphicsContext, 100, -2400, 2500, 0, 90, Color.BLACK, Color.WHITE);
        drawSegment(graphicsContext, 100, 400, 50, 0+90, 90, Color.BLACK, Color.BLACK);
        drawSegment(graphicsContext, 400, 200, 50, 0+90+90, 90, Color.BLACK, Color.BLACK);
        drawSegment(graphicsContext, 400, 400, 50, 0+90+90+90, 90, Color.BLACK, Color.BLACK);

         */
    }
}

