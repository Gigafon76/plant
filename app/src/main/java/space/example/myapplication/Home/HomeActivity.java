package space.example.myapplication.Home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.darwindeveloper.horizontalscrollmenulibrary.custom_views.HorizontalScrollMenuView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;
import com.nostra13.universalimageloader.core.ImageLoader;

import space.example.myapplication.ArrayCategory;
import space.example.myapplication.R;
import space.example.myapplication.Util.BottomNavigationViewHelper;
import space.example.myapplication.Util.UniversalImageLoader;
import space.example.myapplication.login.LoginActivity;

public class HomeActivity extends AppCompatActivity {
    private static final String TAG = "HomeActivity";
    private static final int ACTIVITY_NUM = 0;
    HorizontalScrollMenuView menuView;
    TextView textView;

    //firebase
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    private Context mContext = HomeActivity.this;
    GridView gridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);



        Log.d(TAG, "onCreate: starting.");
        menuView = (HorizontalScrollMenuView)findViewById(R.id.menu);
        textView = (TextView)findViewById(R.id.txtText);
        gridView = findViewById(R.id.gridView);
        menu();

        setupFirebaseAuth();

        initImageLoader();
        setupBottomNavigationView();

        mAuth.signOut();


    }


    private void initImageLoader(){
        UniversalImageLoader universalImageLoader = new UniversalImageLoader(mContext);
        ImageLoader.getInstance().init(universalImageLoader.getConfig());
    }


    private void menu() {
        menuView.addItem("All plants",R.drawable.allplants);
        menuView.addItem("flycatcher",R.drawable.muxolovka);
        menuView.addItem("Dewdrop",R.drawable.rosynka);
        menuView.addItem("Adipem Puellae",R.drawable.jirynka);
        menuView.addItem("Nepenthes",R.drawable.nepentisy);
        menuView.addItem("Ground",R.drawable.grynt);
        menuView.addItem("Master Class",R.drawable.masterclass);
        menuView.setOnHSMenuClickListener(new HorizontalScrollMenuView.OnHSMenuClickListener() {
            @Override
            public void onHSMClick(com.darwindeveloper.horizontalscrollmenulibrary.extras.MenuItem menuItem, int position) {
                Toast.makeText(HomeActivity.this,"" + menuItem.getText(),Toast.LENGTH_SHORT).show();
                textView.setText(menuItem.getText());
;
            }
        });

    }

    /**
     * BottomNavigationView setup
     */
    private void setupBottomNavigationView(){
        Log.d(TAG,"setupBottomNavigationView: setting up BottomNavigationView");
        BottomNavigationViewEx bottomNavigationViewEx = (BottomNavigationViewEx)findViewById(R.id.bottomNavViewBar);
        BottomNavigationViewHelper.setupBottomNavigationView(bottomNavigationViewEx);
        BottomNavigationViewHelper.enableNavigation(mContext,bottomNavigationViewEx);
        Menu menu = bottomNavigationViewEx.getMenu();
        MenuItem menuItem = menu.getItem(ACTIVITY_NUM);
        menuItem.setChecked(true);
    }

      /*
    --------------------------Firebase----------------------------------
     */

    /**
     * checks to see if @param 'user' is logger in
     * @param user
     */
    private void checkCurrentUser(FirebaseUser user){
        Log.d(TAG, "checkCurrentUser: checkinf if user is logged in");
        if (user ==null){
            Intent intent = new Intent(mContext, LoginActivity.class);
            startActivity(intent);
        }
    }
    /**
     * Setup the fire
     */
    private void setupFirebaseAuth() {
        Log.d(TAG, "setupFirebaseAuth: setting up firebase auth.");
        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();

                //check if the user is logger in
                checkCurrentUser(user);
                if (user != null) {
                    //user is signer in
                    Log.d(TAG, "onAuthStateChanged: signer_in" + user.getUid());
                }else{
                    //user is signed out
                    Log.d(TAG, "onAuthStateChanged: signed_out");
                }
            }
        };
    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
        checkCurrentUser(mAuth.getCurrentUser());
    }
    @Override
    public void onStop(){
        super.onStop();
        if (mAuthListener!=null){
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

}