package iitbombay.code_catalyst.com.insti20;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RatingBar;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

/**
 * Created by charith on 1/10/17.
 */
class Post {

    public Float rating;
    public Integer no_of_people;
    public Map<String, Boolean> stars = new HashMap<>();

    public Post() {
        // Default constructor required for calls to DataSnapshot.getValue(Post.class)
    }

    public Post(Float rat,Integer no) {
        this.rating = rat;
        this.no_of_people=no;
    }

    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("Current_Rating", rating);
        result.put("No_of_people", no_of_people);
        return result;
    }

};
public class Tab2Rate extends Fragment{
    int hostel_no;
    public void instantiate(int i){
        hostel_no=i;
        //getting hostel_no from the activity.
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.tab2rate, container, false);
//        RadioButton rb = (RadioButton) getActivity().findViewById(R.id.like_radio);
//        View.OnClickListener first_radio_listener = new View.OnClickListener(){
//            public void onClick(View v) {
//                ImageButton b1=(ImageButton) getActivity().findViewById(R.id.button6);
//                ImageButton b2=(ImageButton) getActivity().findViewById(R.id.button7);
//                b1.setAlpha(1f);
//                b2.setAlpha(1f);
//
//            }
//        };
//        rb.setOnClickListener(first_radio_listener);
        Button location = (Button) rootView.findViewById(R.id.button);
        //RadioGroup

        location.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "hello", Toast.LENGTH_SHORT).show();
                DatabaseReference ref = FirebaseDatabase.getInstance().getReferenceFromUrl("https://code-catalyst-asc.firebaseio.com/Mess_Repo");
                String h_name;
                if (hostel_no < 10) h_name = "Hostel0" + hostel_no;
                else h_name = "Hostel" + hostel_no;
                DatabaseReference hostel_ref = ref.child(h_name);
                final DatabaseReference rating_ref = hostel_ref.child("Rating");
                DatabaseReference curr_rating = rating_ref.child("Current_Rating");
                rating_ref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Float curr = dataSnapshot.child("Current_Rating").getValue(Float.class);
                        Integer numb = dataSnapshot.child("No_of_people").getValue(Integer.class);
                        RatingBar r = (RatingBar) rootView.findViewById(R.id.ratingBar);
                        Float my_rate = r.getRating();
                        r.setRating(0f);
                        Float new_curr_rating = (my_rate + curr * numb) / (numb + 1);
                        Post post = new Post(new_curr_rating, numb + 1);
                        Map<String, Object> postValues = post.toMap();
                        rating_ref.updateChildren(postValues);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
//                final Float[] currrating = new Float[1];
//                curr_rating.addValueEventListener(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(DataSnapshot snapshot) {
//                        currrating[0] = snapshot.getValue(Float.class);  //prints "Do you have data? You'll love Firebase."
//                    }
//                    @Override
//                    public void onCancelled(DatabaseError databaseError) {
//                    }
//                });
//               // Toast.makeText(getActivity(), String.valueOf(currrating[0]),Toast.LENGTH_SHORT).show();
//
//                DatabaseReference no_of_people=rating_ref.child("No_of_people");
//                final Integer[] no_of = new Integer[1];
//                curr_rating.addValueEventListener(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(DataSnapshot snapshot) {
//                        no_of[0] = snapshot.getValue(Integer.class);  //prints "Do you have data? You'll love Firebase."
//                    }
//                    @Override
//                    public void onCancelled(DatabaseError databaseError) {
//                    }
//                });
//                RatingBar r= (RatingBar) rootView.findViewById(R.id.ratingBar);
//               Float my_rate=r.getRating();
//               // Float new_curr_rating=(my_rate+currrating[0]*no_of[0])/(no_of[0]+1);
////                Post post = new Post(new_curr_rating,no_of[0]+1);
////                Map<String, Object> postValues = post.toMap();
//                //rating_ref.updateChildren(postValues);
//            }
//        });



            }

        });
        return rootView;
    }
}