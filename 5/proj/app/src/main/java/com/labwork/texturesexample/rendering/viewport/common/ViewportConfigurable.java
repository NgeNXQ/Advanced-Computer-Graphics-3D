package com.labwork.texturesexample.rendering.viewport.common;

import android.view.View;
import android.widget.RelativeLayout;
import android.opengl.GLSurfaceView;
import com.labwork.texturesexample.rendering.renderer.common.RendererProgrammable;

public interface ViewportConfigurable {
    RelativeLayout getLayout();
    GLSurfaceView getSurfaceView();
    void register(View view);
    void initialize(RendererProgrammable renderer);
}
