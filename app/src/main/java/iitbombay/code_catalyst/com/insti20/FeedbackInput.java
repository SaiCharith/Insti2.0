package iitbombay.code_catalyst.com.insti20;

/**
 * Created by atharvn on 16/10/17.
 */


class FeedbackInput {

    private String Image_ID;
    private String Name;
    private String Roll_Number;
    private String Description;
    private String Feedback;

    public FeedbackInput(){

    }

    public FeedbackInput(String id, String name, String roll, String description, String feed) {
        this.Image_ID=id;
        this.Name = name;
        this.Roll_Number = roll;
        this.Description = description;
        this.Feedback = feed;
    }

    public String getID() {
        return Image_ID;
    }

    public void setID(String ID) {
        this.Image_ID = ID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getRoll_Number() {
        return Roll_Number;
    }

    public void setRoll_Number(String roll_Number) {
        Roll_Number = roll_Number;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getFeedback() {
        return Feedback;
    }

    public void setFeedback(String feedback) {
        Feedback = feedback;
    }
};