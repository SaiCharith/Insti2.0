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

//    public String getID() {
//        return ID;
//    }
//
//    public void setID(String id) {
//        this.ID = id;
//    }
//
//    public String getName() {
//        return Name;
//    }
//
//    public void setName(String name) {
//        Name = name;
//    }
//
//    public String getRoll_Number() {
//        return Roll_Number;
//    }
//
//    public void setRoll_Number(String roll_Number) {
//        Roll_Number = roll_Number;
//    }
//
//    public String getDescription() {
//        return Description;
//    }
//
//    public void setDescription(String description) {
//        Description = description;
//    }
//
//    public String getFeedback() {
//        return Feedback;
//    }
//
//    public void setFeedback(String feedback) {
//        Feedback = feedback;
//    }
};