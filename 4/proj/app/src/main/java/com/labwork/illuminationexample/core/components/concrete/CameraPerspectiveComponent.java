package com.labwork.illuminationexample.core.components.concrete;

import android.opengl.GLES32;
import android.opengl.Matrix;
import android.opengl.GLSurfaceView;
import com.labwork.illuminationexample.runtime.Framework;
import com.labwork.illuminationexample.core.general.Color;
import com.labwork.illuminationexample.core.general.Entity;
import com.labwork.illuminationexample.core.general.Vector3;

public final class CameraPerspectiveComponent extends CameraComponent {
    private final Vector3 target;
    private final GLSurfaceView viewport;

    private Vector3 up;
    private Vector3 position;
    private float aspectRatio;
    private float fieldOfView;
    private TransformComponent transform;

    public CameraPerspectiveComponent(Entity entity, Color color, float nearClippingPlane, float farClippingPlane, float aspectRatio, float fieldOfView) {
        super(entity, color, nearClippingPlane, farClippingPlane);
        this.viewport = Framework.getInstance().getSurfaceView();
        this.fieldOfView = fieldOfView;
        this.aspectRatio = aspectRatio;
        this.up = new Vector3(0.0f, 1.0f, 0.0f);
        this.target = new Vector3(0.0f, 0.0f, 1.0f);
        this.position = new Vector3(0.0f, 0.0f, 0.0f);
    }

    public float getAspectRatio() {
        return this.aspectRatio;
    }

    public void setAspectRatio(float value) {
        this.aspectRatio = value;
    }

    public float getFieldOfView() {
        return this.fieldOfView;
    }

    public void setFieldOfView(float value) {
        this.fieldOfView = value;
    }

    @Override
    public void onStart() {
        this.transform = super.getEntity().getComponent(TransformComponent.class);
        this.up = this.transform.getUp();
        this.position = this.transform.getPosition();
    }

    @Override
    public void onUpdate(float deltaTime) {
        this.setAspectRatio((float)this.viewport.getWidth() / this.viewport.getHeight());
        GLES32.glClearColor(super.backgroundColor.getRNormalized(), super.backgroundColor.getGNormalized(), super.backgroundColor.getBNormalized(), super.backgroundColor.getANormalized());

        Vector3.add(this.transform.getPosition(), this.transform.getForward(), this.target);
        Matrix.perspectiveM(super.matrixProjection, 0, this.fieldOfView, this.aspectRatio, super.nearClippingPlane, super.farClippingPlane);
        Matrix.setLookAtM(super.matrixView, 0, this.position.getX(), this.position.getY(), this.position.getZ(), this.target.getX(), this.target.getY(), this.target.getZ(), this.up.getX(), this.up.getY(), this.up.getZ());
    }
}
