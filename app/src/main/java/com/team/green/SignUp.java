package com.team.green;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.team.green.models.User;

public class SignUp extends AppCompatActivity {

    Button regTologin,register;
    EditText emailTxt, fullnameTxt, passwordTxt, phoneTxt;
    private String fullname, password, email, phone;

    //Firebase
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        mAuth = FirebaseAuth.getInstance();

        regTologin = findViewById(R.id.txtRegToLogin);
        register = findViewById(R.id.btnRegister);
        fullnameTxt = findViewById(R.id.txtFullName);
        emailTxt = findViewById(R.id.input_email);
        passwordTxt = findViewById(R.id.input_password);
        phoneTxt = findViewById(R.id.inputPhone);

        regTologin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), Login.class));
                finish();
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                email = emailTxt.getText().toString();
                fullname = fullnameTxt.getText().toString();
                password = passwordTxt.getText().toString();
                phone = phoneTxt.getText().toString();

                if(isNull(email) || isNull(fullname) || isNull(password) || isNull(phone)){
                    Toast.makeText(SignUp.this, "All Fields are Required", Toast.LENGTH_SHORT).show();
                }else {

                    mAuth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener(SignUp.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        // Sign in success, update UI with the signed-in user's information
                                        FirebaseUser user = mAuth.getCurrentUser();
                                        assert user != null;
                                        updateUI(user, email, fullname, password, phone);

                                    } else {
                                        // If sign in fails, display a message to the user.
                                        Log.w("TAG", "createUserWithEmail:failure", task.getException());
                                        Toast.makeText(SignUp.this, "Registration failed.",
                                                Toast.LENGTH_SHORT).show();

                                    }

                                    // ...
                                }
                            });
                }


            }
        });
    }

    private void updateUI(FirebaseUser user, String email, String fullname, String password,
                          String phone) {

        User user1 = new User(
                user.getUid(),
                fullname,
                phone,
                email
        );

        FirebaseDatabase.getInstance().getReference("Users")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .setValue(user1).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(SignUp.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                    Toast.makeText(SignUp.this, "Login to Continue", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), Login.class));
                    finish();
                }else{
                    Toast.makeText(getApplicationContext(), "Registration failed.",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

    }


    @Override
    protected void onStart() {
        super.onStart();
        if(mAuth.getCurrentUser() != null){
            startActivity(new Intent(getApplicationContext(), Home.class));
            finish();
        }

    }

    private boolean isNull(String string) { return string.equals("");}
}