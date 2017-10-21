package iitbombay.code_catalyst.com.insti20;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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

import static android.app.ProgressDialog.show;

/**
 * Created by charith on 1/10/17.
 */
class Post {

    private Float rating;
    private Integer no_of_people;
    public Map<String, Boolean> stars = new HashMap<>();



    public Post() {
        // Default constructor required for calls to DataSnapshot.getValue(Post.class)
    }

    Post(Float rat, Integer no) {
        this.rating = rat;
        this.no_of_people=no;
    }

    Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("Current_Rating", rating);
        result.put("No_of_people", no_of_people);
        return result;
    }

};
public class Tab2Rate extends Fragment{
    int hostel_no;
    private String uid;
    public void instantiate(int i){
        hostel_no=i;
        //getting hostel_no from the activity.
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.tab2rate, container, false);
        Button location = rootView.findViewById(R.id.button);
        RadioGroup rg= rootView.findViewById(R.id.like_dislike_radio);

        Bundle bundle=getActivity().getIntent().getExtras();
        uid=bundle.getString("uid");

        Toast.makeText(getActivity(),uid,Toast.LENGTH_SHORT).show();


        final Integer[] a = {-1};
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener(){


            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                RadioButton rb= rootView.findViewById(i);
                if (R.id.like_radio == rb.getId()){
                    a[0] =1;
                }
                else if(R.id.Dislike_radio == rb.getId()){
                    a[0]=0;
                }
            }
        });
        location.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
               // Toast.makeText(getActivity(), "hello", Toast.LENGTH_SHORT).show();
                DatabaseReference ref = FirebaseDatabase.getInstance().getReferenceFromUrl("https://code-catalyst-asc.firebaseio.com/Mess_Repo");
                String h_name;
                if (hostel_no < 10) h_name = "Hostel0" + hostel_no;
                else h_name = "Hostel" + hostel_no;
                DatabaseReference hostel_ref = ref.child(h_name);


                if(a[0]!=-1) {
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
                }
                if(a[0]==-1)
                {
                    Toast.makeText(getActivity(),"Please Tell us wheather you like it or not",Toast.LENGTH_LONG).show();
                }
                else if(a[0]==1)
                {
                    final DatabaseReference like_ref = hostel_ref.child("Likes");
                    like_ref.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            Float curr=dataSnapshot.getValue(Float.class);
                            curr=curr+1;
                            like_ref.setValue(curr);
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

                }
                else if(a[0]==0){
                    final DatabaseReference dislike_ref = hostel_ref.child("Dislikes");
                    dislike_ref.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            Float curr=dataSnapshot.getValue(Float.class);
                            curr=curr+1;
                            dislike_ref.setValue(curr);
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }


            }

        });
        return rootView;
    }
}