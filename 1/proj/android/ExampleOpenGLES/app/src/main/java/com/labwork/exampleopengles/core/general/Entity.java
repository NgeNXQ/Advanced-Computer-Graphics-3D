package com.labwork.exampleopengles.core.general;

import java.util.Map;
import java.util.HashMap;
import java.util.Collection;
import com.labwork.exampleopengles.core.components.common.Component;

public class Entity {

    private static int nextId;

    private final int id;
    private final Map<Class<?>, Component> components;

    private boolean isActive;

    public Entity() {
        this.isActive = true;
        this.id = ++Entity.nextId;
        this.components = new HashMap<>();
    }

    public int getId() {
        return this.id;
    }

    public boolean getIsActive() {
        return this.isActive;
    }

    public void setIsActive(boolean value) {
        this.isActive = value;
    }

    public Collection<Component> getComponents() {
        return this.components.values();
    }

    public void addComponent(Component component) {
        if (this.components.containsKey(component.getClass()))
            throw new IllegalArgumentException("Component of type " + component.getClass().getName() + " already exists.");
        
        this.components.put(component.getClass(), component);
    }

    public boolean hasComponent(Class<?> component) {
        return this.components.containsKey(component);
    }

    @SuppressWarnings("unchecked")
    public <T extends Component> T getComponent(Class<T> component) {
        return (T) this.components.getOrDefault(component, null);
    }

    public void onStart() {
        for (Component component : this.components.values())
            component.onStart();
    }

    public void onUpdate() {
        for (Component component : this.components.values())
            component.onUpdate();
    }

    public void onDestroy() {
        for (Component component : this.components.values())
            component.onDestroy();
    }
}
