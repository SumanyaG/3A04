package com.example.securechat.Fragments;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ToggleButton;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.widget.Toast;

import com.example.securechat.R;


public class ChatsFragment extends Fragment {

    ToggleButton toggleDisappearingMessages;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_chats, container, false);

        // Initialize the toggle button
        toggleDisappearingMessages = view.findViewById(R.id.toggle_disappearing_messages);
        toggleDisappearingMessages.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                // Show duration selection dialog
                showDurationSelectionDialog();
            }
        });

        return view;
    }

    private void showDurationSelectionDialog() {
        final CharSequence[] items = {"5 seconds", "30 seconds", "1 minute", "5 minutes"};
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Select Duration for Disappearing Messages");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
                // Placeholder for actual functionality. pop up.
                Toast.makeText(getContext(), items[item] + " selected", Toast.LENGTH_SHORT).show();
                // set duration for the disappearing message would go here
                // Since we're focusing on frontend, i didn't implement the actual logic for messages disappearing
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }
}