package com.labwork.texturesexample.core.general;

import java.util.List;
import java.util.ArrayList;
import java.util.Collection;
import com.labwork.texturesexample.core.components.common.Component;
import com.labwork.texturesexample.core.components.concrete.CameraComponent;
import com.labwork.texturesexample.core.components.concrete.SkyboxRenderingComponent;

public final class Scene {
    private final List<Entity> entities;

    private CameraComponent camera;
    private SkyboxRenderingComponent skybox;

    public Scene() {
        this.entities = new ArrayList<>();
    }

    public List<Entity> getEntities() {
        return this.entities;
    }

    public CameraComponent getCamera() {
        return this.camera;
    }

    public SkyboxRenderingComponent getSkybox() {
        return this.skybox;
    }

    public void addEntity(Entity entity) {
        this.entities.add(entity);

        Collection<Component> components = entity.getComponents();

        for (Component component : components) {
            if (component instanceof CameraComponent) {
                this.camera = (CameraComponent) component;
            }
            if (component instanceof SkyboxRenderingComponent) {
                this.skybox = (SkyboxRenderingComponent) component;
            }
        }
    }

    public void onUnloaded() {
        for (Entity entity: this.entities)
            entity.onDestroy();
    }
}
