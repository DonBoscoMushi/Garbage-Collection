package com.igm.garbage.admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.igm.garbage.R;
import com.igm.garbage.models.User;
import com.igm.garbage.utils.BasicJobs;

public class RegisterAdmin extends AppCompatActivity {

    Button regTologin,register;
    EditText emailTxt, fullnameTxt, passwordTxt1, passwordTxt2, phoneTxt;
    private String fullname, password_1, password_2, email, phone;
    private LottieAnimationView animationView;
    private LinearLayout greyedLinearLayout;

    //Firebase
    private FirebaseAuth mAuth;
    private FirebaseFirestore mFirestore;
    private String TAG = "SignUp Admin";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_admin);


        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        mAuth = FirebaseAuth.getInstance();
        mFirestore = FirebaseFirestore.getInstance();

        greyedLinearLayout = findViewById(R.id.greyedBg);
        animationView = findViewById(R.id.animatedDialog);
        animationView.setVisibility(View.GONE);

        regTologin = findViewById(R.id.txtRegToLogin);
        register = findViewById(R.id.btnRegister);
        fullnameTxt = findViewById(R.id.txtFullName);
        emailTxt = findViewById(R.id.input_email);
        passwordTxt1 = findViewById(R.id.input_password_1);
        passwordTxt2 = findViewById(R.id.input_password_2);
        phoneTxt = findViewById(R.id.inputPhone);

//        findViewById(R.id.regAdmin).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivity(new Intent(getApplicationContext(), Login.class));
//                finish();
//            }
//        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {

                email = emailTxt.getText().toString();
                fullname = fullnameTxt.getText().toString();
                password_1 = passwordTxt1.getText().toString();
                password_2 = passwordTxt2.getText().toString();
                phone = phoneTxt.getText().toString();

                if (email.isEmpty()){
                    emailTxt.setError("Email Required");
                    emailTxt.requestFocus();
                    return;
                }

                if (fullname.isEmpty()){
                    fullnameTxt.setError("Full Required");
                    fullnameTxt.requestFocus();
                    return;
                }

                if (password_1.isEmpty()){
                    passwordTxt1.setError("Password Required");
                    passwordTxt1.requestFocus();
                    return;
                }

                if (password_1.length() < 6){
                    passwordTxt1.setError("6 or more characters are required");
                    passwordTxt1.requestFocus();
                    return;
                }

                if (!password_2.equals(password_1)){
                    passwordTxt2.setError("Passwords don't match");
                    passwordTxt2.requestFocus();
                    return;
                }

                if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    emailTxt.setError("Enter a valid email");
                    emailTxt.requestFocus();
                    return;
                }

                if(phone.isEmpty()){
                    phoneTxt.setError("Phone number required");
                    phoneTxt.requestFocus();
                    return;

                }

                if(phone.length() < 10){
                    phoneTxt.setError("Enter a valid phone number");
                    phoneTxt.requestFocus();
                    return;

                }

                BasicJobs.hideKeyboard(RegisterAdmin.this);

                greyedLinearLayout.setVisibility(View.VISIBLE);
                animationView.setVisibility(View.VISIBLE);
                getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                        WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);



                mAuth.createUserWithEmailAndPassword(email, password_1)
                        .addOnCompleteListener(RegisterAdmin.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    assert user != null;
                                    updateUI(user, email, fullname, password_1, phone, "admin");
                                    Snackbar.make(view, "You have added an Admin", Snackbar.LENGTH_LONG)
                                            .setAction("Action", null).show();

                                } else {
                                    // If sign in fails, display a message to the user.
                                    animationView.setVisibility(View.GONE);
                                    greyedLinearLayout.setVisibility(View.GONE);
                                    getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                                    Log.w("TAG", "createUserWithEmail:failure", task.getException());

                                    Snackbar.make(view, task.getException().getLocalizedMessage(), Snackbar.LENGTH_LONG)
                                            .setAction("Action", null).show();
//                                    Toast.makeText(SignUp.this, "Registration failed.",
//                                            Toast.LENGTH_SHORT).show();

                                }

                                // ...
                            }
                        });


            }
        });
    }

    private void updateUI(FirebaseUser user, String email, String fullname, String password,
                          String phone, String role) {

        User regUser = new User(
                fullname,
                phone,
                email,
                role
        );

        DocumentReference mDocumentReference = mFirestore.collection("users").document(user.getUid());

        mDocumentReference.set(regUser)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(RegisterAdmin.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                        animationView.setVisibility(View.GONE);
                        greyedLinearLayout.setVisibility(View.GONE);
                        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        Toast.makeText(getApplicationContext(), "Registration failed.",
                                Toast.LENGTH_SHORT).show();
                        Log.d(TAG, "onFailure: " + e.getMessage());
                    }
                });
    }
}