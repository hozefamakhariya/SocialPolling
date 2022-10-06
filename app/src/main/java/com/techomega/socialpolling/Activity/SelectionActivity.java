package com.techomega.socialpolling.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.google.android.material.button.MaterialButton;
import com.techomega.socialpolling.Helper.IntentUtils;
import com.techomega.socialpolling.R;

public class SelectionActivity extends AppCompatActivity {

    MaterialButton materialButton;
    LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selection);

        materialButton = (MaterialButton) findViewById(R.id.register);
        linearLayout = (LinearLayout) findViewById(R.id.login);

        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IntentUtils.fireIntent(SelectionActivity.this,MobileActivity.class,true);
                finish();
            }
        });
    }
}