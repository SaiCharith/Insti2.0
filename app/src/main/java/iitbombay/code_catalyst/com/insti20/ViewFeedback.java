package iitbombay.code_catalyst.com.insti20;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;


public class ViewFeedback extends AppCompatActivity {

    int hostel_no;
    public void instantiate(int i){
        hostel_no=i;
        //getting hostel_no from the activity.
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_feedback);


        DatabaseReference ref = FirebaseDatabase.getInstance().getReferenceFromUrl("https://code-catalyst-asc.firebaseio.com/Mess_Repo");
        String h_name;
        if (hostel_no < 10) h_name = "Hostel0" + hostel_no;
        else h_name = "Hostel" + hostel_no;
        DatabaseReference hostel_ref = ref.child(h_name);
        final DatabaseReference feedback_ref = hostel_ref.child("Feedback");

        feedback_ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



    }
}
