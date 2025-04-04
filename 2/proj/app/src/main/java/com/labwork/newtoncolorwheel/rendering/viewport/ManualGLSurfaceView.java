package com.labwork.newtoncolorwheel.rendering.viewport;

import android.content.Context;
import android.opengl.GLSurfaceView;
import com.labwork.newtoncolorwheel.rendering.renderer.SimpleProgrammableRenderer;

public final class ManualGLSurfaceView extends GLSurfaceView {

    public ManualGLSurfaceView(Context context) {
        super(context);
        super.setEGLContextClientVersion(2);
        super.setRenderer(new SimpleProgrammableRenderer());
        super.setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
    }
}