package com.labwork.exampleopengles.core.components.concrete;

import android.opengl.Matrix;
import com.labwork.exampleopengles.core.general.Axis;
import com.labwork.exampleopengles.core.general.Entity;
import com.labwork.exampleopengles.core.general.Vector3;
import com.labwork.exampleopengles.core.components.common.Component;

public final class TransformComponent extends Component {

    private static final float[] MATRIX_VECTOR_UP = { 0.0f, 1.0f, 0.0f, 0.0f };
    private static final float[] MATRIX_VECTOR_RIGHT = { 1.0f, 0.0f, 0.0f, 0.0f };
    private static final float[] MATRIX_VECTOR_FORWARD = { 0.0f, 0.0f, -1.0f, 0.0f };

    private final Vector3 scale;
    private final Vector3 rotation;
    private final Vector3 position;
    private final Vector3 vectorUp;
    private final Vector3 vectorRight;
    private final Vector3 vectorForward;

    private final float[] matrixModel;
    private final float[] matrixRotation;
    private final float[] matrixRotationOutput;

    public TransformComponent(Entity entity) {
        super(entity);
        this.matrixModel = new float[16];
        this.matrixRotation = new float[16];
        this.matrixRotationOutput = new float[4];
        this.scale = new Vector3(1.0f, 1.0f, 1.0f);
        this.rotation = new Vector3(0.0f, 0.0f, 0.0f);
        this.position = new Vector3(0.0f, 0.0f, 0.0f);
        this.vectorUp = new Vector3(0.0f, 0.0f, 0.0f);
        this.vectorRight = new Vector3(0.0f, 0.0f, 0.0f);
        this.vectorForward = new Vector3(0.0f, 0.0f, 0.0f);
    }

    public Vector3 getScale() {
        return this.scale;
    }

    public Vector3 getRotation() {
        return this.rotation;
    }

    public Vector3 getPosition() {
        return this.position;
    }

    public float[] getMatrixModel() {
        Matrix.setIdentityM(this.matrixModel, 0);
        Matrix.scaleM(this.matrixModel, 0, this.scale.getX(), this.scale.getY(), this.scale.getZ());
        Matrix.rotateM(this.matrixModel, 0, this.rotation.getX(), 1.0f, 0.0f, 0.0f);
        Matrix.rotateM(this.matrixModel, 0, this.rotation.getY(), 0.0f, 1.0f, 0.0f);
        Matrix.rotateM(this.matrixModel, 0, this.rotation.getZ(), 0.0f, 0.0f, 1.0f);
        Matrix.translateM(this.matrixModel, 0, this.position.getX(), this.position.getY(), this.position.getZ());
        return this.matrixModel;
    }

    public Vector3 getUp() {
        Matrix.multiplyMV(this.matrixRotationOutput, 0, this.getRotationMatrix(), 0, TransformComponent.MATRIX_VECTOR_UP, 0);
        this.vectorUp.setX(this.matrixRotationOutput[Axis.X.ordinal()]);
        this.vectorUp.setY(this.matrixRotationOutput[Axis.Y.ordinal()]);
        this.vectorUp.setZ(this.matrixRotationOutput[Axis.Z.ordinal()]);
        return this.vectorUp;
    }

    public Vector3 getRight() {
        Matrix.multiplyMV(this.matrixRotationOutput, 0, this.getRotationMatrix(), 0, TransformComponent.MATRIX_VECTOR_RIGHT, 0);
        this.vectorRight.setX(this.matrixRotationOutput[Axis.X.ordinal()]);
        this.vectorRight.setY(this.matrixRotationOutput[Axis.Y.ordinal()]);
        this.vectorRight.setZ(this.matrixRotationOutput[Axis.Z.ordinal()]);
        return this.vectorRight;
    }

    public Vector3 getForward() {
        Matrix.multiplyMV(this.matrixRotationOutput, 0, this.getRotationMatrix(), 0, TransformComponent.MATRIX_VECTOR_FORWARD, 0);
        this.vectorForward.setX(this.matrixRotationOutput[Axis.X.ordinal()]);
        this.vectorForward.setY(this.matrixRotationOutput[Axis.Y.ordinal()]);
        this.vectorForward.setZ(this.matrixRotationOutput[Axis.Z.ordinal()]);
        return this.vectorForward;
    }

    private float[] getRotationMatrix() {
        Matrix.setIdentityM(this.matrixRotation, 0);
        Matrix.rotateM(this.matrixRotation, 0, this.rotation.getX(), 1.0f, 0.0f, 0.0f);
        Matrix.rotateM(this.matrixRotation, 0, this.rotation.getY(), 0.0f, 1.0f, 0.0f);
        Matrix.rotateM(this.matrixRotation, 0, this.rotation.getZ(), 0.0f, 0.0f, 1.0f);
        return this.matrixRotation;
    }
}
