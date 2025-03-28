package com.labwork.texturesexample.runtime;

import android.opengl.GLSurfaceView;
import com.labwork.texturesexample.core.general.Scene;
import com.labwork.texturesexample.rendering.renderer.common.RendererProgrammable;
import com.labwork.texturesexample.rendering.viewport.common.ViewportConfigurable;

public final class Framework {
    private static final Framework INSTANCE = new Framework();

    private Scene scene;
    private GLSurfaceView surfaceView;
    private ViewportConfigurable viewport;
    private RendererProgrammable renderer;

    private Framework() { }

    public static Framework getInstance() {
        return Framework.INSTANCE;
    }

    public Scene getScene() {
        return this.scene;
    }

    public GLSurfaceView getSurfaceView() {
        return this.surfaceView;
    }

    public ViewportConfigurable getViewport() {
        return this.viewport;
    }

    public RendererProgrammable getRenderer() {
        return this.renderer;
    }

    public void loadScene(Scene scene) {
        if (this.scene != null)
            this.scene.onUnloaded();

        this.scene = scene;
        this.renderer.loadScene(scene);
    }

    public void initialize(RendererProgrammable renderer, ViewportConfigurable viewport) {
        viewport.initialize(renderer);
        this.renderer = renderer;
        this.viewport = viewport;
        this.surfaceView = viewport.getSurfaceView();
    }
}
