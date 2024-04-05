package com.example.securechat;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.securechat.Fragments.ChatsFragment;
import com.example.securechat.Fragments.ContactsFragment;
import com.example.securechat.Fragments.GroupsFragment;

public class ViewPagerAdapter extends FragmentStateAdapter {
    public ViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0: return new ChatsFragment();
            case 1: return new GroupsFragment();
            case 2: return new ContactsFragment();
            default: return new ChatsFragment();
        }
    }


    @Override
    public int getItemCount() {
        return 3;
    }
}
