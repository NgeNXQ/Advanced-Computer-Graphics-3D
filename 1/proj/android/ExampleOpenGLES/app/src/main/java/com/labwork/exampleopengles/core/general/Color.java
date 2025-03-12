package com.labwork.exampleopengles.core.general;

public final class Color {

    private static final float MAX_FLOAT_VALUE = 255.0f;

    private int r;
    private int g;
    private int b;
    private int a;

    public Color(int r, int g, int b, int a) {
        this.r = r;
        this.g = g;
        this.b = b;
        this.a = a;
    }

    public float getR() {
        return this.r / Color.MAX_FLOAT_VALUE;
    }

    public void setR(int value) {
        this.r = value;
    }

    public float getG() {
        return this.g / Color.MAX_FLOAT_VALUE;
    }

    public void setG(int value) {
        this.g = value;
    }

    public float getB() {
        return this.b / Color.MAX_FLOAT_VALUE;
    }

    public void setB(int value) {
        this.b = value;
    }

    public float getA() {
        return this.a / Color.MAX_FLOAT_VALUE;
    }

    public void setA(int value) {
        this.a = value;
    }
}