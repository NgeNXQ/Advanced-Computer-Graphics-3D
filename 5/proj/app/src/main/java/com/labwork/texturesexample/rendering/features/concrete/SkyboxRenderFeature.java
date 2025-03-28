package com.labwork.texturesexample.rendering.features.concrete;

import java.util.List;
import android.opengl.GLES32;
import com.labwork.texturesexample.runtime.Framework;
import com.labwork.texturesexample.core.general.Entity;
import com.labwork.texturesexample.core.general.Shader;
import com.labwork.texturesexample.rendering.features.common.RenderFeature;
import com.labwork.texturesexample.core.components.concrete.CameraComponent;
import com.labwork.texturesexample.core.components.concrete.SkyboxRenderingComponent;

public final class SkyboxRenderFeature extends RenderFeature {

    public SkyboxRenderFeature(Shader shader) {
        super(shader);
    }

    @Override
    public final void execute(List<Entity> dispatchedEntities) {
        GLES32.glClear(GLES32.GL_COLOR_BUFFER_BIT | GLES32.GL_DEPTH_BUFFER_BIT);

        GLES32.glDepthMask(false);

        GLES32.glUseProgram(super.shader.getId());
        GLES32.glActiveTexture(GLES32.GL_TEXTURE0);

        CameraComponent camera = Framework.getInstance().getScene().getCamera();
        GLES32.glUniformMatrix4fv(super.shader.getVariableHandler("uMatrixView"), 1, false, camera.getMatrixView(), 0);
        GLES32.glUniformMatrix4fv(super.shader.getVariableHandler("uMatrixProjection"), 1, false, camera.getMatrixProjection(), 0);

        SkyboxRenderingComponent skybox = Framework.getInstance().getScene().getSkybox();

        if (skybox != null) {
            skybox.render();
        }

        GLES32.glUseProgram(0);

        GLES32.glDepthMask(true);
    }
}
