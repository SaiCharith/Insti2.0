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
                DatabaseReference ref = FirebaseDatabase.getInstance().getReferenceFromUrl("https://code-catalyst-asc.firebaseio.com/Mess_Repo");

                String h_name;
                if (hostel_no < 10) h_name = "Hostel0" + hostel_no;
                else h_name = "Hostel" + hostel_no;
                final DatabaseReference hostel_ref = ref.child(h_name);

                final DatabaseReference info_ref=hostel_ref.child("info");

                final DatabaseReference user_info_ref=info_ref.child(uid);

                final Float[] prate = {0f};
                final int[] plike = {0};
                final int[] pdislike = {0};
                final int[] exist = {0};

                info_ref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        if(dataSnapshot.hasChild(uid)) {
                            prate[0] = dataSnapshot.child(uid).child("rating").getValue(Float.class);
                            plike[0] = dataSnapshot.child(uid).child("like").getValue(Integer.class);
                            pdislike[0] = dataSnapshot.child(uid).child("dislike").getValue(Integer.class);
                            exist[0]=1;
                        }

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

                if(a[0]==-1)
                {
                    Toast.makeText(getActivity(),"Please Tell us wheather you like it or not",Toast.LENGTH_LONG).show();
                }

                else {
                    final DatabaseReference rating_ref = hostel_ref.child("Rating");

                    hostel_ref.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            Float curr = dataSnapshot.child("Rating").child("Current_Rating").getValue(Float.class);
                            Integer numb = dataSnapshot.child("Rating").child("No_of_people").getValue(Integer.class);
                            RatingBar r = (RatingBar) rootView.findViewById(R.id.ratingBar);
                            Float my_rate = r.getRating();
                            r.setRating(0f);
                            Float new_curr_rating = (my_rate-prate[0] + curr * numb) / (numb + 1-exist[0]);
                            Post post = new Post(new_curr_rating, numb + 1 - exist[0]);
                            Map<String, Object> postValues = post.toMap();
                            rating_ref.updateChildren(postValues);
                            DatabaseReference user_rating=user_info_ref.child("rating");
                            user_rating.setValue(my_rate);

                            final DatabaseReference like_ref = hostel_ref.child("Likes");
                            final DatabaseReference dislike_ref = hostel_ref.child("Dislikes");

                            DatabaseReference user_like=user_info_ref.child("like");
                            DatabaseReference user_dislike=user_info_ref.child("dislike");

                            if(a[0]==1){

                                        int curr_like=dataSnapshot.child("Likes").getValue(int.class);
                                        curr_like=curr_like+1-plike[0];
                                        like_ref.setValue(curr_like);

                                        user_like.setValue(1);
                                        user_dislike.setValue(0);
                                        if(exist[0]==1){

                                                    int curr1=dataSnapshot.child("Dislikes").getValue(int.class);
                                                    if(plike[0]==0) {
                                                        curr1--;
                                                    }
                                                    dislike_ref.setValue(curr1);
                                                }


                                    }


                            else {

                                        int curr_dislike=dataSnapshot.child("Dislikes").getValue(int.class);
                                        curr_dislike=curr_dislike+1-pdislike[0];
                                        dislike_ref.setValue(curr_dislike);

                                        user_like.setValue(0);
                                        user_dislike.setValue(1);
                                        if(exist[0]==1){

                                                    int curr1=dataSnapshot.child("Likes").getValue(int.class);
                                                    if(pdislike[0]==0) {
                                                        curr1--;
                                                    }
                                                    like_ref.setValue(curr1);
                                                }

                            }
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