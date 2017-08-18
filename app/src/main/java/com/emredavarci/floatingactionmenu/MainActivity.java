package com.emredavarci.floatingactionmenu;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FloatingActionMenu menu = (FloatingActionMenu) findViewById(R.id.floatingMenu);
        menu.setClickEvent(new FloatingActionMenu.ClickEvent() {
            @Override
            public void onClick(int index) {
                Log.d("TAG", String.valueOf(index)); // index of clicked menu item
            }
        });
    }
}
