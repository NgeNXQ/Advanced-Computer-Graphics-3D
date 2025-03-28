package com.labwork.texturesexample.core.general;

import android.opengl.GLES32;
import android.opengl.GLUtils;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public final class Texture2D {
    private static final int HANDLERS_COUNT = 1;
    private static final int HANDLER_INDEX_ID = 0;

    private final int[] handlers;

    public Texture2D(Context context, int resourceId, int wrap, int filter) {
        this.handlers = new int[Texture2D.HANDLERS_COUNT];

        GLES32.glGenTextures(1, this.handlers, 0);

        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inScaled = false;

        final Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), resourceId, options);

        GLES32.glBindTexture(GLES32.GL_TEXTURE_2D, this.handlers[Texture2D.HANDLER_INDEX_ID]);

        GLES32.glTexParameteri(GLES32.GL_TEXTURE_2D, GLES32.GL_TEXTURE_WRAP_S, wrap);
        GLES32.glTexParameteri(GLES32.GL_TEXTURE_2D, GLES32.GL_TEXTURE_WRAP_T, wrap);
        GLES32.glTexParameteri(GLES32.GL_TEXTURE_2D, GLES32.GL_TEXTURE_MIN_FILTER, filter);
        GLES32.glTexParameteri(GLES32.GL_TEXTURE_2D, GLES32.GL_TEXTURE_MAG_FILTER, filter);

        GLUtils.texImage2D(GLES32.GL_TEXTURE_2D, 0, bitmap, 0);

        bitmap.recycle();

        GLES32.glBindTexture(GLES32.GL_TEXTURE_2D, 0);
    }

    public int getId() {
        return this.handlers[Texture2D.HANDLER_INDEX_ID];
    }
}
