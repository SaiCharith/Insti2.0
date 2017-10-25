package iitbombay.code_catalyst.com.insti20;

/**
 * This class is used to hold the fields required to show up in SearchResultActivity
 * @author Code-Catalyst
 *
 */

public class searchobjects {
    /**
     * represents hostel_no
     */
    private int h_no;
    /**
     * represents food item
     */
    private String item;
    /**
     * represents hostel_name
     */
    private String hostel_name;
    /**
     * represents day of the week
     */
    private String day;
    /**
     * represents part of the day.
     */
    private String day_part;

    /**
     * Constructor to initialize all parameters of this class
     * @param h_no
     * @param item
     * @param hostel_name
     * @param day
     * @param day_part
     */
    public searchobjects(int h_no,String item,String hostel_name,String day,String day_part){
        this.h_no=h_no;
        this.item=item;
        this.hostel_name=hostel_name;
        this.day=day;
        this.day_part=day_part;
    }
    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public String getHostel_name() {
        return hostel_name;
    }

    public void setHostel_name(String hostel_name) {
        this.hostel_name = hostel_name;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getDay_part() {
        return day_part;
    }

    public void setDay_part(String day_part) {
        this.day_part = day_part;
    }

    public int getH_no() {
        return h_no;
    }

    public void setH_no(int h_no) {
        this.h_no = h_no;
    }
}
