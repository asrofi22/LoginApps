package com.nonadev.loginapps;

import androidx.activity.OnBackPressedCallback;
import androidx.activity.OnBackPressedDispatcher;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.github.leandroborgesferreira.loadingbutton.customViews.CircularProgressButton;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity  {

    SharedPreferences sharedPreferences;
    private static final String SHARE_PREF_NAME = "share_pref";
    private static final String KEY_USERNAME = "user_name";
    TextView txtUserName;
    CircularProgressButton btnLogout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        txtUserName = findViewById(R.id.txtUser);
        btnLogout = findViewById(R.id.btnLogout);

        sharedPreferences = getSharedPreferences(SHARE_PREF_NAME, MODE_PRIVATE);
        String name = sharedPreferences.getString(KEY_USERNAME, null);

        if (name != null) {
            String WelcomeMessage = getString(R.string.Welcome, name);
            txtUserName.setText(WelcomeMessage);
        }
        Logout();

        OnBackPressedDispatcher on = getOnBackPressedDispatcher();
        OnBackPressedCallback onBackPressedCallback = new OnBackPressedCallback(true) {
            private boolean doubleBackPressed = false;
            @Override
            public void handleOnBackPressed() {
                if (doubleBackPressed) {
                    finishAffinity();
                } else {
                    Toast.makeText(HomeActivity.this, "Tekan sekali lagi untuk keluar",
                            Toast.LENGTH_SHORT).show();
                    doubleBackPressed = true;
            // Reset doubleBackPressed setelah 2 detik.
                    new Handler().postDelayed(() -> doubleBackPressed = false, 2000);
                }
            }
        };
        on.addCallback(this, onBackPressedCallback);
    }

    public static class MyAdapter extends FragmentStateAdapter {

        private final List<Fragment> fragmentList = new ArrayList<>();
        private final List<String> fragmentTitle = new ArrayList<>();

        public MyAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
            super(fragmentManager, lifecycle);
        }
        public void addFragment(Fragment fragment, String title) {
            fragmentList.add(fragment);
            fragmentTitle.add(title);
        }
        public String getFragmentTitle(int position) {
            return fragmentTitle.get(position);
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getItemCount() {
            return fragmentList.size();
        }
    }

    public void Logout () {
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear();
                editor.apply();
                finish();
            }
        });
    }

}




