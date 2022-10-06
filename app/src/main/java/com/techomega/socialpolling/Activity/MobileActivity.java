package com.techomega.socialpolling.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.techomega.socialpolling.BuildConfig;
import com.techomega.socialpolling.Helper.IntentUtils;
import com.techomega.socialpolling.Helper.Prefs;
import com.techomega.socialpolling.Helper.SharedPrefManager;
import com.techomega.socialpolling.R;

import java.util.Arrays;

public class MobileActivity extends AppCompatActivity {

    EditText editText;
    CardView cardView1,cardView2;
    MaterialButton materialButton;
    SharedPreferences mPref;
    String strmobile,newToken;
    Toolbar toolbar;
    TextView txtfrgt,txtversion;
    GoogleSignInClient mGoogleSignInClient;
    GoogleSignInOptions gso;
    private int RC_SIGN_IN = 0;
    DatabaseReference ref;
    private FirebaseAuth mAuth;
    RelativeLayout progressing;
    CallbackManager callbackManager;
    LoginButton loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mobile);

        mAuth = FirebaseAuth.getInstance();
        ref = FirebaseDatabase.getInstance("https://surveymonk-c8bd1-default-rtdb.asia-southeast1.firebasedatabase.app").getReference().child("Users");

        FacebookSdk.sdkInitialize(MobileActivity.this);

        mPref = PreferenceManager.getDefaultSharedPreferences(MobileActivity.this);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.black), PorterDuff.Mode.SRC_ATOP);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        progressing = (RelativeLayout) findViewById(R.id.progress);

        editText = (EditText) findViewById(R.id.mobile);

        cardView1 = (CardView) findViewById(R.id.google);
        cardView2 = (CardView) findViewById(R.id.fb);
        loginButton = (LoginButton) findViewById(R.id.login_button);

        materialButton = (MaterialButton) findViewById(R.id.otp);

        txtfrgt = (TextView) findViewById(R.id.frgt);
        txtversion = (TextView) findViewById(R.id.version);

        txtversion.setText("Version "+ BuildConfig.VERSION_NAME);

        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(MobileActivity.this,  new OnSuccessListener<InstanceIdResult>() {
            @Override
            public void onSuccess(InstanceIdResult instanceIdResult) {
                newToken = instanceIdResult.getToken();
                SharedPrefManager.getInstance(MobileActivity.this).saveDeviceToken(newToken);
            }
        });

        materialButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Verify();
            }
        });

        cardView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GoogleSignIn();
            }
        });

        callbackManager = CallbackManager.Factory.create();
        loginButton.setPermissions(Arrays.asList("email"));

        cardView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginButton.performClick();
            }
        });

        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                ProfileTracker profileTracker = new ProfileTracker() {
                    @Override
                    protected void onCurrentProfileChanged(Profile oldProfile, Profile currentProfile) {
                        if(currentProfile!=null){
                            regUserbyFacebook(currentProfile);
                        }
                    }
                };
                profileTracker.startTracking();
            }

            @Override
            public void onCancel() {
                Toast.makeText(MobileActivity.this, "Cancelled by User", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(FacebookException error) {
                Toast.makeText(MobileActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void Verify(){
        strmobile = editText.getText().toString().trim();

        if(TextUtils.isEmpty(strmobile)){
            editText.requestFocus();
            editText.setError("Enter your 10 digit valid Mobile Number");
            editText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    editText.setError(null);
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });

        } else if(!TextUtils.isDigitsOnly(strmobile)){
            editText.setError("Enter your 10 digit valid Mobile Number");
            editText.requestFocus();
            editText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    editText.setError(null);
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });
        } else {
            mPref.edit().putString(Prefs.TMOBILE,strmobile).apply();
            IntentUtils.fireIntent(MobileActivity.this,OtpActivity.class,true);
            finish();
        }
    }

    private void GoogleSignIn() {

        if(GoogleSignIn.getLastSignedInAccount(MobileActivity.this) != null && !GoogleSignIn.getLastSignedInAccount(MobileActivity.this).isExpired()) {
            regUser(GoogleSignIn.getLastSignedInAccount(MobileActivity.this));
        } else {
            Intent signInIntent = mGoogleSignInClient.getSignInIntent();
            startActivityForResult(signInIntent, RC_SIGN_IN);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);

            // Signed in successfully, show authenticated UI.
            Log.d("Google", "firebaseAuthWithGoogle:" + account.getId());
            regUser(account);
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w("TAG", "signInResult:failed code=" + e.getStatusCode());
            Toast.makeText(MobileActivity.this,e.getMessage(),Toast.LENGTH_SHORT).show();
        }
    }

    private void regUser(GoogleSignInAccount account)
    {
        progressing.setVisibility(View.VISIBLE);
        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            String userId = user.getUid();

                            DatabaseReference mRef = ref.child(userId);
                            mRef.child("name").setValue(account.getDisplayName());
                            mRef.child("email").setValue(account.getEmail());
                            mRef.child("dob").setValue("");
                            mRef.child("gender").setValue("");
                            mRef.child("mobile").setValue("");
                            mRef.child("uid").setValue(userId);
                            mRef.child("profilePicture").setValue("");
                            mRef.child("pushtoken").setValue(newToken);
                            mRef.child("address").setValue("");

                            mRef.get().addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
                                @Override
                                public void onSuccess(DataSnapshot dataSnapshot) {

                                    progressing.setVisibility(View.GONE);
                                    mPref.edit().putString(Prefs.UID,userId).apply();
                                    mPref.edit().putString(Prefs.EMAIL,account.getEmail()).apply();
                                    mPref.edit().putString(Prefs.LTYPE,"Google").apply();
                                    Toast.makeText(MobileActivity.this,"Success",Toast.LENGTH_SHORT).show();
                                    IntentUtils.fireIntent(MobileActivity.this, DashboardActivity.class,true);
                                    finish();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    progressing.setVisibility(View.GONE);
                                    Toast.makeText(MobileActivity.this,"Failed to sign in with google",Toast.LENGTH_SHORT).show();
                                }
                            });
                        } else {
                            progressing.setVisibility(View.GONE);
                            Toast.makeText(MobileActivity.this,"Failed to sign in with google",Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }

    private void regUserbyFacebook(Profile account)
    {
        progressing.setVisibility(View.VISIBLE);
        FirebaseUser user = mAuth.getCurrentUser();
        String userId = user.getUid();

        DatabaseReference mRef = ref.child(userId);
        mRef.child("name").setValue(account.getName());
        mRef.child("email").setValue("");
        mRef.child("dob").setValue("");
        mRef.child("gender").setValue("");
        mRef.child("mobile").setValue("");
        mRef.child("uid").setValue(userId);
        mRef.child("profilePicture").setValue("");
        mRef.child("pushtoken").setValue(newToken);
        mRef.child("address").setValue("");

        mRef.get().addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
            @Override
            public void onSuccess(DataSnapshot dataSnapshot) {

                progressing.setVisibility(View.GONE);
                mPref.edit().putString(Prefs.UID,userId).apply();
                mPref.edit().putString(Prefs.LTYPE,"Facebook").apply();
                Toast.makeText(MobileActivity.this,"Success",Toast.LENGTH_SHORT).show();
                IntentUtils.fireIntent(MobileActivity.this, DashboardActivity.class,true);
                finish();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressing.setVisibility(View.GONE);
                Toast.makeText(MobileActivity.this,"Failed to sign in with google",Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        IntentUtils.fireIntent(MobileActivity.this,SelectionActivity.class,true);
        finish();
    }
}