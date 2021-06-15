package space.example.myapplication.Home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.darwindeveloper.horizontalscrollmenulibrary.custom_views.HorizontalScrollMenuView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;

import space.example.myapplication.R;
import space.example.myapplication.Util.BottomNavigationViewHelper;
import space.example.myapplication.Util.GridImageAdapter;
import space.example.myapplication.Util.UniversalImageLoader;
import space.example.myapplication.login.LoginActivity;

public class HomeActivity extends AppCompatActivity {

    private static final String TAG = "HomeActivity";
    private static final int ACTIVITY_NUM = 0;
    private static final int NUM_GRID_COLUMNS = 3 ;

    private Context mContext = HomeActivity.this;
    boolean isPressed = false;

    HorizontalScrollMenuView menu;
    TextView textView;
    private ImageView profilePhoto;
    private ProgressBar homeProgressBar;

    //firebase
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    GridView gridView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Log.d(TAG, "onCreate: starting.");

        menu = (HorizontalScrollMenuView)findViewById(R.id.menu);
        textView = (TextView)findViewById(R.id.txtTxt);
        gridView = findViewById(R.id.gridView);
        initMenu();


        setupBottomNavigationView();
        setupActivityWidgets();
        tempGridSetup();

        setupFirebaseAuth();
        initImageLoader();
        setupBottomNavigationView();
    }



    private void initImageLoader(){
        UniversalImageLoader universalImageLoader = new UniversalImageLoader(mContext);
        ImageLoader.getInstance().init(universalImageLoader.getConfig());
    }


    private void initMenu() {
        menu.addItem("All plants",R.drawable.allplants);
        menu.addItem("flycatcher",R.drawable.muxolovka);
        menu.addItem("Dewdrop",R.drawable.rosynka);
        menu.addItem("Adipem Puellae",R.drawable.jirynka);
        menu.addItem("Nepenthes",R.drawable.nepentisy);
        menu.addItem("Ground",R.drawable.grynt);
        menu.addItem("Master Class",R.drawable.masterclass);
        menu.setOnHSMenuClickListener(new HorizontalScrollMenuView.OnHSMenuClickListener() {

            @Override
            public void onHSMClick(com.darwindeveloper.horizontalscrollmenulibrary.extras.MenuItem menuItem, int position) {
                Toast.makeText(HomeActivity.this,"" + menuItem.getText(),Toast.LENGTH_SHORT).show();
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

    // setup imageUrl

    private void tempGridSetup(){
        ArrayList<String> imgURLs = new ArrayList<>();
        imgURLs.add("https://sun9-65.userapi.com/impf/c840734/v840734335/75e13/49YVzeDOrws.jpg?size=520x0&quality=95&sign=c1915fe17a299c9f2a0e3ce3faa73ab3");
        imgURLs.add("https://sun9-55.userapi.com/impf/c845216/v845216335/2d09a/gT1bYdOI6Rc.jpg?size=520x0&quality=95&sign=c0aa303789cdb6a9ca77d32f1b461acb");
        imgURLs.add("https://c1.staticflickr.com/5/4276/34102458063_7be616b993_o.jpg");
        imgURLs.add("https://sun9-69.userapi.com/impf/c831408/v831408804/d31d1/uoht1W34TvE.jpg?size=520x0&quality=95&sign=a6f7ed1d580fbbcd8b23176082858f5a");
        imgURLs.add("https://sun9-12.userapi.com/impf/c855024/v855024888/656e5/7atkDXcGKXY.jpg?size=520x0&quality=95&sign=3f63c8e18b157e6af9b5fe99a17139a9");
        imgURLs.add("https://sun9-22.userapi.com/impf/c840636/v840636928/7564f/YC4TfqguHkU.jpg?size=520x0&quality=95&sign=009fb9ab838e48485a3babebabe5d0c1");
        imgURLs.add("https://sun9-76.userapi.com/impf/c854420/v854420521/243dc/3iXdRwpP2Sc.jpg?size=520x0&quality=95&sign=24a08a7386e95c74167042d29e533b59");
        imgURLs.add("https://sun1-18.userapi.com/impf/c9zVNtEL2vbr0WF2MACL-5318YeQKjXlvpvZoQ/cCGXQNAcXMI.jpg?size=520x0&quality=95&sign=94904fd02439e4d5809bf77a5a10c5e0");
        imgURLs.add("https://sun1-57.userapi.com/impf/EFLZljzv5rlKp-28uOlL2EmZrqmr9fo8UbcnBQ/oLs-8EH3C64.jpg?size=520x0&quality=95&sign=2c25037a8c6c11416a8dce14d359cbf1");
        imgURLs.add("https://sun9-72.userapi.com/impf/c845321/v845321560/f76b2/YvcixO2iqfk.jpg?size=520x0&quality=95&sign=44eb04d8b05ccde3060b60a33417849a");
        imgURLs.add("https://sun9-6.userapi.com/impf/c846320/v846320958/100a1d/7-mwAKQBoxA.jpg?size=520x0&quality=95&sign=4694f33dbb975451c813285bd7e00242");
        imgURLs.add("https://sun9-5.userapi.com/impf/c840724/v840724166/78c18/vcRJLfDEGCc.jpg?size=520x0&quality=95&sign=9bef975a8354a98de3f012f24fdd6763");
        imgURLs.add("https://sun9-13.userapi.com/impf/c850536/v850536616/7b44/J46B8CORJZI.jpg?size=520x0&quality=95&sign=7811addb541efe666af32e1fe606bac6");
        imgURLs.add("https://sun9-48.userapi.com/impf/c824502/v824502928/10ff55/X5Tx0Ol7s4M.jpg?size=520x0&quality=95&sign=d1ed629310ae262fc6579470935e9a02");
        imgURLs.add("https://sun9-41.userapi.com/impf/c847019/v847019947/3201d/EWJ8aVRSJlU.jpg?size=520x0&quality=95&sign=8f0d401ad9965c263303042bab32ff51");

        imgURLs.add("https://sun9-48.userapi.com/impf/c824502/v824502928/10ff55/X5Tx0Ol7s4M.jpg?size=520x0&quality=95&sign=d1ed629310ae262fc6579470935e9a02");
        imgURLs.add("https://sun9-41.userapi.com/impf/c847019/v847019947/3201d/EWJ8aVRSJlU.jpg?size=520x0&quality=95&sign=8f0d401ad9965c263303042bab32ff51");
        imgURLs.add("https://sun9-8.userapi.com/impf/c824503/v824503611/198d98/czSDTFMA1wk.jpg?size=520x0&quality=95&sign=faf1b1fe93ac3a76bc224af19d46fb8d");
        setupImageGrid(imgURLs);
    }
    private void setupImageGrid(ArrayList<String> imgURLs){
        GridView gridView = (GridView) findViewById(R.id.gridView);

        int gridWidth = getResources().getDisplayMetrics().widthPixels;
        int imageWidth = gridWidth/NUM_GRID_COLUMNS;
        gridView.setColumnWidth(imageWidth);

        GridImageAdapter adapter = new GridImageAdapter(mContext, R.layout.layout_grid_imageview, "", imgURLs);
        gridView.setAdapter(adapter);
    }



    private void setupActivityWidgets(){
        profilePhoto = (ImageView) findViewById(R.id.profile_photo);

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
     * Setup the firebase
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