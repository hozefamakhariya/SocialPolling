package com.techomega.socialpolling.Fragment;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.techomega.socialpolling.Activity.EditProfileActivity;
import com.techomega.socialpolling.Activity.MobileActivity;
import com.techomega.socialpolling.Helper.IntentUtils;
import com.techomega.socialpolling.Helper.Prefs;
import com.techomega.socialpolling.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class AccountFragment extends Fragment {

    LinearLayout linearLayout1,linearLayout2,linearLayout3,linearLayout4,linearLayout5,linearLayout6;
    LinearLayout linearLayout7,linearLayout8,linearLayout9,linearLayout10;
    CircleImageView circleImageView;
    TextView textView;
    SharedPreferences mPref;
    String ltype;
    GoogleSignInClient mGoogleSignInClient;
    GoogleSignInOptions gso;
    private FirebaseAuth mAuth;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_account, container, false);

        if(getActivity()!=null){

            mPref = PreferenceManager.getDefaultSharedPreferences(getActivity());
            ltype = mPref.getString(Prefs.LTYPE,"");

            mAuth = FirebaseAuth.getInstance();
            FacebookSdk.sdkInitialize(getActivity());

            gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestIdToken(getString(R.string.default_web_client_id))
                    .requestEmail()
                    .build();

            mGoogleSignInClient = GoogleSignIn.getClient(getActivity(), gso);

            textView = (TextView) v.findViewById(R.id.name);
            circleImageView = (CircleImageView) v.findViewById(R.id.profile);

            linearLayout1 = (LinearLayout) v.findViewById(R.id.uprofile);
            linearLayout2 = (LinearLayout) v.findViewById(R.id.eprofile);
            linearLayout3 = (LinearLayout) v.findViewById(R.id.setting);
            linearLayout4 = (LinearLayout) v.findViewById(R.id.about);
            linearLayout5 = (LinearLayout) v.findViewById(R.id.contact);
            linearLayout6 = (LinearLayout) v.findViewById(R.id.rate);
            linearLayout7 = (LinearLayout) v.findViewById(R.id.share);
            linearLayout8 = (LinearLayout) v.findViewById(R.id.terms);
            linearLayout9 = (LinearLayout) v.findViewById(R.id.privacy);
            linearLayout10 = (LinearLayout) v.findViewById(R.id.logout);

            linearLayout1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (getActivity()!=null) {
                        IntentUtils.fireIntent(getActivity(), EditProfileActivity.class, false);
                    }
                }
            });

            linearLayout2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (getActivity()!=null) {
                        IntentUtils.fireIntent(getActivity(), EditProfileActivity.class, false);
                    }
                }
            });

            linearLayout10.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (getActivity()!=null) {
                        if(ltype.equals("Google")){
                            mGoogleSignInClient.signOut();
                            mAuth.signOut();

                            mPref.edit().putString(Prefs.UID,"").apply();
                            mPref.edit().putString(Prefs.MOBILE,"").apply();
                            mPref.edit().putString(Prefs.TMOBILE,"").apply();
                            mPref.edit().putString(Prefs.LTYPE,"").apply();
                            IntentUtils.fireIntent(getActivity(), MobileActivity.class, true);
                            getActivity().finish();
                        } else if(ltype.equals("Facebook")){
                            if(LoginManager.getInstance() != null){
                                LoginManager.getInstance().logOut();
                                AccessToken.setCurrentAccessToken(null);
                            }
                            mPref.edit().putString(Prefs.UID,"").apply();
                            mPref.edit().putString(Prefs.MOBILE,"").apply();
                            mPref.edit().putString(Prefs.TMOBILE,"").apply();
                            mPref.edit().putString(Prefs.LTYPE,"").apply();
                            IntentUtils.fireIntent(getActivity(), MobileActivity.class, true);
                            getActivity().finish();
                        } else {
                            mPref.edit().putString(Prefs.UID,"").apply();
                            mPref.edit().putString(Prefs.MOBILE,"").apply();
                            mPref.edit().putString(Prefs.TMOBILE,"").apply();
                            mPref.edit().putString(Prefs.LTYPE,"").apply();
                            IntentUtils.fireIntent(getActivity(), MobileActivity.class, true);
                            getActivity().finish();
                        }
                    }
                }
            });

        }

        return v;
    }
}