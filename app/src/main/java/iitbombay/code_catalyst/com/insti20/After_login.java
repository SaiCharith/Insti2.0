package iitbombay.code_catalyst.com.insti20;

import android.content.Intent;
import android.content.res.TypedArray;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class After_login extends AppCompatActivity {
    Button button;
    FirebaseAuth mAuth;
    FirebaseAuth.AuthStateListener mAuthListener;


    //sub-class to store to group all fields to be displayed in one unit.
    class objects{
        String Hostel_name;
        int hostel_no;
        float rating;
        int likes;
        int dislikes;
        int image_id;
        objects(int i){hostel_no=i;}

    }

    private static ArrayList<objects> l=new ArrayList<objects>();
    private CustomAdapter customAdapter = new CustomAdapter();

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_after_login);
        button=(Button) findViewById(R.id.logout);
        mAuth =FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener(){
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth){
                if(firebaseAuth.getCurrentUser() == null)
                {
                    startActivity(new Intent(After_login.this,MainActivity.class));
                }
            }

        };

        button.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                mAuth.signOut();
            }
        });

        TypedArray imgs = getResources().obtainTypedArray(R.array.Images);
        for(int i=0;i<16;i++){
            objects s= new objects(i+1);
            s.Hostel_name=getResources().getStringArray(R.array.Hostel_names)[i];
            s.image_id=imgs.getResourceId(i,-1);
            s.rating=(Float.parseFloat(getResources().getStringArray(R.array.Rating)[i]));
            s.likes=getResources().getIntArray(R.array.Likes)[i];
            s.dislikes=getResources().getIntArray(R.array.DisLikes)[i];
            l.add(s);

        }

        ListView listView = (ListView) findViewById(R.id.hostel_display);
        listView.setAdapter(customAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){


            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long L) {
                Intent intent = new Intent(After_login.this,HostelActivity.class);
                intent.putExtra("index",l.get(i).hostel_no); //passing hostel no to the upcoming activity.
                startActivity(intent);
                Toast.makeText(After_login.this, l.get(i).Hostel_name, Toast.LENGTH_SHORT).show();
            }
        });


     }
    //To hold view of each element in list view.
    //Used for optimization.
    //This avoids re-initializing of the view-holder contents.
    class viewHolder{
        ImageView imageview;
        TextView hostel_name;
        RatingBar rating_bar;
        TextView likes;
        TextView dislikes;

        viewHolder(View v){
            imageview = v.findViewById(R.id.imageView);
            hostel_name=v.findViewById(R.id.Hostel_name);
            rating_bar=v.findViewById(R.id.ratingBar);
            likes=v.findViewById(R.id.likes);
            dislikes=v.findViewById(R.id.dislikes);
        }
    }

    class CustomAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return 16;
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {

            viewHolder holder=null;

            if(view==null){
                view=getLayoutInflater().inflate(R.layout.each_hostel_display,null);
                holder=new viewHolder(view);
                view.setTag(holder);
            }

            else{
                holder= (viewHolder) view.getTag();
            }

            holder.imageview.setImageResource(l.get(i).image_id);
            holder.hostel_name.setText(l.get(i).Hostel_name);
            holder.rating_bar.setRating(l.get(i).rating);
            holder.likes.setText(String.valueOf(l.get(i).likes));
            holder.dislikes.setText(String.valueOf(l.get(i).dislikes));


            return view;
        }
    }

}
