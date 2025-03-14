package com.labwork.newtoncolorwheel.rendering.passes.concrete;

import java.util.List;
import android.opengl.GLES32;
import com.labwork.newtoncolorwheel.core.general.Entity;
import com.labwork.newtoncolorwheel.rendering.passes.common.RenderPass;
import com.labwork.newtoncolorwheel.core.components.concrete.RenderingComponent;

public final class OpaqueRenderPass extends RenderPass {

    @Override
    public final void execute(List<Entity> dispatchedEntities) {
        GLES32.glClear(GLES32.GL_COLOR_BUFFER_BIT | GLES32.GL_DEPTH_BUFFER_BIT);

        for (Entity entity: dispatchedEntities) {
            RenderingComponent renderingComponent = entity.getComponent(RenderingComponent.class);

            if (renderingComponent == null)
                continue;

            if (renderingComponent.getMaterial().getShader(OpaqueRenderPass.class) == null)
                continue;

            renderingComponent.render(OpaqueRenderPass.class);
        }
    }
}
