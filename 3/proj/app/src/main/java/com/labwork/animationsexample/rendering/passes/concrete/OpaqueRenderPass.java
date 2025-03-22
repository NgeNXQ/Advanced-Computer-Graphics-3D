package com.labwork.animationsexample.rendering.passes.concrete;

import java.util.List;
import android.opengl.GLES32;
import com.labwork.animationsexample.runtime.Framework;
import com.labwork.animationsexample.core.general.Entity;
import com.labwork.animationsexample.core.general.Shader;
import com.labwork.animationsexample.rendering.passes.common.RenderPass;
import com.labwork.animationsexample.core.components.concrete.CameraComponent;
import com.labwork.animationsexample.core.components.concrete.RenderingComponent;

public final class OpaqueRenderPass extends RenderPass {

    public OpaqueRenderPass(Shader shader) {
        super(shader);
    }

    @Override
    public final void execute(List<Entity> dispatchedEntities) {
        CameraComponent camera = Framework.getInstance().getScene().getCamera();

        GLES32.glEnable(GLES32.GL_DEPTH_TEST);

        GLES32.glClear(GLES32.GL_COLOR_BUFFER_BIT | GLES32.GL_DEPTH_BUFFER_BIT);

        GLES32.glUseProgram(super.shader.getId());
        GLES32.glUniformMatrix4fv(super.shader.getVariableHandler("uMatrixView"), 1, false, camera.getMatrixView(), 0);
        GLES32.glUniformMatrix4fv(super.shader.getVariableHandler("uMatrixProjection"), 1, false, camera.getMatrixProjection(), 0);

        for (Entity entity: dispatchedEntities) {
            RenderingComponent rendering = entity.getComponent(RenderingComponent.class);

            if (rendering == null || rendering.getMaterial().getShader(OpaqueRenderPass.class) == null)
                continue;

            rendering.render(OpaqueRenderPass.class);
        }

        GLES32.glUseProgram(0);

        GLES32.glDisable(GLES32.GL_DEPTH_TEST);
    }
}
