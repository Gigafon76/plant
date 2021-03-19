package space.example.myapplication.Profile;

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

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;

import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import java.util.ArrayList;

import space.example.myapplication.R;
import space.example.myapplication.Util.BottomNavigationViewHelper;
import space.example.myapplication.Util.GridImageAdapter;
import space.example.myapplication.Util.UniversalImageLoader;

public class ProfileActivity extends AppCompatActivity{
    private static final String TAG = "ProfileActivity";
    private static final int ACTIVITY_NUM = 2;
    private static final int NUM_GRID_COLUMNS = 3 ;

    private final Context mContext = ProfileActivity.this;

    private ProgressBar mProgressBar;
    private ImageView profilePhoto;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Log.d(TAG, "onCreate: started.");


        setupBottomNavigationView();
        setupToolbar();
        setupActivityWidgets();
        setProfileImage();

        tempGridSetup();
    }


   private void tempGridSetup(){
        ArrayList<String> imgURLs = new ArrayList<>();
       imgURLs.add("https://sun9-11.userapi.com/impf/c307215/v307215885/4e01/z8CiGZ5m0FA.jpg?size=200x496&quality=96&sign=33bda57012d0460d8f17bb98ef1762f5&type=album");
       imgURLs.add("https://i.redd.it/9bf67ygj710z.jpg");
       imgURLs.add("https://c1.staticflickr.com/5/4276/34102458063_7be616b993_o.jpg");
       imgURLs.add("https://sun9-63 .userapi.com/impf/c845321/v845321560/f76c3/-r6GiY_HwTM.jpg?size=520x0&quality=95&sign=12eb0ead9a02eac20798574119a987b5");
       imgURLs.add("http://i.imgur.com/JTb2pXP.jpg");
       imgURLs.add("https://i.redd.it/59kjlxxf720z.jpg");
       imgURLs.add("https://i.redd.it/pwduhknig00z.jpg");
       imgURLs.add("https://i.redd.it/clusqsm4oxzy.jpg");
       imgURLs.add("https://i.redd.it/svqvn7xs420z.jpg");
       imgURLs.add("http://i.imgur.com/j4AfH6P.jpg");
       imgURLs.add("https://i.redd.it/89cjkojkl10z.jpg");
       imgURLs.add("https://i.redd.it/aw7pv8jq4zzy.jpg");


       setupImageGrid(imgURLs);

   }

    private void setupImageGrid(ArrayList<String>imgUrls){
        GridView gridView = (GridView) findViewById(R.id.gridView);

        int gridWith = getResources().getDisplayMetrics().widthPixels;
        int imageWidth = gridWith/NUM_GRID_COLUMNS;
        gridView.setColumnWidth(imageWidth);


        GridImageAdapter adapter = new GridImageAdapter(mContext,R.layout.layout_grid_imageview, "",imgUrls);
        gridView.setAdapter(adapter);


    }
    private void setProfileImage(){
        Log.d(TAG, "setProfileImage: setting profile photo");
        String imgURL = "sun9-11.userapi.com/impf/c307215/v307215885/4e01/z8CiGZ5m0FA.jpg?size=200x496&quality=96&sign=33bda57012d0460d8f17bb98ef1762f5&type=album";
        UniversalImageLoader.setImage(imgURL, profilePhoto, mProgressBar, "https://");
    }
    private void setupActivityWidgets(){
        mProgressBar = (ProgressBar) findViewById(R.id.profileProgressBar);
        mProgressBar.setVisibility(View.GONE);
        profilePhoto = (ImageView)findViewById(R.id.profile_photo);
    }



    /**
     * Responsible for setting up the profile toolbar
     */
    private void setupToolbar(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.profileToolBar);
        setSupportActionBar(toolbar);

        ImageView profileMenu = (ImageView) findViewById(R.id.profileMenu);
        profileMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: navigating to account settings.");
                Intent intent = new Intent(mContext, AccountSettingActivity.class);
                startActivity(intent);
            }
        });
    }

    /**
     * BottomNavigationView setup
     */
    private void setupBottomNavigationView(){
        Log.d(TAG, "setupBottomNavigationView: setting up BottomNavigationView");
        BottomNavigationViewEx bottomNavigationViewEx = (BottomNavigationViewEx) findViewById(R.id.bottomNavViewBar);
        BottomNavigationViewHelper.setupBottomNavigationView(bottomNavigationViewEx);
        BottomNavigationViewHelper.enableNavigation(mContext, bottomNavigationViewEx);
        Menu menu = bottomNavigationViewEx.getMenu();
        MenuItem menuItem = menu.getItem(ACTIVITY_NUM);
        menuItem.setChecked(true);
    }

}