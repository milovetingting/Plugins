package com.wangyz.sample;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.wangyz.utils.debounce.DebounceHelper;

public class MainActivity extends Activity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ConstraintLayout layout = findViewById(R.id.container);
//        DebounceHelper.setChildIgnoreFlag(layout);
    }

    @Override
    public void onClick(View view) {
        Toast.makeText(this, "click", Toast.LENGTH_SHORT).show();
    }

    public void onDoubleClick(View view) {
        Toast.makeText(this, "DoubleClick", Toast.LENGTH_SHORT).show();
    }
}