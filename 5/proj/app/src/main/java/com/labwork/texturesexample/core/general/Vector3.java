package com.labwork.texturesexample.core.general;

public final class Vector3 {
    private float x;
    private float y;
    private float z;

    public Vector3(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public float getX() {
        return this.x;
    }
    public void setX(float value) {
        this.x = value;
    }

    public float getY() {
        return this.y;
    }
    public void setY(float value) {
        this.y = value;
    }

    public float getZ() {
        return this.z;
    }

    public void setZ(float value) {
        this.z = value;
    }

    public void setXYZ(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public float getMagnitude() {
        return (float) Math.sqrt(this.x * this.x + this.y * this.y + this.z * this.z);
    }

    public static float dot(Vector3 a, Vector3 b) {
        return a.x * b.x + a.y * b.y + a.z * b.z;
    }

    public static void add(Vector3 a, Vector3 b, Vector3 output) {
        output.x = a.x + b.x;
        output.y = a.y + b.y;
        output.z = a.z + b.z;
    }

    public static void subtract(Vector3 a, Vector3 b, Vector3 output) {
        output.x = a.x - b.x;
        output.y = a.y - b.y;
        output.z = a.z - b.z;
    }

    public static void multiply(Vector3 a, float scalar, Vector3 output) {
        output.x = a.x * scalar;
        output.y = a.y * scalar;
        output.z = a.z * scalar;
    }

    public static void cross(Vector3 a, Vector3 b, Vector3 output) {
        output.x = a.y * b.z - a.z * b.y;
        output.y = a.z * b.x - a.x * b.z;
        output.z = a.x * b.y - a.y * b.x;
    }

    public static void normalize(Vector3 a, Vector3 output) {
        float magnitude = (float) Math.sqrt(a.x * a.x + a.y * a.y + a.z * a.z);

        if (magnitude == 0) {
            output.x = 0;
            output.y = 0;
            output.z = 0;
        } else {
            output.x = a.x / magnitude;
            output.y = a.y / magnitude;
            output.z = a.z / magnitude;
        }
    }
}
