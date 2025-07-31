package com.example.contactnotes.fragments;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.contactnotes.DatabaseHelper;
import com.example.contactnotes.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

public class AddContactFragment extends Fragment {

    TextInputEditText nameInput, contactInput;
    MaterialButton saveBtn;
    DatabaseHelper db;

    public AddContactFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_contact, container, false);

        nameInput = view.findViewById(R.id.nameInput);
        contactInput = view.findViewById(R.id.contactInput);
        saveBtn = view.findViewById(R.id.saveBtn);
        db = new DatabaseHelper(getContext());

        saveBtn.setOnClickListener(v -> {
            String name = nameInput.getText().toString().trim();
            String contact = contactInput.getText().toString().trim();

            if (TextUtils.isEmpty(name) || TextUtils.isEmpty(contact)) {
                Toast.makeText(getContext(), "Please enter both name and contact", Toast.LENGTH_SHORT).show();
                return;
            }
            if (!contact.matches("\\d+")) {
                Toast.makeText(getContext(), "Contact must be numeric only", Toast.LENGTH_SHORT).show();
                return;
            }

            boolean inserted = db.insertContact(name, contact);
            if (inserted) {
                Toast.makeText(getContext(), "Contact saved", Toast.LENGTH_SHORT).show();
                nameInput.setText("");
                contactInput.setText("");
            } else {
                Toast.makeText(getContext(), "Failed to save contact", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }
}
