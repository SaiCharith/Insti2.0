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

/**
 *  This is 3rd Fragment of the tabbed-activity. This fragment is platform for students to give their
 *  feedback and also view everyone's feedback. Students fill thier details and feedback in different
 *  EditText's which is further added to Firebase database and then restored in view feedback section.
 *  Students can also add photos alongwith feedback which is stored under Firebase Storage.
 *  In the parent class while initiating this Fragment the function called instantiate must be called, which
 *  instantiates the hostel_no which is required for getting information of secy and warden for respective hostels.
 */

public class Tab3feedback extends Fragment {

   // private DatabaseReference DatabaseReference;
    /**
     * to hold the name of student.
     */
    private EditText editTextName;

    /**
     * to hold the name of roll number of student.
     */
    private EditText editTextRoll;

    /**
     * to hold the description for feedback.
     */
    private EditText editTextDescription;

    /**
     * to hold the feedback.
     */
    private EditText editTextFeed;

    /**
     * button to submit the feedback.
     */
    private Button buttonSubmit;

    /**
     * button to view all feedbacks.
     */
    private Button buttonView;

    /**
     * button to add photo alongwith feedback.
     */
    private Button buttonadd;

    /**
     * Reference for Firebase Storage where all the uploaded images are stored.
     */
    private StorageReference mStorage;

    /**
     * Progress dialog box which will appear while uploading photo.
     */
    private ProgressDialog mProgressDialog;

    /**
     * request code for startActivityForResult
     */
    private static final int GALLERY_INTENT = 2;

    /**
     * to check whether image is uploaded or not
     */
    public boolean b = false;

    /**
     * to hold url of uploaded image.
     */
    Uri image_url;

    /**
     * to hold the url in the form of string.
     */
    String image_id;

    /**
     * randomly generated index-key at which feedback is added.
     */
    String push_id;

    /**
     * randomly generated index-key at which image is added.
     */
    String push_id1;

    /**
     * to hold the value of current hostel number.
     */
    int hostel_no;

    /**
     * gets hostel_no and updates it.
     * @param i represents hostel_no
     */
    public void instantiate(int i){
        hostel_no=i;
        //getting hostel_no from the activity.
    }

    /**
     * This method is called when the this Fragment gets created.
     * All the fields filled by student are uploaded at particular sub-branch of database with the
     * help of FeedbackInput class. On clicking add photo button, new intent is called which allows us
     * to pick images from our phone's gallery. This button will start a new startActivityForResult
     * for uploading selected image.
     * @param inflater to fill the fragment with tab1info.xml
     * @param container to get the view
     * @param savedInstanceState not useful
     * @return rootView
     */
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.tab3feedback, container, false);

        mStorage = FirebaseStorage.getInstance().getReference();

        editTextName = rootView.findViewById(R.id.editTextName);
        editTextRoll = rootView.findViewById(R.id.editTextRoll);
        editTextDescription = rootView.findViewById(R.id.editTextDescription);
        editTextFeed = rootView.findViewById(R.id.editTextFeed);
        buttonSubmit = rootView.findViewById(R.id.buttonSubmit);
        buttonView = rootView.findViewById(R.id.view_feedback);
        buttonadd = rootView.findViewById(R.id.buttonAdd);

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

                    if(b == false){
                        image_id ="";
                        b = true;
                    }else{
                        image_id = image_url.toString();
                        b = false;
                    }

                    push_id = feedback_ref.push().getKey();
                    FeedbackInput feedbackInput = new FeedbackInput(image_id, name, roll, description, feed);
                    feedback_ref.child(push_id).setValue(feedbackInput);
                    Toast.makeText(getActivity(), "Feedback Submitted", Toast.LENGTH_SHORT).show();

                    editTextName.setText("");
                    editTextRoll.setText("");
                    editTextDescription.setText("");
                    editTextFeed.setText("");

                }
            }
        });

        return rootView;
    }

    /**
     * This method is called when Add photo button is clicked.
     * If request code is matched and operation is successfull then the image is uploaded under specific
     * branch in Firebase Storage. URl of that images is stored im image_url variable which is then passes to
     * FeedbackInput class. This URL is then extracted while viewing feedback.
     * @param requestCode will start the activity only if matches desired code
     * @param resultCode to check whether the operation is successfull or not
     * @param data carries the result data
     * @return rootView
     */
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
                    image_url = taskSnapshot.getDownloadUrl();
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
