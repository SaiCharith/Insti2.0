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

import java.net.URL;
import java.util.ArrayList;

//import com.squareup.picasso.Picasso;

/**
 * Created by Code Catalyst on 16/10/17.
 */

public class CustomAdapter3 extends ArrayAdapter<FeedbackInput> {
    private Activity context;
    private ArrayList<FeedbackInput> feedbackList;
    URL url;

    @Override
    public int getCount() {
        return feedbackList.size();
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

        viewholder.name.setText(feedbackInput.Name);
        viewholder.description.setText(feedbackInput.Description);
//        url = new URL("http://image10.bizrate-images.com/resize?sq=60&uid=2216744464");
//        Bitmap bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
//        img.setImageBitmap(bmp);
        if(!(feedbackInput.ID == "")){
        Glide.with(context).load(feedbackInput.ID).into(viewholder.img);}
        //Glide.with(context).load("https://firebasestorage.googleapis.com/v0/b/code-catalyst-asc.appspot.com/o/Hostel14%2F-Kwkbfq9THzL-QHTeud1?alt=media&token=5b767e93-3fe3-463c-8a41-55edd5e835b8").into(viewholder.img);
        //Picasso.with(context).load("http://i.imgur.com/DvpvklR.png").into(imageView);
       // Picasso.with(context).load("http://i.imgur.com/DvpvklR.png").into(viewholder.img);
        viewholder.feedback.setText(feedbackInput.Feedback);

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
            img =v.findViewById((R.id.imgview));
            feedback = v.findViewById(R.id.feedback);
        }
    }
}
