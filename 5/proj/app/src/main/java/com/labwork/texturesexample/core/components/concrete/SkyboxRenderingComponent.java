package com.labwork.texturesexample.core.components.concrete;

import android.opengl.GLES32;
import com.labwork.texturesexample.core.general.Mesh;
import com.labwork.texturesexample.runtime.Framework;
import com.labwork.texturesexample.core.general.Color;
import com.labwork.texturesexample.core.general.Shader;
import com.labwork.texturesexample.core.general.Entity;
import com.labwork.texturesexample.core.general.Material;
import com.labwork.texturesexample.core.general.Texture3D;
import com.labwork.texturesexample.core.components.common.Component;

public final class SkyboxRenderingComponent extends Component {
    private static final int TEXTURE_UNIT_INDEX_SKYBOX = 0;

    private final Mesh mesh;
    private final Texture3D cubeTexture;

    private Material material;
    private TransformComponent transform;

    public SkyboxRenderingComponent(Entity entity, Mesh mesh, Material material, Texture3D cubeTexture) {
        super(entity);
        this.mesh = mesh;
        this.material = material;
        this.cubeTexture = cubeTexture;
    }

    public Mesh getMesh() {
        return this.mesh;
    }

    public Material getMaterial() {
        return this.material;
    }

    public void setMaterial(Material value) {
        this.material = value;
    }

    @Override
    public final void onStart() {
        this.transform = super.getEntity().getComponent(TransformComponent.class);
    }

    public void render() {
        Shader shader = this.material.getShader();

        GLES32.glUniformMatrix4fv(shader.getVariableHandler("uMatrixModel"), 1, false, this.transform.getMatrixModel(), 0);

        Color color = this.material.getColor();
        GLES32.glUniform4f(shader.getVariableHandler("uMaterialColorRGBA"), color.getRNormalized(), color.getGNormalized(), color.getBNormalized(), color.getANormalized());

        GLES32.glBindTexture(GLES32.GL_TEXTURE_CUBE_MAP, this.cubeTexture.getId());
        GLES32.glUniform1i(shader.getVariableHandler("uCubemapTextureAlbedo"), SkyboxRenderingComponent.TEXTURE_UNIT_INDEX_SKYBOX);

        this.mesh.draw();
    }
}