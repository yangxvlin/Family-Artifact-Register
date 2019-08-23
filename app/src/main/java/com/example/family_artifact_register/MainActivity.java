//package com.example.family_artifact_register;
//
//import androidx.appcompat.app.AppCompatActivity;
//
//import static util.ActivityNavigator.navigateFromTo;
//
//import android.os.Bundle;
//import android.view.View;
//import android.widget.Button;
//
///**
// * @author XuLin Yang 904904,
// * @time 2019-8-10 17:01:49
// * @description main activity let user to choose to sign in or sign up
// */
//public class MainActivity extends AppCompatActivity {
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//
//        // user click "Sign In" to be directed to sign in activity
//        final Button signInButton = (Button) findViewById(R.id.sign_in_button);
//        signInButton.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                navigateFromTo(MainActivity.this, SignInActivity.class);
//            }
//        });
//
//        // user click "Sign Up" to be directed to sign up activity
//        final Button signUpButton = (Button) findViewById(R.id.sign_up_button);
//        signUpButton.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                navigateFromTo(MainActivity.this, SignUpActivity.class);
//            }
//        });
//    }
//}

package com.example.family_artifact_register;

import android.util.Log;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.drawerlayout.widget.DrawerLayout;
import org.jetbrains.annotations.NotNull;
import androidx.appcompat.widget.Toolbar;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

/**
 * @author XuLin Yang 904904,
 * @time 2019-8-10 17:03:06
 * @description home activity let user
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // set up top app bar
        final Toolbar toolBar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolBar);

        // set up bottom navigation bar
        BottomNavigationView bottomBar = (BottomNavigationView) findViewById(R.id.bottom_bar);
        bottomBar.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                toolBar.setTitle(item.getTitle());
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
