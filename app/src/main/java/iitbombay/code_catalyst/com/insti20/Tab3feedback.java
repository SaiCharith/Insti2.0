package iitbombay.code_catalyst.com.insti20;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by charith on 1/10/17.
 */

class FeedbackInput {

    public String ID;
    public String Name;
    public String Roll_Number;
    public String Description;
    public String Feedback;

    public FeedbackInput(){

    }

    public FeedbackInput(String id, String name, String roll, String description, String feed) {
        this.ID=id;
        this.Name = name;
        this.Roll_Number = roll;
        this.Description = description;
        this.Feedback = feed;
    }

};


public class Tab3feedback extends Fragment {

   // private DatabaseReference DatabaseReference;
    private EditText editTextName, editTextRoll,editTextDescription, editTextFeed;
    private Button buttonSubmit;
    private Button buttonView;
    private Button buttonadd;
    private StorageReference mStorage;
    private ProgressDialog mProgressDialog;
    private static final int GALLERY_INTENT = 2;
    String id;
    int hostel_no;
    public void instantiate(int i){
        hostel_no=i;
        //getting hostel_no from the activity.
    }


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.tab3feedback, container, false);

        //mStorage = FirebaseStorage.getInstance().getReferenceFromUrl("https://code-catalyst-asc.firebaseio.com/Mess_Repo");
        mStorage = FirebaseStorage.getInstance().getReference();

        editTextName = (EditText) rootView.findViewById(R.id.editTextName);
        editTextRoll = (EditText) rootView.findViewById(R.id.editTextRoll);
        editTextDescription = (EditText) rootView.findViewById(R.id.editTextDescription);
        editTextFeed = (EditText) rootView.findViewById(R.id.editTextFeed);
        buttonSubmit = (Button) rootView.findViewById(R.id.buttonSubmit);
        buttonView = (Button) rootView.findViewById(R.id.view_feedback);
        buttonadd = (Button) rootView.findViewById(R.id.buttonAdd);

        //adding listener to button
        buttonView.setOnClickListener(new View.OnClickListener(){

            @Override
                    public void onClick (View view){
                Intent intent = new Intent(getActivity(), ViewFeedback.class);
                startActivity(intent);
            }
        });

        mProgressDialog = new ProgressDialog(getActivity());

        buttonadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View view){
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");

                startActivityForResult(intent, GALLERY_INTENT);
            }
        });


        buttonSubmit.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick (View view){


                //Tab3feedback.this.saveFeedbackInput();

              //  private void saveFeedbackInput(){

                String name = editTextName.getText().toString().trim();
                String roll = editTextRoll.getText().toString().trim();
                String description = editTextDescription.getText().toString().trim();
                String feed = editTextFeed.getText().toString().trim();

                if(name.equals("") || roll.equals("") || description.equals("") || feed.equals("")){
                    Toast.makeText(getActivity(), "All Fields must be filled", Toast.LENGTH_SHORT).show();
                }else {

                    DatabaseReference ref = FirebaseDatabase.getInstance().getReferenceFromUrl("https://code-catalyst-asc.firebaseio.com/Mess_Repo");
                    String h_name;
                    if (hostel_no < 10) h_name = "Hostel0" + hostel_no;
                    else h_name = "Hostel" + hostel_no;
                    DatabaseReference hostel_ref = ref.child(h_name);
                    final DatabaseReference feedback_ref = hostel_ref.child("Feedback");
                    //  final DatabaseReference roll_ref = feedback_ref.child(""+x);


                    id = feedback_ref.push().getKey();
                    final FeedbackInput feedbackInput = new FeedbackInput(id, name, roll, description, feed);
                    //FirebaseUser user = getActivity().firebaseAuth.getCurrentUser();


                    feedback_ref.child(id).setValue(feedbackInput);

                    Toast.makeText(getActivity(), "Now you can add images", Toast.LENGTH_SHORT).show();
                    buttonadd.setVisibility(View.VISIBLE);

                    //   }
                }

            }
        });



        return rootView;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == GALLERY_INTENT && resultCode == getActivity().RESULT_OK){
            String h_name;
            if (hostel_no < 10) h_name = "Hostel0" + hostel_no;
            else h_name = "Hostel" + hostel_no;
            mProgressDialog.setMessage("Uploading...");
            mProgressDialog.show();
            Uri uri = data.getData();
            //StorageReference filepath = mStorage.child("photos").child(uri.getLastPathSegment());
            StorageReference filepath = mStorage.child(h_name).child(id);
            filepath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Toast.makeText(getActivity(),"Image is uploaded",Toast.LENGTH_SHORT).show();
                    mProgressDialog.dismiss();
                }
            });
        }
    }
}
