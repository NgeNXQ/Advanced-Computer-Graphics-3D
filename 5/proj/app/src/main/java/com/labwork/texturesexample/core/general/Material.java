package com.labwork.texturesexample.core.general;

public final class Material {
    private Color color;
    private Shader shader;
    private float propertyAmbient;
    private float propertyDiffuse;
    private float propertySpecular;
    private Texture2D textureAlbedo;


    public Material(Shader shader, Color color, Texture2D textureAlbedo, float propertyAmbient, float propertyDiffuse, float propertySpecular) {
        this.color = color;
        this.shader = shader;
        this.textureAlbedo = textureAlbedo;
        this.propertyAmbient = propertyAmbient;
        this.propertyDiffuse = propertyDiffuse;
        this.propertySpecular = propertySpecular;
    }

    public Color getColor() {
        return this.color;
    }

    public void setColor(Color value) {
        this.color= value;
    }

    public Shader getShader() {
        return this.shader;
    }

    public void setShader(Shader value) {
        this.shader = value;
    }

    public Texture2D getTextureAlbedo() {
        return this.textureAlbedo;
    }

    public void setTextureAlbedo(Texture2D value) {
        this.textureAlbedo = value;
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
