package space.example.myapplication.Util;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.MenuItem;

import androidx.annotation.NonNull;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import space.example.myapplication.Basket.BasketActivity;
import space.example.myapplication.Favorite.FavoritesActivity;
import space.example.myapplication.Home.HomeActivity;
import space.example.myapplication.Profile.ProfileActivity;
import space.example.myapplication.R;
import space.example.myapplication.Support.SupportActivity;


public class BottomNavigationViewHelper {

    private static final String TAG = "BottomNavigationViewHel";

    public static void setupBottomNavigationView(BottomNavigationViewEx bottomNavigationViewEx){
        Log.d(TAG, "setupBottomNavigationView : Setting up ButtonNavigationView");
        bottomNavigationViewEx.enableAnimation(false);
        bottomNavigationViewEx.enableItemShiftingMode(false);
        bottomNavigationViewEx.enableShiftingMode(false);
        bottomNavigationViewEx.setTextVisibility(true);
    }
    public static void enableNavigation(final Context context, BottomNavigationViewEx view){
        view.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
              switch (item.getItemId()){
                  case R.id.navigation_catalog:
                      Intent intent1 = new Intent(context, HomeActivity.class);//ACTIVITY_NUM = 0;
                      context.startActivity(intent1);
                      break;
                  case R.id.navigation_favorites:
                      Intent intent2 = new Intent(context, FavoritesActivity.class);//ACTIVITY_NUM = 1;
                      context.startActivity(intent2);
                      break;

                  case R.id.navigation_info:
                      Intent intent3 = new Intent(context, ProfileActivity.class);//ACTIVITY_NUM = 2;
                      context.startActivity(intent3);
                      break;

                  case R.id.navigation_support:
                      Intent intent4 = new Intent(context, SupportActivity.class);//ACTIVITY_NUM = 3;
                      context.startActivity(intent4);
                      break;

                      case R.id.navigation_basket:
                      Intent intent5 = new Intent(context,BasketActivity.class);//ACTIVITY_NUM = 4;
                      context.startActivity(intent5);
                      break;

              }
                return false;
            }
        });
    }
}
