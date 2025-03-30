package com.labwork.illuminationexample.core.general;

public final class Material {
    private Shader shader;
    private Color colorAlbedo;
    private float propertyAmbient;
    private float propertyDiffuse;
    private float propertySpecular;

    public Material(Shader shader, Color colorAlbedo, float propertyAmbient, float propertyDiffuse, float propertySpecular) {
        this.shader = shader;
        this.colorAlbedo = colorAlbedo;
        this.propertyAmbient = propertyAmbient;
        this.propertyDiffuse = propertyDiffuse;
        this.propertySpecular = propertySpecular;
    }

    public Shader getShader() {
        return this.shader;
    }

    public void setShader(Shader value) {
        this.shader = value;
    }

    public Color getColorAlbedo() {
        return this.colorAlbedo;
    }

    public void setColorAlbedo(Color value) {
        this.colorAlbedo= value;
    }

    public float getPropertyAmbient() {
        return this.propertyAmbient;
    }

    public void setPropertyAmbient(float value) {
        this.propertyAmbient = value;
    }

    public float getPropertyDiffuse() {
        return this.propertyDiffuse;
    }

    public void setPropertyDiffuse(float value) {
        this.propertyDiffuse = value;
    }

    public float getPropertySpecular() {
        return this.propertySpecular;
    }

    public void setPropertySpecular(float value) {
        this.propertySpecular = value;
    }
}
