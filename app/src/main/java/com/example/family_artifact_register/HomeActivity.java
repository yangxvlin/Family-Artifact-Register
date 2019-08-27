package com.example.family_artifact_register;

import android.content.Intent;
import android.util.Log;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import androidx.annotation.NonNull;
import org.jetbrains.annotations.NotNull;
import androidx.appcompat.widget.Toolbar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

import static com.example.family_artifact_register.util.ActivityNavigator.navigateFromTo;

/**
 * @author XuLin Yang 904904,
 * @time 2019-8-10 17:03:06
 * @description home activity let user
 */
public class HomeActivity extends AppCompatActivity {

    private ActionBarDrawerToggle drawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // set up top app bar
        final Toolbar toolBar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolBar);

        DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        // quote from the doc "please use ActionBarDrawerToggle(this, drawerLayout, open, close) (i.e. without toolbar)
        // if you are setting the Toolbar as the Actionbar of your activity"
//        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolBar, R.string.drawer_open, R.string.drawer_close);
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.drawer_open, R.string.drawer_close);

        // set up bottom navigation bar
        BottomNavigationView bottomBar = (BottomNavigationView) findViewById(R.id.bottom_bar);
        bottomBar.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                toolBar.setTitle(item.getTitle());
                return true;
            }
        });

        NavigationView drawer = findViewById(R.id.nav_drawer);
        drawer.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                // code to sign out
                FirebaseAuth.getInstance().signOut();
                getIntent().setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                // To clean up all activities
                navigateFromTo(HomeActivity.this, MainActivity.class);
                return true;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NotNull MenuItem menuItem) {

        // handle navigation icon selection
        if(drawerToggle.onOptionsItemSelected(menuItem)) {
            return true;
        }

        // re-implement this method for more complex activity
        // so far, this method is used only for debugging
        switch (menuItem.getItemId()) {
            case R.id.search:
                Log.i("", "Enter search option.");
                System.out.println("Enter search option.");
                return true;
            case R.id.more:
                Log.i("", "Enter more option.");
                System.out.println("Enter more option.");
                return true;
            default:
                Log.i("", "Enter default case, something is wrong  !");
                System.out.println("Enter default case, something is wrong !");
                return super.onOptionsItemSelected(menuItem);
        }
    }
}
