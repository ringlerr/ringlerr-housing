package com.luncher.santanu.ais;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.provider.Settings;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.ringlerr.callplus.CallPlus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private ViewPager view_pager;
    private SectionsPagerAdapter viewPagerAdapter;
    private TabLayout tab_layout;
    String ringlerr_key = "54fdf887b7418a41fa99db39251c1726ff13fda38b2589509e1c6d4f3533emt";
    String phone_from;

    public static final int CODE_DRAW_OVER_OTHER_APP_PERMISSION = 7;
    public static final int MY_PERMISSIONS_REQUEST_READ_PHONE_STATE = 8;
    public static final int MY_PERMISSIONS_REQUEST_READ_CALL_LOG = 98;
    final String[] NECESSARY_PERMISSIONS = new String[] { Manifest.permission.CALL_PHONE, Manifest.permission.READ_CALL_LOG };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SessionManager session = new SessionManager(getApplicationContext());
        // get user data from session
        HashMap<String, String> user = session.getUserDetails();
        final String mPhone = user.get(SessionManager.KEY_PHONE);
        phone_from = mPhone;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !Settings.canDrawOverlays(MainActivity.this)) {
            //If the draw over permission is not available open the settings screen
            //to grant the permission.
            Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                    Uri.parse("package:" + MainActivity.this.getPackageName()));
            startActivityForResult(intent, CODE_DRAW_OVER_OTHER_APP_PERMISSION);
        }

        if(null == mPhone){
            Intent loginActivity = new Intent(MainActivity.this, LoginActivity.class);
            loginActivity.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(loginActivity);
            finish();
        }else {

            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    //Do something after 100ms
                    updateToken();
                }
            }, 1000);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                if (ContextCompat.checkSelfPermission(MainActivity.this,
                        Manifest.permission.READ_CALL_LOG) != PackageManager.PERMISSION_GRANTED) {
                    // Permission has not been granted, therefore prompt the user to grant permission'
                    ActivityCompat.requestPermissions(
                            MainActivity.this,
                            NECESSARY_PERMISSIONS, MY_PERMISSIONS_REQUEST_READ_CALL_LOG);
                }
            }

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (getApplicationContext().checkSelfPermission(Manifest.permission.READ_PHONE_STATE)
                        != PackageManager.PERMISSION_GRANTED) {
                    // Permission has not been granted, therefore prompt the user to grant permission
                    ActivityCompat.requestPermissions(this,
                            new String[]{Manifest.permission.READ_PHONE_STATE},
                            MY_PERMISSIONS_REQUEST_READ_PHONE_STATE);
                }
            }
        }

        initComponent();
        initToolbar();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
//            if (ContextCompat.checkSelfPermission(MainActivity.this,
//                    Manifest.permission.READ_CALL_LOG) != PackageManager.PERMISSION_GRANTED) {
//                // Permission has not been granted, therefore prompt the user to grant permission'
//                ActivityCompat.requestPermissions(
//                        MainActivity.this,
//                        NECESSARY_PERMISSIONS, MY_PERMISSIONS_REQUEST_READ_CALL_LOG);
//            }
        }

    }

    private void updateToken() {

        FirebaseInstanceId.getInstance().getInstanceId().addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
            @Override
            public void onComplete(@NonNull Task<InstanceIdResult> task) {
                if (!task.isSuccessful()) {
                    Log.w("FIREBASE", "getInstanceId failed", task.getException());
                    return;
                }

                // Get new Instance ID token
                String token = Objects.requireNonNull(task.getResult()).getToken();
                CallPlus.updateToken(ringlerr_key, phone_from, token);
            }
        });
    }

    private void initToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_menu);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("AIS");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setSystemBarColor(this);
    }

    public static void setSystemBarColor(Activity act) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = act.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(act.getResources().getColor(R.color.colorPrimaryDark));
        }
    }

    private void initComponent() {
        view_pager = findViewById(R.id.view_pager);
        tab_layout = findViewById(R.id.tab_layout);
        setupViewPager(view_pager);

        tab_layout.setupWithViewPager(view_pager);

        tab_layout.getTabAt(0).setIcon(R.drawable.ic_add_a_photo);
        tab_layout.getTabAt(1).setIcon(R.drawable.ic_group_black_24dp);

        // set icon color pre-selected
        tab_layout.getTabAt(0).getIcon().setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_IN);
        tab_layout.getTabAt(1).getIcon().setColorFilter(getResources().getColor(R.color.grey_20), PorterDuff.Mode.SRC_IN);

        tab_layout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                tab.getIcon().setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_IN);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                tab.getIcon().setColorFilter(getResources().getColor(R.color.grey_20), PorterDuff.Mode.SRC_IN);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }

    private void setupViewPager(ViewPager viewPager) {
        viewPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.addFragment(new OneFragment(), "2 way call");    // index 0
        viewPagerAdapter.addFragment(new TwoFragment(), "Robo Call");   // index 1
        viewPager.setAdapter(viewPagerAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);
        return true;
    }

    private class SectionsPagerAdapter extends FragmentPagerAdapter {

        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public SectionsPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    public static class FragmentTabsStore extends Fragment {

        public FragmentTabsStore() {
        }

        public static FragmentTabsStore newInstance() {
            FragmentTabsStore fragment = new FragmentTabsStore();
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View root = inflater.inflate(R.layout.fragment_tabs_store, container, false);

//        Tools.displayImageOriginal(getActivity(), (ImageView) root.findViewById(R.id.image_1), R.drawable.image_8);
//        Tools.displayImageOriginal(getActivity(), (ImageView) root.findViewById(R.id.image_2), R.drawable.image_9);
//        Tools.displayImageOriginal(getActivity(), (ImageView) root.findViewById(R.id.image_3), R.drawable.image_15);
//        Tools.displayImageOriginal(getActivity(), (ImageView) root.findViewById(R.id.image_4), R.drawable.image_14);
//        Tools.displayImageOriginal(getActivity(), (ImageView) root.findViewById(R.id.image_5), R.drawable.image_12);
//        Tools.displayImageOriginal(getActivity(), (ImageView) root.findViewById(R.id.image_6), R.drawable.image_2);
//        Tools.displayImageOriginal(getActivity(), (ImageView) root.findViewById(R.id.image_7), R.drawable.image_5);

            return root;
        }
    }

}
