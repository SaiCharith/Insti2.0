package iitbombay.code_catalyst.com.insti20;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by atharvn on 16/10/17.
 */

public class CustomAdapter3 extends ArrayAdapter<FeedbackInput> {
    private Activity context;
    private ArrayList<FeedbackInput> feedbackList;

    @Override
    public int getCount() {
        return feedbackList.size();
    }

    @Nullable
    @Override
    public FeedbackInput getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public CustomAdapter3(Activity context, ArrayList<FeedbackInput>feedbackList){
        super(context, R.layout.view_feedback);
        this.context = context;
        this.feedbackList = feedbackList;
    }

    @NonNull
    @Override
    public View getView (int position , View convertView, ViewGroup parent){
        viewHolder viewholder;

        if (convertView == null) {
            LayoutInflater inflater = context.getLayoutInflater();
            convertView = inflater.inflate(R.layout.view_feedback, null);
            viewholder = new viewHolder(convertView);
            convertView.setTag(viewholder);
        }

        else {
            viewholder = (viewHolder) convertView.getTag();
        }
//        TextView name = (TextView) listViewItem.findViewById(R.id.name);
//        TextView description = (TextView) listViewItem.findViewById(R.id.description);
//        TextView feedback = (TextView) listViewItem.findViewById(R.id.feedback);

        FeedbackInput feedbackInput = feedbackList.get(position);

        viewholder.name.setText(feedbackInput.getName());
        viewholder.description.setText(feedbackInput.getDescription());
        Glide.with(context).load(feedbackList.get(position).getID()).into(viewholder.img);
        viewholder.feedback.setText(feedbackInput.getFeedback());

        return convertView;
    }

    private static class viewHolder{
        TextView name;
        TextView description;
        ImageView img;
        TextView feedback;


        viewHolder(View v){
            name=v.findViewById(R.id.name);
            description=v.findViewById(R.id.description);
            ImageView img = (ImageView) v.findViewById((R.id.imgview));
            feedback = v.findViewById(R.id.feedback);
        }
    }
}
