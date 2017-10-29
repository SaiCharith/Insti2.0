package iitbombay.code_catalyst.com.insti20;

/**
 * Created by Code Catalyst on 2/10/17.
 */


import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.ArrayList;



/**
 * CustomAdapter converts list of <objects> to a listview with fields as in each_hostel_display.xml
 * @author Code-Catalyst
 *
 */
class  CustomAdapter extends ArrayAdapter<objects> {
    /**
     * Arraylist l is used to hold instances of the type 'objects'
     * Contents of this list are displayed in the listview.
     */
    private ArrayList<objects> l;


    /**
     * Default Constructor
     * @param context gets the context
     * @param resource gets the resource
     */
    public CustomAdapter(@NonNull Context context, @LayoutRes int resource) {
        super(context, resource);
    }

    /**
     * Custom constructor
     * @param data gets the list to be displayed
     * @param context gets the current context
     */

    public CustomAdapter(ArrayList<objects> data, Context context) {
        super(context, R.layout.each_hostel_display, data);
        this.l = data;
       // this.mContext=context;

    }

    /**
     * returns the size of the listview.
     * @return int
     */

    @Override
    public int getCount() {
        return l.size();
    }


    /**
     * This method fills the elements of listview with the corresponding objects in the list l.
     * viewHolder is an instance of this element .
     * viewHolder is used here for optimization using geTag and seTag methods.
     *
     * @param i current position
     * @param view current view to be added to the listview
     * @param viewGroup not very useful here
     * @return View
     */
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        viewHolder holder;       //to set data to be displayed.
        if(view==null){          //inflating view only if it is null
            LayoutInflater inflater = LayoutInflater.from(getContext());
            view = inflater.inflate(R.layout.each_hostel_display,null);
            holder=new viewHolder(view);
            view.setTag(holder);     //setting tag to viewHolder so that it is not required to reinitialize later

        }

        else{
            holder= (viewHolder) view.getTag();   //getting reference to viewHolder
        }
        //setting values to be displayed in the listview.
        holder.imageview.setImageResource(l.get(i).image_id);
        holder.hostel_name.setText(l.get(i).Hostel_name);
        holder.rating_bar.setRating(l.get(i).rating);
        holder.likes.setText(String.valueOf(l.get(i).likes));
        holder.dislikes.setText(String.valueOf(l.get(i).dislikes));

        return view;
    }
}

/**
 * This class is an instance of elements of listview, used for optimization.
 */

class viewHolder{
    /**
     * to hold the image.
     */
    ImageView imageview;
    /**
     * to hold the hostel name.
     */
    TextView hostel_name;
    /**
     * to hold the rating_bar.
     */
    RatingBar rating_bar;
    /**
     * to hold the likes.
     */
    TextView likes;
    /**
     * to hold the dislikes.
     */
    TextView dislikes;

    /**
     * This sets the id's of variables using the method findViewById(..).
     * @param v to set the id's of various variables of viewHolder
     */
    viewHolder(View v){
        imageview = v.findViewById(R.id.imageView);
        hostel_name=v.findViewById(R.id.Hostel_name);
        rating_bar=v.findViewById(R.id.ratingBar);
        likes=v.findViewById(R.id.likes);
        dislikes=v.findViewById(R.id.dislikes);
    }
}
