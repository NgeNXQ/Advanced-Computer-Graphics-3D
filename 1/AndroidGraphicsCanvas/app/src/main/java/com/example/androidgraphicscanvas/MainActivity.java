package com.example.androidgraphicscanvas;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

public final class MainActivity extends AppCompatActivity
{
    @Override
    protected final void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(new CustomGraphicsView(this));
    }
}
