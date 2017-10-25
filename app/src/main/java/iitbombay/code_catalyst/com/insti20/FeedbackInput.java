package iitbombay.code_catalyst.com.insti20;

/**
 * Created by atharvn on 16/10/17.
 */

public class FeedbackInput {

    public String ID;
    public String Name;
    public String Roll_Number;
    public String Description;
    public String Feedback;

    public FeedbackInput(){

    }

    public FeedbackInput(String id, String name, String roll, String description, String feed) {
        this.ID=id;
        this.Name = name;
        this.Roll_Number = roll;
        this.Description = description;
        this.Feedback = feed;
    }

};