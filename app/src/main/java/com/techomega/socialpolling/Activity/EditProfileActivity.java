package com.techomega.socialpolling.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.DatePickerDialog;
import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.techomega.socialpolling.Helper.Prefs;
import com.techomega.socialpolling.Helper.SharedPrefManager;
import com.techomega.socialpolling.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class EditProfileActivity extends AppCompatActivity {

    SharedPreferences mPref;
    String strid,strmobile,strtype,newToken,emailid;
    DatabaseReference ref;
    private FirebaseAuth mAuth;
    RelativeLayout progressing;
    EditText edtname,edtemail,edtdob,edtmobile,edtadd;
    String strname,stremail,strdob,stradd,strgender = "Male",strphone;
    LinearLayout linearLayout1,linearLayout2;
    ImageView imageView1,imageView2;
    TextView textView1,textView2;
    MaterialButton materialButton;
    Calendar date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        mAuth = FirebaseAuth.getInstance();
        mPref = PreferenceManager.getDefaultSharedPreferences(EditProfileActivity.this);
        strid = mPref.getString(Prefs.UID,"");
        strmobile = mPref.getString(Prefs.MOBILE,"");
        emailid = mPref.getString(Prefs.EMAIL,"");
        strtype = mPref.getString(Prefs.LTYPE,"");

        ref = FirebaseDatabase.getInstance("https://surveymonk-c8bd1-default-rtdb.asia-southeast1.firebasedatabase.app").getReference().child("Users");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Edit Profile");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(EditProfileActivity.this,  new OnSuccessListener<InstanceIdResult>() {
            @Override
            public void onSuccess(InstanceIdResult instanceIdResult) {
                newToken = instanceIdResult.getToken();
                SharedPrefManager.getInstance(EditProfileActivity.this).saveDeviceToken(newToken);
            }
        });

        progressing = (RelativeLayout) findViewById(R.id.progress);

        edtname = (EditText) findViewById(R.id.name);
        edtemail = (EditText) findViewById(R.id.email);
        edtdob = (EditText) findViewById(R.id.dob);
        edtmobile = (EditText) findViewById(R.id.mobile);
        edtadd = (EditText) findViewById(R.id.address);

        linearLayout1 = (LinearLayout) findViewById(R.id.linear1);
        linearLayout2 = (LinearLayout) findViewById(R.id.linear2);

        imageView1 = (ImageView) findViewById(R.id.img1);
        imageView2 = (ImageView) findViewById(R.id.img2);

        textView1 = (TextView) findViewById(R.id.txt1);
        textView2 = (TextView) findViewById(R.id.txt2);

        materialButton = (MaterialButton) findViewById(R.id.update);

        if(strtype.equals("Phone")){
            edtmobile.setText(strmobile);
            edtmobile.setEnabled(false);
            edtmobile.setClickable(false);
            edtemail.setClickable(true);
            edtemail.setEnabled(true);
        } else if(strtype.equals("Google")){
            edtemail.setText(emailid);
            edtmobile.setEnabled(true);
            edtmobile.setClickable(true);
            edtemail.setClickable(false);
            edtemail.setEnabled(false);
        } else {
            edtmobile.setEnabled(true);
            edtmobile.setClickable(true);
            edtemail.setClickable(true);
            edtemail.setEnabled(true);
        }

        linearLayout1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(linearLayout1.getTag().equals("fill")){
                    strgender = "";
                    linearLayout1.setTag("unfill");
                    imageView1.setTag("unfill");
                    textView1.setTag("unfill");
                    linearLayout1.setBackgroundDrawable(getResources().getDrawable(R.drawable.line_bg));
                    textView1.setTextColor(getResources().getColor(R.color.black));
                    imageView1.getDrawable().setTint(getResources().getColor(R.color.toolcolor));
                } else {
                    strgender = "Male";
                    linearLayout1.setTag("fill");
                    imageView1.setTag("fill");
                    textView1.setTag("fill");
                    linearLayout1.setBackgroundDrawable(getResources().getDrawable(R.drawable.new_linear_bg));
                    textView1.setTextColor(getResources().getColor(R.color.white));
                    imageView1.getDrawable().setTint(getResources().getColor(R.color.white));

                    linearLayout2.setTag("unfill");
                    imageView2.setTag("unfill");
                    textView2.setTag("unfill");
                    linearLayout2.setBackgroundDrawable(getResources().getDrawable(R.drawable.line_bg));
                    textView2.setTextColor(getResources().getColor(R.color.black));
                    imageView2.getDrawable().setTint(getResources().getColor(R.color.toolcolor));

                }
            }
        });

        linearLayout2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(linearLayout2.getTag().equals("fill")){
                    strgender = "";
                    linearLayout2.setTag("unfill");
                    imageView2.setTag("unfill");
                    textView2.setTag("unfill");
                    linearLayout2.setBackgroundDrawable(getResources().getDrawable(R.drawable.line_bg));
                    textView2.setTextColor(getResources().getColor(R.color.black));
                    imageView2.getDrawable().setTint(getResources().getColor(R.color.toolcolor));

                } else {
                    strgender = "Female";
                    linearLayout2.setTag("fill");
                    imageView2.setTag("fill");
                    textView2.setTag("fill");
                    linearLayout2.setBackgroundDrawable(getResources().getDrawable(R.drawable.new_linear_bg));
                    textView2.setTextColor(getResources().getColor(R.color.white));
                    imageView2.getDrawable().setTint(getResources().getColor(R.color.white));

                    linearLayout1.setTag("unfill");
                    imageView1.setTag("unfill");
                    textView1.setTag("unfill");
                    linearLayout1.setBackgroundDrawable(getResources().getDrawable(R.drawable.line_bg));
                    textView1.setTextColor(getResources().getColor(R.color.black));
                    imageView1.getDrawable().setTint(getResources().getColor(R.color.toolcolor));

                }
            }
        });

        edtdob.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(b){
                    showDateTimePicker();
                }
            }
        });

        edtdob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDateTimePicker();
            }
        });

        materialButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Verify();
            }
        });

        if(!TextUtils.isEmpty(strid)) {
            FetchProfile();
        }
    }

    public void showDateTimePicker(){
        final Calendar currentDate = Calendar.getInstance();
        date = Calendar.getInstance();

        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                date.set(year, monthOfYear, dayOfMonth);
                monthOfYear=monthOfYear+1;
                strdob=dayOfMonth+"/"+monthOfYear+"/"+year;
                Date selecteddate=date.getTime();
                SimpleDateFormat dateFormat=new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
                strdob=dateFormat.format(selecteddate);
                edtdob.setText(strdob);

            }
        };
        DatePickerDialog datePickerDialog = new  DatePickerDialog(EditProfileActivity.this, dateSetListener, currentDate.get(Calendar.YEAR), currentDate.get(Calendar.MONTH),   currentDate.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis() - 1000);
        datePickerDialog.show();
    }

    private void Verify(){
        strname = edtname.getText().toString().trim();
        stremail = edtemail.getText().toString().trim();
        strphone = edtmobile.getText().toString().trim();
        stradd = edtadd.getText().toString().trim();

        if(TextUtils.isEmpty(strname)){
            edtname.setError("Please Enter your Name");
            edtname.requestFocus();
            edtname.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    edtname.setError(null);
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });
        } else if(TextUtils.isEmpty(stremail)){
            edtemail.setError("Please Enter your Email");
            edtemail.requestFocus();
            edtemail.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    edtemail.setError(null);
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });
        } else if(TextUtils.isEmpty(strphone)){
            edtmobile.setError("Please Enter your valid 10 digit mobile number");
            edtmobile.requestFocus();
            edtmobile.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    edtmobile.setError(null);
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });
        } else if(TextUtils.isEmpty(strdob)){
            edtdob.setError("Please Enter your Email");
            edtdob.requestFocus();
            edtdob.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    edtdob.setError(null);
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });
        } else if(TextUtils.isEmpty(strgender)){
            Toast.makeText(EditProfileActivity.this,"Please Select your gender",Toast.LENGTH_SHORT).show();
        } else {
            UpdateProfile();
        }
    }

    private void UpdateProfile(){
        progressing.setVisibility(View.VISIBLE);

        DatabaseReference mRef = ref.child(strid);
        mRef.child("name").setValue(strname);
        mRef.child("email").setValue(stremail);
        mRef.child("dob").setValue(strdob);
        mRef.child("gender").setValue(strgender);
        mRef.child("mobile").setValue(strphone);
        mRef.child("uid").setValue(strid);
        mRef.child("profilePicture").setValue("");
        mRef.child("pushtoken").setValue(newToken);
        mRef.child("address").setValue(stradd);
        mRef.get().addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
            @Override
            public void onSuccess(DataSnapshot dataSnapshot) {
                progressing.setVisibility(View.GONE);
                FetchProfile();
                Toast.makeText(EditProfileActivity.this,"Success",Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressing.setVisibility(View.GONE);
                Toast.makeText(EditProfileActivity.this,"Failure "+e.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void FetchProfile(){

        progressing.setVisibility(View.VISIBLE);

        DatabaseReference mRef = ref.child(strid);

        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                progressing.setVisibility(View.GONE);
                strname=dataSnapshot.child("name").getValue(String.class).toString();
                stremail =dataSnapshot.child("email").getValue(String.class).toString();
                strphone =dataSnapshot.child("mobile").getValue(String.class).toString();
                stradd =dataSnapshot.child("address").getValue(String.class).toString();
                strdob =dataSnapshot.child("dob").getValue(String.class).toString();
                strgender =dataSnapshot.child("gender").getValue(String.class).toString();

                edtname.setText(strname);
                edtemail.setText(stremail);
                edtadd.setText(stradd);
                edtdob.setText(strdob);
                edtmobile.setText(strphone);

                if(strgender.equals("Male")){
                    strgender = "Male";
                    linearLayout1.setTag("fill");
                    imageView1.setTag("fill");
                    textView1.setTag("fill");
                    linearLayout1.setBackgroundDrawable(getResources().getDrawable(R.drawable.new_linear_bg));
                    textView1.setTextColor(getResources().getColor(R.color.white));
                    imageView1.getDrawable().setTint(getResources().getColor(R.color.white));

                    linearLayout2.setTag("unfill");
                    imageView2.setTag("unfill");
                    textView2.setTag("unfill");
                    linearLayout2.setBackgroundDrawable(getResources().getDrawable(R.drawable.line_bg));
                    textView2.setTextColor(getResources().getColor(R.color.black));
                    imageView2.getDrawable().setTint(getResources().getColor(R.color.toolcolor));
                } else {
                    strgender = "Female";
                    linearLayout2.setTag("fill");
                    imageView2.setTag("fill");
                    textView2.setTag("fill");
                    linearLayout2.setBackgroundDrawable(getResources().getDrawable(R.drawable.new_linear_bg));
                    textView2.setTextColor(getResources().getColor(R.color.white));
                    imageView2.getDrawable().setTint(getResources().getColor(R.color.white));

                    linearLayout1.setTag("unfill");
                    imageView1.setTag("unfill");
                    textView1.setTag("unfill");
                    linearLayout1.setBackgroundDrawable(getResources().getDrawable(R.drawable.line_bg));
                    textView1.setTextColor(getResources().getColor(R.color.black));
                    imageView1.getDrawable().setTint(getResources().getColor(R.color.toolcolor));
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                progressing.setVisibility(View.GONE);
                Toast.makeText(EditProfileActivity.this,databaseError.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}