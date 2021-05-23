package com.igm.garbage;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.google.firebase.auth.FirebaseAuth;
import com.igm.garbage.models.User;

public class ProfileDialogFragment extends DialogFragment {

    private FirebaseAuth firebaseAuth;
    private TextView profileName, profileEmail, profileEdit, profileContacts, profileAbout;
    private LinearLayout linearLayout7;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        firebaseAuth = FirebaseAuth.getInstance();
        View view = inflater.inflate(R.layout.profile_dialog_fragment, container, false);

        profileName = view.findViewById(R.id.profileName);
        profileEmail = view.findViewById(R.id.profileEmail);
        profileEdit = view.findViewById(R.id.profileEdit);
        profileContacts = view.findViewById(R.id.profileContacts);
        profileAbout = view.findViewById(R.id.profileAbout);
        linearLayout7 = view.findViewById(R.id.linear7);


        String name = User.getInstance().getFullname();
        String email = User.getInstance().getEmail();

        profileName.setText(name);
        profileEmail.setText(email);

        //remove feedback
        if(User.getInstance().getRole().equals("admin")){
            linearLayout7.setVisibility(View.GONE);
        }

        profileEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), Profile.class));
            }
        });

        profileContacts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), About.class));
            }
        });

        profileAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), Settings.class));
            }
        });


        view.findViewById(R.id.txtLogout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firebaseAuth.signOut();
                Intent intent = new Intent(getActivity(), Login.class);
                getActivity().deleteDatabase("GreenDb");
                User.getInstance().resetUser();
                startActivity(intent);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getActivity().finish();

            }
        });


        return view;

    }
}
