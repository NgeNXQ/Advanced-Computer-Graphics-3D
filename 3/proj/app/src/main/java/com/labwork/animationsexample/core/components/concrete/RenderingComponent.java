package com.labwork.animationsexample.core.components.concrete;

import android.opengl.GLES32;
import com.labwork.animationsexample.core.general.Mesh;
import com.labwork.animationsexample.core.general.Color;
import com.labwork.animationsexample.core.general.Entity;
import com.labwork.animationsexample.core.general.Shader;
import com.labwork.animationsexample.core.general.Material;
import com.labwork.animationsexample.core.components.common.Component;

public final class RenderingComponent extends Component {
    private Mesh mesh;
    private Material material;
    private TransformComponent transform;

    public RenderingComponent(Entity entity, Mesh mesh, Material material) {
        super(entity);
        this.mesh = mesh;
        this.material = material;
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
        Color color = this.material.getColorAlbedo();
        Shader shader = this.material.getShader(renderPass);

        GLES32.glUniformMatrix4fv(shader.getVariableHandler("uMatrixModel"), 1, false, this.transform.getMatrixModel(), 0);
        GLES32.glUniform4f(shader.getVariableHandler("uMaterialAlbedoColor"), color.getRNormalized(), color.getGNormalized(), color.getBNormalized(), color.getANormalized());

        this.mesh.draw();
    }
}
