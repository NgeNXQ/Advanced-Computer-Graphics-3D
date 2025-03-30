package com.labwork.illuminationexample.demo.components;

import com.labwork.illuminationexample.core.general.Entity;
import com.labwork.illuminationexample.core.components.common.Component;
import com.labwork.illuminationexample.core.components.concrete.TransformComponent;

public final class RotationComponent extends Component {
    private final float speed = 300.0f;

    private float angle;
    private TransformComponent transform;

    public RotationComponent(Entity entity) {
        super(entity);
    }

    @Override
    public void onStart() {
        this.transform = super.getEntity().getComponent(TransformComponent.class);
    }

    @Override
    public void onUpdate(float deltaTime) {
        this.angle += this.speed * deltaTime;
        this.transform.getRotation().setY(this.angle);
    }
}
