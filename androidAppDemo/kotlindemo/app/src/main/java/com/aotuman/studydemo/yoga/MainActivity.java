package com.aotuman.studydemo.yoga;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;

import com.aotuman.studydemo.R;
import com.facebook.soloader.SoLoader;
import com.facebook.yoga.android.YogaViewLayoutFactory;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        LayoutInflater.from(this).setFactory(YogaViewLayoutFactory.getInstance());
        super.onCreate(savedInstanceState);
        SoLoader.init(this, false);

        setContentView(R.layout.main_layout);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.action_bar_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // There is only one option
        Intent intent = new Intent(this, BenchmarkActivity.class);
        startActivity(intent);
        this.finish();
        return true;
    }
}