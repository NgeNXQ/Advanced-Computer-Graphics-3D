package com.labwork.illuminationexample.core.components.concrete;

import android.opengl.GLES32;
import com.labwork.illuminationexample.core.general.Color;
import com.labwork.illuminationexample.core.general.Entity;
import com.labwork.illuminationexample.core.general.Shader;
import com.labwork.illuminationexample.core.general.Vector3;
import com.labwork.illuminationexample.core.components.common.Component;

public final class LightComponent extends Component {
    private Color color;
    private Shader shader;
    private float intensity;
    private boolean isDistanceDependent;
    private TransformComponent transform;

    public LightComponent(Entity entity, Shader shader, Color color, float intensity, boolean isDistanceDependent) {
        super(entity);
        this.color = color;
        this.shader = shader;
        this.intensity = intensity;
        this.isDistanceDependent = isDistanceDependent;
    }

    public Color getColor() {
        return this.color;
    }

    public void setColor(Color value) {
        this.color = value;
    }

    public float getIntensity() {
        return this.intensity;
    }

    public void setIntensity(float value) {
        this.intensity = value;
    }

    @Override
    public void onStart() {
        this.transform = super.getEntity().getComponent(TransformComponent.class);
    }

    public void render() {
        GLES32.glUniform1f(this.shader.getVariableHandler("uLightPropertyIntensity"), this.intensity);
        GLES32.glUniform1i(this.shader.getVariableHandler("uIsDistanceDependent"), this.isDistanceDependent ? 1 : 0);
        GLES32.glUniform4f(this.shader.getVariableHandler("uLightPropertyColorRGBA"), this.color.getRNormalized(), this.color.getGNormalized(), this.color.getBNormalized(), this.color.getANormalized());

        Vector3 position = this.transform.getPosition();
        GLES32.glUniform3f(this.shader.getVariableHandler("uTransformLightPositionGlobal"), position.getX(), position.getY(), position.getZ());
    }
}
