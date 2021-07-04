package com.igm.garbage;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.igm.garbage.utils.BasicJobs;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Feedback extends AppCompatActivity {

    Button sendBtn;
    EditText feedbackText;

    FirebaseFirestore mFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        feedbackText = findViewById(R.id.edtFeedback);
        sendBtn = findViewById(R.id.btnSend);



        String Uid = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();

        mFirestore = FirebaseFirestore.getInstance();

        sendBtn.setOnClickListener(view -> {

            BasicJobs.hideKeyboard(Feedback.this);

            if(feedbackText.getText().toString().isEmpty()){
                feedbackText.setError("Required");
                feedbackText.requestFocus();

                return;
            }

            Map<String, Object> feedback = new HashMap<>();
            feedback.put("message", feedbackText.getText().toString());

            mFirestore.collection("feedback")
                    .document(Uid)
                    .set(feedback)
                    .addOnSuccessListener(unused -> {
                        feedbackText.setText("");
                        Toast.makeText(this, "Feedback Sent", Toast.LENGTH_SHORT).show();
                    })
                    .addOnFailureListener(e -> {
                        Toast.makeText(this, "Failed. Try Again", Toast.LENGTH_SHORT).show();
                    });

        });
    }
}