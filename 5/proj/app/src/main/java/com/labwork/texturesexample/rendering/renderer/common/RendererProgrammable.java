package com.labwork.texturesexample.rendering.renderer.common;

import android.opengl.GLSurfaceView.Renderer;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;
import com.labwork.texturesexample.core.general.Scene;
import com.labwork.texturesexample.rendering.features.common.RenderFeature;

public interface RendererProgrammable extends Renderer {
    void onDrawFrame(GL10 unused);
    void onSurfaceCreated(GL10 unused, EGLConfig config);
    void onSurfaceChanged(GL10 unused, int width, int height);
    void loadScene(Scene scene);
    void registerRenderFeature(RenderFeature feature);
}
