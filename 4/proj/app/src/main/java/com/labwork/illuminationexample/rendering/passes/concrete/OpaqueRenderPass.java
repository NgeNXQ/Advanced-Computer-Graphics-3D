package com.labwork.illuminationexample.rendering.passes.concrete;

import java.util.List;
import android.opengl.GLES32;
import com.labwork.illuminationexample.runtime.Framework;
import com.labwork.illuminationexample.core.general.Color;
import com.labwork.illuminationexample.core.general.Entity;
import com.labwork.illuminationexample.core.general.Shader;
import com.labwork.illuminationexample.core.general.Vector3;
import com.labwork.illuminationexample.core.general.Material;
import com.labwork.illuminationexample.rendering.passes.common.RenderPass;
import com.labwork.illuminationexample.core.components.concrete.LightComponent;
import com.labwork.illuminationexample.core.components.concrete.CameraComponent;
import com.labwork.illuminationexample.core.components.concrete.RenderingComponent;
import com.labwork.illuminationexample.core.components.concrete.TransformComponent;

public final class OpaqueRenderPass extends RenderPass {

    public OpaqueRenderPass(Shader shader) {
        super(shader);
    }

    @Override
    public final void execute(List<Entity> dispatchedEntities) {
        GLES32.glEnable(GLES32.GL_DEPTH_TEST);

        GLES32.glClear(GLES32.GL_COLOR_BUFFER_BIT | GLES32.GL_DEPTH_BUFFER_BIT);

        GLES32.glUseProgram(super.shader.getId());

        CameraComponent camera = Framework.getInstance().getScene().getCamera();
        Vector3 cameraPosition = camera.getEntity().getComponent(TransformComponent.class).getPosition();
        GLES32.glUniformMatrix4fv(super.shader.getVariableHandler("uMatrixView"), 1, false, camera.getMatrixView(), 0);
        GLES32.glUniformMatrix4fv(super.shader.getVariableHandler("uMatrixProjection"), 1, false, camera.getMatrixProjection(), 0);
        GLES32.glUniform3f(super.shader.getVariableHandler("uTransformCameraPositionGlobal"), cameraPosition.getX(), cameraPosition.getY(), cameraPosition.getZ());

        for (Entity entity: dispatchedEntities) {
            LightComponent light = entity.getComponent(LightComponent.class);
            RenderingComponent rendering = entity.getComponent(RenderingComponent.class);

            if (light != null) {
                light.render();
            }

            if (rendering != null) {
                Material material = rendering.getMaterial();
                GLES32.glUniform1f(super.shader.getVariableHandler("uMaterialPropertyAmbient"), material.getPropertyAmbient());
                GLES32.glUniform1f(super.shader.getVariableHandler("uMaterialPropertyDiffuse"), material.getPropertyDiffuse());
                GLES32.glUniform1f(super.shader.getVariableHandler("uMaterialPropertySpecular"), material.getPropertySpecular());

                Color color = material.getColorAlbedo();
                GLES32.glUniform4f(super.shader.getVariableHandler("uMaterialColorAlbedoRGBA"), color.getRNormalized(), color.getGNormalized(), color.getBNormalized(), color.getANormalized());

                rendering.render();
            }
        }

        GLES32.glUseProgram(0);

        GLES32.glDisable(GLES32.GL_DEPTH_TEST);
    }
}
