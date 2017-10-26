package iitbombay.code_catalyst.com.insti20;

import android.app.Activity;
import android.app.ProgressDialog;
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
/**
 *
 * CustomAdapter3 converts the obtained feedbacklist to listview for viewing feedbacks of all students.
 * @author Code-Catalyst
 */

public class CustomAdapter3 extends ArrayAdapter<FeedbackInput> {

    /**
     * Stores the id of current Activity/Context
     */
    private Activity context;
    /**
     * List to be displayed in the listview
     */
    private ArrayList<FeedbackInput> feedbackList;

    /**
     * to get the number of elements to be displayed on the listview.
     * @return (int) size of listview
     */
    @Override
    public int getCount() {
        return feedbackList.size();
    }
//    private ProgressDialog progressDialog;

    /**
     *
     * @param context to update context
     * @param feedbackList to get the list of feedbacks.
     */
    public CustomAdapter3(Activity context, ArrayList<FeedbackInput>feedbackList){
        super(context, R.layout.view_feedback);
        this.context = context;
        this.feedbackList = feedbackList;
    }

    /**
     * Most important method in to display the listview.
     * Optimizing the number of inflation calls using setTag and getTag methods.
     * @param position index
     * @param convertView veiw of list corresponding to index position
     * @param parent not very important here
     * @return view
     */
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

        FeedbackInput feedbackInput = feedbackList.get(position);

        viewholder.name.setText(feedbackInput.Name);
        viewholder.description.setText(feedbackInput.Description);
        Glide.with(context).load(feedbackInput.ID).into(viewholder.img);
        viewholder.feedback.setText(feedbackInput.Feedback);

        return convertView;
    }

    /**
     * Sub-class of CustomAdapter3.
     * Used to hold the view of each instance of listview.
     */
    private static class viewHolder{

        /**
         * to hold the name of student.
         */
        TextView name;

        /**
         * to hold the description of feedback.
         */
        TextView description;

        /**
         * to hold the image uploaded along with feedback.
         */
        ImageView img;

        /**
         * to hold the feedback.
         */
        TextView feedback;

        /**
         * Constructor which initializes variables of viewHolder.
         * @param v current view
         */
        viewHolder(View v){
            name=v.findViewById(R.id.name);
            description=v.findViewById(R.id.description);
            img =v.findViewById(R.id.imgview);
            feedback = v.findViewById(R.id.feedback);
        }
    }
}
