package com.labwork.exampleopengles.core.components.concrete;

import android.opengl.GLES32;
import android.opengl.Matrix;
import com.labwork.exampleopengles.runtime.Framework;
import com.labwork.exampleopengles.core.general.Mesh;
import com.labwork.exampleopengles.core.general.Entity;
import com.labwork.exampleopengles.core.general.Shader;
import com.labwork.exampleopengles.core.general.Material;
import com.labwork.exampleopengles.core.components.common.Component;

public final class RenderingComponent extends Component {

    private static final int MATRIX_DIMENSIONS_COUNT = 16;

    private final float[] matrixViewModel;
    private final float[] matrixProjectionViewModel;

    private Mesh mesh;
    private Material material;
    private TransformComponent transform;

    public RenderingComponent(Entity entity, Mesh mesh, Material material) {
        super(entity);
        this.mesh = mesh;
        this.material = material;
        this.matrixViewModel = new float[RenderingComponent.MATRIX_DIMENSIONS_COUNT];
        this.matrixProjectionViewModel = new float[RenderingComponent.MATRIX_DIMENSIONS_COUNT];
    }

    public Mesh getMesh() {
        return this.mesh;
    }

    public Material getMaterial() {
        return this.material;
    }

    @Override
    public void onStart() {
        this.transform = super.getEntity().getComponent(TransformComponent.class);
    }

    public void render(Class<?> renderPass) {
        Shader shader = this.material.getShader(renderPass);
        GLES32.glUseProgram(shader.getProgramId());

        CameraComponent camera = Framework.getInstance().getScene().getCamera();

        Matrix.multiplyMM(this.matrixViewModel, 0, camera.getMatrixView(), 0, this.transform.getMatrixModel(), 0);
        Matrix.multiplyMM(this.matrixProjectionViewModel, 0, camera.getMatrixProjection(), 0, this.matrixViewModel, 0);

        int handlerUMatrixMVP = GLES32.glGetUniformLocation(shader.getProgramId(), "uMatrixMVP");
        GLES32.glUniformMatrix4fv(handlerUMatrixMVP, 1, false, this.matrixProjectionViewModel, 0);

        int handlerUColorBase = GLES32.glGetUniformLocation(shader.getProgramId(), "uColorBase");
        GLES32.glUniform4f(handlerUColorBase, this.material.getBase().getR(), this.material.getBase().getG(), this.material.getBase().getB(), this.material.getBase().getA());

        int handlerInVertexPosition = GLES32.glGetAttribLocation(shader.getProgramId(), "inVertexPosition");
        GLES32.glVertexAttribPointer(handlerInVertexPosition, Mesh.PAYLOAD_VERTEX_POSITION_SIZE, GLES32.GL_FLOAT, false, Mesh.PAYLOAD_STRIDE, Mesh.PAYLOAD_VERTEX_POSITION_OFFSET);
        GLES32.glEnableVertexAttribArray(handlerInVertexPosition);

        this.mesh.draw();
        GLES32.glUseProgram(0);
    }
}
