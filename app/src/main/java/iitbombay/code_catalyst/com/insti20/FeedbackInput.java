package iitbombay.code_catalyst.com.insti20;

/**
 * Created by atharvn on 16/10/17.
 */

/**
 * This is the only class defined regarding feedback which is used to store student's feedback in database
 * and also extract them later for viewing
 */

public class FeedbackInput {

    /**
     * to hold the URL of uploaded image.
     */
    public String ID;

    /**
     * to hold the name of student.
     */
    public String Name;

    /**
     * to hold the roll number of student.
     */
    public String Roll_Number;

    /**
     * to hold short description for feedback.
     */
    public String Description;

    /**
     * to hold the feedback.
     */
    public String Feedback;

    /**
     * non-parameterized constructor for variables of FeedbackInput.
     */
    public FeedbackInput(){

    }

    /**
     * Constructor which initializes variables of FeedbackInput.
     * @param id current url of image if uploaded.
     * @param name current name of student.
     * @param roll current roll number of student.
     * @param description current description for feedback.
     * @param feed current feedback.
     */
    public FeedbackInput(String id, String name, String roll, String description, String feed) {
        this.ID=id;
        this.Name = name;
        this.Roll_Number = roll;
        this.Description = description;
        this.Feedback = feed;
    }

};