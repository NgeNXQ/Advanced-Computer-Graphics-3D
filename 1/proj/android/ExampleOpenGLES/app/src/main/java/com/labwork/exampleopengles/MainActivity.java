package com.labwork.exampleopengles;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.labwork.exampleopengles.rendering.viewport.ManualGLSurfaceView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected final void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(new ManualGLSurfaceView(this));
    }
}
