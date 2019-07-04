package com.ranran.myapplication.shortcutsdynamic;

import android.Manifest;
import android.content.Intent;
import android.content.pm.ShortcutInfo;
import android.content.pm.ShortcutManager;
import android.graphics.drawable.Icon;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.qw.soul.permission.SoulPermission;
import com.qw.soul.permission.bean.Permissions;
import com.qw.soul.permission.callbcak.CheckRequestPermissionsListener;
import com.ranran.myapplication.R;

import java.security.acl.Permission;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainAShortctivity extends AppCompatActivity {

    private ListView mListView;
    private Adapter mAdapter;
    private ShortcutManager mShortcutManager;

    @RequiresApi(api = Build.VERSION_CODES.N_MR1)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        SoulPermission.getInstance().checkAndRequestPermissions(
                Permissions.build(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE),
                //if you want do noting or no need all the callbacks you may use SimplePermissionsAdapter instead
                new CheckRequestPermissionsListener() {
                    @Override
                    public void onAllPermissionOk(com.qw.soul.permission.bean.Permission[] allPermissions) {
                        Toast.makeText(MainAShortctivity.this, allPermissions.length + "permissions is ok" +
                                " \n  you can do your operations", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onPermissionDenied(com.qw.soul.permission.bean.Permission[] refusedPermissions) {
                        Toast.makeText(MainAShortctivity.this, refusedPermissions[0].toString() +
                                " \n is refused you can not do next things", Toast.LENGTH_SHORT).show();
                    }
                });

        mListView = (ListView) findViewById(R.id.list);
        setupAdapter();
        mListView.setAdapter(mAdapter);
        setupShortcuts();



        mListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N_MR1)
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                mAdapter.remove(i);
                removeItem(i);
                return true;
            }
        });

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                mAdapter.set("新的名字", i);
                updItem(i);
            }
        });


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N_MR1) {
            mShortcutManager = getSystemService(ShortcutManager.class);
            getNewShortcutInfo();
        }
    }

    /**
     * 动态添加三个
     */
    @RequiresApi(api = Build.VERSION_CODES.N_MR1)
    private void getNewShortcutInfo() {
        ShortcutInfo shortcut = new ShortcutInfo.Builder(this, "id1")
                .setShortLabel("Web site")
                .setLongLabel("打开百度")
                .setIcon(Icon.createWithResource(this, R.drawable.player))
                .setIntent(new Intent(Intent.ACTION_VIEW,
                        Uri.parse("https://www.baidu.com/")))
                .build();
        ShortcutInfo shortcut2 = new ShortcutInfo.Builder(this, "id2")
                .setShortLabel("Web site")
                .setLongLabel("我的Github")
                .setIcon(Icon.createWithResource(this, R.drawable.player))
                .setIntent(new Intent(Intent.ACTION_VIEW,
                        Uri.parse("https://github.com/growuptang")))
                .build();
        ShortcutInfo shortcut3 = new ShortcutInfo.Builder(this, "id3")
                .setShortLabel("Web site")
                .setLongLabel("我的CSDN")
                .setIcon(Icon.createWithResource(this, R.drawable.player))
                .setIntent(new Intent(Intent.ACTION_VIEW,
                        Uri.parse("https://blog.csdn.net/qq_33721320")))
                .build();
        mShortcutManager.setDynamicShortcuts(Arrays.asList(shortcut, shortcut2, shortcut3));
    }

    @RequiresApi(api = Build.VERSION_CODES.N_MR1)
    private void updItem(int index) {
        Intent intent = new Intent(this, MessageActivity.class);
        intent.setAction(Intent.ACTION_VIEW);
        intent.putExtra("msg", "我和" + mAdapter.getItem(index) + "的对话");

        ShortcutInfo info = new ShortcutInfo.Builder(this, "id" + index)
                .setShortLabel(mAdapter.getItem(index))
                .setLongLabel("联系人:" + mAdapter.getItem(index))
                .setIcon(Icon.createWithResource(this, R.drawable.got))
                .setIntent(intent)
                .build();

        mShortcutManager.updateShortcuts(Arrays.asList(info));
    }

    @RequiresApi(api = Build.VERSION_CODES.N_MR1)
    private void removeItem(int index) {
        List<ShortcutInfo> infos = mShortcutManager.getPinnedShortcuts();
        for (ShortcutInfo info : infos) {
            if (info.getId().equals("id" + index)) {
                mShortcutManager.disableShortcuts(Arrays.asList(info.getId()), "暂无该联系人");
            }
        }
        mShortcutManager.removeDynamicShortcuts(Arrays.asList("id" + index));
    }

    @RequiresApi(api = Build.VERSION_CODES.N_MR1)
    private void setupShortcuts() {
        mShortcutManager = getSystemService(ShortcutManager.class);

        List<ShortcutInfo> infos = new ArrayList<>();
        for (int i = 0; i < mShortcutManager.getMaxShortcutCountPerActivity(); i++) {
            Intent intent = new Intent(this, MessageActivity.class);
            intent.setAction(Intent.ACTION_VIEW);
            intent.putExtra("msg", "我和" + mAdapter.getItem(i) + "的对话");

            ShortcutInfo info = new ShortcutInfo.Builder(this, "id" + i)
                    .setShortLabel(mAdapter.getItem(i))
                    .setLongLabel("联系人:" + mAdapter.getItem(i))
                    .setIcon(Icon.createWithResource(this, R.drawable.got))
                    .setIntent(intent)
                    .build();
            infos.add(info);
//            manager.addDynamicShortcuts(Arrays.asList(info));
        }

        mShortcutManager.setDynamicShortcuts(infos);
    }

    private void setupAdapter() {
        List<String> list = new ArrayList<>(10);
        list.add("杨");
        list.add("老李");
        list.add("老刘");
        list.add("张三");
        list.add("李四");
        list.add("王五");
        list.add("二哈");
        list.add("毛驴");
        mAdapter = new Adapter();
        mAdapter.setDatas(list);
    }

}
