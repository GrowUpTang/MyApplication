package com.ranran.myapplication.shortcutsdynamic;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.ranran.myapplication.R;

/**
 * Created by qibin on 16-10-20.
 */

public class MessageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.message);
        TextView msg = (TextView) findViewById(R.id.msg);
        msg.setText(getIntent().getStringExtra("msg"));
    }
}
