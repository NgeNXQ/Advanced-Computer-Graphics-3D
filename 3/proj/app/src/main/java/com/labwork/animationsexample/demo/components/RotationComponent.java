package com.labwork.animationsexample.demo.components;

import com.labwork.animationsexample.core.general.Entity;
import com.labwork.animationsexample.core.components.common.Component;
import com.labwork.animationsexample.core.components.concrete.TransformComponent;

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
