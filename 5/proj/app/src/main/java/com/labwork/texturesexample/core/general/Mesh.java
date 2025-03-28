package com.labwork.texturesexample.core.general;

import java.nio.ByteOrder;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import android.opengl.GLES32;

public final class Mesh {
    private static final int HANDLERS_COUNT = 3;

    private static final int HANDLER_INDEX_VAO = 0;
    private static final int HANDLER_INDEX_VBO = 1;
    private static final int HANDLER_INDEX_EBO = 2;

    public static final int PAYLOAD_POSITION_SIZE = 3;
    public static final int PAYLOAD_POSITION_INDEX = 0;
    public static final int PAYLOAD_POSITION_OFFSET = 0;

    public static final int PAYLOAD_TEXTURE_SIZE = 2;
    public static final int PAYLOAD_TEXTURE_INDEX = 1;
    public static final int PAYLOAD_TEXTURE_OFFSET = (Mesh.PAYLOAD_POSITION_SIZE) * Float.BYTES;

    public static final int PAYLOAD_NORMAL_SIZE = 3;
    public static final int PAYLOAD_NORMAL_INDEX = 2;
    public static final int PAYLOAD_NORMAL_OFFSET = (Mesh.PAYLOAD_POSITION_SIZE + Mesh.PAYLOAD_TEXTURE_SIZE) * Float.BYTES;

    public static final int PAYLOAD_COLOR_SIZE = 3;
    public static final int PAYLOAD_COLOR_INDEX = 3;
    public static final int PAYLOAD_COLOR_OFFSET = (Mesh.PAYLOAD_POSITION_SIZE + Mesh.PAYLOAD_TEXTURE_SIZE + Mesh.PAYLOAD_NORMAL_SIZE) * Float.BYTES;

    public static final int PAYLOAD_STRIDE = (Mesh.PAYLOAD_POSITION_SIZE + Mesh.PAYLOAD_TEXTURE_SIZE + Mesh.PAYLOAD_NORMAL_SIZE + Mesh.PAYLOAD_COLOR_SIZE) * Float.BYTES;

    private final int[] handlers;
    private final int drawingMode;
    private final int indicesCount;

    public Mesh(float[] vertices, int[] indices, int drawingMode) {
        this.drawingMode = drawingMode;
        this.indicesCount = indices.length;
        this.handlers = new int[Mesh.HANDLERS_COUNT];

        FloatBuffer vertexBuffer = ByteBuffer.allocateDirect(vertices.length * Float.BYTES).order(ByteOrder.nativeOrder()).asFloatBuffer();
        vertexBuffer.put(vertices).position(0);

        IntBuffer indexBuffer = ByteBuffer.allocateDirect(indices.length * Integer.BYTES).order(ByteOrder.nativeOrder()).asIntBuffer();
        indexBuffer.put(indices).position(0);

        GLES32.glGenVertexArrays(1, this.handlers, Mesh.HANDLER_INDEX_VAO);
        GLES32.glGenBuffers(1, this.handlers, Mesh.HANDLER_INDEX_VBO);
        GLES32.glGenBuffers(1, this.handlers, Mesh.HANDLER_INDEX_EBO);

        GLES32.glBindVertexArray(this.handlers[Mesh.HANDLER_INDEX_VAO]);

        GLES32.glBindBuffer(GLES32.GL_ARRAY_BUFFER, this.handlers[Mesh.HANDLER_INDEX_VBO]);
        GLES32.glBufferData(GLES32.GL_ARRAY_BUFFER, vertices.length * Float.BYTES, vertexBuffer, GLES32.GL_STATIC_DRAW);

        GLES32.glBindBuffer(GLES32.GL_ELEMENT_ARRAY_BUFFER, this.handlers[Mesh.HANDLER_INDEX_EBO]);
        GLES32.glBufferData(GLES32.GL_ELEMENT_ARRAY_BUFFER, indices.length * Integer.BYTES, indexBuffer, GLES32.GL_STATIC_DRAW);

        GLES32.glVertexAttribPointer(Mesh.PAYLOAD_POSITION_INDEX, Mesh.PAYLOAD_POSITION_SIZE, GLES32.GL_FLOAT, false, Mesh.PAYLOAD_STRIDE, Mesh.PAYLOAD_POSITION_OFFSET);
        GLES32.glEnableVertexAttribArray(Mesh.PAYLOAD_POSITION_INDEX);

        GLES32.glVertexAttribPointer(Mesh.PAYLOAD_TEXTURE_INDEX, Mesh.PAYLOAD_TEXTURE_SIZE, GLES32.GL_FLOAT, false, Mesh.PAYLOAD_STRIDE, Mesh.PAYLOAD_TEXTURE_OFFSET);
        GLES32.glEnableVertexAttribArray(Mesh.PAYLOAD_TEXTURE_INDEX);

        GLES32.glVertexAttribPointer(Mesh.PAYLOAD_NORMAL_INDEX, Mesh.PAYLOAD_NORMAL_SIZE, GLES32.GL_FLOAT, false, Mesh.PAYLOAD_STRIDE, Mesh.PAYLOAD_NORMAL_OFFSET);
        GLES32.glEnableVertexAttribArray(Mesh.PAYLOAD_NORMAL_INDEX);

        GLES32.glVertexAttribPointer(Mesh.PAYLOAD_COLOR_INDEX, Mesh.PAYLOAD_COLOR_SIZE, GLES32.GL_FLOAT, false, Mesh.PAYLOAD_STRIDE, Mesh.PAYLOAD_COLOR_OFFSET);
        GLES32.glEnableVertexAttribArray(Mesh.PAYLOAD_COLOR_INDEX);

        GLES32.glBindVertexArray(0);
        GLES32.glBindBuffer(GLES32.GL_ARRAY_BUFFER, 0);
        GLES32.glBindBuffer(GLES32.GL_ELEMENT_ARRAY_BUFFER, 0);
    }

    public int getId() {
        return this.handlers[Mesh.HANDLER_INDEX_VAO];
    }

    public void draw() {
        GLES32.glBindVertexArray(this.handlers[Mesh.HANDLER_INDEX_VAO]);
        GLES32.glDrawElements(this.drawingMode, this.indicesCount, GLES32.GL_UNSIGNED_INT, 0);
        GLES32.glBindVertexArray(0);
    }

    public void delete() {
        GLES32.glDeleteBuffers(this.handlers.length, this.handlers, 0);
    }
}