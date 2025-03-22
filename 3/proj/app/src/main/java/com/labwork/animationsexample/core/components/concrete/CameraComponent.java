package com.labwork.animationsexample.core.components.concrete;

import android.opengl.Matrix;
import com.labwork.animationsexample.core.general.Color;
import com.labwork.animationsexample.core.general.Entity;
import com.labwork.animationsexample.core.components.common.Component;

public class CameraComponent extends Component {

    private static final int MATRIX_DIMENSIONS_COUNT = 16;

    protected final float[] matrixView;
    protected final float[] matrixProjection;

    protected Color backgroundColor;
    protected float farClippingPlane;
    protected float nearClippingPlane;

    public CameraComponent(Entity entity, Color color, float nearClippingPlane, float farClippingPlane) {
        super(entity);
        this.backgroundColor = color;
        this.farClippingPlane = farClippingPlane;
        this.nearClippingPlane = nearClippingPlane;
        this.matrixView = new float[CameraComponent.MATRIX_DIMENSIONS_COUNT];
        this.matrixProjection = new float[CameraComponent.MATRIX_DIMENSIONS_COUNT];
        Matrix.setIdentityM(this.matrixView, 0);
        Matrix.setIdentityM(this.matrixProjection, 0);
    }

    public float[] getMatrixView() {
        return this.matrixView;
    }

    public float[] getMatrixProjection() {
        return this.matrixProjection;
    }

    public Color getBackgroundColor() {
        return this.backgroundColor;
    }

    public void setBackgroundColor(Color value) {
        this.backgroundColor = value;
    }

    public float getFarClippingPlane() {
        return this.farClippingPlane;
    }

    public void setFarClippingPlane(float value) {
        this.farClippingPlane = value;
    }

    public float getNearClippingPlane() {
        return this.nearClippingPlane;
    }

    public void setNearClippingPlane(float value) {
        this.nearClippingPlane = value;
    }
}
