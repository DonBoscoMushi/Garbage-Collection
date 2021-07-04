package com.igm.garbage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.igm.garbage.utils.BasicJobs;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class ForgotPassword extends AppCompatActivity {

    EditText emailEd;
    Button sendBtn;
    private LottieAnimationView animationView;
    private LinearLayout greyedLinearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        emailEd = findViewById(R.id.forgot_password_email);
        sendBtn = findViewById(R.id.forgot_password_btn);
        animationView = findViewById(R.id.animatedDialog);
        greyedLinearLayout = findViewById(R.id.greyedBg);
        animationView.setVisibility(View.GONE);


        FirebaseAuth auth = FirebaseAuth.getInstance();

        sendBtn.setOnClickListener(view ->  {

            String email = emailEd.getText().toString();

            //hide keybord and load animation
            BasicJobs.hideKeyboard(ForgotPassword.this);
            greyedLinearLayout.setVisibility(View.VISIBLE);
            animationView.setVisibility(View.VISIBLE);
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                    WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

            auth.sendPasswordResetEmail(email)
                    .addOnCompleteListener(task -> {

                        if (task.isSuccessful()) {

                            animationView.setVisibility(View.GONE);
                            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                            greyedLinearLayout.setVisibility(View.GONE);
                            Snackbar.make(view, "Email Sent. Check your Inbox", Snackbar.LENGTH_LONG).show();
                            emailEd.setText("");
                        }else {
                            animationView.setVisibility(View.GONE);
                            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                            greyedLinearLayout.setVisibility(View.GONE);
                            Snackbar.make(view, "Failed to send email", Snackbar.LENGTH_LONG).show();
                        }
                    })
                    .addOnFailureListener(e -> {

                        animationView.setVisibility(View.GONE);
                        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                        greyedLinearLayout.setVisibility(View.GONE);
                        Snackbar.make(view, Objects.requireNonNull(e.getLocalizedMessage()), Snackbar.LENGTH_LONG).show();

                    });

        });



    }
}