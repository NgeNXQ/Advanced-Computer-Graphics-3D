package com.labwork.newtoncolorwheel.core.components.common;

import com.labwork.newtoncolorwheel.core.general.Entity;

public class Component {

    private static int nextId;

    private final int id;
    private final Entity entity;

    private boolean isActive;

    public Component(Entity entity) {
        this.entity = entity;
        this.id = ++Component.nextId;
    }

    public int getId() {
        return this.id;
    }

    public Entity getEntity() {
        return this.entity;
    }

    public boolean getIsActive() {
        return this.isActive;
    }

    public void setIsActive(boolean value) {
        this.isActive = value;
    }

    public void onStart() {}

    public void onUpdate() {}

    public void onDestroy() {}
}
