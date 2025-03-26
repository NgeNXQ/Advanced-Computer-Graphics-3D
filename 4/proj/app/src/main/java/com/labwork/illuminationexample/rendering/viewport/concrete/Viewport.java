package com.labwork.illuminationexample.rendering.viewport.concrete;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import com.labwork.illuminationexample.rendering.renderer.common.RendererProgrammable;
import com.labwork.illuminationexample.rendering.viewport.common.ViewportConfigurable;

public final class Viewport extends GLSurfaceView implements ViewportConfigurable {
    private final RelativeLayout layout;

    public Viewport(Context context) {
        super(context);
        super.setEGLContextClientVersion(3);
        this.layout = new RelativeLayout(context);
        this.layout.addView(this, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
    }

    public RelativeLayout getLayout() {
        return this.layout;
    }

    public GLSurfaceView getSurfaceView() {
        return this;
    }

    public void register(View view) {
        this.layout.post(() -> {
            this.layout.addView(view);
        });
    }

    public void initialize(RendererProgrammable renderer) {
        super.setFocusable(true);
        super.setRenderer(renderer);
        super.setFocusableInTouchMode(true);
        super.setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);
    }
}