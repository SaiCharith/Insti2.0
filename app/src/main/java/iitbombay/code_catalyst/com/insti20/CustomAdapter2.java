package iitbombay.code_catalyst.com.insti20;

/**
 * Created by charith on 8/10/17.
 */


import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;



/**
 * Created by charith on 2/10/17.
 */
class CustomAdapter2 extends ArrayAdapter<String> {

//    private ArrayList<String> l;
    private String[] day_parts={"Breakfast","lunch","tiffin","dinner"};
    private String H_name;
    private String Day;
    DatabaseReference Day_menu;

    //Context mContext;

    public CustomAdapter2(@NonNull Context context, @LayoutRes int resource) {
        super(context, resource);
    }

    public CustomAdapter2(Context context,String h_name,String day) {
        super(context, R.layout.activity_display_menu);
       // this.l = data;
        this.H_name=h_name;
        this.Day=day;
        // this.mContext=context;

        DatabaseReference ref = FirebaseDatabase.getInstance().getReferenceFromUrl("https://code-catalyst-asc.firebaseio.com/Mess_Repo");
        DatabaseReference hostel_ref=ref.child(h_name);
        DatabaseReference Mess_menu_ref=hostel_ref.child("Mess_Menu");
        DatabaseReference Current_menu=Mess_menu_ref.child("Current");
        Day_menu=Current_menu.child(day);

    }

    static class viewHolder{

        TextView dish;
        Button day_part;


        viewHolder(View v){
            dish=v.findViewById(R.id.menu);
            day_part=v.findViewById(R.id.day_part);
        }
    }

    @Override
    public int getCount() {
        return 4;
    }

    @Override
    public String getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {


        final viewHolder holder;


        if(view==null){
            LayoutInflater inflater = LayoutInflater.from(getContext());
            view = inflater.inflate(R.layout.fill_day_menu,null);
            holder=new viewHolder(view);
            view.setTag(holder);

        }

        else{
            holder= (viewHolder) view.getTag();
        }



        DatabaseReference Days_menu_each_part=Day_menu.child(day_parts[i]);
        Days_menu_each_part.addValueEventListener(new ValueEventListener(){
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String s =  dataSnapshot.getValue(String.class);
                holder.dish.setText(s);
                notifyDataSetChanged();

            }


            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
       // holder.dish.setText(l.get(i));
        holder.day_part.setText(day_parts[i]);


        return view;
    }
}
