package com.developer.dejavu.display;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.developer.dejavu.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener{

    private static final String TAG ="RegisterActivity";
    EditText etName, etEmail, etPassword, etConfirmPassword;
    Button btnRegister;
    private FirebaseAuth mAuth;

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        //redirectToPlayerActivity(currentUser);
    }

    private void redirectToPlayerActivity(FirebaseUser user) {
        finish();
        startActivity(new Intent(RegisterActivity.this, PlayerActivity.class));
    }

    @Override
    public void onClick(View view)
    {
        switch (view.getId()){
            case R.id.btn_register:
                registerUser();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        etName = findViewById(R.id.et_name);
        etEmail = findViewById(R.id.et_email);
        etPassword = findViewById(R.id.et_password);
        etConfirmPassword = findViewById(R.id.et_confirm);
        btnRegister = findViewById(R.id.btn_register);
        mAuth = FirebaseAuth.getInstance();
        btnRegister.setOnClickListener(this);
    }

    private void registerUser() {
        final String email =etEmail.getText().toString();
        final String name= etName.getText().toString();
        final String password = etPassword.getText().toString();
        String confirm = etConfirmPassword.getText().toString();

        if(email.isEmpty() || name.isEmpty() || password.isEmpty() || confirm.isEmpty())
        {
            Toast.makeText(getApplicationContext(),"Please fill in all the details!", Toast.LENGTH_LONG).show();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches())
        {
            etEmail.setError("Please enter a valid email");
            etEmail.requestFocus();
            return;
        }

        if(password.length()<6 || confirm.length()<6)
        {
            etPassword.setError("Passwords should be at least of six characters");
            return;
        }
        if(!password.equals(confirm))
        {
            etPassword.setError("Passwords should match");
            etConfirmPassword.setError("Passwords should match");
        }

        btnRegister.setEnabled(false);
        final ProgressDialog progressDialog = new ProgressDialog(RegisterActivity.this, R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Creating Account...");
        progressDialog.show();

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser currentUser = mAuth.getCurrentUser();

                            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                    .setDisplayName(name).build();

                            currentUser.updateProfile(profileUpdates)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                Log.d(TAG, "User profile updated.");
                                            }
                                        }
                                    });
                            progressDialog.dismiss();
                            signIn(email,password);

                        } else {
                            // If sign in fails, display a message to the user.
                            progressDialog.dismiss();
                            if(task.getException() instanceof FirebaseAuthUserCollisionException)
                            {
                                Toast.makeText(getApplicationContext(),"You are already registered!",Toast.LENGTH_SHORT).show();
                                Log.w(TAG, "signInWithEmail:AlreadyRegisteredFailure", task.getException());
                                btnRegister.setEnabled(true);
                                return;
                            }
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(RegisterActivity.this, "Authentication failed. Please try again!",
                                    Toast.LENGTH_SHORT).show();
                            btnRegister.setEnabled(true);
                        }

                    }
                });

    }

    private void signIn(String email, String password) {
        Log.d(TAG, "signIn:" + email);
        final ProgressDialog progressDialog = new ProgressDialog(RegisterActivity.this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Signing In...");
        progressDialog.show();

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            progressDialog.dismiss();
                            redirectToPlayerActivity(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            progressDialog.dismiss();
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(RegisterActivity.this, "Signing process failed. Try again!",
                                    Toast.LENGTH_SHORT).show();
                            btnRegister.setEnabled(true);
                        }
                    }
                });
    }

    private void signOut() {
        mAuth.signOut();
        redirectToLoginActivity();
    }

    private void redirectToLoginActivity() {
        startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
    }

}
