package com.techomega.socialpolling.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.ismaeldivita.chipnavigation.ChipNavigationBar;
import com.techomega.socialpolling.Fragment.AccountFragment;
import com.techomega.socialpolling.Fragment.HomeFragment;
import com.techomega.socialpolling.Fragment.SearchFragment;
import com.techomega.socialpolling.R;

public class DashboardActivity extends AppCompatActivity {

    ChipNavigationBar chipNavigationBar;
    TextView txttitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        toolbar.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

        txttitle = (TextView) toolbar.findViewById(R.id.title);
        txttitle.setText(R.string.app_name);

        chipNavigationBar = (ChipNavigationBar) findViewById(R.id.chipnav);
        chipNavigationBar.setItemSelected(R.id.home, true);
        replace_fragment(new HomeFragment());

        Bottom_menu();

    }

    private void Bottom_menu(){
        chipNavigationBar.setOnItemSelectedListener(new ChipNavigationBar.OnItemSelectedListener() {
            @Override
            public void onItemSelected(int i) {
                switch (i){
                    case R.id.home:
                        txttitle.setText(R.string.app_name);
                        replace_fragment(new HomeFragment());
                        break;
                    case R.id.search:
                        txttitle.setText("Feeds");
                        replace_fragment(new SearchFragment());
                        break;
                    case R.id.acc:
                        txttitle.setText("Profile Settings");
                        replace_fragment(new AccountFragment());
                        break;
                }
            }
        });
    }

    public void replace_fragment(Fragment fragment){
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout,fragment);
        fragmentTransaction.commit();
    }
}