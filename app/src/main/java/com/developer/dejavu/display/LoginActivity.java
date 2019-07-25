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
import android.widget.TextView;
import android.widget.Toast;

import com.developer.dejavu.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

/**
 * Login Activity to manage user logging and authentication tasks
 */
public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    TextView tvRegister;
    Button btnLogin;
    EditText etEmail, etPassword;
    private FirebaseAuth mAuth;
    private static final String TAG = "LoginActivity";
    SignInButton signInButton;
    private final static int RC_SIGN_IN =9001;
    public static final String GTAG="GoogleActivity";
    private GoogleSignInClient mGoogleSignInClient;

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser!=null)
        {
            mAuth.signOut();
//            redirectToPlayerActivity(currentUser);
        }
    }

    /**
     * Method for signing out logged user.
     */
    private void signOut() {
        // Firebase sign out
        mAuth.signOut();

        // Google sign out
        mGoogleSignInClient.signOut().addOnCompleteListener(this,
                new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        updateUI(null);
                    }
                });
    }

    /**
     * Method to redirect user to Player Activity when the sign in is successful.
     * @param user
     */
    private void redirectToPlayerActivity(FirebaseUser user) {
        finish();
        startActivity(new Intent(LoginActivity.this, PlayerActivity.class));
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        etEmail= (EditText)findViewById(R.id.et_email);
        etPassword= (EditText)findViewById(R.id.et_password);
        btnLogin =(Button) findViewById(R.id.btn_login);
        tvRegister = (TextView) findViewById(R.id.tv_register);
        signInButton = findViewById(R.id.sign_in_button);
        mAuth = FirebaseAuth.getInstance();
        tvRegister.setOnClickListener(this);
        btnLogin.setOnClickListener(this);
        findViewById(R.id.sign_in_button).setOnClickListener(this);

        // Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.google_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
    }


    @Override
    public void onClick(View view)
    {
        switch (view.getId()){
            case R.id.btn_login:
                if(checkDetails())
                signIn(etEmail.getText().toString(), etPassword.getText().toString());
                break;
            case R.id.tv_register:
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
                break;
            case R.id.sign_in_button:
                googleSignIn();
                break;
        }
    }

    /**
     * Method for Google Sign in feature to login using Google Account.
     */
    private void googleSignIn()
    {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    /**
     * Method to handle to results of Google Sign in attempt by the user.
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    /**
     * Helper Method to Handle Google Sign in Result.
     * @param completedTask
     */
    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            firebaseAuthWithGoogle(account);

        } catch (ApiException e) {
            Log.w(GTAG, "signInResult:failed code=" + e.getStatusCode());
            Toast.makeText(LoginActivity.this,"Auth went wrong!",Toast.LENGTH_LONG).show();
            updateUI(null);
        }
    }

    /**
     * Method to authenticate user with Google
     * @param acct
     */
    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Log.d(GTAG, "firebaseAuthWithGoogle:" + acct.getId());
        //showProgressDialog();
        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d(GTAG, "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            Log.w(GTAG, "signInWithCredential:failure", task.getException());
                            Toast.makeText(LoginActivity.this, "Authentication Failed.", Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }

//                        hideProgressDialog();
                    }
                });
    }

    /**
     * UI method to be handle changes in UI after successful login.
     */
    private void updateUI(FirebaseUser user) {
        finish();
        if(user!=null)
        startActivity(new Intent(LoginActivity.this, PlayerActivity.class));
    }

    /**
     * Method to handle login validations.
     */
    private Boolean checkDetails()
    {
        String email, password;
        email=etEmail.getText().toString();
        password = etPassword.getText().toString();

        if(email.isEmpty() || password.isEmpty())
        {
            Toast.makeText(getApplicationContext(),"Please fill in all the details!", Toast.LENGTH_SHORT).show();
            return false;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches())
        {
            etEmail.setError("Please enter a valid email");
            etEmail.requestFocus();
            return false;
        }

        if(password.length()<6)
        {
            etPassword.setError("Passwords should be at least of six characters");
            return false;
        }
        return true;
    }

    /**
     * Method for Sign in using email and Password
     */
    private void signIn(String email, String password) {
        Log.d(TAG, "signIn:" + email);
        final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Signing In...");
        progressDialog.show();

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            progressDialog.dismiss();
                            redirectToPlayerActivity(user);
                        } else {
                            progressDialog.dismiss();
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(LoginActivity.this, "Try again with a valid email and password!",
                                    Toast.LENGTH_SHORT).show();
                            btnLogin.setEnabled(true);
                        }
                    }
                });
    }
}
