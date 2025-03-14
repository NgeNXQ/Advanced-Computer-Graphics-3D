package com.labwork.newtoncolorwheel.core.components.concrete;

import android.opengl.GLES32;
import android.opengl.Matrix;
import com.labwork.newtoncolorwheel.runtime.Framework;
import com.labwork.newtoncolorwheel.core.general.Mesh;
import com.labwork.newtoncolorwheel.core.general.Color;
import com.labwork.newtoncolorwheel.core.general.Entity;
import com.labwork.newtoncolorwheel.core.general.Shader;
import com.labwork.newtoncolorwheel.core.general.Material;
import com.labwork.newtoncolorwheel.core.components.common.Component;

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

    public void setMesh(Mesh value) {
        this.mesh = value;
    }

    public Material getMaterial() {
        return this.material;
    }

    public void setMaterial(Material value) {
        this.material = value;
    }

    @Override
    public void onStart() {
        this.transform = super.getEntity().getComponent(TransformComponent.class);
    }

    public void render(Class<?> renderPass) {
        Shader shader = this.material.getShader(renderPass);
        GLES32.glUseProgram(shader.getProgramId());

        Color color = this.material.getColorAlbedo();
        CameraComponent camera = Framework.getInstance().getScene().getCamera();

        Matrix.multiplyMM(this.matrixViewModel, 0, camera.getMatrixView(), 0, this.transform.getMatrixModel(), 0);
        Matrix.multiplyMM(this.matrixProjectionViewModel, 0, camera.getMatrixProjection(), 0, this.matrixViewModel, 0);
        GLES32.glUniformMatrix4fv(shader.getHandlerUniformMatrixMVP(), 1, false, this.matrixProjectionViewModel, 0);
        GLES32.glUniform4f(shader.getHandlerUniformMaterialAlbedoColor(), color.getRNormalized(), color.getGNormalized(), color.getBNormalized(), color.getANormalized());

        this.mesh.draw();

        GLES32.glUseProgram(0);
    }
}
