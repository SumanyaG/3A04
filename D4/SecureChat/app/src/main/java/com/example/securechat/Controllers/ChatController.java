package com.example.securechat.Controllers;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.example.securechat.R;
import com.example.securechat.Adapters.ViewPagerAdapter;
import com.google.android.material.tabs.TabLayout;

public class ChatController extends AppCompatActivity {

    TabLayout tabLayout;
    ViewPager2 viewPager2;
    ViewPagerAdapter viewPagerAdapter;
    FrameLayout frameLayout;

    private Button btnCreateGroup;

    Dialog dialog;
    Button btnDialogCreate, btnDialogCancel;

    @SuppressLint({"MissingInflatedId", "UseCompatLoadingForDrawables"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tabLayout = findViewById(R.id.tabLayout);
        viewPager2 = findViewById(R.id.viewPager);
        viewPagerAdapter = new ViewPagerAdapter(this);
        viewPager2.setAdapter(viewPagerAdapter);
        frameLayout = findViewById(R.id.frameLayout);


        btnCreateGroup = findViewById(R.id.createGroupButton);
        dialog = new Dialog(ChatController.this);
        dialog.setContentView(R.layout.dialog_create_group);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.create_group_dialog_bg));
        dialog.setCancelable(false);

        btnDialogCreate = dialog.findViewById(R.id.btnDiaglogCreate);
        btnDialogCancel = dialog.findViewById(R.id.btnDiaglogCancel);

        btnDialogCancel.setOnClickListener(new View.OnClickListener(){
           @Override
            public void onClick(View v){
               dialog.dismiss();
           }
        });

        btnDialogCreate.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                dialog.dismiss();
            }
        });



        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager2.setVisibility(View.VISIBLE);
                frameLayout.setVisibility(View.GONE);
                viewPager2.setCurrentItem(tab.getPosition());
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }
            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                viewPager2.setVisibility(View.VISIBLE);
                frameLayout.setVisibility(View.GONE);
            }
        });

        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                switch (position){
                    case 0:
                    case 1:
                    case 2:
                        tabLayout.getTabAt(position).select();
                }
                super.onPageSelected(position);
            }
        });

    }

}