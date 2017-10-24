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
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by charith on 1/10/17.
 */

public class Tab3feedback extends Fragment {

   // private DatabaseReference DatabaseReference;
    private EditText editTextName, editTextRoll,editTextDescription, editTextFeed;
    private Button buttonSubmit;
    private Button buttonView;
    private Button buttonadd;
    private StorageReference mStorage;
    private ProgressDialog mProgressDialog;
    private static final int GALLERY_INTENT = 2;
    public boolean b = false;
//    Uri uri;
    Uri image_uri;
    String image_id;
    String push_id,push_id1;
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
                intent.putExtra("hostel_no",hostel_no);
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

                    if(b == false){
                        //id = feedback_ref.push().getKey();
                        image_id ="";
                        b = true;
                    }else{
                        image_id = image_uri.toString();
                        b = false;
                    }



                    push_id = feedback_ref.push().getKey();
                    //final FeedbackInput feedbackInput;
                    FeedbackInput feedbackInput = new FeedbackInput(image_id, name, roll, description, feed);
                    //FirebaseUser user = getActivity().firebaseAuth.getCurrentUser();


                    feedback_ref.child(push_id).setValue(feedbackInput);

                    Toast.makeText(getActivity(), "Feedback Submitted", Toast.LENGTH_SHORT).show();
                    editTextName.setText("");
                    editTextRoll.setText("");
                    editTextDescription.setText("");
                    editTextFeed.setText("");
                    //b = true;
                    //   }
                }

            }
        });



        return rootView;
    }

    @SuppressWarnings("VisibleForTests")
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if(requestCode == GALLERY_INTENT && resultCode == getActivity().RESULT_OK){


            mProgressDialog.show();
            Uri uri = data.getData();
            DatabaseReference ref = FirebaseDatabase.getInstance().getReferenceFromUrl("https://code-catalyst-asc.firebaseio.com/Mess_Repo");
            String h_name;
            if (hostel_no < 10) h_name = "Hostel0" + hostel_no;
            else h_name = "Hostel" + hostel_no;
            final DatabaseReference hostel_ref = ref.child(h_name);
            //final DatabaseReference feedback_ref = hostel_ref.child("Feedback");
            push_id1 = hostel_ref.push().getKey();
            b = true;
            //StorageReference filepath = mStorage.child("photos").child(uri.getLastPathSegment());
            StorageReference filepath = mStorage.child(h_name).child(push_id1);
            filepath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Toast.makeText(getActivity(),"Image is uploaded",Toast.LENGTH_SHORT).show();

                    mProgressDialog.dismiss();
                    image_uri = taskSnapshot.getDownloadUrl();
                }
            })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                    double progress = ( 100 * taskSnapshot.getBytesTransferred())/taskSnapshot.getTotalByteCount();
                    mProgressDialog.setMessage("Uploading..."+(int)progress+"%");
                }
            });
        }
    }
}
