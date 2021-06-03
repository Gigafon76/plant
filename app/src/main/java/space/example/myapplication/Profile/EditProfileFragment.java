package space.example.myapplication.Profile;

import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.nostra13.universalimageloader.core.ImageLoader;

import de.hdodenhof.circleimageview.CircleImageView;
import space.example.myapplication.R;
import space.example.myapplication.Util.FirebaseMethods;
import space.example.myapplication.Util.UniversalImageLoader;
import space.example.myapplication.dialogs.ConfirmPasswordDialog;
import space.example.myapplication.models.User;
import space.example.myapplication.models.UserAccountSetting;
import space.example.myapplication.models.UserSetting;

public class EditProfileFragment extends Fragment implements ConfirmPasswordDialog.OnConfirmPasswordListener{

    @Override
    public void onConfirmPassword(String password) {
        Log.d(TAG , "onConfirmPassword: got the password" + password);
    }
    private static final String TAG = "EditProfileFragment";



    //firebase
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference myRef;
    private FirebaseMethods mFirebaseMethods;
    private String userID;

    //EditProfile Fragment widgets
    private EditText mDisplayName, mUserName,mWebsite, mDescription,mEmail,mPhoneNumber;
    private TextView mChangeProfilePhoto;
    private CircleImageView mProfilePhoto;

    //vars
    private UserSetting mUserSetting;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_editprofile, container, false);
        mProfilePhoto = (CircleImageView) view.findViewById(R.id.profile_photo);
        mDisplayName = (EditText)view.findViewById(R.id.display_name);
        mUserName = (EditText)view.findViewById(R.id.username);
        mWebsite = (EditText)view.findViewById(R.id.website);
        mDescription = (EditText)view.findViewById(R.id.description);
        mEmail = (EditText)view.findViewById(R.id.email);
        mPhoneNumber = (EditText) view.findViewById(R.id.phone_number);
        mChangeProfilePhoto = (TextView) view.findViewById(R.id.changeProfilePhoto);
        mFirebaseMethods = new FirebaseMethods(getActivity());

        //setProfileImage();
        setupFirebaseAuth();

        //back arrow for navigation back to "ProfileActivity"
        ImageView backArrow = (ImageView)view.findViewById(R.id.backArrow);
        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: navigation back to ProfileActivity");
                getActivity().finish();
            }
        });
        ImageView checkmark = (ImageView) view.findViewById(R.id.saveChanges);
        checkmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick:attempting to save changes ");
                saveProfileSetting();
            }
        });


        return view;
    }
    /**
     * Retrieves the data contained in the widgets and submits it to the database
     * Before donig so it chekcs to make sure the username chosen is unqiue
     */
    private void saveProfileSetting(){
        final String displayName = mDisplayName.getText().toString();
        final String username = mUserName.getText().toString();
        final String website = mWebsite.getText().toString();
        final String description  = mDescription.getText().toString();
        final String email = mEmail.getText().toString();
        final long  phoneNumber = Long.parseLong(mPhoneNumber.getText().toString());


        // case1: if the user made a change to their username
        if (!mUserSetting.getUser().getUsername().equals(username)) {

            checkUsernameExists(username);
        }
        //case2: if the user made a change to their email
        if (!mUserSetting.getUser().getEmail().equals(email)) {

            //step1) Reauthenticate
            //     -Confirm the password and email
            ConfirmPasswordDialog dialog = new ConfirmPasswordDialog();
            dialog.show(getChildFragmentManager() , getString(R.string.confirm_password_dialog));
            dialog.setTargetFragment(EditProfileFragment.this, 1);

            //step2) check if the email already is registered
            //      -fetchProvidersForEmail(String Email)
            //step3) change the Email
            //       - submit the new email to the database and authentication
        }
    }

    /**
     * Check is @param usrname already exist in teh database
     * @param username
     */
    private void checkUsernameExists(final String username) {
        Log.d(TAG, "checkUsernameExists: Checking if " + username + "already exists");

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        Query query = reference
                .child(getString(R.string.dbname_users))
                .orderByChild(getString(R.string.field_username))
                .equalTo(username);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if(!dataSnapshot.exists()){
                    //add the username
                    mFirebaseMethods.updateUsername(username);
                    Toast.makeText(getActivity(), "saved username.", Toast.LENGTH_SHORT).show();
                }
                for (DataSnapshot singleSnapshot: dataSnapshot.getChildren()){
                    if (singleSnapshot.exists() ){
                        Log.d(TAG, "checkIfUsernameExists: FOUND A MATCH " + singleSnapshot.getValue(User.class).getUsername());
                        Toast.makeText(getActivity(), "That username already exists.", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void setProfileWidgets(UserSetting userSettings){
        Log.d(TAG, "setProfileWidgets: setting widgets with data retrieving from firebase database: " + userSettings.toString());
        Log.d(TAG, "setProfileWidgets: setting widgets with data retrieving from firebase database: " + userSettings.getUser().getEmail());
        Log.d(TAG, "setProfileWidgets: setting widgets with data retrieving from firebase database: " + userSettings.getUser().getPhone_number());

        mUserSetting = userSettings;
        //User user = userSettings.getUser();
        UserAccountSetting settings = userSettings.getSetting();
        UniversalImageLoader.setImage(settings.getProfile_photo(), mProfilePhoto, null, "");
        mDisplayName.setText(settings.getDisplay_name());
        mUserName.setText(settings.getUsername());
        mWebsite.setText(settings.getWebsite());
        mDescription.setText(settings.getDescription());
        mEmail.setText(userSettings.getUser().getEmail());
        mPhoneNumber.setText(String.valueOf(userSettings.getUser().getPhone_number()));

    }

    /*
    ----------------------------------FireBase------------------------------
     */
    /**
     * Setup the firebase auth object
     */
    private void setupFirebaseAuth(){
        Log.d(TAG, "setupFirebaseAuth: setting up firebase auth.");

        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference();
        userID = mAuth.getCurrentUser().getUid();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();


                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
                // ...
            }
        };


        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                //retrieve user information from the database
                setProfileWidgets(mFirebaseMethods.getUserSetting(dataSnapshot));

                //retrieve images for the user in question

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener !=null){
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }


}

