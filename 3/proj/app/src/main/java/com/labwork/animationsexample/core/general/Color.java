package com.labwork.animationsexample.core.general;

public final class Color {

    private static final float MAX_CHANNEL_VALUE = 255.0f;

    private int r, g, b, a;
    private float rNormalized, gNormalized, bNormalized, aNormalized;

    public Color(int r, int g, int b, int a) {
        this.r = r;
        this.g = g;
        this.b = b;
        this.a = a;
        this.rNormalized = r / Color.MAX_CHANNEL_VALUE;
        this.gNormalized = g / Color.MAX_CHANNEL_VALUE;
        this.bNormalized = b / Color.MAX_CHANNEL_VALUE;
        this.aNormalized = a / Color.MAX_CHANNEL_VALUE;
    }

    public int getR() {
        return this.r;
    }

    public void setR(int value) {
        this.r = value;
        this.rNormalized = value / Color.MAX_CHANNEL_VALUE;
    }

    public float getRNormalized() {
        return this.rNormalized;
    }

    public int getG() {
        return this.g;
    }

    public void setG(int value) {
        this.g = value;
        this.gNormalized = value / Color.MAX_CHANNEL_VALUE;
    }

    public float getGNormalized() {
        return this.gNormalized;
    }

    public int getB() {
        return this.b;
    }

    public void setB(int value) {
        this.b = value;
        this.bNormalized = value / Color.MAX_CHANNEL_VALUE;
    }

    public float getBNormalized() {
        return this.bNormalized;
    }

    public int getA() {
        return this.a;
    }

    public void setA(int value) {
        this.a = value;
        this.aNormalized = value / Color.MAX_CHANNEL_VALUE;
    }

    public float getANormalized() {
        return this.aNormalized;
    }
}
