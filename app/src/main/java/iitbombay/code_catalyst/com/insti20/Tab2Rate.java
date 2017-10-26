package iitbombay.code_catalyst.com.insti20;

import android.app.Fragment;
import android.content.Intent;
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
 * This class is used by Tab2rate to hold and push values of rating ,people to firebasedatabase.
 * @author Code-Catalyst
 */
class Post {
    /**
     * holds rating
     */
    private Float rating;
    /**
     * Stores no of people.
     */
    private Integer no_of_people;

    /**
     * Default constructor required for calls to DataSnapshot.getValue(Post.class)
     */
    public Post() {
    }

    /**
     *
     * @param rat gets rating
     * @param no gets no of people
     */
    Post(Float rat, Integer no) {
        this.rating = rat;
        this.no_of_people=no;
    }

    /**
     *
     * @return hashmap with rating and number of people
     */

    Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("Current_Rating", rating);
        result.put("No_of_people", no_of_people);
        return result;
    }

}

/**
 *
 * This is 2nd Fragment of the Hostel-activity. This fragment enbles the user rate and like/dislike.
 * Rating is recoreded under ratebar and like/dislike is recoreded under radiobutton.
 *
 * Here we ensured that one user can rate only once. But this rating can be updated whenever user rates again.
 * This is done by storing the rating values in the firebasedatabase under Mess_repo/hostel/info/UID
 *
 *
 * @author Code-Catalyst
 */
public class Tab2Rate extends Fragment{
    /**
     * stores hostel number
     */
    int hostel_no;
    /**
     * UID is unique to each user which is used to store some specific details of user like his rating
     */
    private String uid;


    /**
     *
     * Get users rating, like/dislike and update the values to the firebasedatabase.
     *
     * @param inflater to fill the fragment with tab4menu.xml
     * @param container to get the view
     * @param savedInstanceState not useful
     * @return rootView
     */

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.rating_try, container, false);

        Button location = rootView.findViewById(R.id.button);
        RadioGroup rg= rootView.findViewById(R.id.like_dislike_radio);

        Bundle bundle=getActivity().getIntent().getExtras();
        uid=bundle.getString("uid");
        hostel_no=bundle.getInt("index");

        /**
         * a[0] is used as a marker.
         * a[0] = -1 or 0 or 1
         * -1 represents user did not rate
         *  0 represents dislike is selected
         *  1 represents like is selected
         */
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

        Button buttonanalysis = (Button) rootView.findViewById(R.id.Analysis);
        buttonanalysis.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick (View view){
                Intent intent = new Intent(getActivity(), TabbedActivity.class);
               // intent.putExtra("hostel_no",hostel_no);
                startActivity(intent);
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

                /**
                 * exist[0] is used as marker to know whether user has already rated or not.
                 */
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

                /**
                 * if user does not fill the fields and presses submit
                 */
                if(a[0]==-1)
                {
                    Toast.makeText(getActivity(),"Please Tell us wheather you like it or not",Toast.LENGTH_LONG).show();
                }

                /**
                 * updating the values at the firebasedatabse
                 */
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
                                /**
                                 * user likes
                                 */

                                        int curr_like=dataSnapshot.child("Likes").getValue(int.class);
                                        curr_like=curr_like+1-plike[0];
                                        like_ref.setValue(curr_like);

                                        user_like.setValue(1);
                                        user_dislike.setValue(0);
                                        if(exist[0]==1){
                                            /**
                                             * user already exists
                                             */

                                                    int curr1=dataSnapshot.child("Dislikes").getValue(int.class);
                                                    if(plike[0]==0) {
                                                        /**
                                                         * user disliked previously
                                                         */
                                                        curr1--;
                                                    }
                                                    dislike_ref.setValue(curr1);
                                                }


                                    }


                            else {
                                /**
                                 * user dislikes
                                 */

                                        int curr_dislike=dataSnapshot.child("Dislikes").getValue(int.class);
                                        curr_dislike=curr_dislike+1-pdislike[0];
                                        dislike_ref.setValue(curr_dislike);

                                        user_like.setValue(0);
                                        user_dislike.setValue(1);
                                        if(exist[0]==1){
                                            /**
                                             * user exists
                                             */

                                                    int curr1=dataSnapshot.child("Likes").getValue(int.class);
                                                    if(pdislike[0]==0) {
                                                        /**
                                                         * user liked previously
                                                         */
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