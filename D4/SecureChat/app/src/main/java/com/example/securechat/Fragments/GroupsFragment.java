package com.example.securechat.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.securechat.ChatListManager;
import com.example.securechat.R;

public class GroupsFragment extends Fragment {

    private View groupFragmentView;
    private ListView list_view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View groupFragmentView = inflater.inflate(R.layout.fragment_groups, container, false);

        ChatListManager chatListManager = new ChatListManager();
        chatListManager.displayPage(groupFragmentView);

        return groupFragmentView;
    }
}

