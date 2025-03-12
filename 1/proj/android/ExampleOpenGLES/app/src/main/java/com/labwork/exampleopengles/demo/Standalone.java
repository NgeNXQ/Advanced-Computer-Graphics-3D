package com.labwork.exampleopengles.demo;

public final class Standalone {

    public static final String SHADER_VERT_SOURCE =
            "#version 300 es\n" +
            "in vec3 inVertexPosition;\n" +
            "uniform mat4 uMatrixMVP;\n" +
            "void main() {\n" +
            "   gl_Position = uMatrixMVP * vec4(inVertexPosition, 1.0);\n" +
            "}\n";

    public static final String SHADER_FRAG_SOURCE =
            "#version 300 es\n" +
            "precision mediump float;\n" +
            "uniform vec4 uColorBase;\n" +
            "out vec4 outColorBase;\n" +
            "void main() {\n" +
            "   outColorBase = uColorBase;\n" +
            "}\n";
}
