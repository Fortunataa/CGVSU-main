package com.cgvsu.protocurvefxapp;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class DrawCircleSegment {
    private static final double EPS = 1e-10;

    public static void drawSegment(GraphicsContext graphicsContext, double x, double y, double r, double startAngle,
                                   double arcAngle, Color color_center, Color color_end) {
        startAngle = ((startAngle % 360) + 360) % 360;
        arcAngle = arcAngle % 360;
        double endAngle = arcAngle + startAngle;
        boolean isFullCircle = Math.abs(arcAngle) < EPS || Math.abs(arcAngle - 360) < EPS;

        double oa_x = r * Math.cos(Math.toRadians(startAngle));
        double oa_y = r * Math.sin(Math.toRadians(startAngle));
        double ob_x = r * Math.cos(Math.toRadians(endAngle));
        double ob_y = r * Math.sin(Math.toRadians(endAngle));

        double left = x - r;
        double top = y - r;
        double box = 2 * r;

        for (double i = left; i < left + box; i++) {
            for (double j = top; j < top + box; j++) {
                double pixelCenterX = i + 0.5;
                double pixelCenterY = j + 0.5;

                double op_x = pixelCenterX - x;
                double op_y = pixelCenterY - y;

                double distance = op_x * op_x + op_y * op_y;
                if (distance <= r * r + EPS) {
                    if (isFullCircle || isPointInSegment(op_x, op_y, oa_x, oa_y, ob_x, ob_y)) {
                        double ratio = Math.sqrt(distance) / r;
                        Color interpolatedColor = interpolate(color_center, color_end, ratio);
                        graphicsContext.getPixelWriter().setColor((int) i, (int) j, interpolatedColor);
                    }
                }
            }
        }
    }

    private static boolean isPointInSegment(double pointX, double pointY, double oa_x, double oa_y, double ob_x, double ob_y) {
        double vector_M = oa_x * pointY - oa_y * pointX;
        double vector_N = pointX * ob_y - pointY * ob_x;
        double productOA_OB = oa_x * ob_y - oa_y * ob_x;
        if (productOA_OB > EPS) {
            return vector_M >= -EPS && vector_N >= -EPS;
        } else if (productOA_OB < -EPS) {
            return vector_M >= -EPS || vector_N >= -EPS;
        } else {
            return false;
        }
    }

    private static Color interpolate(Color center, Color end, double ratio) {
        double r = center.getRed() + (end.getRed() - center.getRed()) * ratio;
        double g = center.getGreen() + (end.getGreen() - center.getGreen()) * ratio;
        double b = center.getBlue() + (end.getBlue() - center.getBlue()) * ratio;

        r = Math.min(1, Math.max(0, r));
        g = Math.min(1, Math.max(0, g));
        b = Math.min(1, Math.max(0, b));

        return new Color(r, g, b, 1.0);
    }
}
