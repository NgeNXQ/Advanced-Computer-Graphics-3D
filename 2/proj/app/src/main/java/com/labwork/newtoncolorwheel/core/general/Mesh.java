package com.labwork.newtoncolorwheel.core.general;

import java.nio.ByteOrder;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import android.opengl.GLES32;

public final class Mesh {

    private static int BINDING_HANDLERS_COUNT = 2;
    private static int BINDING_HANDLER_INDEX_VAO = 0;
    private static int BINDING_HANDLER_INDEX_VBO = 1;

    public static final int PAYLOAD_VERTEX_POSITION_SIZE = 3;
    public static final int PAYLOAD_VERTEX_POSITION_INDEX = 0;
    public static final int PAYLOAD_VERTEX_POSITION_OFFSET = 0;
    public static final int PAYLOAD_VERTEX_COLOR_SIZE = 4;
    public static final int PAYLOAD_VERTEX_COLOR_INDEX = 1;
    public static final int PAYLOAD_VERTEX_COLOR_OFFSET = Mesh.PAYLOAD_VERTEX_POSITION_SIZE * Float.BYTES;
    public static final int PAYLOAD_STRIDE = (Mesh.PAYLOAD_VERTEX_POSITION_SIZE + Mesh.PAYLOAD_VERTEX_COLOR_SIZE) * Float.BYTES;

    private final int drawingMode;
    private final int verticesCount;
    private final float[] verticesData;
    private final int[] bindingHandlers;

    public Mesh(float[] verticesData, int drawingMode) {
        this.drawingMode = drawingMode;
        this.verticesData = verticesData;
        this.bindingHandlers = new int[Mesh.BINDING_HANDLERS_COUNT];
        this.verticesCount = verticesData.length / (Mesh.PAYLOAD_VERTEX_POSITION_SIZE + Mesh.PAYLOAD_VERTEX_COLOR_SIZE);

        FloatBuffer vertexBuffer = ByteBuffer.allocateDirect(this.verticesData.length * Float.BYTES).order(ByteOrder.nativeOrder()).asFloatBuffer();
        vertexBuffer.put(this.verticesData).position(0);

        GLES32.glGenVertexArrays(1, this.bindingHandlers, Mesh.BINDING_HANDLER_INDEX_VAO);
        GLES32.glGenBuffers(1, this.bindingHandlers, Mesh.BINDING_HANDLER_INDEX_VBO);

        GLES32.glBindVertexArray(this.bindingHandlers[Mesh.BINDING_HANDLER_INDEX_VAO]);
        GLES32.glBindBuffer(GLES32.GL_ARRAY_BUFFER, this.bindingHandlers[Mesh.BINDING_HANDLER_INDEX_VBO]);
        GLES32.glBufferData(GLES32.GL_ARRAY_BUFFER, this.verticesData.length * Float.BYTES, vertexBuffer, GLES32.GL_STATIC_DRAW);

        GLES32.glVertexAttribPointer(Mesh.PAYLOAD_VERTEX_POSITION_INDEX, Mesh.PAYLOAD_VERTEX_POSITION_SIZE, GLES32.GL_FLOAT, false, Mesh.PAYLOAD_STRIDE, Mesh.PAYLOAD_VERTEX_POSITION_OFFSET);
        GLES32.glEnableVertexAttribArray(Mesh.PAYLOAD_VERTEX_POSITION_INDEX);

        GLES32.glVertexAttribPointer(Mesh.PAYLOAD_VERTEX_COLOR_INDEX, Mesh.PAYLOAD_VERTEX_COLOR_SIZE, GLES32.GL_FLOAT, false, Mesh.PAYLOAD_STRIDE, Mesh.PAYLOAD_VERTEX_COLOR_OFFSET);
        GLES32.glEnableVertexAttribArray(Mesh.PAYLOAD_VERTEX_COLOR_INDEX);

        GLES32.glBindVertexArray(0);
        GLES32.glEnableVertexAttribArray(0);
        GLES32.glBindBuffer(GLES32.GL_ARRAY_BUFFER, 0);
    }

    public void draw() {
        GLES32.glBindVertexArray(this.bindingHandlers[Mesh.BINDING_HANDLER_INDEX_VAO]);
        GLES32.glDrawArrays(this.drawingMode, 0, this.verticesCount);
        GLES32.glBindVertexArray(0);
    }

    public void delete() {
        GLES32.glDeleteBuffers(this.bindingHandlers.length, this.bindingHandlers, 0);
    }
}
