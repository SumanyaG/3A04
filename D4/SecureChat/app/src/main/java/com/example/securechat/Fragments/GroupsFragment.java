package com.example.securechat.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.securechat.Controllers.ChatController;
import com.example.securechat.R;

public class GroupsFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_groups, container, false);

        Button createGroupButton = view.findViewById(R.id.createGroupButton);
        createGroupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((ChatController) requireActivity()).RequestNewGroup();
            }
        });
        return view;
    }
}