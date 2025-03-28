package com.labwork.texturesexample.core.general;

import android.opengl.GLES32;
import android.opengl.GLUtils;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public final class Texture3D {
    private static final int HANDLERS_COUNT = 1;
    private static final int HANDLER_INDEX_ID = 0;

    private final int[] handlers;

    public Texture3D(Context context, int[] resourceIds, int wrap, int filter) {
        this.handlers = new int[Texture3D.HANDLERS_COUNT];

        GLES32.glGenTextures(1, this.handlers, 0);
        GLES32.glBindTexture(GLES32.GL_TEXTURE_CUBE_MAP, this.handlers[Texture3D.HANDLER_INDEX_ID]);

        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inScaled = false;

        int[] cubeTargets = {
                GLES32.GL_TEXTURE_CUBE_MAP_POSITIVE_X, // Right
                GLES32.GL_TEXTURE_CUBE_MAP_NEGATIVE_X, // Left
                GLES32.GL_TEXTURE_CUBE_MAP_POSITIVE_Y, // Top
                GLES32.GL_TEXTURE_CUBE_MAP_NEGATIVE_Y, // Bottom
                GLES32.GL_TEXTURE_CUBE_MAP_POSITIVE_Z, // Front
                GLES32.GL_TEXTURE_CUBE_MAP_NEGATIVE_Z  // Back
        };

        for (int i = 0; i < 6; ++i) {
            final Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), resourceIds[i], options);
            GLUtils.texImage2D(cubeTargets[i], 0, bitmap, 0);
            bitmap.recycle();
        }

        GLES32.glTexParameteri(GLES32.GL_TEXTURE_CUBE_MAP, GLES32.GL_TEXTURE_WRAP_S, wrap);
        GLES32.glTexParameteri(GLES32.GL_TEXTURE_CUBE_MAP, GLES32.GL_TEXTURE_WRAP_T, wrap);
        GLES32.glTexParameteri(GLES32.GL_TEXTURE_CUBE_MAP, GLES32.GL_TEXTURE_WRAP_R, wrap);
        GLES32.glTexParameteri(GLES32.GL_TEXTURE_CUBE_MAP, GLES32.GL_TEXTURE_MIN_FILTER, filter);
        GLES32.glTexParameteri(GLES32.GL_TEXTURE_CUBE_MAP, GLES32.GL_TEXTURE_MAG_FILTER, filter);

        GLES32.glBindTexture(GLES32.GL_TEXTURE_CUBE_MAP, 0);
    }

    public int getId() {
        return this.handlers[Texture3D.HANDLER_INDEX_ID];
    }
}