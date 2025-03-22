package com.labwork.animationsexample.rendering.renderer.common;

import android.opengl.GLSurfaceView.Renderer;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;
import com.labwork.animationsexample.core.general.Scene;
import com.labwork.animationsexample.rendering.passes.common.RenderPass;

public interface RendererProgrammable extends Renderer {
    void onDrawFrame(GL10 unused);
    void onSurfaceCreated(GL10 unused, EGLConfig config);
    void onSurfaceChanged(GL10 unused, int width, int height);
    void loadScene(Scene scene);
    void registerRenderPass(RenderPass pass);
}
