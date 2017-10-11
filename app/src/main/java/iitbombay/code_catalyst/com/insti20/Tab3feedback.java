package iitbombay.code_catalyst.com.insti20;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by charith on 1/10/17.
 */

class FeedbackInput {

    public String Name;
    public String Roll_Number;
    public String Feedback;

    public FeedbackInput(){

    }

    public FeedbackInput(String name, String roll, String feed) {
        this.Name = name;
        this.Roll_Number = roll;
        this.Feedback = feed;
    }

};

public class Tab3feedback extends Fragment {

   // private DatabaseReference DatabaseReference;
    private EditText editTextName, editTextRoll, editTextFeed;
    private Button buttonSubmit;
    int hostel_no;
    public void instantiate(int i){
        hostel_no=i;
        //getting hostel_no from the activity.
    }


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.tab3feedback, container, false);


        editTextName = (EditText) rootView.findViewById(R.id.editTextName);
        editTextRoll = (EditText) rootView.findViewById(R.id.editTextRoll);
        editTextFeed = (EditText) rootView.findViewById(R.id.editTextFeed);
        buttonSubmit = (Button) rootView.findViewById(R.id.buttonSubmit);

        //adding listener to button
        buttonSubmit.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick (View view){


                //Tab3feedback.this.saveFeedbackInput();

              //  private void saveFeedbackInput(){

                String name = editTextName.getText().toString().trim();
                String roll = editTextRoll.getText().toString().trim();
                String feed = editTextFeed.getText().toString().trim();

                DatabaseReference ref = FirebaseDatabase.getInstance().getReferenceFromUrl("https://code-catalyst-asc.firebaseio.com/Mess_Repo");
                String h_name;
                if (hostel_no < 10) h_name = "Hostel0" + hostel_no;
                else h_name = "Hostel" + hostel_no;
                DatabaseReference hostel_ref = ref.child(h_name);
                DatabaseReference feedback_ref = hostel_ref.child("Feedback");
                final DatabaseReference roll_ref = feedback_ref.child(roll);



                FeedbackInput feedbackInput = new FeedbackInput(name, roll, feed);
                    //FirebaseUser user = getActivity().firebaseAuth.getCurrentUser();

                roll_ref.setValue(feedbackInput);
                Toast.makeText(getActivity(), "FeedBack Submitted", Toast.LENGTH_SHORT).show();

             //   }

            }
        });



        return rootView;
    }








}
