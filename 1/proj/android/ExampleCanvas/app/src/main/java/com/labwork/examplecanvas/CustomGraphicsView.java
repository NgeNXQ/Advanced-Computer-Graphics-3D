package com.labwork.examplecanvas;

import android.view.View;
import android.graphics.Path;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Canvas;
import android.content.Context;

final class CustomGraphicsView extends View {

    private static final int PREFERENCES_SUN_OFFSET_Y = 600;
    private static final int PREFERENCES_PYRAMID_OFFSET_X = 200;
    private static final int PREFERENCES_PYRAMID_OFFSET_Y = 50;
    private static final float PREFERENCES_PEN_FOREGROUND_THICKNESS = 2f;
    private static final int PREFERENCES_COLOR_BACKGROUND = Color.rgb(27, 27, 27);
    private static final int PREFERENCES_COLOR_FOREGROUND = Color.rgb(255, 20, 147);

    private static final int SHAPE_LINE_LENGTH = 225;
    private static final int SHAPE_TRIANGLE_SIDE_SIZE = 200;
    private static final int SHAPE_POLYGON_RADIUS = 125;
    private static final int SHAPE_POLYGON_VERTICES_COUNT = 16;

    private final Path polygonPath;
    private final Path trianglePath;

    private final Paint backgroundPaint;
    private final Paint foregroundPaint;

    public CustomGraphicsView(Context context) {
        super(context);

        this.polygonPath = new Path();
        this.trianglePath = new Path();

        this.backgroundPaint = new Paint();
        this.backgroundPaint.setStyle(Paint.Style.FILL);
        this.backgroundPaint.setColor(CustomGraphicsView.PREFERENCES_COLOR_BACKGROUND);

        this.foregroundPaint = new Paint();
        this.foregroundPaint.setStyle(Paint.Style.FILL);
        this.foregroundPaint.setColor(CustomGraphicsView.PREFERENCES_COLOR_FOREGROUND);
        this.foregroundPaint.setStrokeWidth(CustomGraphicsView.PREFERENCES_PEN_FOREGROUND_THICKNESS);
    }

    @Override
    protected final void onDraw(Canvas canvas) {
        if (canvas == null) {
            throw new IllegalArgumentException("canvas cannot be null");
        }

        super.onDraw(canvas);

        float centerX = getWidth() / 2f;
        float centerY = getHeight() / 2f;

        canvas.drawRect(0, 0, super.getWidth(), super.getHeight(), this.backgroundPaint);

        this.drawTriangle(canvas, centerX, centerY);
        this.drawPolygon(canvas, centerX, centerY);
        this.drawRays(canvas, centerX, centerY);
    }

    private void drawTriangle(Canvas canvas, float centerX, float centerY) {
        if (canvas == null) {
            throw new IllegalArgumentException("canvas cannot be null");
        }

        this.trianglePath.reset();

        centerX += CustomGraphicsView.PREFERENCES_PYRAMID_OFFSET_X;
        centerY += CustomGraphicsView.PREFERENCES_PYRAMID_OFFSET_Y;

        // Top vertex
        trianglePath.moveTo(centerX, centerY - CustomGraphicsView.SHAPE_TRIANGLE_SIDE_SIZE);
        // Bottom left
        trianglePath.lineTo(centerX - CustomGraphicsView.SHAPE_TRIANGLE_SIDE_SIZE,
                centerY + CustomGraphicsView.SHAPE_TRIANGLE_SIDE_SIZE);
        // Bottom right
        trianglePath.lineTo(centerX + CustomGraphicsView.SHAPE_TRIANGLE_SIDE_SIZE,
                centerY + CustomGraphicsView.SHAPE_TRIANGLE_SIDE_SIZE);

        trianglePath.close();

        canvas.drawPath(trianglePath, this.foregroundPaint);
    }

    private void drawPolygon(Canvas canvas, float centerX, float centerY) {
        if (canvas == null) {
            throw new IllegalArgumentException("canvas cannot be null");
        }

        this.polygonPath.reset();

        centerY -= CustomGraphicsView.PREFERENCES_SUN_OFFSET_Y;

        for (int i = 0; i < CustomGraphicsView.SHAPE_POLYGON_VERTICES_COUNT; ++i) {
            float angle = (float) (2 * Math.PI * i / CustomGraphicsView.SHAPE_POLYGON_VERTICES_COUNT);
            float x = (float) (CustomGraphicsView.SHAPE_POLYGON_RADIUS * Math.cos(angle)) + centerX;
            float y = (float) (CustomGraphicsView.SHAPE_POLYGON_RADIUS * Math.sin(angle)) + centerY;

            if (i == 0) {
                polygonPath.moveTo(x, y); 
            }else {
                polygonPath.lineTo(x, y);
            }
        }

        polygonPath.close();

        canvas.drawPath(polygonPath, this.foregroundPaint);
    }

    private void drawRays(Canvas canvas, float centerX, float centerY) {
        if (canvas == null) {
            throw new IllegalArgumentException("canvas cannot be null");
        }

        centerY -= CustomGraphicsView.PREFERENCES_SUN_OFFSET_Y;

        for (int i = 0; i < CustomGraphicsView.SHAPE_POLYGON_VERTICES_COUNT; ++i) {
            float angle = (float) (2 * Math.PI * i / CustomGraphicsView.SHAPE_POLYGON_VERTICES_COUNT);
            float endX = (float) (centerX + CustomGraphicsView.SHAPE_LINE_LENGTH * Math.cos(angle));
            float endY = (float) (centerY + CustomGraphicsView.SHAPE_LINE_LENGTH * Math.sin(angle));

            canvas.drawLine(centerX, centerY, endX, endY, this.foregroundPaint);
        }
    }
}
