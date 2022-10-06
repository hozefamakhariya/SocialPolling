package com.techomega.socialpolling.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.chaos.view.PinView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
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

import java.util.concurrent.TimeUnit;

public class OtpActivity extends AppCompatActivity {

    PinView pinView;
    MaterialButton otp_button;
    String mVerificationId;
    private FirebaseAuth mAuth;
    SharedPreferences mPref;
    String strphone,newToken;
    PhoneAuthProvider.ForceResendingToken mresend;
    RelativeLayout progressing;
    LinearLayout layout_resend;
    CardView cardView1,cardView2;
    TextView textView;
    DatabaseReference ref;
    TextView txtfrgt,txtversion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);

        mAuth = FirebaseAuth.getInstance();
        ref = FirebaseDatabase.getInstance("https://surveymonk-c8bd1-default-rtdb.asia-southeast1.firebasedatabase.app").getReference().child("Users");
        mPref = PreferenceManager.getDefaultSharedPreferences(OtpActivity.this);
        strphone = mPref.getString(Prefs.TMOBILE,"");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.black), PorterDuff.Mode.SRC_ATOP);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        progressing = (RelativeLayout) findViewById(R.id.progress);

        textView = (TextView) findViewById(R.id.mobile);
        pinView = (PinView) findViewById(R.id.otpview);

        otp_button = (MaterialButton) findViewById(R.id.otp);

        layout_resend = (LinearLayout) findViewById(R.id.resend_otp);
        cardView1 = (CardView) findViewById(R.id.google);
        cardView2 = (CardView) findViewById(R.id.fb);

        txtfrgt = (TextView) findViewById(R.id.frgt);
        txtversion = (TextView) findViewById(R.id.version);

        txtversion.setText("Version "+ BuildConfig.VERSION_NAME);

        textView.setText("Enter the OTP send to +91 "+strphone);

        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(OtpActivity.this,  new OnSuccessListener<InstanceIdResult>() {
            @Override
            public void onSuccess(InstanceIdResult instanceIdResult) {
                newToken = instanceIdResult.getToken();
                SharedPrefManager.getInstance(OtpActivity.this).saveDeviceToken(newToken);
            }
        });

        sendVerificationCode(strphone);

        layout_resend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendVerificationCode(strphone);
            }
        });

        otp_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Verify();
            }
        });
    }

    private void sendVerificationCode(String mobile) {
        progressing.setVisibility(View.VISIBLE);
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                "+91" + mobile,
                60,
                TimeUnit.SECONDS,
                OtpActivity.this,
                mCallbacks
        );
    }

    private void Verify(){
        String code = pinView.getText().toString().trim();
        if(TextUtils.isEmpty(code)){
            Toast.makeText(OtpActivity.this,"Enter 6 digit OTP",Toast.LENGTH_SHORT).show();
        } else {
            verifyVerificationCode(code);
        }
    }

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

            String ncode = phoneAuthCredential.getSmsCode();
            if (ncode != null) {
                progressing.setVisibility(View.GONE);
                pinView.setText(ncode);
                verifyVerificationCode(ncode);
            } else {
                progressing.setVisibility(View.GONE);
                signInWithPhoneAuthCredential(phoneAuthCredential);
            }
        }

        @Override
        public void onVerificationFailed(@NonNull FirebaseException e) {
            Toast.makeText(OtpActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
        }

        @Override
        public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            progressing.setVisibility(View.GONE);
            mVerificationId = s;
            mresend = forceResendingToken;
        }
    };

    private void verifyVerificationCode(String code) {
        try {
            PhoneAuthCredential phoneAuthCredential = PhoneAuthProvider.getCredential(mVerificationId, code);
            signInWithPhoneAuthCredential(phoneAuthCredential);

        } catch (Exception e){
            Toast.makeText(OtpActivity.this,"Verification Code is wrong",Toast.LENGTH_SHORT).show();
        }
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential phoneAuthCredential) {

        mAuth.signInWithCredential(phoneAuthCredential).addOnCompleteListener(OtpActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()) {
                    FirebaseRegister(mAuth.getCurrentUser().getUid());
                } else {
                    String message = "Something is Wrong";
                    if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                        message = "Invalide Code";
                    }
                    Toast.makeText(OtpActivity.this,message,Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private void FirebaseRegister(String id) {

        progressing.setVisibility(View.VISIBLE);

        DatabaseReference mRef = ref.child(id);
        mRef.child("name").setValue("");
        mRef.child("email").setValue("");
        mRef.child("dob").setValue("");
        mRef.child("gender").setValue("");
        mRef.child("mobile").setValue(strphone);
        mRef.child("uid").setValue(id);
        mRef.child("profilePicture").setValue("");
        mRef.child("pushtoken").setValue(newToken);
        mRef.child("address").setValue("");
        mRef.get().addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
            @Override
            public void onSuccess(DataSnapshot dataSnapshot) {
                progressing.setVisibility(View.GONE);
                Toast.makeText(OtpActivity.this,"Success",Toast.LENGTH_SHORT).show();
                mPref.edit().putString(Prefs.MOBILE,strphone).apply();
                mPref.edit().putString(Prefs.UID,id).apply();
                mPref.edit().putString(Prefs.LTYPE,"Phone").apply();
                IntentUtils.fireIntent(OtpActivity.this, DashboardActivity.class,true);
                finish();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressing.setVisibility(View.GONE);
                Toast.makeText(OtpActivity.this,"Failure "+e.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        IntentUtils.fireIntent(OtpActivity.this,MobileActivity.class,true);
        finish();
    }
}