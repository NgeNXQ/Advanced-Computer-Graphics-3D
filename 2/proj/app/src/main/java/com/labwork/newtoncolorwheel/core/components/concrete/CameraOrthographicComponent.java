package com.labwork.newtoncolorwheel.core.components.concrete;

import android.opengl.GLES32;
import android.opengl.Matrix;
import com.labwork.newtoncolorwheel.core.general.Color;
import com.labwork.newtoncolorwheel.core.general.Entity;
import com.labwork.newtoncolorwheel.core.general.Vector3;

public final class CameraOrthographicComponent extends CameraComponent {

    private final Vector3 target;

    private Vector3 up;
    private Vector3 position;
    private TransformComponent transform;
    private float left, right, bottom, top;

    public CameraOrthographicComponent(Entity entity, Color color, float nearClippingPlane, float farClippingPlane, float left, float right, float bottom, float top) {
        super(entity, color, nearClippingPlane, farClippingPlane);
        this.top = top;
        this.left = left;
        this.right = right;
        this.bottom = bottom;
        this.up = new Vector3(0.0f, 1.0f, 0.0f);
        this.target = new Vector3(0.0f, 0.0f, -1.0f);
        this.position = new Vector3(0.0f, 0.0f, 0.0f);
    }

    public float getTop() {
        return top;
    }

    public float getLeft() {
        return left;
    }

    public float getRight() {
        return right;
    }

    public float getBottom() {
        return bottom;
    }

    public void setBounds(float left, float right, float bottom, float top) {
        this.top = top;
        this.left = left;
        this.right = right;
        this.bottom = bottom;
        Matrix.orthoM(super.matrixProjection, 0, left, right, bottom, top, super.nearClippingPlane, super.farClippingPlane);
    }

    @Override
    public void onStart() {
        this.transform = super.getEntity().getComponent(TransformComponent.class);
        this.up = this.transform.getUp();
        this.position = this.transform.getPosition();
        Matrix.orthoM(super.matrixProjection, 0, this.left, this.right, this.bottom, this.top, super.nearClippingPlane, super.farClippingPlane);
        GLES32.glClearColor(super.backgroundColor.getR(), super.backgroundColor.getG(), super.backgroundColor.getB(), super.backgroundColor.getA());
    }

    @Override
    public void onUpdate() {
        Vector3.add(this.transform.getPosition(), this.transform.getForward(), this.target);
        Matrix.orthoM(super.matrixProjection, 0, this.left, this.right, this.bottom, this.top, super.nearClippingPlane, super.farClippingPlane);
        Matrix.setLookAtM(super.matrixView, 0, this.position.getX(), this.position.getY(), this.position.getZ(), this.target.getX(), this.target.getY(), this.target.getZ(), this.up.getX(), this.up.getY(), this.up.getZ());
    }
}
