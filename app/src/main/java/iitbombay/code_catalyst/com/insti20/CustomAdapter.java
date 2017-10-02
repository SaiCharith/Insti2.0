package iitbombay.code_catalyst.com.insti20;

/**
 * Created by charith on 2/10/17.
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
 * Created by charith on 2/10/17.
 */
class  CustomAdapter extends ArrayAdapter<objects> {

    private ArrayList<objects> l;
    //Context mContext;

    public CustomAdapter(@NonNull Context context, @LayoutRes int resource) {
        super(context, resource);
    }

    public CustomAdapter(ArrayList<objects> data, Context context) {
        super(context, R.layout.each_hostel_display, data);
        this.l = data;
       // this.mContext=context;

    }

    static class viewHolder{
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

    // Activity context;

    //CustomAdapter(Activity c){
    //      context=c;
    // }

    @Override
    public int getCount() {
        return 16;
    }

    @Override
    public objects getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {


        viewHolder holder;


        if(view==null){
            //               LayoutInflater inflater=context.getLayoutInflater();
            //               view=inflater.inflate(R.layout.customlayout,null);
//                view=.inflate(R.layout.customlayout,null);

            //   view=mContext.getLayoutInflater().inflate(R.layout.customlayout,null);
            LayoutInflater inflater = LayoutInflater.from(getContext());
            view = inflater.inflate(R.layout.each_hostel_display,null);
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
