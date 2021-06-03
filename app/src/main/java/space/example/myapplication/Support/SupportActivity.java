package space.example.myapplication.Support;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Gallery;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import space.example.myapplication.Favorite.FavoritesActivity;
import space.example.myapplication.R;
import space.example.myapplication.Util.BottomNavigationViewHelper;
import space.example.myapplication.Util.Permission;
import space.example.myapplication.Util.SectionsPagerAdapter;

public class SupportActivity extends AppCompatActivity {
    public static final String TAG = "SupportActivity";
    private Context mContext = SupportActivity.this;

    //constants
    private static final int ACTIVITY_NUM = 3;
    private static final int VERIFY_PERMISSIONS_REQUEST =1;

    private ViewPager mViewPager;

    private ViewPager mViewpager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_support);
        Log.d(TAG, "onCreate: starting.");


        if (checkPermissionsArray(Permission.PERMISSIONS)){
            setupViewPager();
        }else{
            verifyPermissions(Permission.PERMISSIONS);
        }
    }

    /**
     * return the current tab number
     * 0 = GalleryFragment
     * 1 = PhotoGallery
     * @return
     */
    public int getCurrentTabNumber(){
        return mViewPager.getCurrentItem();
    }

    /**
     * setup viewpager for manager the tabs
     */
    private void setupViewPager(){
        SectionsPagerAdapter adapter =  new SectionsPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new GalleryFragment());
        adapter.addFragment(new PhotoFragment());

        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(adapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabsBottom);
        tabLayout.setupWithViewPager(mViewPager);

        tabLayout.getTabAt(0).setText(getString(R.string.gallery));
        tabLayout.getTabAt(1).setText(getString(R.string.photo));

    }

    /**
     * verify all the permission passed to the array
     * @param permissions
     */
    public void verifyPermissions (String [] permissions){
        Log.d(TAG , "verifyPermissions: verification permissions");

        ActivityCompat.requestPermissions(
                SupportActivity.this,
                permissions,
                VERIFY_PERMISSIONS_REQUEST
        );
    }

    /**
     *  Check an array of permissions
     * @param permissions
     * @return
     */
    public boolean checkPermissionsArray(String[] permissions){
        Log.d(TAG , "checkPermissionsArray: checking permissions array.");

        for (int i =0;i<permissions.length;i++){
            String check = permissions[i];
            if (!checkPermission(check)){
                return false;
            }
        }
        return true;
    }

    /**
     * Check a single permissions is it has been verified
     * @param permissions
     * @return
     */
    public boolean checkPermission(String permissions) {
        Log.d(TAG , "checkPermission: checking permissions " + permissions);

        int permissionsRequest = ActivityCompat.checkSelfPermission(SupportActivity.this , permissions);

        if (permissionsRequest != PackageManager.PERMISSION_GRANTED) {
            Log.d(TAG , "checkPermission: \n Permissions was not granted for: " + permissions);
            return false;
        } else {
            Log.d(TAG , "checkPermission: \n Permissions was  granted for: " + permissions);
            return true;
        }
    }

    /**
     * BottomNavigationView setup
     */
    private void setupBottomNavigationView() {
        Log.d(TAG, "setupBottomNavigationView: setting up BottomNavigationView");
        BottomNavigationViewEx bottomNavigationViewEx = (BottomNavigationViewEx) findViewById(R.id.bottomNavViewBar);
        BottomNavigationViewHelper.setupBottomNavigationView(bottomNavigationViewEx);
        BottomNavigationViewHelper.enableNavigation(mContext, bottomNavigationViewEx);
        Menu menu = bottomNavigationViewEx.getMenu();
        MenuItem menuItem = menu.getItem(ACTIVITY_NUM);
        menuItem.setChecked(true);
    }
}
