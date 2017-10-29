package iitbombay.code_catalyst.com.insti20;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * This class adds all the feedback details to the list and then calls CustomAdapter3 which in turn
 * creates listview.
 */

public class ViewFeedback extends AppCompatActivity {

    int hostel_no;
    String h_name;
    ListView feedback_Listview;
    ArrayList<FeedbackInput> feedbackList;
    DatabaseReference ref = FirebaseDatabase.getInstance().getReferenceFromUrl("https://code-catalyst-asc.firebaseio.com/Mess_Repo");

    /**
     * This takes all the necessary details from database and adds them to list. CustomAdapter3 is also called over
     * here and this converts the list to listview.
     */
    @Override
    protected void onStart() {
        super.onStart();
        Bundle bundle = getIntent().getExtras();
        hostel_no = bundle.getInt("hostel_no");     //getting hostel number from previous activity
        if (hostel_no < 10) h_name = "Hostel0" + hostel_no;
        else h_name = "Hostel" + hostel_no;
        DatabaseReference hostel_ref = ref.child(h_name);
        final DatabaseReference feedback_ref = hostel_ref.child("Feedback");  //Reference to database

        feedback_ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                feedbackList.clear();
                for (DataSnapshot feedbackSnapshot: dataSnapshot.getChildren()){                    //iterating through childern of  Feedback
                    FeedbackInput feedbackInput = feedbackSnapshot.getValue(FeedbackInput.class);   //getting data from datbse

                    feedbackList.add(feedbackInput);                                                //saving data as list
                }
                CustomAdapter3 adapter = new CustomAdapter3(ViewFeedback.this, feedbackList);       //setting the adapter.
                feedback_Listview.setAdapter(adapter);                                              //making the listview.
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });


    }

    /**
     * initialising the feedbacklist.
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_feedback_list);


        feedback_Listview= findViewById(R.id.feedback_list);
        feedbackList = new ArrayList<>();
    }
}
