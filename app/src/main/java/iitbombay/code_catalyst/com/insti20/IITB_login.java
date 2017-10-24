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

public class IITB_login extends AppCompatActivity {
    private EditText username; //Get Username
    private EditText password;  //Get Password
    private FirebaseAuth mAuth; //Authentication object
    private ImageButton mLoginButton;   //Login Button
    private FirebaseAuth.AuthStateListener mAuthListener; //Authentication Listeners
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

//                    String uid=firebaseAuth.getCurrentUser().getUid();

                    boolean b=true;
                    String user=firebaseAuth.getCurrentUser().getEmail();
                    for(int i=0;i<16;i++) {
                        String string= getResources().getStringArray(R.array.messsecys)[i];
                        if (user.equals(string)) {
                            b=false;
                            Intent intent = new Intent(IITB_login.this, Mess_Secy_choice.class);
                            intent.putExtra("hostel_no",i+1);
                            startActivity(intent);
                        }
                    }
                    if(b){
//                        Intent intent=new Intent(IITB_login.this,HostelActivity.class);
//                        intent.putExtra("uid","uid");
                        startActivity(new Intent(IITB_login.this,After_login.class));
                    }
                }

            }
        };
        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                start_Sign_In();
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    private void start_Sign_In(){
        String User_name=username.getText().toString();
        String pass_word=password.getText().toString();
        if(TextUtils.isEmpty(User_name)||TextUtils.isEmpty(pass_word)){
            Toast.makeText(IITB_login.this, "Fields are Empty", Toast.LENGTH_SHORT).show();
        }
        else {
            mAuth.signInWithEmailAndPassword(User_name, pass_word).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (!task.isSuccessful()) {
                        Toast.makeText(IITB_login.this, "Authentication failed", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}
