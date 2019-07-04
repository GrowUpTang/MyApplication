package com.ranran.myapplication;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ShortcutInfo;
import android.content.pm.ShortcutManager;
import android.graphics.drawable.Icon;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main2Activity extends AppCompatActivity {

    private TextView textView3;
    private ShortcutManager mShortcutManager;

    @RequiresApi(api = Build.VERSION_CODES.N_MR1)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        textView3 = findViewById(R.id.bt_3);
        textView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(Main2Activity.this, Main3Activity.class);
                startActivity(intent);
            }
        });

        getSystemService(ShortcutManager.class);

    }

    private void setupShortcuts() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N_MR1) {
                mShortcutManager = getSystemService(ShortcutManager.class);
            }
        }

        List<ShortcutInfo> infos = new ArrayList<>();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N_MR1) {
            for (int i = 0; i < mShortcutManager.getMaxShortcutCountPerActivity(); i++) {
                Intent intent = new Intent(this, MainActivity.class);
                intent.setAction(Intent.ACTION_VIEW);
//                intent.putExtra("msg", "我和" + mAdapter.getItem(i) + "的对话");
//
//                ShortcutInfo info = new ShortcutInfo.Builder(this, "id" + i)
//                        .setShortLabel(mAdapter.getItem(i))
//                        .setLongLabel("联系人:" + mAdapter.getItem(i))
//                        .setIcon(Icon.createWithResource(this, R.drawable.got))
//                        .setIntent(intent)
//                        .build();
//                infos.add(info);
    //            manager.addDynamicShortcuts(Arrays.asList(info));
            }
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N_MR1) {
            mShortcutManager.setDynamicShortcuts(infos);
        }
    }


    private void removeItem(int index) {
        List<ShortcutInfo> infos = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N_MR1) {
            infos = mShortcutManager.getPinnedShortcuts();
        }
        for (ShortcutInfo info : infos) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N_MR1) {
                if (info.getId().equals("id" + index)) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N_MR1) {
                        mShortcutManager.disableShortcuts(Arrays.asList(info.getId()), "暂无该联系人");
                    }
                }
            }
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N_MR1) {
            mShortcutManager.removeDynamicShortcuts(Arrays.asList("id" + index));
        }
    }

}
