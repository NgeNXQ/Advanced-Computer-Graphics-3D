package com.labwork.animationsexample.core.general;

import android.opengl.GLES32;

public final class Shader {
    private final int vertId;
    private final int fragId;
    private final int programId;
    private final Class<?> renderPass;

    public Shader(Class<?> renderPass, String sourceVert, String sourceFrag) {
        this.renderPass = renderPass;
        this.programId = GLES32.glCreateProgram();

        this.vertId = GLES32.glCreateShader(GLES32.GL_VERTEX_SHADER);
        GLES32.glShaderSource(this.vertId, sourceVert);

        this.fragId = GLES32.glCreateShader(GLES32.GL_FRAGMENT_SHADER);
        GLES32.glShaderSource(this.fragId, sourceFrag);

        GLES32.glCompileShader(this.vertId);
        GLES32.glCompileShader(this.fragId);

        GLES32.glAttachShader(this.programId, this.vertId);
        GLES32.glAttachShader(this.programId, this.fragId);

        GLES32.glBindAttribLocation(this.programId, Mesh.PAYLOAD_VERTEX_COLOR_INDEX, "inVertexColor");
        GLES32.glBindAttribLocation(this.programId, Mesh.PAYLOAD_VERTEX_POSITION_INDEX, "inVertexPosition");

        GLES32.glLinkProgram(this.programId);
    }

    public int getId() {
        return this.programId;
    }

    public Class<?> getRenderPass() {
        return this.renderPass;
    }

    public int getVariableHandler(String identifier) {
        return GLES32.glGetUniformLocation(this.programId, identifier);
    }

    public void delete() {
        GLES32.glDetachShader(this.programId, this.vertId);
        GLES32.glDetachShader(this.programId, this.fragId);
        GLES32.glDeleteShader(this.vertId);
        GLES32.glDeleteShader(this.fragId);
        GLES32.glDeleteProgram(this.programId);
    }
}
