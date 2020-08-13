package com.example.test;

import android.Manifest;
import android.content.ClipData;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.test.Fragment.CommunityFragment;
import com.example.test.Fragment.GalleryFragment;
import com.example.test.Fragment.HomeFragment;
import com.example.test.Fragment.MenuFragment;
import com.example.test.Fragment.SettingFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    CommunityFragment communityFragment;
    GalleryFragment galleryFragment;
    HomeFragment homeFragment;
    MenuFragment menuFragment;
    SettingFragment settingFragment;
    ArrayList imageList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bottomNavigationView=findViewById(R.id.bottom_nav);
        setFragment();
        PermissionListener permissionlistener = new PermissionListener() {
            @Override
            public void onPermissionGranted() {

                // 허락을 한 경우 실행할 부분

            }

            @Override
            public void onPermissionDenied(ArrayList<String> deniedPermissions) {
                Toast.makeText(MainActivity.this, "권한이 없습니다.\n" + deniedPermissions.toString(), Toast.LENGTH_SHORT).show();


                // 거절을 한 경우 실행할 부분


            }
        };
        TedPermission.with(MainActivity.this)
                .setPermissionListener(permissionlistener)
                .setDeniedMessage("If you reject permission,you can not use this service\n\nPlease turn on permissions at [Setting] > [Permission]")
                .setPermissions(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .check();
        System.out.println("activity="+this.toString());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        imageList=new ArrayList();
        if(requestCode==1) {
            if (data == null) {

            } else {
                if (data.getClipData() == null)
                    Toast.makeText(this, "다중선택이 불가합니다.", Toast.LENGTH_SHORT).show();
                else {
                    ClipData clipData = data.getClipData();

                    Log.i("clipdata", String.valueOf(clipData.getItemCount()));
                    for(int i=0; i<clipData.getItemCount();i++)
                    {
                        imageList.add(String.valueOf(clipData.getItemAt(i).getUri()));
                    }

                }
            }
        }
        galleryFragment.setImages(imageList);
    }

    private void setFragment()
    {
        bottomNavigationView.setSelectedItemId(R.id.homebutton);//첫화면은 홈버튼이 클릭되어있음.
        communityFragment=new CommunityFragment();
        galleryFragment=new GalleryFragment();
        homeFragment=new HomeFragment();
        menuFragment=new MenuFragment();
        settingFragment=new SettingFragment();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main_layout,homeFragment)
                .commitAllowingStateLoss();
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch(menuItem.getItemId())
                {
                    case R.id.communitybutton:
                    {
                        replaceFragment(communityFragment);
                        return true;
                    }

                    case R.id.gallerybutton:
                    {
                        replaceFragment(galleryFragment);
                        return true;
                    }
                    case R.id.homebutton:
                    {
                        replaceFragment(homeFragment);
                        return true;
                    }
                    case R.id.menubutton:
                    {
                        replaceFragment(menuFragment);
                        return true;
                    }
                    case R.id.settingbutton:
                    {
                        replaceFragment(settingFragment);
                        return true;
                    }
                    default: return false;
                }
            }
        });
    }
    public void replaceFragment(Fragment fragment)
    {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main_layout,fragment)
                .commitAllowingStateLoss();
    }
    public void replaceFragmentStack(Fragment fragment)
    {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main_layout,fragment)
                .addToBackStack(null)
                .commitAllowingStateLoss();
    }
}