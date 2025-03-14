package com.labwork.newtoncolorwheel;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import androidx.appcompat.app.AppCompatActivity;
import com.labwork.newtoncolorwheel.rendering.viewport.ManualGLSurfaceView;
import com.labwork.newtoncolorwheel.rendering.viewport.ContinuouslyGLSurfaceView;

public class MainActivity extends AppCompatActivity {

    private static final int MENU_ITEM_ID_DIRTY = 1;
    private static final int MENU_ITEM_ID_CONTINUOUSLY = 2;

    private ManualGLSurfaceView viewportManual;
    private ContinuouslyGLSurfaceView viewportContinuous;

    @Override
    protected final void onCreate(Bundle savedInstanceState) {
        this.viewportManual = new ManualGLSurfaceView(this);
        this.viewportContinuous = new ContinuouslyGLSurfaceView(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(0, MainActivity.MENU_ITEM_ID_DIRTY, 0, "Dirty");
        menu.add(0, MainActivity.MENU_ITEM_ID_CONTINUOUSLY, 0, "Continuously");
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.setTitle(item.getTitle());

        switch (item.getItemId()) {
            case MainActivity.MENU_ITEM_ID_DIRTY:
                super.setContentView(this.viewportManual);
                return true;
            case MainActivity.MENU_ITEM_ID_CONTINUOUSLY:
                super.setContentView(this.viewportContinuous);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
