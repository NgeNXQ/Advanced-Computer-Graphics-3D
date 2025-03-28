package com.labwork.texturesexample.core.components.concrete;

import android.opengl.GLES32;
import com.labwork.texturesexample.core.general.Mesh;
import com.labwork.texturesexample.core.general.Color;
import com.labwork.texturesexample.core.general.Entity;
import com.labwork.texturesexample.core.general.Shader;
import com.labwork.texturesexample.core.general.Material;
import com.labwork.texturesexample.core.components.common.Component;

public final class OpaqueRenderingComponent extends Component {
    private static final int TEXTURE_UNIT_INDEX_OPAQUE = 0;

    private Mesh mesh;
    private Material material;
    private TransformComponent transform;

    public OpaqueRenderingComponent(Entity entity, Mesh mesh, Material material) {
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

        Color color = this.material.getColor();
        GLES32.glUniform4f(shader.getVariableHandler("uMaterialColorRGBA"), color.getRNormalized(), color.getGNormalized(), color.getBNormalized(), color.getANormalized());

        GLES32.glBindTexture(GLES32.GL_TEXTURE_2D, this.material.getTextureAlbedo().getId());
        GLES32.glUniform1i(shader.getVariableHandler("uTextureAlbedo"), OpaqueRenderingComponent.TEXTURE_UNIT_INDEX_OPAQUE);

        this.mesh.draw();
    }
}
