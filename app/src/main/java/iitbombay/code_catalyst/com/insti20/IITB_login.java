package iitbombay.code_catalyst.com.insti20;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.widget.EdgeEffectCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

/**
 * This is The activity called from the {@link MainActivity} which takes id and password from user and verifies it and send us to {@link Mess_Secy_choice} or {@link After_login} depending on the user
 */
public class IITB_login extends AppCompatActivity {
    /**
     * variable to store the username which the user types
     */
    private EditText username; //Get Username
    /**
     * variable to store the password which the user types
     */
    private EditText password;  //Get Password
    /**
     * An Object of mAuth(from Firebase) to verify if the user is signed in or not
     */
    private FirebaseAuth mAuth; //Authentication object
    /**
     * An object to Implement the Login Button
     */
    private ImageButton mLoginButton;   //Login Button
    /**
     * A listener attached to mAuth to see if Authentication mode is changed or not
     */
    private FirebaseAuth.AuthStateListener mAuthListener; //Authentication Listeners

    /**
     * When The acticity is created this function initializes all the private variable with required values finding them by id from resources
     * @param savedInstanceState
     */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iitb_login);
        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        mLoginButton = (ImageButton) findViewById(R.id.loginButton);
        mAuth =FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if(firebaseAuth.getCurrentUser()!=null){

                    Toast.makeText(IITB_login.this,firebaseAuth.getCurrentUser().getEmail(), Toast.LENGTH_SHORT).show();

                    boolean b=true;
                    String user=firebaseAuth.getCurrentUser().getEmail();
                    for(int i=0;i<16;i++) {    //cheching whwether user is a messsecy or not
                        String string= getResources().getStringArray(R.array.messsecys)[i];
                        if (user.equals(string)) {
                            b=false;
                            Intent intent = new Intent(IITB_login.this, Mess_Secy_choice.class);
                            intent.putExtra("hostel_no",i+1);
                            startActivity(intent);  //Satrting Activity Mess_Secy_chioce
                        }
                    }
                    if(b){          //if user is not me-ssecy
                        Intent intent = new Intent(IITB_login.this, After_login.class);
                        intent.putExtra("hostel_no",0);
                        startActivity(intent);  //Starting After_login Activity and passing 0 indicating that user is not mess-secy

                    }
                }

            }
        };
        mLoginButton.setOnClickListener(new View.OnClickListener() {
            /**
             * Method start_Sign_In() is called as soon as button is clicked
             * @param view
             */
            @Override
            public void onClick(View view) {
                start_Sign_In();
            }
        });

    }

    /**
     * Just attaches the mAuthListener to mAuth as activity is started
     */
    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    /**
     * Checks if the user has entered the password and username and toasts the required text if not. Once entered it verifies the user and password and update the Authentication state which is detected by mAuthListener and we are intended to the desired activity
     */
    private void start_Sign_In(){
        String User_name=username.getText().toString();
        String pass_word=password.getText().toString();
        if(TextUtils.isEmpty(User_name)||TextUtils.isEmpty(pass_word)){
            Toast.makeText(IITB_login.this, "Fields are Empty", Toast.LENGTH_SHORT).show(); //notifying user of empty fields.
        }
        else {
            mAuth.signInWithEmailAndPassword(User_name, pass_word).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (!task.isSuccessful()) {
                        Toast.makeText(IITB_login.this, "Authentication failed", Toast.LENGTH_SHORT).show(); //notifying user of invalid credentials
                    }
                }
            });
        }
    }
}
