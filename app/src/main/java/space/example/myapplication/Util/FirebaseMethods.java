package space.example.myapplication.Util;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import space.example.myapplication.R;
import space.example.myapplication.models.User;
import space.example.myapplication.models.UserAccountSetting;
import space.example.myapplication.models.UserSetting;

public class FirebaseMethods {

    private static final String TAG = "FirebaseMethods";

    //firebase
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private String userID;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference myRef;
    private Context mContext;

    public FirebaseMethods(Context context) {
        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference();
        mContext = context;

        if (mAuth.getCurrentUser() != null) {
            userID = mAuth.getCurrentUser().getUid();
        }
    }

    public boolean checkIfUsernameExists(String username, DataSnapshot datasnapshot) {
        Log.d(TAG, "checkIfUsernameExists: checking if " + username + " already exists.");

        User user = new User();

        for (DataSnapshot ds : datasnapshot.child(userID).getChildren()) {
            Log.d(TAG, "checkIfUsernameExists: datasnapshot: " + ds);

            user.setUsername(ds.getValue(User.class).getUsername());
            Log.d(TAG, "checkIfUsernameExists: username: " + user.getUsername());

            if (StringManipulation.expandUsername(user.getUsername()).equals(username)) {
                Log.d(TAG, "checkIfUsernameExists: FOUND A MATCH: " + user.getUsername());
                return true;
            }
        }
        return false;
    }

    /**
     * Register a new email and password to Firebase Authentication
     *
     * @param email
     * @param password
     * @param username
     */
    public void registerNewEmail(final String email, String password, final String username) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "createUserWithEmail:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Toast.makeText(mContext, R.string.auth_failed,
                                    Toast.LENGTH_SHORT).show();
                        } else if (task.isSuccessful()) {
                            //send verification email
                            sendVerificationEmail();
                            userID = mAuth.getCurrentUser().getUid();
                            Log.d(TAG, "onComplete: Authstate changed: " + userID);
                        }

                    }
                });
    }

    public void sendVerificationEmail() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (user != null) {
            user.sendEmailVerification()
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {

                            } else {
                                Toast.makeText(mContext, "could send verification email", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }

    /**
     * add information to the users nodes
     * add information to the user_account_setting node
     *
     * @param email
     * @param username
     * @param description
     * @param website
     * @param profile_photo
     */
    public void addNewUser(String email, String username, String description, String website, String profile_photo) {
        User user = new User(userID, 1, email, StringManipulation.condenseUsername(username));

        myRef.child(mContext.getString(R.string.dbname_users))
                .child(userID)
                .setValue(user);

        UserAccountSetting setting = new UserAccountSetting(
                description,
                username,
                0,
                0,
                0,
                profile_photo,
                StringManipulation.condenseUsername(username),
                website
        );
        myRef.child(mContext.getString(R.string.dbname_user_account_setting))
                .child(userID)
                .setValue(setting);
    }

    public UserSetting getUserSetting(DataSnapshot dataSnapshot) {
        Log.d(TAG, "getUserAccountSetting: retvieving account user account setting from firebase");

        UserAccountSetting setting = new UserAccountSetting();
        User user = new User();

        for (DataSnapshot ds : dataSnapshot.getChildren()) {

            //user_account_setting node
            if (ds.getKey().equals(mContext.getString(R.string.dbname_user_account_setting))) {
                Log.d(TAG, "getUserAccountSetting: datasnapshot" + ds);

                try {

                    setting.setDisplay_name(
                            ds.child(userID)
                                    .getValue(UserAccountSetting.class)
                                    .getDisplay_name()
                    );
                    setting.setUsername(
                            ds.child(userID)
                                    .getValue(UserAccountSetting.class)
                                    .getUsername()
                    );
                    setting.setWebsite(
                            ds.child(userID)
                                    .getValue(UserAccountSetting.class)
                                    .getWebsite()
                    );
                    setting.setProfile_photo(
                            ds.child(userID)
                                    .getValue(UserAccountSetting.class)
                                    .getProfile_photo()
                    );
                    setting.setPosts(
                            ds.child(userID)
                                    .getValue(UserAccountSetting.class)
                                    .getPosts()
                    );
                    setting.setFollowing(
                            ds.child(userID)
                                    .getValue(UserAccountSetting.class)

                                    .getFollowing()
                    );
                    Log.d(TAG, "getUserAccountSetting: retrieved user_accont_setting information " + setting.toString());
                } catch (NullPointerException e) {
                    Log.e(TAG, "getUserAccountSetting:NullPointerException " + e.getMessage());
                }
                //user node
                if (ds.getKey().equals(mContext.getString(R.string.dbname_users))) {
                    Log.d(TAG, "getUserAccountSetting: datasnapshot" + ds);
                }
                user.setUsername(
                        ds.child(userID)
                                .getValue(User.class)
                                .getUsername()
                );
                user.setEmail(
                        ds.child(userID)
                                .getValue(User.class)
                                .getEmail()
                );
                user.setPhone_number(
                        ds.child(userID)
                                .getValue(User.class)
                                .getPhone_number()
                );
                user.setUser_id(
                        ds.child(userID)
                                .getValue(User.class)
                                .getUser_id()
                );

                Log.e(TAG, "getUserAccountSetting: retrieved users information " + user.toString());
            }
        }

        return  new UserSetting(user,setting);
    }
}