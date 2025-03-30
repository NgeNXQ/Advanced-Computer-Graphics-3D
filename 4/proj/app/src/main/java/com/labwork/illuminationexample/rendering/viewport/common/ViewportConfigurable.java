package com.labwork.illuminationexample.rendering.viewport.common;

import android.view.View;
import android.widget.RelativeLayout;
import android.opengl.GLSurfaceView;
import com.labwork.illuminationexample.rendering.renderer.common.RendererProgrammable;

public interface ViewportConfigurable {
    RelativeLayout getLayout();
    GLSurfaceView getSurfaceView();
    void register(View view);
    void initialize(RendererProgrammable renderer);
}
