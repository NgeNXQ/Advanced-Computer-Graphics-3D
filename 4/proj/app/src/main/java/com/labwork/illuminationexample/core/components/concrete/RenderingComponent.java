package com.labwork.illuminationexample.core.components.concrete;

import android.opengl.GLES32;
import com.labwork.illuminationexample.core.general.Mesh;
import com.labwork.illuminationexample.core.general.Entity;
import com.labwork.illuminationexample.core.general.Shader;
import com.labwork.illuminationexample.core.general.Material;
import com.labwork.illuminationexample.core.components.common.Component;

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

    public void render() {
        Shader shader = this.material.getShader();
        GLES32.glUniformMatrix4fv(shader.getVariableHandler("uMatrixModel"), 1, false, this.transform.getMatrixModel(), 0);

        this.mesh.draw();
    }
}
