package com.labwork.exampleopengles.core.general;

import java.util.List;
import java.util.ArrayList;
import java.util.Collection;
import com.labwork.exampleopengles.core.components.common.Component;
import com.labwork.exampleopengles.core.components.concrete.CameraComponent;

public final class Scene {

    private final List<Entity> entities;

    private CameraComponent camera;

    public Scene() {
        this.entities = new ArrayList<>();
    }

    public List<Entity> getEntities() {
        return this.entities;
    }

    public CameraComponent getCamera() {
        return this.camera;
    }

    public void addEntity(Entity entity) {
        this.entities.add(entity);

        Collection<Component> components = entity.getComponents();

        for (Component component : components) {
            if (component instanceof CameraComponent) {
                this.camera = (CameraComponent) component;
            }
        }
    }
}
