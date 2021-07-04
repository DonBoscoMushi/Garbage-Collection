package com.igm.garbage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.annotations.Nullable;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.igm.garbage.admin.Admin;
import com.igm.garbage.models.User;
import com.igm.garbage.utils.BasicJobs;
import com.igm.garbage.utils.DatabaseHelper;
import com.igm.garbage.utils.FirebaseMethods;
import com.igm.garbage.utils.InternetCheck;


public class Login extends AppCompatActivity {

    //Firebase instances
    private FirebaseAuth mAuth;
    private FirebaseFirestore mFirebaseFirestore;

    FirebaseMethods firebaseMethods;
    private LottieAnimationView animationView;
    private LinearLayout greyedLinearLayout;

    DatabaseHelper db;
    InternetCheck internetCheck;

    Button registerBtn, loginBtn, forgotPassword;
    private String TAG = "Login";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //remove focus/hide keyboard as activity starts
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        // Initialize Firebase
        mAuth = FirebaseAuth.getInstance();
        firebaseMethods = new FirebaseMethods();
        mFirebaseFirestore = FirebaseFirestore.getInstance();

        db = new DatabaseHelper(Login.this);

        animationView = findViewById(R.id.animatedDialog);
        greyedLinearLayout = findViewById(R.id.greyedBg);
        forgotPassword = findViewById(R.id.forgot_password);
        animationView.setVisibility(View.GONE);

        registerBtn = findViewById(R.id.txtRegister);
        loginBtn = findViewById(R.id.btnLogin);

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login.this, SignUp.class);
                startActivity(intent);
            }
        });

        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Login.this, ForgotPassword.class);
                startActivity(i);
            }
        });

        internetCheck = new InternetCheck();

        if(!internetCheck.isConnected(this)){
            Toast.makeText(this, "Data disconnected", Toast.LENGTH_SHORT).show();
        }

//        findViewById(R.id.admin).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivity(new Intent(getApplicationContext(), Admin.class
//                ));
//            }
//        });
        initialise();

     }

    private void initialise() {

        final EditText usernameTxt = findViewById(R.id.edtEmail);
        final EditText passwordTxt = findViewById(R.id.edtPassword);

        //when the login button is clicked
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {

                //Initialize the login button, And all Other fields
                final String username = usernameTxt.getText().toString();
                final String password = passwordTxt.getText().toString();

                if (username.isEmpty()){
                    usernameTxt.setError("Email Required");
                    usernameTxt.requestFocus();
                    return;
                }

                if (password.isEmpty()){
                    passwordTxt.setError("Password Required");
                    passwordTxt.requestFocus();
                    return;
                }

                //hide keybord and load animation
                BasicJobs.hideKeyboard(Login.this);
                greyedLinearLayout.setVisibility(View.VISIBLE);
                animationView.setVisibility(View.VISIBLE);
                getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                        WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

                mAuth.signInWithEmailAndPassword(username, password)
                        .addOnCompleteListener(Login.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    Log.d("TAG", "signInWithEmail:success");
                                    animationView.setVisibility(View.GONE);
                                    greyedLinearLayout.setVisibility(View.GONE);
                                    getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    updateUI(user);
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Log.w("TAG", "signInWithEmail:failure", task.getException());
                                    passwordTxt.setText("");
                                    Snackbar.make(view, "No user found", Snackbar.LENGTH_LONG)
                                            .setAction("Action", null).show();
//                                    Toast.makeText(Login.this, "Email/Password incorrect.",
//                                            Toast.LENGTH_SHORT).show();
                                    animationView.setVisibility(View.GONE);
                                    getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                                    greyedLinearLayout.setVisibility(View.GONE);
                                    updateUI(null);
                                }

                                // ...
                            }
                        });

            }
        });
    }

    //if the user is successfully  logged in, then this function is called..
    private void updateUI(FirebaseUser user) {
//        if(user != null){
//            firebaseMethods.checkRole(Login.this, user.getUid());
//        }
        if(user != null){

            mFirebaseFirestore = FirebaseFirestore.getInstance();

            final DocumentReference docRef = mFirebaseFirestore.collection("users").document(user.getUid());
            docRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {
                @Override
                public void onEvent(@Nullable DocumentSnapshot snapshot,
                                    @Nullable FirebaseFirestoreException e) {
                    if (e != null) {
                        Log.w(TAG, "Listen failed.", e);
                        return;
                    }

                    if (snapshot != null && snapshot.exists()) {

                        User user = snapshot.toObject(User.class);
                        Log.d(TAG, "greeen: " + user.getFullname());

                        //send the cloud data to local database
                        String Uid = FirebaseAuth.getInstance().getUid();
                        Log.d(TAG, "onEvent: database user data" + user.getRole());
                        db.insert(Uid, user.getFullname(), user.getEmail(), user.getPhone_no(), user.getRole());

                        Log.d(TAG, "btnLogin: " + user.getEmail());

                        String role = user.getRole();
                        if (role.equals("customer")) {
                            Log.d(TAG, "onEvent: " + user.getRole());
                            startActivity(new Intent(getApplicationContext(), Home.class));
                            Toast.makeText(Login.this, "Login Successful", Toast.LENGTH_SHORT).show();
                            finish();
                        } else if(role.equals("admin")){
                            Log.d(TAG, "onEvent: " + user.getRole());
                            startActivity(new Intent(getApplicationContext(), Admin.class));
                            Toast.makeText(Login.this, "Login Successful", Toast.LENGTH_SHORT).show();
                            finish();
                        }

                        animationView.setVisibility(View.GONE);
                        greyedLinearLayout.setVisibility(View.GONE);
                        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

                    } else {
                        Log.d(TAG, "Current data: null");
                    }
                }
            });

        }

    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();

        String role = db.checkRole();
        Log.d(TAG, "call checkRole: " + role );

//        User userModel = new User();
//        Toast.makeText(this, userModel.getFullname(), Toast.LENGTH_SHORT).show();

        if(currentUser != null){
            if(role != null){
//                String role = user.getRole();
                Log.d(TAG, "onStart angalia role: " + role);

                if (role.equals("customer")) {
                    startActivity(new Intent(getApplicationContext(), Home.class));
                    //Toast.makeText(Login.this, "Login Successful", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    startActivity(new Intent(getApplicationContext(), Admin.class));
                    //Toast.makeText(Login.this, "Login Successful", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }

        }
    }

}