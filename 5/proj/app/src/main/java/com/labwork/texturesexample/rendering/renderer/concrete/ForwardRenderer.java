package com.labwork.texturesexample.rendering.renderer.concrete;

import java.util.List;
import java.util.ArrayList;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;
import android.opengl.GLES32;
import com.labwork.texturesexample.runtime.Framework;
import com.labwork.texturesexample.core.general.Scene;
import com.labwork.texturesexample.core.general.Entity;
import com.labwork.texturesexample.rendering.features.common.RenderFeature;
import com.labwork.texturesexample.rendering.renderer.common.RendererProgrammable;

public final class ForwardRenderer implements RendererProgrammable {
    private final List<RenderFeature> features;
    private final List<Entity> dispatchedEntities;
    private final Runnable initializationCallback;

    private float deltaTime;
    private float timestampCurrent;
    private float timestampPrevious;

    public ForwardRenderer(Runnable initializationCallback) {
        this.features = new ArrayList<>();
        this.dispatchedEntities = new ArrayList<>();
        this.initializationCallback = initializationCallback;
    }

    public void onDrawFrame(GL10 unused) {
        this.timestampCurrent = System.nanoTime();
        this.deltaTime = (this.timestampCurrent - this.timestampPrevious) / 1_000_000_000.0f;
        this.timestampPrevious = this.timestampCurrent;

        if (this.deltaTime > 0.95f) {
            this.deltaTime = 0.95f;
        }

        if (Framework.getInstance().getScene() == null)
            return;

        this.dispatchedEntities.clear();

        List<Entity> entities = Framework.getInstance().getScene().getEntities();

        for (Entity entity : entities) {
            if (entity.getIsActive()) {
                entity.onUpdate(this.deltaTime);
                this.dispatchedEntities.add(entity);
            }
        }

        for (RenderFeature feature : this.features)
            feature.execute(this.dispatchedEntities);
    }

    public void onSurfaceCreated(GL10 unused, EGLConfig config) {
        this.initializationCallback.run();
        this.timestampPrevious = System.nanoTime();
    }

    public void onSurfaceChanged(GL10 unused, int width, int height) {
        GLES32.glViewport(0, 0, width, height);
    }

    public void loadScene(Scene scene) {
        List<Entity> entities = Framework.getInstance().getScene().getEntities();

        for (Entity entity : entities)
            entity.onStart();
    }

    public void registerRenderFeature(RenderFeature feature) {
        this.features.add(feature);
    }
}