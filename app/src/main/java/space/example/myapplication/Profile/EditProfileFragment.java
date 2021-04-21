package space.example.myapplication.Profile;

import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.nostra13.universalimageloader.core.ImageLoader;

import space.example.myapplication.R;
import space.example.myapplication.Util.UniversalImageLoader;

public class EditProfileFragment extends Fragment {

    private static final String TAG = "EditProfileFragment";

    private ImageView mProfilePhoto;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_editprofile, container, false);
        mProfilePhoto = (ImageView) view.findViewById(R.id.profile_photo);

        setProfileImage();

        //back arrow for navigation back to "ProfileActivity"
        ImageView backArrow = (ImageView)view.findViewById(R.id.backArrow);
        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: navigation back to ProfileActivity");
                getActivity().finish();
            }
        });
        
        
        return view;
    }


    private void setProfileImage(){
        Log.d(TAG, "setProfileImage: setting profile image.");
        String imgURL = "sun9-11.userapi.com/impf/c307215/v307215885/4e01/z8CiGZ5m0FA.jpg?size=200x496&quality=96&sign=33bda57012d0460d8f17bb98ef1762f5&type=album";
        UniversalImageLoader.setImage(imgURL, mProfilePhoto, null, "https://");
    }
}

